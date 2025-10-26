package com.viniciusroc.weatherapp.weatherapp.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Weather {
    private String main;
    private String description;
}
