package com.example.weather.service;

import com.example.weather.model.Weather;
import com.example.weather.repo.CityWeatherByZipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CityService implements ICityService {

    private CityWeatherByZipRepository cityRepo;

    @Autowired
    public CityService(CityWeatherByZipRepository rep){
        cityRepo = rep;
    }

    @Override
    public List<Weather> findAll() {
        return (List<Weather>) cityRepo.findAll();
    }

    public List<Weather> getCityForecastByZIP(String zip, LocalDate date){
        return cityRepo.findByZipCodeAndDate(zip, date);
    }
}
