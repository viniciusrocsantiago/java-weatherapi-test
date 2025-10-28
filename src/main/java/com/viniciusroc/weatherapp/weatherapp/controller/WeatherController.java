package com.viniciusroc.weatherapp.weatherapp.controller;

import com.viniciusroc.weatherapp.weatherapp.model.City;
import com.viniciusroc.weatherapp.weatherapp.model.ForecastMain;
import com.viniciusroc.weatherapp.weatherapp.service.WeatherService;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.Objects;

@RestController
@RequestMapping("weather")
public class WeatherController {
    private final WeatherService weatherService;
    private final CacheManager cacheManager;

    WeatherController(WeatherService weatherService, CacheManager cacheManager) {
        this.weatherService = weatherService;
        this.cacheManager = cacheManager;
    }

    /**
     * Get the forecast by zip code
     * @param zipCode Zip Code
     * @return The forecast of provided zip code. Limited to 10 timestamps.
     */
    @GetMapping("/forecast/{zipCode}")
    public ForecastMain getForecastByZipCode(@PathVariable String zipCode) {
        try {
            Cache weatherCache = cacheManager.getCache("weatherForecasts");
            boolean isCached = weatherCache != null && Objects.requireNonNull(weatherCache).get(zipCode) != null;

            if (isCached) {
                System.out.println("From Cache - zipCode: " + zipCode);
            } else {
                System.out.println("Service will be executed.");
            }
            return weatherService.getForecastByZipCode(zipCode);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Get Today's temperature by city
     * @param city City name
     * @return The object containing coordinates (latitude, longitude),
     * current temperature, temp min and max, humidity, wind speed
     */
    @GetMapping("/city/{city}")
    public City getTemperatureByCity(@PathVariable String city) {
        try {
            return weatherService.getTemperatureByCity(city);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
