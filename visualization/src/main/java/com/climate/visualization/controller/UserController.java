package com.climate.visualization.controller;


import com.climate.visualization.model.SavedVisualization;
import com.climate.visualization.model.User;
import com.climate.visualization.repository.UserRepository;
import com.climate.visualization.service.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserInterface userService;

    @Autowired
    public UserController(UserInterface userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if(userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if(userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        User registered = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registered);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.findUserById(id).get();
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User userToUpdate = new User();
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else{
            user.setId(id);
            userToUpdate = userService.updateUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(userToUpdate);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        User user = userService.findUserById(id).get();
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else{
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username).get();
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email).get();
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUserByRole(@PathVariable String role) {
        List<User> users = userService.findByRole(role);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<User> updateUserRole(@PathVariable Long userId, @RequestBody User user) {
        User userToUpdate = userService.assignRole(userId, user.getRole());
        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(userToUpdate);
        }
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<User> updateUserPassword(@PathVariable Long userId, @RequestParam String oldpassword, @RequestParam String newpassword) {
        if (userService.findUserById(userId).isPresent()) {
            userService.changePassword(userId, oldpassword, newpassword);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{userId}/visualizations")
    public ResponseEntity<List<SavedVisualization>> getUserVisualizations(@PathVariable Long userId) {
        User user = userService.findUserById(userId).get();
        if(user == null) {
            List<SavedVisualization> visualizations = new ArrayList<>();
            visualizations = userService.getUserVisualizations(userId);
            return ResponseEntity.status(HttpStatus.OK).body(visualizations);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{userId}/visualizations/public")
    public ResponseEntity<List<SavedVisualization>> getUserPublicVisualizations(@PathVariable Long userId) {
        if (!userService.findUserById(userId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        List<SavedVisualization> visualizations = userService.getUserPublicVisualizations(userId);
        return ResponseEntity.ok(visualizations);
    }

    @GetMapping("/{userId}/visualizations/count")
    public ResponseEntity<Integer> getUserVisualizationCount(@PathVariable Long userId) {
        if (!userService.findUserById(userId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        int count = userService.getUserVisualizationCount(userId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/active")
    public ResponseEntity<List<User>> getMostActiveUsers(@RequestParam(defaultValue = "5") int limit) {
        List<User> activeUsers = userService.findMostActiveUsers(limit);
        return ResponseEntity.ok(activeUsers);
    }







}
