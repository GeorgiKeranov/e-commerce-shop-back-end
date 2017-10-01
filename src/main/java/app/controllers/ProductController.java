package app.controllers;

import app.entities.Product;
import app.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    // get product categories.
    // get products by category
    // test 1:1 urls with different RequestParams


    @Autowired
    private ProductService productService;

    @GetMapping()
    private List<Product> lastProducts(
            @RequestParam(value = "last", required = false) boolean lastProducts) {


        List<Product> products = productService.getLastProducts();

        int size = products.size();

        return products;

    }

}
