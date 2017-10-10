package app.repositories;


import app.entities.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o.id FROM app.entities.Order o WHERE o.user.id = ?1 and o.status = \'active\'")
    Long getActiveOrderIdByUserId(Long userId);

    @Query("SELECT o FROM app.entities.Order o WHERE o.user.id = ?1 and o.status = \'active\'")
    Order getActiveOrderByUserId(Long userId);

    @Query("SELECT o FROM app.entities.Order o WHERE o.status = \'sent\'")
    List<Order> getOrdersWithStatusSent(Pageable pageable);

    @Query("SELECT o FROM app.entities.Order o WHERE o.user.id = ?1 and o.status != \'active\'")
    List<Order> getOrdersWithStatusSentAndCompletedById(Long id, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE app.entities.Order o SET o.status = \'completed\' " +
            "WHERE o.id = ?1 and o.status = \'sent\'")
    void updateStatusToCompletedById(Long id);
}
