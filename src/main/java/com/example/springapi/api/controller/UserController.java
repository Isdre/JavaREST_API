package com.example.springapi.api.controller;

import com.example.springapi.api.model.User;
import com.example.springapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/user")
    public User getUser(@RequestParam Long id) {
        Optional user = userService.getUser(id);
        if (user.isPresent()) return (User) user.get();
        else return null;
    }

    @PostMapping("/user")
    public void createUser(@RequestBody User user) {
        userService.addNewUser(user);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/user/{id}")
    public void updateUser(@PathVariable("id") Long id,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = false) LocalDate dob){
        userService.updateUser(id, name, dob);
    }
}
