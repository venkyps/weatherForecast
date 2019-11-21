package com.weather.forecast.service;

import com.weather.forecast.model.ForecastResponse;

public interface ForecastRetriever {

    ForecastResponse getForcastFor(String longitude, String latitude);
}

