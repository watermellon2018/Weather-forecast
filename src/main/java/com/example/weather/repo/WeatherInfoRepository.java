package com.example.weather.repo;

import com.example.weather.model.InfoWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherInfoRepository extends JpaRepository<InfoWeather, Integer> {
   // List<InfoWeather> findByDescription(String desctr);
    InfoWeather findById(int id);

}
