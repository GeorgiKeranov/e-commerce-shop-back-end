package app.services.implementations;

import app.entities.Role;
import app.entities.User;
import app.repositories.RoleRepository;
import app.repositories.UserRepository;
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
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(username == null || username.equals("")) throw new UsernameNotFoundException("Invalid Username");

        User user = userRepository.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("Invalid Username");
        }

        else {
            Set<GrantedAuthority> authorities = user.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toSet());

            return new org.springframework.security.core.userdetails
                    .User(user.getUsername(), user.getPassword(), authorities);
        }
    }


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

        return null;
    }





}
