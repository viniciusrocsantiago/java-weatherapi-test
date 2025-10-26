package com.viniciusroc.weatherapp.weatherapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Coord {
    private Double lat;
    private Double lon;
}
