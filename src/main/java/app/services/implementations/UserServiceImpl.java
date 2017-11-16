package app.services.implementations;

import app.entities.Order;
import app.entities.Role;
import app.entities.User;
import app.repositories.OrderRepository;
import app.repositories.RoleRepository;
import app.repositories.UserRepository;
import app.services.interfaces.OrderService;
import app.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public String registerUser(User user) {

        // Check if username or email already exists in the database.
        User userWithThatUsername = userRepository.findByUsername(user.getUsername());
        if(userWithThatUsername != null)
            return "User with that username already exists.";

        User userWithThatEmail = userRepository.findByEmail(user.getEmail());
        if(userWithThatEmail != null)
            return "User with that email already exists.";

        // Encoding user's password.
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        user.setPassword(bCrypt.encode(user.getPassword()));

        Role userRole = roleRepository.findOneByName("ROLE_USER");

        user.addRole(userRole);

        userRepository.save(user);

        // Creating default Order for that User.
        Order order = new Order();
        order.setUser(user);
        orderService.saveOrder(order);

        return null;
    }

    @Override
    public User getUserByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    @Override
    public Long getUserIdByUsername(String username) {

        return userRepository.getUserIdByUsername(username);
    }

}
