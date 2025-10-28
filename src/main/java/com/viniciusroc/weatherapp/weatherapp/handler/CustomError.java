package com.viniciusroc.weatherapp.weatherapp.handler;

public class CustomError extends RuntimeException {
    public CustomError(String message) {
        super(message);
    }
}
