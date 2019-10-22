package com.example.weather.repo;

import com.example.weather.model.InfoWeather;
import com.example.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomRepository extends JpaRepository<InfoWeather, Integer> {
   // List<InfoWeather> findByDescription(String desctr);
    List<InfoWeather> findById(int id);

}
