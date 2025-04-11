package com.climate.visualization.controller;

import com.climate.visualization.model.ClimateRecord;
import com.climate.visualization.model.DataSet;
import com.climate.visualization.service.DataSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/datasets")
public class DataSetController {

    private final DataSetService dataSetService;

    @Autowired
    public DataSetController(DataSetService dataSetService) {
        this.dataSetService = dataSetService;
    }

    @PostMapping
    public ResponseEntity<DataSet> createDataSet(@RequestBody DataSet dataSet) {
        dataSet.setCreatedAt(LocalDateTime.now());
        DataSet savedDataSet = dataSetService.save(dataSet);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDataSet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataSet> getDataSetById(@PathVariable Long id) {
        return dataSetService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<DataSet>> getAllDataSets() {
        List<DataSet> dataSets = dataSetService.findAll();
        return ResponseEntity.ok(dataSets);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataSet> updateDataSet(@PathVariable Long id, @RequestBody DataSet dataSet) {
        if (!dataSetService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        dataSet.setId(id);
        DataSet updatedDataSet = dataSetService.update(dataSet);
        return ResponseEntity.ok(updatedDataSet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDataSet(@PathVariable Long id) {
        if (!dataSetService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        dataSetService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/records")
    public ResponseEntity<DataSet> addRecordToDataSet(
            @PathVariable Long id,
            @RequestBody ClimateRecord record) {

        return dataSetService.findById(id)
                .map(dataSet -> {
                    DataSet updatedDataSet = dataSetService.addRecordToDataSet(dataSet, record);
                    return ResponseEntity.ok(updatedDataSet);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/records/batch")
    public ResponseEntity<DataSet> addRecordsToDataSet(
            @PathVariable Long id,
            @RequestBody List<ClimateRecord> records) {

        return dataSetService.findById(id)
                .map(dataSet -> {
                    DataSet updatedDataSet = dataSetService.addRecordsToDataSet(dataSet, records);
                    return ResponseEntity.ok(updatedDataSet);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{dataSetId}/records/{recordId}")
    public ResponseEntity<DataSet> removeRecordFromDataSet(
            @PathVariable Long dataSetId,
            @PathVariable Long recordId) {

        if (!dataSetService.findById(dataSetId).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        DataSet updatedDataSet = dataSetService.removeRecordFromDataSet(dataSetId, recordId);
        return ResponseEntity.ok(updatedDataSet);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<DataSet>> getDataSetsByName(@PathVariable String name) {
        List<DataSet> dataSets = dataSetService.findByName(name);
        return ResponseEntity.ok(dataSets);
    }

    @GetMapping("/source/{source}")
    public ResponseEntity<List<DataSet>> getDataSetsBySource(@PathVariable String source) {
        List<DataSet> dataSets = dataSetService.findBySource(source);
        return ResponseEntity.ok(dataSets);
    }

    @GetMapping("/created-after")
    public ResponseEntity<List<DataSet>> getDataSetsCreatedAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {

        List<DataSet> dataSets = dataSetService.findByCreatedAtAfter(date);
        return ResponseEntity.ok(dataSets);
    }

    @PostMapping("/import")
    public ResponseEntity<DataSet> importDataSet(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String source,
            @RequestBody List<ClimateRecord> records) {

        DataSet importedDataSet = dataSetService.importDataSet(name, description, source, records);
        return ResponseEntity.status(HttpStatus.CREATED).body(importedDataSet);
    }

    @GetMapping("/{id}/export")
    public ResponseEntity<List<ClimateRecord>> exportDataSetRecords(@PathVariable Long id) {
        if (!dataSetService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        List<ClimateRecord> records = dataSetService.exportDataSetRecords(id);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/largest")
    public ResponseEntity<List<DataSet>> getLargestDataSets(
            @RequestParam(defaultValue = "5") int limit) {

        List<DataSet> largestDataSets = dataSetService.findLargestDataSets(limit);
        return ResponseEntity.ok(largestDataSets);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<DataSet>> getMostRecentDataSets(
            @RequestParam(defaultValue = "5") int limit) {

        List<DataSet> recentDataSets = dataSetService.findMostRecentDataSets(limit);
        return ResponseEntity.ok(recentDataSets);
    }

    @GetMapping("/{id}/location-counts")
    public ResponseEntity<Map<String, Long>> getRecordCountByLocation(@PathVariable Long id) {
        if (!dataSetService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Map<String, Long> locationCounts = dataSetService.getRecordCountByLocation(id);
        return ResponseEntity.ok(locationCounts);
    }

    @GetMapping("/{id}/summary")
    public ResponseEntity<Map<String, Object>> getDataSetSummary(@PathVariable Long id) {
        if (!dataSetService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Map<String, Object> summary = dataSetService.getDataSetSummary(id);
        return ResponseEntity.ok(summary);
    }
}