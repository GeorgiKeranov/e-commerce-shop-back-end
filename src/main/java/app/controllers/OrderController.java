package app.controllers;

import app.entities.Order;
import app.entities.OrderItem;
import app.entities.User;
import app.models.Message;
import app.models.Count;
import app.services.interfaces.OrderService;
import app.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class OrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("/order/items/count")
    private ResponseEntity<Count> getOrderItemsCount(Principal principal) {
        Long userId = userService.getUserIdByUsername(principal.getName());
        Long itemCount = orderService.getOrderItemsCountByUserId(userId);

        Count count = new Count(itemCount);

        return new ResponseEntity<Count>(count, HttpStatus.OK);
    }

    @PostMapping("/order/items")
    private ResponseEntity<Message> addOrderItemToOrder(@RequestBody OrderItem orderItem,
                                                  Principal principal) {

        // Validate requested Product form the orderItem.
        if(orderItem.getProduct().getId() == null) {
            return new ResponseEntity<Message>(
                    new Message(true, "Product with that id is not existing"),
                    HttpStatus.BAD_REQUEST
            );
        }

        Long userId = userService.getUserIdByUsername(principal.getName());

        orderService.saveOrderItem(orderItem, userId);

        return new ResponseEntity<Message>(
                new Message(false, "Product was successful added to your shopping cart"),
                HttpStatus.OK
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
                                                    @RequestParam("quantity") int quantity,
                                                    Principal principal) {

        Long userId = userService.getUserIdByUsername(principal.getName());

        // OrderId is used to check if the OrderItem is on the authenticated user Order.
        Long orderId = orderService.getActiveOrderIdByUserId(userId);

        orderService.updateOrderItemByOrderItemIdAndQuantityAndOrderId(
                orderItemId, quantity, orderId
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