package com.library.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.library.entity.User;
import com.library.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        user.setRole("USER");

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Integer userId) {

        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Integer userId) {

        userRepository.deleteById(userId);
    }
}