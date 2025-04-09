package com.climate.visualization.service.impl;

import com.climate.visualization.model.SavedVisualization;
import com.climate.visualization.model.User;
import com.climate.visualization.repository.SavedVisualizationRepository;
import com.climate.visualization.service.SavedVisualizationService;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class SavedVisualizationServiceImpl implements SavedVisualizationService {

    SavedVisualizationRepository savedVisualizationRepository;

    @Override
    @Transactional
    public SavedVisualization saveVisualization(SavedVisualization visualization) {
        return savedVisualizationRepository.save(visualization);
    }

    @Override
    public Optional<SavedVisualization> findById(Long id) {
        return savedVisualizationRepository.findById(id);
    }

    @Override
    public List<SavedVisualization> findAllVisualizations() {
        return savedVisualizationRepository.findAll();
    }

    @Override
    @Transactional
    public SavedVisualization updateVisualization(SavedVisualization visualization) {
        return savedVisualizationRepository.save(visualization);
    }

    @Override
    @Transactional
    public void deleteVisualization(Long id) {
        savedVisualizationRepository.deleteById(id);
    }

    @Override
    public List<SavedVisualization> findByUser(User user) {
        return savedVisualizationRepository.findByUser(user);
    }

    @Override
    public List<SavedVisualization> findByUserId(Long userId) {
        return savedVisualizationRepository.findByUserId(userId);
    }

    @Override
    public List<SavedVisualization> findPublicVisualizations() {
        return List.of();
    }

    @Override
    public List<SavedVisualization> findRecentPublicVisualizations(int limit) {
        return List.of();
    }

    @Override
    public List<SavedVisualization> findByUserAndPublicStatus(User user, boolean isPublic) {
        return List.of();
    }

    @Override
    public List<SavedVisualization> findCreatedAfter(LocalDateTime date) {
        return List.of();
    }

    @Override
    public List<SavedVisualization> findModifiedAfter(LocalDateTime date) {
        return List.of();
    }
}
