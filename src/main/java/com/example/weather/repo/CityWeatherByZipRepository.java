package com.example.weather.repo;


import com.example.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface CityWeatherByZipRepository extends JpaRepository<Weather, Integer> {


    // interval (7 day default)
    @Query("select w from Weather w where w.date>=:beginD and w.date<=:endD and w.city=:city")
    List<Weather> findByCityAndDate(@Param("beginD") LocalDate begin, @Param("endD") LocalDate end, @Param("city") String city);


    // certainly data
    List<Weather> findByCityAndDate(String city, LocalDate date);

    List<Weather> findByDate(LocalDate date);
//
//    @Query("select city, date from Weather ")
//    List<Weather> findAllBy();




}