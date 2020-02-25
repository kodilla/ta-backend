package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.exceptions.UserNotFoundException;
import com.kodilla.ecommercee.domain.users.User;
import com.kodilla.ecommercee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User blockUser(Long userId) throws UserNotFoundException {
        User userToBlock = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        userToBlock.setBlocked(true);
        return userToBlock;
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
}
