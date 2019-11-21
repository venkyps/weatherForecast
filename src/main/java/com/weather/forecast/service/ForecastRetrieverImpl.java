package com.weather.forecast.service;

import com.weather.forecast.model.ForecastResponse;
import com.weather.forecast.model.exception.ExternalServiceGatewayException;
import com.weather.forecast.model.exception.ExternalServiceInvocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class ForecastRetrieverImpl implements ForecastRetriever {

    private static final String FORECAST_IO_SERVICE_NAME = "ForecastIOException";
    Logger logger = LoggerFactory.getLogger(ForecastRetrieverImpl.class);

    @Autowired
    private Environment environment;

    @Value("${DARKSKY_API_KEY}")
    private String darkskyApiKey;

    @Value("${darksky.base.url}")
    private String darkskyBaseUrl;

    @Autowired
    private RestTemplate restTemplate;

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getDarkskyApiKey() {
        if (darkskyApiKey == null) {
            this.darkskyApiKey = environment.getProperty("DARKSKY_API_KEY");
        }
        return darkskyApiKey;
    }

    public void setDarkskyApiKey(String forecastioApiKey) {
        this.darkskyApiKey = forecastioApiKey;
    }

    public String getDarkskyBaseUrl() {
        return darkskyBaseUrl;
    }


    public void setDarkskyBaseUrl(String forecastioBaseUrl) {
        this.darkskyBaseUrl = forecastioBaseUrl;
    }

    /**
     * Build dark API url
     * @param latitude
     * @param longitude
     * @return
     */
    private String buildURL(String latitude, String longitude) {
        return getDarkskyBaseUrl()
                +"/"+getDarkskyApiKey()+"/"+latitude+","+longitude;
    }

    /**
     * Retrieves weather data for given latitude,longitude values
     * @param latitude
     * @param longitude
     * @return
     */
    @Override
    public ForecastResponse getForcastFor(String latitude, String longitude) {
        try {
            logger.info("Weather data retrieved for latitude "+latitude + " longitude "+longitude);
            ForecastResponse forecast = restTemplate.getForObject(buildURL(latitude,longitude),
                    ForecastResponse.class);
            return forecast;
        } catch (HttpStatusCodeException httpStatusEx) {
            throw new ExternalServiceInvocationException(FORECAST_IO_SERVICE_NAME, httpStatusEx.getRawStatusCode());
        } catch (Exception ex) {
            throw new ExternalServiceGatewayException(FORECAST_IO_SERVICE_NAME, ex);
        }
    }
}
