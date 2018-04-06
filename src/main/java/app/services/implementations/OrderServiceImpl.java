package app.services.implementations;

import app.entities.Order;
import app.entities.OrderItem;
import app.repositories.OrderItemRepository;
import app.repositories.OrderRepository;
import app.services.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Long getOrderItemsCountByUserId(Long userId) {
        return orderItemRepository.getOrderItemsCountByUserIdWithStatusActive(userId);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.getOrderById(orderId);
    }

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
    public List<Order> getOrdersWithStatusCompleted() {
        return orderRepository.getOrdersWithStatusCompleted();
    }

    @Override
    public List<Order> getOrdersWithStatusSent() {
        return orderRepository.getOrdersWithStatusSent();
    }

    @Override
    public List<Order> getOrdersWithStatusSentAndCompletedById(Long userId) {
        return orderRepository.getOrdersWithStatusSentAndCompletedById(userId);
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void saveOrderItem(OrderItem orderItem, Long userId) {

        Order order = orderRepository.getActiveOrderByUserId(userId);

        OrderItem oldOrderItem = orderItemRepository.getOrderItemByOrderAndProductId(
                order.getId(),
                orderItem.getProduct().getId()
        );

        if(oldOrderItem != null) {
            oldOrderItem.setQuantity(oldOrderItem.getQuantity() + orderItem.getQuantity());
            orderItemRepository.save(oldOrderItem);
        }
        else {
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
        }
    }

    @Override
    public void setActiveOrderStatusToSent(Order order) {

        order.setStatus("sent");
        orderRepository.save(order);
    }

    @Override
    public void setSentOrderStatusToCompleted(Long orderId) {
        orderRepository.updateStatusToCompletedById(orderId);
    }

    @Override
    public void updateOrderItemByOrderItemIdAndQuantityAndOrderId(Long orderItemId, int quantity, Long orderId) {
        orderItemRepository.updateOrderItemByOrderItemIdAndQuantityAndOrderId(orderItemId, quantity, orderId);
    }

    @Override
    public void updateOrderStatusToSentById(Long orderId) {
        orderRepository.updateStatusToSentById(orderId);
    }

    @Override
    public void deleteOrderItemByIdAndOrderId(Long orderItemId, Long orderId) {
        orderItemRepository.deleteOrderItemByIdAndOrderId(orderItemId, orderId);
    }
}
