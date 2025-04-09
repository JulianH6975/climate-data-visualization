package com.climate.visualization.service;

import com.climate.visualization.model.SavedVisualization;
import com.climate.visualization.model.User;

import java.util.List;
import java.util.Optional;

public interface UserInterface {

    User registerUser(User user);

    Optional<User> findUserById(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findAllUsers();

    User updateUser(User user);

    void deleteUser(Long id);


    boolean validateCredentials(String username, String password);

    void changePassword(Long userId, String oldPassword, String newPassword);

    User assignRole(Long userId, String role);

    List<User> findByRole(String role);


    User addVisualization(Long userId, SavedVisualization visualization);

    User removeVisualization(Long userId, Long visualizationId);

    List<SavedVisualization> getUserVisualizations(Long userId);

    List<SavedVisualization> getUserPublicVisualizations(Long userId);


    boolean existsByUsername(String username);

    boolean existsByEmail(String email);


    int getUserVisualizationCount(Long userId);

    List<User> findMostActiveUsers(int limit);
}
