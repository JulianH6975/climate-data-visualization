package com.climate.visualization.controller;

import com.climate.visualization.model.ClimateRecord;
import com.climate.visualization.model.DataSet;
import com.climate.visualization.service.ClimateRecordService;
import com.climate.visualization.service.DataSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/climate-records")
public class ClimateRecordController {

    ClimateRecordService recordService;
    DataSetService dataSetService;

    @Autowired
    public ClimateRecordController(ClimateRecordService recordService, DataSetService dataSetService) {
        this.recordService = recordService;
        dataSetService = dataSetService;
    }

    @PostMapping
    public ResponseEntity<ClimateRecord> createClimateRecord(@RequestBody ClimateRecord climateRecord) {
        ClimateRecord savedRecord = recordService.save(climateRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecord);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClimateRecord> getClimateRecordById(@PathVariable long id) {
        return recordService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ClimateRecord>> getAllClimateRecords() {
        List<ClimateRecord> records = recordService.findAll();
        return ResponseEntity.ok(records);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClimateRecord> updateClimateRecord(@PathVariable long id, @RequestBody ClimateRecord climateRecord) {
        if (!recordService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        climateRecord.setId(id);
        ClimateRecord updatedRecord = recordService.update(climateRecord);
        return ResponseEntity.ok(updatedRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClimateRecord(@PathVariable long id) {
        if (!recordService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        recordService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<ClimateRecord>> getClimateRecordsByLocation(@PathVariable String location) {
        List<ClimateRecord> records = recordService.findByLocation(location);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<ClimateRecord>> getClimateRecordsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<ClimateRecord> records = recordService.findByDateRange(start, end);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/location-date-range")
    public ResponseEntity<List<ClimateRecord>> getClimateRecordsByLocationAndDateRange(
            @RequestParam String location,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<ClimateRecord> records = recordService.findByLocationAndDateRange(location, start, end);
        return ResponseEntity.ok(records);
    }


    @GetMapping("/dataset/{dataSetId}")
    public ResponseEntity<List<ClimateRecord>> getClimateRecordsByDataSetId(@PathVariable long dataSetId) {
        List<ClimateRecord> records = recordService.findbyDataSetId(dataSetId);
        return ResponseEntity.ok(records);
    }


    @GetMapping("/temperature-trend")
    public ResponseEntity<Map<LocalDate, Double>> getTemperatureTrend(
            @RequestParam String location,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<LocalDate, Double> trend = recordService.calculateTemperatureTrend(location, startDate, endDate);
        return ResponseEntity.ok(trend);
    }

    @GetMapping("/precipitation-trend")
    public ResponseEntity<Map<LocalDate, Double>> getPrecipitationTrend(
            @RequestParam String location,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<LocalDate, Double> trend = recordService.calculatePrecipitationTrend(location, startDate, endDate);
        return ResponseEntity.ok(trend);
    }

    @GetMapping("/anomalies")
    public ResponseEntity<List<ClimateRecord>> getAnomalies(
            @RequestParam String location,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam double threshold) {
        List<ClimateRecord> anomalies = recordService.findAnomalies(location, startDate, endDate, threshold);
        return ResponseEntity.ok(anomalies);
    }

    @GetMapping("/extreme-conditions")
    public ResponseEntity<List<String>> getLocationsWithExtremeConditions(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam boolean highTemperature) {
        List<String> locations = recordService.findLocationsWithExtremeConditions(date, highTemperature);
        return ResponseEntity.ok(locations);
    }

    @PostMapping("/batch-import")
    public ResponseEntity<List<ClimateRecord>> importClimateRecords(
            @RequestBody List<ClimateRecord> records,
            @RequestParam long dataSetId) {
        // Assuming we have a method to find DataSet by ID
        DataSet dataSet = new DataSet(); // This would be fetched from service
        dataSet = dataSetService.save(dataSet);
        List<ClimateRecord> importedRecords = recordService.importRecords(records, dataSet);
        return ResponseEntity.status(HttpStatus.CREATED).body(importedRecords);
    }

    @GetMapping("/stats/average-temperature")
    public ResponseEntity<Double> getAverageTemperature(@RequestBody List<ClimateRecord> records) {
        Double average = recordService.calculateAverageTemperature(records);
        return ResponseEntity.ok(average);
    }

    @GetMapping("/stats/average-precipitation")
    public ResponseEntity<Double> getAveragePrecipitation(@RequestBody List<ClimateRecord> records) {
        Double average = recordService.calculateAveragePrecipitation(records);
        return ResponseEntity.ok(average);
    }




}
