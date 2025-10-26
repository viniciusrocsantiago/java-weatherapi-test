package com.viniciusroc.weatherapp.weatherapp.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Temperature {
    private Double temp;
    private Double temp_min;
    private Double temp_max;
    private Double feels_like;
    private Double humidity;
}
