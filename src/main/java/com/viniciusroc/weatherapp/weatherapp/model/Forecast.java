package com.viniciusroc.weatherapp.weatherapp.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class Forecast {
    private Integer dt;
    private Temperature main;
    private Wind wind;
    private List<Weather> weather;
}
