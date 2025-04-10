package com.climate.visualization.service;

import com.climate.visualization.model.SavedVisualization;
import com.climate.visualization.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SavedVisualizationService {

    SavedVisualization saveVisualization(SavedVisualization visualization);

    Optional<SavedVisualization> findById(Long id);

    List<SavedVisualization> findAllVisualizations();

    SavedVisualization updateVisualization(SavedVisualization visualization);

    void deleteVisualization(Long id);


    List<SavedVisualization> findByUser(User user);

    List<SavedVisualization> findByUserId(Long userId);

    List<SavedVisualization> findPublicVisualizations();

    List<SavedVisualization> findRecentPublicVisualizations(int limit);

    List<SavedVisualization> findByUserAndPublicStatus(User user, boolean isPublic);

    List<SavedVisualization> findCreatedAfter(LocalDateTime date);

    List<SavedVisualization> findModifiedAfter(LocalDateTime date);


    List<SavedVisualization> searchByNameOrDescription(String searchTerm);

    // Visibility Management
    SavedVisualization setPublicStatus(Long visualizationId, boolean isPublic);

    // Sharing
    String generateSharingLink(Long visualizationId);

    boolean isAccessibleToUser(Long visualizationId, Long userId);

    // Configuration Management
    SavedVisualization updateConfiguration(Long visualizationId, String newConfiguration);

    // Statistics
    List<SavedVisualization> findMostViewedVisualizations(int limit);

    // Cloning
    SavedVisualization cloneVisualization(Long visualizationId, Long newOwnerId, String newName);
}
