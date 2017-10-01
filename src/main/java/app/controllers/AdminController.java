package app.controllers;

import app.entities.Product;
import app.models.Message;
import app.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    // Add products url.
    @PostMapping("/products")
    private Message createNewProduct(@RequestBody Product product) {

        productService.save(product);

        return new Message(false, "Product successful saved");
    }




}
