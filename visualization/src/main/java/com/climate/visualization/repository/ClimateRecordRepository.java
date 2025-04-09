package com.climate.visualization.repository;

import com.climate.visualization.model.ClimateRecord;
import com.climate.visualization.model.DataSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface ClimateRecordRepository extends JpaRepository<ClimateRecord, Long> {

    List<ClimateRecord> findByLocation(String location);

    List<ClimateRecord> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<ClimateRecord> findByLocationAndDateBetween(String location, LocalDateTime startDate, LocalDateTime endDate);

    List<ClimateRecord> findByDatasetId(Long datasetId);

    @Query("SELECT d FROM DataSet d ORDER BY d.createdAt DESC")
    List<DataSet> findMostRecent();

    @Query("SELECT d FROM DataSet d JOIN d.records r GROUP BY d.id ORDER BY COUNT(r) DESC")
    List<DataSet> findLargestDatasets();


}
