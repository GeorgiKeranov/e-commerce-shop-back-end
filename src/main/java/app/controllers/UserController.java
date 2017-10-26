package app.controllers;

import app.entities.User;
import app.models.Message;
import app.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<Message> register(@RequestBody User user) {

        // Validate request data.
        Message errorMessage = user.validateData();
        if(errorMessage.isError())
            return new ResponseEntity<Message>(errorMessage, HttpStatus.BAD_REQUEST);

        String errorMsg = userService.registerUser(user);

        if(errorMsg != null)
            return new ResponseEntity<Message>(new Message(true, errorMsg), HttpStatus.BAD_REQUEST);

        Message successful = new Message(false, "You have been registered successful");
        return new ResponseEntity<Message>(successful, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAuthenticatedUserData(Principal principal) {

            return new ResponseEntity<User>(
                    userService.getUserByUsername(principal.getName()),
                    HttpStatus.OK);
    }
}
