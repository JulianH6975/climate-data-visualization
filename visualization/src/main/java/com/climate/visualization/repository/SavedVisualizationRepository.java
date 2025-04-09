package com.climate.visualization.repository;

import com.climate.visualization.model.SavedVisualization;
import com.climate.visualization.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;


public interface SavedVisualizationRepository extends JpaRepository<SavedVisualization,Long> {

    List<SavedVisualization> findByUser(User user);

    List<SavedVisualization> findByIsPublic(boolean isPublic);

    List<SavedVisualization> findByUserAndIsPublic(User user, boolean isPublic);

    List<SavedVisualization> findByCreatedAtAfter(LocalDateTime date);

    List<SavedVisualization> findByLastModifiedAfter(LocalDateTime date);

    @Query("SELECT sv FROM SavedVisualization sv WHERE sv.isPublic = true ORDER BY sv.lastModified DESC")
    List<SavedVisualization> findRecentPublicVisualizations();

    @Query("SELECT sv FROM SavedVisualization sv WHERE LOWER(sv.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(sv.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<SavedVisualization> searchByNameOrDescription(String searchTerm);
}
