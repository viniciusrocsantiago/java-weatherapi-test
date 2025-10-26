package com.viniciusroc.weatherapp.weatherapp.model;

import lombok.*;

@Data
@Builder
public class WeatherDataSet {
    private String station;
    private String stationName;
    private double elevation;
    private double latitude;
    private double longitude;
    private String date;
    private int tmax;
    private int tmin;
    private int prcp;

    @Override
    public String toString() {
        return "WeatherDataSet{" +
                "station='" + station + '\'' +
                ", stationName='" + stationName + '\'' +
                ", date='" + date + '\'' +
                ", TMAX=" + (tmax / 10.0) + "°C" +
                ", TMIN=" + (tmin / 10.0) + "°C" +
                '}';
    }
}
