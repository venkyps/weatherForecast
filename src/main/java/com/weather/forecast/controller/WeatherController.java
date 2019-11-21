package com.weather.forecast.controller;


import com.weather.forecast.model.CountryDetail;
import com.weather.forecast.model.DailyForecastData;
import com.weather.forecast.model.ForecastResponse;
import com.weather.forecast.model.WeatherData;
import com.weather.forecast.repository.CountryDetailRepository;
import com.weather.forecast.repository.WeatherRepository;
import com.weather.forecast.service.ForecastRetriever;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class WeatherController {

    Logger logger = LoggerFactory.getLogger(WeatherController.class);

    static List<CountryDetail> countryDetailList;

    @Autowired
    private ForecastRetriever forecastRetriever;

    @Autowired
    WeatherRepository weatherRepository;

    @Autowired
    CountryDetailRepository countryDetailRepository;

    @RequestMapping("/weatherForecast")
    private String welcomePage(Model model){

        List<WeatherData> weatherDataList = new ArrayList<>();
        countryDetailList.stream().forEach(countryDetail -> {
            Optional<WeatherData> weatherDataDBOptional = checkDBDataExists(countryDetail.getCityName());
            if (weatherDataDBOptional.isPresent()) {
                logger.info("Data retrieve from DB");
                weatherDataList.add(weatherDataDBOptional.get());
            } else {
                logger.info("Data retrieve from API");
                weatherDataList.add(retrieveAndPersistWeatherData(countryDetail.getLatitude(),countryDetail.getLongitude(), countryDetail.getCityName()));
            }
        });

        model.addAttribute("weatherList", weatherDataList);
        return "weatherForecast";
    }

    /**
     * Retrieves weather data from dark API
     * @param latitude
     * @param longitude
     * @param cityName
     * @return
     */
    private WeatherData retrieveAndPersistWeatherData(String latitude,String longitude,String cityName){
        ForecastResponse forecastResponse = forecastRetriever.getForcastFor(latitude,longitude);
        WeatherData weatherData = new WeatherData();
        weatherData.setCityName(cityName);
        weatherData.setTemperature(String.valueOf(forecastResponse.getCurrently().getTemperature()));
        weatherData.setLatitude(forecastResponse.getLatitude());
        weatherData.setLongitude(forecastResponse.getLongitude());
        Optional<DailyForecastData> data =forecastResponse.getDaily().getData().stream().findFirst();
        DailyForecastData dailyForecastData= data.get();
        weatherData.setTemperatureMax(String.valueOf(dailyForecastData.getTemperatureMax()));
        weatherData.setTemperatureMin(String.valueOf(dailyForecastData.getTemperatureMin()));
        weatherData.setSummary(dailyForecastData.getSummary());
        weatherRepository.save(weatherData);
        return weatherData;
    }

    /**
     * Checks if weather data exists for given city name in DB
     * @param cityName
     * @return
     */
    private Optional<WeatherData> checkDBDataExists(String cityName){
        return weatherRepository.getByCityName(cityName);
    }

    /**
     * Loads list of cities data for retrieving weather information
     */
    @PostConstruct
    private void loadCountryDetails(){
        countryDetailList = countryDetailRepository.findAll();
        countryDetailList.stream().forEach(countryDetail -> logger.debug("List of cities for weather data "+countryDetail.getCityName()));
    }

}
