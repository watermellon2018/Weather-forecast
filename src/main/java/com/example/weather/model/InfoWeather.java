package com.example.weather.model;

import javax.persistence.*;

@Entity
@Table(name = "weather_info")
public class InfoWeather {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "picture_url")
    private String pictureURL = "nothing";

    public InfoWeather(String decr, String path){
        pictureURL = path;
        description = decr;
    }

    public InfoWeather(){

    }

    public String getUrlPicture(){
        return pictureURL;
    }

    public String getDescription(){
        return description;
    }

    @Override
    public String toString(){
        return description;
    }
}