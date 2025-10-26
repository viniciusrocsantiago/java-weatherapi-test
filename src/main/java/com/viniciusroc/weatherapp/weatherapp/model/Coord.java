package com.viniciusroc.weatherapp.weatherapp.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Coord {
    private Double lat;
    private Double lon;
}
