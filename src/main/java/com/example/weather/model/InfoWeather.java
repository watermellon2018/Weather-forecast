package com.example.weather.model;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Table(name = "weather_info")
public class InfoWeather {

    public InfoWeather(String s){
        description = s;
    }

    public InfoWeather(){

    }
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "picture_url")
    private Blob pictureURL;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "weatherInfo")
//    private List<Weather> cityList ;


    @Override
    public String toString(){
        return description;
    }
}
