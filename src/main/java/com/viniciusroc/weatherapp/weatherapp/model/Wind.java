package com.viniciusroc.weatherapp.weatherapp.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Wind {
    public double speed;
}
