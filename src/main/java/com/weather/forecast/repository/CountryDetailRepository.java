package com.weather.forecast.repository;

import com.weather.forecast.model.CountryDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CountryDetailRepository  extends MongoRepository<CountryDetail, String> {
}
