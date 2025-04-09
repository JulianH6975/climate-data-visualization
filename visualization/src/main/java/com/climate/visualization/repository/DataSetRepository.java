package com.climate.visualization.repository;

import com.climate.visualization.model.DataSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface DataSetRepository extends JpaRepository<DataSet, Long> {

    List<DataSet> findByName(String name);

    List<DataSet> findBySource(String source);

    List<DataSet> findByCreatedAtAfter(LocalDateTime date);

    @Query("SELECT d FROM DataSet d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(d.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<DataSet> searchByNameOrDescription(String searchTerm);

    @Query("SELECT d FROM DataSet d ORDER BY SIZE(d.records) DESC")
    List<DataSet> findDatasetsOrderByRecordCountDesc();
}
