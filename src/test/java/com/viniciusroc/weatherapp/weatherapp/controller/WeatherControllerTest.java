package com.viniciusroc.weatherapp.weatherapp.controller;

import com.viniciusroc.weatherapp.weatherapp.model.*;
import com.viniciusroc.weatherapp.weatherapp.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeatherControllerTest {
    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;

    @Test
    public void getTemperatureByCity_Success() throws Exception {
        // Arrange
        City mockCityData = getCity("Campinas");

        when(weatherService.getTemperatureByCity("Campinas")).thenReturn(mockCityData);

        // Act
        City result = weatherController.getTemperatureByCity("Campinas");

        // Assert
        assertNotNull(result, "The city should not be null.");
        assertEquals("Campinas", result.getName(), "The city name should match the request.");
        assertEquals(30.0, result.getMain().getTemp(), "The temperature should match the mocked value.");

        // Verify that the service method was called exactly once with the correct parameter
        verify(weatherService, times(1)).getTemperatureByCity("Campinas");
    }

    private City getCity(String city) {
        Coord coord = new Coord(-22.9056, -47.0608);
        Temperature temp = new Temperature(30.0, 26.0, 34.0, 31.0, 10.0);
        Wind wind = new Wind(10.5);
        List<Weather> weatherList = new ArrayList<>();
        return new City(city, coord, temp, wind, weatherList);
    }

}
