package com.climate.visualization.service.impl;

import com.climate.visualization.model.SavedVisualization;
import com.climate.visualization.model.User;
import com.climate.visualization.repository.SavedVisualizationRepository;
import com.climate.visualization.repository.UserRepository;
import com.climate.visualization.service.UserInterface;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserInterface {

    UserRepository userRepository;
    SavedVisualizationRepository savedVisualizationRepository;

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
        return userRepository.findByRole(role);
    }

    @Override
    @Transactional
    public User addVisualization(Long userId, SavedVisualization visualization) {
        User user = userRepository.findById(userId).orElse(null);
        user.addVisualization(visualization);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User removeVisualization(Long userId, Long visualizationId) {
        User user = userRepository.findById(userId).orElse(null);
        SavedVisualization savedVisualization = savedVisualizationRepository.findById(visualizationId).orElse(null);
        user.removeVisualization(savedVisualization);
        savedVisualizationRepository.delete(savedVisualization);
        userRepository.save(user);
        return null;
    }

    @Override
    public List<SavedVisualization> getUserVisualizations(Long userId) {
        List<SavedVisualization> savedVisualizations = savedVisualizationRepository.findByUserId(userId);
        return savedVisualizations;
    }

    @Override
    public List<SavedVisualization> getUserPublicVisualizations(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        List<SavedVisualization> savedVisualizations = new ArrayList<>();
        if (user != null) {
            savedVisualizations = savedVisualizationRepository.findByUserAndIsPublic(user, true);
        }
        return savedVisualizations;

    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public int getUserVisualizationCount(Long userId) {
        int n = savedVisualizationRepository.findByUserId(userId).size();
        return n;
    }

    @Override
    public List<User> findMostActiveUsers(int limit) {
        return null;
    }


}
