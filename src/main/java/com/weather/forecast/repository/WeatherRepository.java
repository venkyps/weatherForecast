package com.weather.forecast.repository;

import com.weather.forecast.model.WeatherData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface  WeatherRepository extends MongoRepository<WeatherData, String> {

    Optional<WeatherData> getByCityName(String cityName);
}