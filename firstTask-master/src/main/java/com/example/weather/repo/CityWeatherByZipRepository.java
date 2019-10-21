package com.example.weather.repo;


import com.example.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface CityWeatherByZipRepository extends JpaRepository<Weather, Integer> {

    //@Query("SELECT n FROM weather_info n join cities a on n.id = a.weatherid order by a.zip_code  ")
    //List<Weather> findAllByWeatherIDOrderByZipCode();
    // List<Weather> findByWeatherID(InfoWeather i);
//    @Query("SELECT e, d  FROM Weather e LEFT JOIN e.weatherID d")
//    List<Weather> aa();

    // List<Weather> findAllBy


    List<Weather> findByZipCodeAndDate(String zip, LocalDate date);

}