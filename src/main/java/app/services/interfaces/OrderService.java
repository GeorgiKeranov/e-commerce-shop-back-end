package app.services.interfaces;

import app.entities.Order;
import app.entities.OrderItem;

import java.util.List;

public interface OrderService {

    Long getOrderItemsCountByUserId(Long userId);

    Order getActiveOrderByUserId(Long userId);
    Long getActiveOrderIdByUserId(Long userId);
    List<OrderItem> getOrderItemsByOrderId(Long orderId);
    List<Order> getOrdersWithStatusSent(int page);
    List<Order> getOrdersWithStatusSentAndCompletedById(Long userId, int page);

    void saveOrder(Order order);
    void saveOrderItem(OrderItem orderItem, Long userId);

    void setActiveOrderStatusToSent(Order order);
    void setSentOrderStatusToCompleted(Long orderId);
    void updateOrderItemByOrderItemIdAndQuantityAndOrderId(Long orderItemId, int quantity, Long orderId);

    void deleteOrderItemByIdAndOrderId(Long orderItemId, Long orderId);
}
