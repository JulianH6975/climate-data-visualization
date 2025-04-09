package com.climate.visualization.service.impl;

import com.climate.visualization.model.ClimateRecord;
import com.climate.visualization.model.DataSet;
import com.climate.visualization.repository.ClimateRecordRepository;
import com.climate.visualization.service.ClimateRecordService;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ClimateRecordServiceImpl implements ClimateRecordService {

    ClimateRecordRepository climateRecordRepository;

    @Override
    @Transactional
    public ClimateRecord save(ClimateRecord climateRecord) {
        return climateRecordRepository.save(climateRecord);
    }

    @Override
    public Optional<ClimateRecord> findById(long id) {
        Optional<ClimateRecord> climateRecord = climateRecordRepository.findById(id);
        return climateRecord;
    }

    @Override
    public List<ClimateRecord> findAll() {
        List<ClimateRecord> climateRecords = climateRecordRepository.findAll();
        return climateRecords;
    }

    @Override
    @Transactional
    public ClimateRecord update(ClimateRecord climateRecord) {
        return climateRecordRepository.save(climateRecord);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        ClimateRecord climateRecord = climateRecordRepository.findById(id).orElse(null);
        if (climateRecord != null) {
            climateRecordRepository.delete(climateRecord);
        }
    }

    @Override
    public List<ClimateRecord> findByLocation(String location) {
        return climateRecordRepository.findByLocation(location);
    }

    @Override
    public List<ClimateRecord> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return climateRecordRepository.findByDateBetween(start,end);
    }

    @Override
    public List<ClimateRecord> findByLocationAndDateRange(String location, LocalDateTime start, LocalDateTime end) {
        return climateRecordRepository.findByLocationAndDateBetween(location, start, end);
    }

    @Override
    public List<ClimateRecord> findbyDataSetId(long dataSetId) {
        return climateRecordRepository.findByDatasetId(dataSetId);
    }

    @Override
    public Double calculateAverageTemperature(List<ClimateRecord> climateRecords) {
        Double averageTemperature = 0.0;
        for (ClimateRecord climateRecord : climateRecords) {
            averageTemperature += climateRecord.getTemperature();
        }
        return averageTemperature / climateRecords.size();
    }

    @Override
    public Double calculateAveragePrecipitation(List<ClimateRecord> climateRecords) {
        Double averagePrecipitation = 0.0;
        for (ClimateRecord climateRecord : climateRecords) {
            averagePrecipitation += climateRecord.getPrecipitation();
        }
        return averagePrecipitation / climateRecords.size();
    }

    @Override
    public List<ClimateRecord> importRecords(List<ClimateRecord> climateRecords, DataSet dataSet) {
        return List.of();
    }

    @Override
    public Map<LocalDate, Double> calculateTemperatureTrend(String location, LocalDate startDate, LocalDate endDate) {
        return Map.of();
    }

    @Override
    public Map<LocalDate, Double> calculatePrecipitationTrend(String location, LocalDate startDate, LocalDate endDate) {
        return Map.of();
    }

    @Override
    public List<ClimateRecord> findAnomalies(String location, LocalDate startDate, LocalDate endDate, double threshold) {
        return List.of();
    }

    @Override
    public List<String> findLocationsWithExtremeConditions(LocalDate date, boolean highTemperature) {
        return List.of();
    }


}
