package com.climate.visualization.service.impl;

import com.climate.visualization.model.SavedVisualization;
import com.climate.visualization.model.User;
import com.climate.visualization.repository.UserRepository;
import com.climate.visualization.service.UserInterface;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserInterface {

    UserRepository userRepository;

    @Override
    @Transactional
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean validateCredentials(String username, String password) {
        return false;
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {

    }

    @Override
    public User assignRole(Long userId, String role) {
        return null;
    }

    @Override
    public List<User> findByRole(String role) {
        return List.of();
    }

    @Override
    public User addVisualization(Long userId, SavedVisualization visualization) {
        return null;
    }

    @Override
    public User removeVisualization(Long userId, Long visualizationId) {
        return null;
    }

    @Override
    public List<SavedVisualization> getUserVisualizations(Long userId) {
        return List.of();
    }

    @Override
    public List<SavedVisualization> getUserPublicVisualizations(Long userId) {
        return List.of();
    }

    @Override
    public boolean existsByUsername(String username) {
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public int getUserVisualizationCount(Long userId) {
        return 0;
    }

    @Override
    public List<User> findMostActiveUsers(int limit) {
        return List.of();
    }


}
