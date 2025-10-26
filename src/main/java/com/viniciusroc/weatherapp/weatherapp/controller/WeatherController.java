package com.viniciusroc.weatherapp.weatherapp.controller;

import com.viniciusroc.weatherapp.weatherapp.model.City;
import com.viniciusroc.weatherapp.weatherapp.model.ForecastMain;
import com.viniciusroc.weatherapp.weatherapp.service.WeatherService;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.util.Properties;

@RestController
@RequestMapping("weather")
public class WeatherController {
    private final WeatherService weatherService;

    WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/city/{city}")
    public City getTemperatureByCity(@PathVariable String city) {
        try {
            return weatherService.getTemperatureByCity(city);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/geo")
    public String getTemperatureForGeo(@RequestParam String latitude, @RequestParam String longitude) {
        return "latitude and longitude";
    }

    @GetMapping("/forecast/{city}")
    public ForecastMain getForecastByCity(@PathVariable String city) {
        try {
            return weatherService.getForecastByCity(city);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
