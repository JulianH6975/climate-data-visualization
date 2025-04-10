package com.climate.visualization.service.impl;

import com.climate.visualization.model.SavedVisualization;
import com.climate.visualization.model.User;
import com.climate.visualization.repository.SavedVisualizationRepository;
import com.climate.visualization.service.SavedVisualizationService;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        return savedVisualizationRepository.findByIsPublic(true);
    }

    @Override
    public List<SavedVisualization> findRecentPublicVisualizations(int limit) {
        List<SavedVisualization> publicVisualizations = savedVisualizationRepository.findRecentPublicVisualizations();
        List<SavedVisualization> recentVisualizations = new ArrayList<>();
        int realLimit = Math.min(limit, publicVisualizations.size());
        for (int i = 0; i < realLimit; i++) {
            recentVisualizations.add(publicVisualizations.get(i));
        }
        return recentVisualizations;
    }

    @Override
    public List<SavedVisualization> findByUserAndPublicStatus(User user, boolean isPublic) {
        return savedVisualizationRepository.findByUserAndIsPublic(user, isPublic);
    }

    @Override
    public List<SavedVisualization> findCreatedAfter(LocalDateTime date) {
        return savedVisualizationRepository.findByCreatedAtAfter(date);
    }

    @Override
    public List<SavedVisualization> findModifiedAfter(LocalDateTime date) {
        return savedVisualizationRepository.findByLastModifiedAfter(date);
    }

    @Override
    public List<SavedVisualization> searchByNameOrDescription(String searchTerm) {
        return savedVisualizationRepository.searchByNameOrDescription(searchTerm);
    }

    @Override
    @Transactional
    public SavedVisualization setPublicStatus(Long visualizationId, boolean isPublic) {
        SavedVisualization savedVisualization = savedVisualizationRepository.findById(visualizationId).orElse(null);
        if (savedVisualization != null) {
            savedVisualization.setPublic(isPublic);
            savedVisualizationRepository.save(savedVisualization);
        }
        return savedVisualization;
    }

    @Override
    public String generateSharingLink(Long visualizationId) {
        return "";
    }

    @Override
    public boolean isAccessibleToUser(Long visualizationId, Long userId) {
        return false;
    }

    @Override
    public SavedVisualization updateConfiguration(Long visualizationId, String newConfiguration) {
        return null;
    }

    @Override
    public List<SavedVisualization> findMostViewedVisualizations(int limit) {
        return List.of();
    }

    @Override
    public SavedVisualization cloneVisualization(Long visualizationId, Long newOwnerId, String newName) {
        return null;
    }
}
