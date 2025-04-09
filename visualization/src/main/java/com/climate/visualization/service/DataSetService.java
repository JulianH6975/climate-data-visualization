package com.climate.visualization.service;

import com.climate.visualization.model.ClimateRecord;
import com.climate.visualization.model.DataSet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Map;

public interface DataSetService {

    DataSet save(DataSet dataSet);

    Optional<DataSet> findById(Long id);

    List<DataSet> findAll();

    DataSet update(DataSet dataSet);

    void deleteById(Long id);


    DataSet addRecordToDataSet(DataSet dataSet, ClimateRecord record);

    DataSet addRecordsToDataSet(DataSet dataSet, List<ClimateRecord> record);

    DataSet removeRecordFromDataSet(Long dataSetId, Long recordId);


    List<DataSet> findByName(String name);

    List<DataSet> findBySource(String source);

    List<DataSet> findByCreatedAtAfter(LocalDateTime date);


    DataSet importDataSet(String name, String description, String source, List<ClimateRecord> records);

    List<ClimateRecord> exportDataSetRecords(Long dataSetId);


    List<DataSet> findLargestDataSets(int limit);

    List<DataSet> findMostRecentDataSets(int limit);

    Map<String, Long> getRecordCountByLocation(Long dataSetId);

    Map<String, Object> getDataSetSummary(Long dataSetId);


}
