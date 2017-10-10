package app.repositories;


import app.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT o FROM OrderItem AS o where o.order.id = ?1 and o.order.status = \'active\'")
    List<OrderItem> getOrderItemsByOrderId(Long orderId);

    @Transactional
    @Modifying
    @Query("UPDATE OrderItem o SET o.quantity = ?2 where o.id = ?1 and o.order.id = ?3")
    void updateOrderItemByOrderItemIdAndQuantityAndOrderId(Long orderItemId, int quantity, Long orderId);

    @Transactional
    @Modifying
    @Query("DELETE OrderItem o WHERE o.id = ?1 and o.order.id = ?2")
    void deleteOrderItemByIdAndOrderId(Long orderItemId, Long orderId);

}
