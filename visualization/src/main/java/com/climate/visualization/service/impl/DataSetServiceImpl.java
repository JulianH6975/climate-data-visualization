package com.climate.visualization.service.impl;

import com.climate.visualization.model.ClimateRecord;
import com.climate.visualization.model.DataSet;
import com.climate.visualization.repository.ClimateRecordRepository;
import com.climate.visualization.repository.DataSetRepository;
import com.climate.visualization.service.DataSetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class DataSetServiceImpl implements DataSetService {

    DataSetRepository dataSetRepository;
    ClimateRecordRepository climateRecordRepository;

    @Override
    @Transactional
    public DataSet save(DataSet dataSet) {
        return dataSetRepository.save(dataSet);
    }

    @Override
    public Optional<DataSet> findById(Long id) {
        return dataSetRepository.findById(id);
    }

    @Override
    public List<DataSet> findAll() {
        return dataSetRepository.findAll();
    }

    @Override
    @Transactional
    public DataSet update(DataSet dataSet) {
        return dataSetRepository.save(dataSet);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        DataSet dataSet = dataSetRepository.findById(id).orElse(null);
        if (dataSet != null) {
            dataSetRepository.delete(dataSet);
        }
    }

    @Override
    @Transactional
    public DataSet addRecordToDataSet(DataSet dataSet, ClimateRecord record) {
        dataSet.addRecord(record);
        dataSetRepository.save(dataSet);
        return dataSet;
    }

    @Override
    @Transactional
    public DataSet addRecordsToDataSet(DataSet dataSet, List<ClimateRecord> record) {
        for (ClimateRecord record1 : record) {
            dataSet.addRecord(record1);
        }
        dataSetRepository.save(dataSet);
        return dataSet;
    }

    @Override
    @Transactional
    public DataSet removeRecordFromDataSet(Long dataSetId, Long recordId) {
        DataSet dataSet = dataSetRepository.findById(dataSetId).orElse(null);
        ClimateRecord climateRecord = climateRecordRepository.findById(recordId).orElse(null);
        if (dataSet != null && climateRecord != null) {
            dataSet.removeRecord(climateRecord);
            dataSetRepository.save(dataSet);
        }
        return dataSet;
    }

    @Override
    public List<DataSet> findByName(String name) {
        return dataSetRepository.findByName(name);
    }

    @Override
    public List<DataSet> findBySource(String source) {
        return dataSetRepository.findBySource(source);
    }

    @Override
    public List<DataSet> findByCreatedAtAfter(LocalDateTime date) {
        return dataSetRepository.findByCreatedAtAfter(date);
    }

    @Override
    @Transactional
    public DataSet importDataSet(String name, String description, String source, List<ClimateRecord> records) {
        DataSet dataSet = new DataSet();
        dataSet.setName(name);
        dataSet.setDescription(description);
        dataSet.setSource(source);
        dataSet.setCreatedAt(LocalDateTime.now());
        Set<ClimateRecord> climateRecords = new HashSet<>();
        for (ClimateRecord record : records) {
            climateRecords.add(record);
        }
        dataSet.setClimateRecords(climateRecords);
        dataSetRepository.save(dataSet);
        return dataSet;
    }

    @Override
    public List<ClimateRecord> exportDataSetRecords(Long dataSetId) {
        return List.of();
    }

    @Override
    public List<DataSet> findLargestDataSets(int limit) {
        List<DataSet> largestDataSets = new ArrayList<>();
        List<DataSet> datasets = dataSetRepository.findDatasetsOrderByRecordCountDesc();
        int realLimit = Math.min(datasets.size(), limit);
        for(int i = 0; i < realLimit; i++) {
            largestDataSets.add(datasets.get(i));
        }
        return largestDataSets;
    }

    @Override
    public List<DataSet> findMostRecentDataSets(int limit) {
        List<DataSet> mostRecentDataSets = new ArrayList<>();
        List<DataSet> datasets = dataSetRepository.findDatasetsOrderByRecordCountDesc();
        int realLimit = Math.min(datasets.size(), limit);
        for(int i = 0; i < realLimit; i++) {
            mostRecentDataSets.add(datasets.get(i));
        }
        return mostRecentDataSets;
    }

    @Override
    public Map<String, Long> getRecordCountByLocation(Long dataSetId) {
        return Map.of();
    }

    @Override
    public Map<String, Object> getDataSetSummary(Long dataSetId) {
        return Map.of();
    }


}
