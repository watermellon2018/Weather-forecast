package com.example.weather.repo;


import com.example.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Repository
public interface CityWeatherByZipRepository extends JpaRepository<Weather, Integer> {

    //@Query("SELECT n FROM weather_info n join cities a on n.id = a.weatherid order by a.zip_code  ")
    //List<Weather> findAllByWeatherIDOrderByZipCode();
    // List<Weather> findByWeatherID(InfoWeather i);
//    @Query("SELECT e, d  FROM Weather e LEFT JOIN e.weatherID d")
//    List<Weather> aa();

    // List<Weather> findAllBy

    // interval (7 day default)
    @Query("select w from Weather w where w.date>=:beginD and w.date<=:endD and w.city=:city")
    List<Weather> findByCityAndDate(@Param("beginD") LocalDate begin, @Param("endD") LocalDate end, @Param("city") String city);


    // certainly data
    List<Weather> findByCityAndDate(String city, LocalDate date);

//    @Query("select w from Weather w group by w.date order by w.time")
    //Weather findByCityAndDateAndTime(@Param("city") String city, @Param("date") LocalDate date, @Param("time") LocalTime time);

}