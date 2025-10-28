package com.viniciusroc.weatherapp.weatherapp.controller;

import com.viniciusroc.weatherapp.weatherapp.model.*;
import com.viniciusroc.weatherapp.weatherapp.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WeatherControllerTest {
    @Mock
    private WeatherService weatherService;
    @Mock
    private CacheManager cacheManager;
    @Mock
    private Cache cache;
    @Mock
    private Cache.ValueWrapper valueWrapper;

    @InjectMocks
    private WeatherController weatherController;


    @Test
    void testGetForecastByZipCode_WhenCached() throws IOException {
        // Arrange
        String zipCode = "12345";

        var mockForecast = getForecastMain();

        when(cacheManager.getCache("weatherForecasts")).thenReturn(cache);
        when(cache.get(zipCode)).thenReturn(valueWrapper);
        when(weatherService.getForecastByZipCode(zipCode)).thenReturn(mockForecast);

        // Act
        ForecastMain result = weatherController.getForecastByZipCode(zipCode);

        // Assert
        assertNotNull(result);
        assertEquals(mockForecast, result);

        //verify(cacheManager).getCache("weatherForecasts");
        verify(cache).get(zipCode);
        verify(weatherService).getForecastByZipCode(zipCode);
    }

    @Test
    void testGetForecastByZipCode_WhenNotCached() throws IOException {
        // Arrange
        String zipCode = "67890";

        var mockForecast = getForecastMain();

        when(cacheManager.getCache("weatherForecasts")).thenReturn(cache);
        when(cache.get(zipCode)).thenReturn(null);
        when(weatherService.getForecastByZipCode(zipCode)).thenReturn(mockForecast);

        // Act
        ForecastMain result = weatherController.getForecastByZipCode(zipCode);

        // Assert
        assertNotNull(result);
        assertEquals(mockForecast, result);

        verify(cacheManager).getCache("weatherForecasts");
        verify(cache).get(zipCode);
        verify(weatherService).getForecastByZipCode(zipCode);
    }

    @Test
    void testGetForecastByZipCode_WhenCacheIsNull() throws IOException {
        // Arrange
        String zipCode = "00000";
        var mockForecast = getForecastMain();

        when(cacheManager.getCache("weatherForecasts")).thenReturn(null);
        when(weatherService.getForecastByZipCode(zipCode)).thenReturn(mockForecast);

        // Act
        ForecastMain result = weatherController.getForecastByZipCode(zipCode);

        // Assert
        assertNotNull(result);
        assertEquals(mockForecast, result);

        verify(cacheManager).getCache("weatherForecasts");
        verify(weatherService).getForecastByZipCode(zipCode);
    }

    @Test
    void testGetForecastByZipCode_WhenServiceThrowsException() throws IOException {
        // Arrange
        String zipCode = "99999";
        var mockForecast = getForecastMain();

        when(cacheManager.getCache("weatherForecasts")).thenReturn(cache);
        when(cache.get(zipCode)).thenReturn(null);
        when(weatherService.getForecastByZipCode(zipCode)).thenThrow(new RuntimeException("Service failure"));

        // Act + Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                weatherController.getForecastByZipCode(zipCode));

        assertEquals("Service failure", exception.getMessage());

        verify(cacheManager).getCache("weatherForecasts");
        verify(weatherService).getForecastByZipCode(zipCode);
    }

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

    private ForecastMain getForecastMain() {
        return ForecastMain.builder().build();
    }

    private City getCity(String city) {
        Coord coord = new Coord(-22.9056, -47.0608);
        Temperature temp = new Temperature(30.0, 26.0, 34.0, 31.0, 10.0);
        Wind wind = new Wind(10.5);
        List<Weather> weatherList = new ArrayList<>();
        return new City(city, coord, temp, wind, weatherList);
    }
}
