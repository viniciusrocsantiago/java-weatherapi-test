package com.viniciusroc.weatherapp.weatherapp.service;

import com.google.gson.Gson;
import com.viniciusroc.weatherapp.weatherapp.model.City;
import com.viniciusroc.weatherapp.weatherapp.model.ForecastMain;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
public class WeatherService {
    private final String url = "http://api.openweathermap.org/data/2.5/";
    private final String metric = "&units=imperial"; // Unit default: Kelvin, metric: Celsius, imperial: Fahrenheit
    private final String lang = "&lang=en";
    private final String cnt = "&cnt=10"; // Number of timestamps to be returned
    private final String apiKey = "b6e493b31a799226736092c1c7e72588"; // Free API_KEY valid per 30 days

    @Cacheable(value = "weatherForecasts")
    public ForecastMain getForecastByZipCode(String zipCode) {
        // Cache miss - this log will be executed only once during the cache configured time (30min)
        System.out.println("LOG: Cache Miss - zipCode: " + zipCode);

        String weatherUrl = url + "forecast?zip=" + zipCode + metric + cnt + lang + "&appid=" + apiKey;
        try {
            Result result = getResult(weatherUrl);
            return result.gson().fromJson(result.json(), ForecastMain.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public City getTemperatureByCity(String name) throws Exception {
        String encodedCityName = URLEncoder.encode(name, StandardCharsets.UTF_8);
        String weatherUrl = url + "weather?q=" + encodedCityName + metric + lang + "&appid=" + apiKey;

        try {
            Result result = getResult(weatherUrl);
            return result.gson().fromJson(result.json(), City.class);

        } catch (Exception e) {
            throw new Exception("Error: " + e);
        }
    }

    private static Result getResult(String weatherUrl) throws IOException {
        URL url = URI.create(weatherUrl).toURL();
        var conn = (HttpURLConnection) url.openConnection();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Not found. HTTP response code: " + conn.getResponseCode());
        }

        BufferedReader resp = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String json = resp.lines().collect(Collectors.joining());
        Gson gson = new Gson();
        return new Result(json, gson);
    }

    private record Result(String json, Gson gson) { }
}
