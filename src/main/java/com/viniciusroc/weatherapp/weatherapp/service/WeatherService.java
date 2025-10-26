package com.viniciusroc.weatherapp.weatherapp.service;

import com.google.gson.Gson;
import com.viniciusroc.weatherapp.weatherapp.model.City;
import com.viniciusroc.weatherapp.weatherapp.model.ForecastMain;
import com.viniciusroc.weatherapp.weatherapp.model.WeatherDataSet;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    public List<WeatherDataSet> getTemperatureForCsv(){
        List<WeatherDataSet> weatherDataList = new ArrayList<>();
        String line;
        String delimiter = ",";

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/GHCND_sample_csv.csv"))) {
            if ((line = br.readLine()) != null) {
                System.out.println("Skipping Header: " + line);
            }

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(delimiter);
                if (fields.length == 9) {
                    try {
                        WeatherDataSet data = getWeatherDataSet(fields);
                        weatherDataList.add(data);
                    } catch (NumberFormatException e) {
                        System.err.println("Parsing error: " + line);
                    }
                } else {
                    System.err.println("Other parsing error: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return weatherDataList;
    }

    private static WeatherDataSet getWeatherDataSet(String[] fields) {
        String station = fields[0].trim();
        String stationName = fields[1].trim();
        double elevation = Double.parseDouble(fields[2].trim());
        double latitude = Double.parseDouble(fields[3].trim());
        double longitude = Double.parseDouble(fields[4].trim());
        String date = fields[5].trim();
        int tmax = Integer.parseInt(fields[6].trim());
        int tmin = Integer.parseInt(fields[7].trim());
        int prcp = Integer.parseInt(fields[8].trim());

        // Create the WeatherData object and add to the list
        return WeatherDataSet.builder()
                .station(station)
                .stationName(stationName)
                .elevation(elevation)
                .latitude(latitude)
                .longitude(longitude)
                .date(date)
                .tmax(tmax)
                .tmin(tmin)
                .prcp(prcp)
                .build();
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
