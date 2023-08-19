package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.User;
import org.springframework.stereotype.Service;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
    private  UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User saveOneUser(User newUser) {
        return userRepository.save(newUser);
    }
    public User getOneUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }
    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}