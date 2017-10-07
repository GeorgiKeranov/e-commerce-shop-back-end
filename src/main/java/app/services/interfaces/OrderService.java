package app.services.interfaces;

import app.entities.Order;
import app.entities.OrderItem;
import app.entities.User;

import java.util.List;

public interface OrderService {

    Order getActiveOrderByUserId(Long userId);
    Long getActiveOrderIdByUserId(Long userId);
    List<OrderItem> getOrderItemsByOrderId(Long orderId);

    void saveOrder(Order order);
    void saveOrderItem(OrderItem orderItem, Long userId);

    void setActiveOrderStatusToSent(Order order);
    void updateOrderItem(Long orderItemId, int quantity);

    void deleteOrderItem(Long orderItemId);
}
