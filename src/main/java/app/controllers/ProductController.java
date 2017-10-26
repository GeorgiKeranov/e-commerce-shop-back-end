package app.controllers;

import app.entities.Category;
import app.entities.Product;
import app.models.Message;
import app.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    //TODO get products by category

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    private ResponseEntity<?> getAllProductsPage(
            @RequestParam(value = "page", defaultValue = "0") int page) {

        List<Product> products = productService.getAllProductsByPage(page);

        if(products == null) {
            return new ResponseEntity<Message>(
                    new Message(true, "Invalid page requested"),
                    HttpStatus.BAD_REQUEST
            );
        }

        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @GetMapping("/categories")
    private ResponseEntity<List<Category>> getAllCategories() {

        List<Category> allCategories = productService.getAllCategories();

        return new ResponseEntity<List<Category>>(allCategories, HttpStatus.OK);
    }
}
