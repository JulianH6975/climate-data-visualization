package com.climate.visualization.service;

import com.climate.visualization.model.ClimateRecord;
import com.climate.visualization.model.DataSet;
import com.climate.visualization.repository.ClimateRecordRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ClimateRecordService {

    ClimateRecord save(ClimateRecord climateRecord);

    Optional<ClimateRecord> findById(long id);

    List<ClimateRecord> findAll();

    ClimateRecord update(ClimateRecord climateRecord);

    void deleteById(long id);


    List<ClimateRecord> findByLocation(String location);

    List<ClimateRecord> findByDateRange(LocalDateTime start, LocalDateTime end);

    List<ClimateRecord> findByLocationAndDateRange(String location, LocalDateTime start, LocalDateTime end);

    List<ClimateRecord> findbyDataSetId(long dataSetId);


    Double calculateAverageTemperature(List<ClimateRecord> climateRecords);

    Double calculateAveragePrecipitation(List<ClimateRecord> climateRecords);

    List<ClimateRecord> importRecords(List<ClimateRecord> climateRecords, DataSet dataSet);

    Map<LocalDate, Double> calculateTemperatureTrend(String location, LocalDate startDate, LocalDate endDate);

    Map<LocalDate, Double> calculatePrecipitationTrend(String location, LocalDate startDate, LocalDate endDate);

    List<ClimateRecord> findAnomalies(String location, LocalDate startDate, LocalDate endDate, double threshold);

    List<String> findLocationsWithExtremeConditions(LocalDate date, boolean highTemperature);






}
