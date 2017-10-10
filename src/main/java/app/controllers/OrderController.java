package app.controllers;

import app.entities.Order;
import app.entities.OrderItem;
import app.entities.User;
import app.models.Message;
import app.services.interfaces.OrderService;
import app.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/order/items")
    private ResponseEntity<?> addOrderItemToOrder(@RequestBody OrderItem orderItem,
                                                  Principal principal) {

        // Validate requested OrderItem.
        if(orderItem.getProduct().getId() == null) {
            return new ResponseEntity<Message>(
                    new Message(true, "No id for product"),
                    HttpStatus.BAD_REQUEST
            );
        }

        Long userId = userService.getUserIdByUsername(principal.getName());

        orderService.saveOrderItem(orderItem, userId);

        return new ResponseEntity<Message>(
                new Message(false, "successful")
                , HttpStatus.OK
        );
    }

    @GetMapping("/order/items")
    private ResponseEntity<List<OrderItem>> getOrderItems(Principal principal) {

        Long userId = userService.getUserIdByUsername(principal.getName());
        Long orderId = orderService.getActiveOrderIdByUserId(userId);

        //TODO validate orderItems are not empty.
        List<OrderItem> orderItems = orderService.getOrderItemsByOrderId(orderId);

        return new ResponseEntity<List<OrderItem>>(orderItems, HttpStatus.OK);
    }

    @DeleteMapping("/order/items/{id}")
    private ResponseEntity<Message> deleteOrderItem(@PathVariable("id") Long orderItemId,
                                                    Principal principal) {

        Long userId = userService.getUserIdByUsername(principal.getName());

        // OrderId is used to check if the OrderItem is on the authenticated user Order.
        Long orderId = orderService.getActiveOrderIdByUserId(userId);


        orderService.deleteOrderItemByIdAndOrderId(orderItemId, orderId);

        return new ResponseEntity<Message>(
                new Message(false, "successful"),
                HttpStatus.OK
        );
    }

    @PutMapping("/order/items/{id}")
    private ResponseEntity<Message> updateOrderItem(@PathVariable("id") Long orderItemId,
                                                    @RequestBody Map<String, Integer> body,
                                                    Principal principal) {

        Long userId = userService.getUserIdByUsername(principal.getName());

        // OrderId is used to check if the OrderItem is on the authenticated user Order.
        Long orderId = orderService.getActiveOrderIdByUserId(userId);

        orderService.updateOrderItemByOrderItemIdAndQuantityAndOrderId(
                orderItemId, body.get("quantity"), orderId
        );

        return new ResponseEntity<Message>(
                new Message(false, "successful"),
                HttpStatus.OK
        );
    }

    @PostMapping("/order/buy")
    private ResponseEntity<Message> buyOrder(Principal principal) {

        Long userId = userService.getUserIdByUsername(principal.getName());
        Order order = orderService.getActiveOrderByUserId(userId);

        if(order.getOrderItems().size() == 0) {
            return new ResponseEntity<Message>(
                    new Message(true, "Your order does not contain products")
                    , HttpStatus.BAD_REQUEST
            );
        }

        // Setting status "active" to "sent" in the database.
        orderService.setActiveOrderStatusToSent(order);

        // Creating new order with "active" status for authenticated user.
        Order newActiveOrder = new Order();

        User user = new User();
        user.setId(userId);
        newActiveOrder.setUser(user);

        orderService.saveOrder(newActiveOrder);

        return new ResponseEntity<Message>(new Message(false, "successful"), HttpStatus.OK);
    }

    @GetMapping("/orders/history")
    private ResponseEntity<List<Order>> getUserOrdersHistory(@RequestParam("page") int page,
                                                             Principal principal) {

        Long userId = userService.getUserIdByUsername(principal.getName());

        List<Order> sentAndCompletedOrders =
                orderService.getOrdersWithStatusSentAndCompletedById(userId, page);

        return new ResponseEntity<List<Order>>(sentAndCompletedOrders, HttpStatus.OK);
    }
}