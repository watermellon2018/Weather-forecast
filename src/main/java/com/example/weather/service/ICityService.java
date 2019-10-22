package com.example.weather.service;

import com.example.weather.model.Weather;

import java.util.List;

public interface ICityService {
    List<Weather> findAll();
}