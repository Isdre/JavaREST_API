package com.example.springapi.service;

import com.example.springapi.api.model.User;
import com.example.springapi.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(int id) {
        Optional optional = Optional.of(userRepository.findAll().stream().filter(x -> x.getId() == id).findFirst().get());
        return optional;
    }

    public void addNewUser(User user) {
        Optional<User> userOptional = userRepository.findUserByName(user.getName());
        if (userOptional.isPresent()) {
            throw new IllegalStateException("User exists");
        }
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        boolean exists = userRepository.existsById(id);

        if (!exists) throw new IllegalStateException("User does not exist");
        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUser(Long id, String name, LocalDate dob) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User does not exist"));

        if (name != null && name.length() > 0 && !name.equals(user.getName())) {
            Optional<User> userOptional = userRepository.findUserByName(name);
            if (userOptional.isPresent()) throw new IllegalStateException("Name already exists");
            user.setName(name);
        }

        if (dob != null && !dob.equals(user.getDob())) {
            user.setDob(dob);
        }

    }
}
