package com.viniciusroc.weatherapp.weatherapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Getter
public class City {
    private String name;
    private Coord coord;
    private Temperature main;
    private Wind wind;
    private List<Weather> weather;
}
