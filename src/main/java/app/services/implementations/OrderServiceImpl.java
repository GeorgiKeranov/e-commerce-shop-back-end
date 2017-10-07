package app.services.implementations;

import app.entities.Order;
import app.entities.OrderItem;
import app.entities.User;
import app.repositories.OrderItemRepository;
import app.repositories.OrderRepository;
import app.services.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Order getActiveOrderByUserId(Long userId) {
        return orderRepository.getActiveOrderByUserId(userId);
    }

    @Override
    public Long getActiveOrderIdByUserId(Long userId) {
        return orderRepository.getActiveOrderIdByUserId(userId);
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.getOrderItemsByOrderId(orderId);
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void saveOrderItem(OrderItem orderItem, Long userId) {

        Order order = orderRepository.getActiveOrderByUserId(userId);

        orderItem.setOrder(order);

        orderItemRepository.save(orderItem);
    }

    @Override
    public void setActiveOrderStatusToSent(Order order) {

        order.setStatus("sent");
        orderRepository.save(order);
    }

    @Override
    public void updateOrderItem(Long orderItemId, int quantity) {
        orderItemRepository.updateOrderItemByIdAndQuantity(orderItemId, quantity);
    }

    @Override
    public void deleteOrderItem(Long orderItemId) {
        orderItemRepository.delete(orderItemId);
    }
}
