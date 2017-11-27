package app.controllers;

import app.entities.Category;
import app.entities.Product;
import app.models.Count;
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
    private ResponseEntity<?> getProducts(
            @RequestParam(value = "searchWord", required = false) String searchWord,
            @RequestParam(value = "categoryId", defaultValue = "-1") Long categoryId,
            @RequestParam(value = "page", defaultValue = "0") int page) {

        List<Product> products;


        if(searchWord == null) {
            // If there is no category chosen return all products.
            if (categoryId == -1)
                products = productService.getAllProductsByPage(page);
            else
                products = productService.getProductsByCategoryIdAndPage(categoryId, page);
        }

        else {
            if (categoryId == -1)
                products = productService.getProductsByWordAndPage(searchWord, page);
            else
                products = productService.getProductsByCategoryIdAndWordAndPage(categoryId, searchWord, page);
        }

        if(products == null) {
            return new ResponseEntity<Message>(
                    new Message(true, "No products found"),
                    HttpStatus.BAD_REQUEST
            );
        }

        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    private ResponseEntity<?> getProductById(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        if(product != null)
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        else
            return new ResponseEntity<Message>(new Message(true), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/products/count")
    private ResponseEntity<Count> getProducts(
            @RequestParam(value = "searchWord", defaultValue = "") String searchWord,
            @RequestParam(value = "categoryId", defaultValue = "-1") Long categoryId
    ) {

        Count count = new Count();

        count.setCount(0L);

        // if there are no searchWord and categoryId returning
        // count of pages for all products
        if(searchWord.equals("") && categoryId == -1)
            count.setCount(productService.getCountOfPagesForAllProducts());

        // If only categoryId is present returning count of pages
        // only for that category.
        if(searchWord.equals("") && categoryId >= 0)
            count.setCount(productService.getCountOfPagesForProductsByCategoryId(categoryId));

        // If only searchWord is present returning
        // count of pages with products containing that word.
        if(!searchWord.equals("") && categoryId == -1)
            count.setCount(productService.getCountOfPagesForProductsByWord(searchWord));

        if(!searchWord.equals("") && categoryId >= 0)
            count.setCount(productService.getCountOfPagesForProductsByCategoryIdAndWord(categoryId, searchWord));


        return new ResponseEntity<Count>(count, HttpStatus.OK);
    }

    @GetMapping("/categories")
    private ResponseEntity<List<Category>> getAllCategories() {

        List<Category> allCategories = productService.getAllCategories();

        return new ResponseEntity<List<Category>>(allCategories, HttpStatus.OK);
    }

    @GetMapping("/categories/{id}")
    private ResponseEntity<?> getCategoryById(@PathVariable("id") Long id) {

        Category category = productService.getCategoryById(id);
        return new ResponseEntity<Category>(category, HttpStatus.OK);
    }
}
