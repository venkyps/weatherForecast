package com.weather.forecast.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyForecastData extends ForecastBase {

    private double temperatureMin;
    private double temperatureMax;
    @JsonIgnore
    //@JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime temperatureMinTime;
    @JsonIgnore
    //@JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime temperatureMaxTime;

    public DailyForecastData() {
    }

    public double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public LocalDateTime getTemperatureMinTime() {
        return temperatureMinTime;
    }

    public void setTemperatureMinTime(LocalDateTime temperatureMinTime) {
        this.temperatureMinTime = temperatureMinTime;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public LocalDateTime getTemperatureMaxTime() {
        return temperatureMaxTime;
    }

    public void setTemperatureMaxTime(LocalDateTime temperatureMaxTime) {
        this.temperatureMaxTime = temperatureMaxTime;
    }

}
