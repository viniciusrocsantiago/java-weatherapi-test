package com.viniciusroc.weatherapp.weatherapp.service;

import com.google.gson.Gson;
import com.viniciusroc.weatherapp.weatherapp.model.City;
import com.viniciusroc.weatherapp.weatherapp.model.ForecastMain;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class WeatherService {
    private final String url = "http://api.openweathermap.org/data/2.5/";
    private final String metric = "&units=metric";
    private final String lang = "&lang=pt_br";
    private final String apiKey = "b6e493b31a799226736092c1c7e72588"; // API_KEY


    public City getTemperatureByCity(String name) throws Exception {
        String weatherUrl = url + "weather?q=" + name + metric + lang + "&appid=" + apiKey;

        try {
            Result result = getResult(weatherUrl);
            return result.gson().fromJson(result.json(), City.class);

        } catch (Exception e) {
            throw new Exception("Error: " + e);
        }
    }

    public ForecastMain getForecastByCity(String city) throws IOException {
        String weatherUrl = url + "forecast?q=" + city + metric + lang + "&appid=" + apiKey;

        try {
            Result result = getResult(weatherUrl);
            return result.gson().fromJson(result.json(), ForecastMain.class);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Result getResult(String weatherUrl) throws IOException {
        URL url = URI.create(weatherUrl).toURL();
        var conn = (HttpURLConnection) url.openConnection();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("HTTP response code: " + conn.getResponseCode());
        }

        BufferedReader resp = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String json = resp.lines().collect(Collectors.joining());
        Gson gson = new Gson();
        return new Result(json, gson);
    }

    private record Result(String json, Gson gson) {
    }

    private String getUrl() throws IOException {
        var props = getAppProperties();
        return props.getProperty("openweather.url");
    }

    private String getLang() throws IOException {
        var props = getAppProperties();
        return props.getProperty("openweather.lang");
    }

    private String getMetric() throws IOException {
        var props = getAppProperties();
        return props.getProperty("openweather.metric");
    }

    private Properties getAppProperties() throws IOException {
        Properties props = new Properties();
        FileInputStream input = new FileInputStream("src/main/resources/application.properties");
        props.load(input);
        input.close();
        return  props;
    }
}
