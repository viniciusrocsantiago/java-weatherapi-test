package com.viniciusroc.weatherapp.weatherapp.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class City {
    private String name;
    private Coord coord;
    private Temperature main;
    private Wind wind;
    private List<Weather> weather;
}
