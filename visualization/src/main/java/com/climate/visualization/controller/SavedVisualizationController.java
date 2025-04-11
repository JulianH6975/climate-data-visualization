package com.climate.visualization.controller;


import com.climate.visualization.model.SavedVisualization;
import com.climate.visualization.service.impl.SavedVisualizationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/saved-visualizations")
public class SavedVisualizationController {

    SavedVisualizationServiceImpl savedVisualizationService;

    @Autowired
    public SavedVisualizationController(SavedVisualizationServiceImpl savedVisualizationService) {
        this.savedVisualizationService = savedVisualizationService;
    }

    @PostMapping
    public ResponseEntity<SavedVisualization> createVisualization(@RequestBody SavedVisualization savedVisualization) {
        savedVisualizationService.saveVisualization(savedVisualization);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVisualization);
    }

    @GetMapping("{id}")
    public ResponseEntity<SavedVisualization> getVisualization(@PathVariable Long id) {
        SavedVisualization visualization = savedVisualizationService.findById(id).get();
        if(visualization == null) {
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(visualization);
        }
    }

    @GetMapping
    public ResponseEntity<List<SavedVisualization>> getVisualizations() {
        return ResponseEntity.ok(savedVisualizationService.findAllVisualizations());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SavedVisualization> updateVisualization(@PathVariable Long id, @RequestBody SavedVisualization visualization) {
        if (!savedVisualizationService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        visualization.setId(id);
        SavedVisualization updatedVisualization = savedVisualizationService.updateVisualization(visualization);
        return ResponseEntity.ok(updatedVisualization);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SavedVisualization> deleteVisualization(@PathVariable Long id) {
        if(!savedVisualizationService.findById(id).isPresent()) {
            savedVisualizationService.deleteVisualization(id);
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/public")
    public ResponseEntity<List<SavedVisualization>> getPublicVisualizations() {
        List<SavedVisualization> publicVisualizations = savedVisualizationService.findPublicVisualizations();
        return ResponseEntity.ok(publicVisualizations);
    }

    @GetMapping("/public/recent")
    public ResponseEntity<List<SavedVisualization>> getRecentPublicVisualizations(@RequestParam(defaultValue = "5") int limit) {
        List<SavedVisualization> recentVisualizations = savedVisualizationService.findRecentPublicVisualizations(limit);
        return ResponseEntity.ok(recentVisualizations);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SavedVisualization>> searchVisualizations(@RequestParam String term) {
        List<SavedVisualization> foundVisualizations = savedVisualizationService.searchByNameOrDescription(term);
        return ResponseEntity.ok(foundVisualizations);
    }

    @PutMapping("/{id}/public")
    public ResponseEntity<SavedVisualization> setPublicStatus(
            @PathVariable Long id, @RequestParam boolean isPublic) {
        SavedVisualization visualization = savedVisualizationService.setPublicStatus(id, isPublic);
        if (visualization == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(visualization);
    }

    @GetMapping("/{id}/share")
    public ResponseEntity<String> generateSharingLink(@PathVariable Long id) {
        if (!savedVisualizationService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        String sharingLink = savedVisualizationService.generateSharingLink(id);
        return ResponseEntity.ok(sharingLink);
    }

    @PutMapping("/{id}/configuration")
    public ResponseEntity<SavedVisualization> updateConfiguration(
            @PathVariable Long id, @RequestBody String configuration) {
        SavedVisualization visualization = savedVisualizationService.updateConfiguration(id, configuration);
        if (visualization == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(visualization);
    }

    @GetMapping("/created-after")
    public ResponseEntity<List<SavedVisualization>> getVisualizationsCreatedAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        List<SavedVisualization> visualizations = savedVisualizationService.findCreatedAfter(date);
        return ResponseEntity.ok(visualizations);
    }

    @GetMapping("/modified-after")
    public ResponseEntity<List<SavedVisualization>> getVisualizationsModifiedAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        List<SavedVisualization> visualizations = savedVisualizationService.findModifiedAfter(date);
        return ResponseEntity.ok(visualizations);
    }

    @GetMapping("/most-viewed")
    public ResponseEntity<List<SavedVisualization>> getMostViewedVisualizations(
            @RequestParam(defaultValue = "5") int limit) {
        List<SavedVisualization> visualizations = savedVisualizationService.findMostViewedVisualizations(limit);
        return ResponseEntity.ok(visualizations);
    }


}
