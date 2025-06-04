package com.example.ecommerce.services;

import com.example.ecommerce.entities.Role;
import com.example.ecommerce.entities.User;
import com.example.ecommerce.exception.UserNotFoundException;
import com.example.ecommerce.exception.UsernameAlreadyExistException;
import com.example.ecommerce.modals.UserRequest;
import com.example.ecommerce.repository.UserRepo;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo repository, @Lazy PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User createUserByAdmin(UserRequest userRequest) {
        Optional<User> optionalUser = repository.findByUsername(userRequest.getUsername());
        if (optionalUser.isPresent()) {
            throw new UsernameAlreadyExistException("User already exists: " + userRequest.getUsername());
        }
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(userRequest.getRole());
        return repository.save(user);
    }

    public List<User> getAllUsersByAdmin() {
        return repository.findAll();
    }

    public User getUserById(Long id) {  // Changed Integer to Long
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
    }

    public User softDeleteByAdmin(Long id) {  // Changed Integer to Long
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
        user.setDeleted(true);
        return repository.save(user);
    }

    public User updateUserRoleByAdmin(Long id, Role newRole) {  // Changed Integer to Long
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setRole(newRole);
            return repository.save(user);
        } else {
            throw new UserNotFoundException("User with ID " + id + " not found");
        }
    }
}
