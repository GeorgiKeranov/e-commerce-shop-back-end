package app.controllers;

import app.entities.Product;
import app.models.Message;
import app.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    private ResponseEntity<Message> createNewProduct(@RequestBody Product product) {

        Message errorMessage = product.validateData();
        if(errorMessage.isError())
            return new ResponseEntity<Message>(errorMessage,HttpStatus.BAD_REQUEST);

        String errorMsg = productService.save(product);
        if(errorMsg != null)
            return new ResponseEntity<Message>(new Message(true, errorMsg),HttpStatus.BAD_REQUEST);

        return new ResponseEntity<Message>(new Message(false), HttpStatus.OK);
    }




}
