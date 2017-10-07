package app.repositories;


import app.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o.id FROM app.entities.Order o WHERE o.user.id = ?1 and o.status = \'active\'")
    Long getActiveOrderIdByUserId(Long userId);

    @Query("SELECT o FROM app.entities.Order o WHERE o.user.id = ?1 and o.status = \'active\'")
    Order getActiveOrderByUserId(Long userId);
}
