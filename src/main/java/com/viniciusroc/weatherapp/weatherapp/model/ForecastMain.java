package com.viniciusroc.weatherapp.weatherapp.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ForecastMain {
    private Integer cnt;
    private List<Forecast> list;
}
