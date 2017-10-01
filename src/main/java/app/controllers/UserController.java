package app.controllers;

import app.entities.User;
import app.models.Message;
import app.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        String errorMsg = userService.registerUser(user);

        if(errorMsg != null)
            return new ResponseEntity<Message>(new Message(true, errorMsg), HttpStatus.CONFLICT);

        return new ResponseEntity(HttpStatus.OK);
    }


}
