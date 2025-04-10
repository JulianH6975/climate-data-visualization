package com.climate.visualization.controller;

import com.climate.visualization.model.ClimateRecord;
import com.climate.visualization.service.ClimateRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/climate-records")
public class ClimateRecordController {

    ClimateRecordService recordService;

    @Autowired
    public ClimateRecordController(ClimateRecordService recordService) {
        this.recordService = recordService;
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
}
