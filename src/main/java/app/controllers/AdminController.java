package app.controllers;

import app.entities.Category;
import app.entities.Order;
import app.entities.Product;
import app.models.Message;
import app.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;


    // TODO Add List<MultipartFile> images !!
    @PostMapping("/products/create")
    private ResponseEntity<Message> createNewProduct(Product product,
                                                     @RequestParam("productCategories") List<Long> categories,
                                                     @RequestParam(value = "image", required = false) MultipartFile imageFile) {

        // Checking if the required fields aren't empty.
        Message errorMessage = product.validateData();
        if(errorMessage.isError())
            return new ResponseEntity<Message>(errorMessage,HttpStatus.BAD_REQUEST);

        // Adding all id's to product categories.
        for(Long categoryId: categories) {
            product.addCategory(new Category(categoryId));
        }

        // Saving the product and checking if there is error with saving.
        String errorMsg = productService.save(product);
        if(errorMsg != null)
            return new ResponseEntity<Message>(new Message(true, errorMsg),HttpStatus.BAD_REQUEST);

        // Checking if there is imageFile.
        if(imageFile != null && !imageFile.isEmpty()) {

            // Saving imageFile to local storage and returning the name of the image.
            String imageName = storageService.saveImage(imageFile);

            // Image is not saved in local storage.
            if(imageName == null)
                return new ResponseEntity<Message>(
                        new Message(true, "Image maximum size is 10MB"),
                        HttpStatus.BAD_REQUEST
                );

            // Image is saved in local storage.
            else {
                // Saving the image in the database too.
                imageService.saveImageWithNameAndProductId(imageName, product.getId());

                // Updating the product field mainImageName with the saved image above.
                productService.setMainImageNameByProductId(
                        imageName,
                        product.getId()
                );
            }

        }

        return new ResponseEntity<Message>(new Message(false), HttpStatus.OK);
    }

    @PostMapping("/products/update")
    private ResponseEntity<Message> updateExistingProduct(Product product,
                                                           @RequestParam(value = "productCategories", required = false) List<Long> categories,
                                                           @RequestParam(value = "image", required = false) MultipartFile imageFile) {

        // Checking if the required fields aren't empty.
        Message errorMessage = product.validateData();
        if(errorMessage.isError())
            return new ResponseEntity<Message>(errorMessage,HttpStatus.BAD_REQUEST);

        // Adding all id's to product categories.
        for(Long categoryId: categories) {
            product.addCategory(new Category(categoryId));
        }

        // Checking if there is imageFile.
        if(imageFile != null && !imageFile.isEmpty()) {

            // Saving imageFile to local storage and returning the name of the image.
            String imageName = storageService.saveImage(imageFile);

            // Image is not saved in local storage.
            if(imageName == null)
                return new ResponseEntity<Message>(
                        new Message(true, "Image maximum size is 10MB"),
                        HttpStatus.BAD_REQUEST
                );

            // Image is saved in local storage.
            else {
                // Check if there is old image saved to that product.
                Product oldProduct = productService.getProductById(product.getId());
                if(oldProduct.getMainImageName() != null) {
                    // Delete the image from local storage.
                    storageService.deleteImage(oldProduct.getMainImageName());
                    imageService.deleteImageByImageName(oldProduct.getMainImageName());
                }

                // Saving the new image in the database.
                imageService.saveImageWithNameAndProductId(imageName, product.getId());

                // Updating the product field mainImageName with the saved image above.
                product.setMainImageName(imageName);
            }

        }

        productService.update(product);

        return new ResponseEntity<Message>(new Message(false), HttpStatus.OK);
    }

    @PostMapping("/categories/create")
    private ResponseEntity<Message> createNewCategory(@RequestBody Category category) {
        // TODO
        productService.saveCategory(category);
        return new ResponseEntity<Message>(new Message(false), HttpStatus.OK);
    }

    @PostMapping("/categories/update")
    private ResponseEntity<Message> updateCategory(@RequestBody Category category) {
        // TODO
        productService.updateCategory(category);
        return new ResponseEntity<Message>(new Message(false), HttpStatus.OK);
    }

    @GetMapping("/orders")
    private ResponseEntity<?> getOrdersWithStatusSent(@RequestParam("page") int page) {

        List<Order> orders = orderService.getOrdersWithStatusSent(page);

        if(orders.size() == 0) {
            return new ResponseEntity<Message>(
                    new Message(false, "No orders found."),
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
    }

    @PostMapping("/orders/{id}/complete")
    private ResponseEntity<Message> completeOrderById(@PathVariable("id") Long orderId) {


        // TODO check if the order is with status sent
        orderService.setSentOrderStatusToCompleted(orderId);

        return new ResponseEntity<Message>(
                new Message(false, "successful"),
                HttpStatus.OK
        );
    }

}
