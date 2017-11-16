package app.services.interfaces;

import app.entities.User;
import org.springframework.stereotype.Service;

public interface UserService {

    // Returns null if the user is registered successful.
    // Returns String with message what is wrong with the register.
    String registerUser(User user);

    User getUserByUsername(String username);

    Long getUserIdByUsername(String username);
}
