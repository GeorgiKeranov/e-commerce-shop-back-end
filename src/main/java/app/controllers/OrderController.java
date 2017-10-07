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
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/items")
    private ResponseEntity<?> addOrderItemToOrder(@RequestBody OrderItem orderItem,
                                                  Principal principal) {

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

    @GetMapping("/items")
    private ResponseEntity<List<OrderItem>> getOrderItems(Principal principal) {

        Long userId = userService.getUserIdByUsername(principal.getName());
        Long orderId = orderService.getActiveOrderIdByUserId(userId);

        List<OrderItem> orderItems = orderService.getOrderItemsByOrderId(orderId);

        return new ResponseEntity<List<OrderItem>>(orderItems, HttpStatus.OK);
    }

    @DeleteMapping("/items/{id}")
    private ResponseEntity<Message> deleteOrderItem(@PathVariable("id") Long orderItemId) {

        orderService.deleteOrderItem(orderItemId);

        return new ResponseEntity<Message>(
                new Message(false, "successful"),
                HttpStatus.OK
        );
    }

    @PutMapping("/items/{id}")
    private ResponseEntity<Message> updateOrderItem(@PathVariable("id") Long orderItemId,
                                                    @RequestBody Map<String, Integer> body) {

        orderService.updateOrderItem(orderItemId, body.get("quantity"));

        return new ResponseEntity<Message>(
                new Message(false, "successful"),
                HttpStatus.OK
        );
    }

    @PostMapping("/buy")
    private ResponseEntity<Message> buyOrder(Principal principal) {

        Long userId = userService.getUserIdByUsername(principal.getName());
        Order order = orderService.getActiveOrderByUserId(userId);

        if(order.getOrderItems().size() == 0) {
            return new ResponseEntity<Message>(
                    new Message(true, "Your order does not contain products")
                    , HttpStatus.BAD_REQUEST
            );
        }

        orderService.setActiveOrderStatusToSent(order);

        Order newActiveOrder = new Order();

        User user = new User();
        user.setId(userId);
        newActiveOrder.setUser(user);

        orderService.saveOrder(newActiveOrder);

        return new ResponseEntity<Message>(new Message(false, "successful"), HttpStatus.OK);
    }
}
