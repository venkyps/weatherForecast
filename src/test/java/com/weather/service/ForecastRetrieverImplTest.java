package com.weather.service;


import com.weather.forecast.model.ForecastResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ForecastRetrieverImplTest {

    @Test
    public void testGetForcastForSuccess() throws URISyntaxException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "https://api.darksky.net/forecast/" + "dca61823e6c940565fde67057bd55a26" + "/6.2088,106.8456";
        URI uri = new URI(baseUrl);

        ForecastResponse forecast = restTemplate.getForObject(baseUrl,
                ForecastResponse.class);

        assertEquals(true, forecast!=null);
    }

}
