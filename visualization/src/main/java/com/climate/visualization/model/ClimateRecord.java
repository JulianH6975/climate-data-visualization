package com.climate.visualization.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name="climate-records")
public class ClimateRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String location;

    private Double temperature;

    private Double precipitation;

    @ManyToOne
    @JoinColumn(name="dataset_id")
    private DataSet dataset;

    public ClimateRecord() {
    }

    public ClimateRecord(LocalDate date, String location, Double temperature, Double precipitation) {
        this.date = date;
        this.location = location;
        this.temperature = temperature;
        this.precipitation = precipitation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(Double precipitation) {
        this.precipitation = precipitation;
    }

    public DataSet getDataset() {
        return dataset;
    }

    public void setDataset(DataSet dataset) {
        this.dataset = dataset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClimateRecord that = (ClimateRecord) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ClimateRecord{" +
                "id=" + id +
                ", date=" + date +
                ", location='" + location + '\'' +
                ", temperature=" + temperature +
                ", precipitation=" + precipitation +
                '}';
    }



}
