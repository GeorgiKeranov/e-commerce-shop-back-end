package app.controllers;

import app.entities.Product;
import app.models.Message;
import app.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    // get product categories.
    // get products by category


    @Autowired
    private ProductService productService;

    @GetMapping
    private ResponseEntity<?> getAllProductsPage(
            @RequestParam(value = "page") int page) {

        // page = page - 1 so our page starts from 1 in the front end.
        page--;

        List<Product> products = productService.getAllProductsByPage(page);

        if(products == null) {
            return new ResponseEntity<Message>(
                    new Message(true, "Invalid page requested"),
                    HttpStatus.BAD_REQUEST
            );
        }

        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);

    }
}
