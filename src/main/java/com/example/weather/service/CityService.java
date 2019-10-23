package com.example.weather.service;

import com.example.weather.model.Weather;
import com.example.weather.repo.CityWeatherByZipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
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
        //return cityRepo.findAllBy();
    }

    public List<Weather> getForecastToday(String city, LocalDate date){
        return  (city.isEmpty()) ? cityRepo.findByDate(date) : cityRepo.findByCityAndDate(city, date);
       // return cityRepo.findByCityAndDate(city, date);
    }

    public List<Weather> getIntervalData(LocalDate b, LocalDate e, String city){
        return cityRepo.findByCityAndDate(b, e, city);
    }

    /*public Weather findCur(String city){
        return cityRepo.findByCityAndDateAndTime(city, LocalDate.now(), LocalTime.now());
    }*/
}
