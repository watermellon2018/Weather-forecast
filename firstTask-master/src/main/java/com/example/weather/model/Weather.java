package com.example.weather.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "cities")
public class Weather {

    @Id
    @Column(name = "id")
    private String zipCode;

   // private String state;
    @Column(name = "city")
    private String city;
 //   private String weatherStation;
    @Column(name = "weather_id")
    private int weather_id;


//    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    @JoinColumn(name = "weather_id", nullable = false)
//    private InfoWeather weather_id;


    @Column(name = "temp")
    private String temperature;
   // private String relativeHumidity;
   // private String directionWind;
    @Column(name = "press")
    private String pressure;


    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    // дата время температура давление описание


    public Weather(String zipCode, LocalDate data, LocalTime time, String temp, String press, String city, int weatherID){
        this.date = data;
        this.time = time;
        temperature = temp;
        pressure = press;
        this.city = city;
        this.weather_id = weatherID;
        this.zipCode = zipCode;
    }

    public Weather(String zipCode, LocalDate data, LocalTime time, int temp, int press, String city, int weatherID){
        this.date = data;
        this.time = time;
        temperature = String.valueOf(temp);
        pressure = String.valueOf(press);
        this.city = city;
        this.weather_id = weatherID;
        this.zipCode = zipCode;
    }

    public Weather(){}

    public String getCity(){
        return city;
    }

    public String getZipCode(){
        return zipCode;
    }


    public String getDate(){
        return date.toString();
    }

    public String getTime(){
        return time.toString();
    }

    public String getTemp(){
        return temperature;
    }

    public String getPress(){
        return pressure;
    }
//
    public int getWeather_id() {
        return weather_id;
    }

    @Override
    public String toString(){
        return zipCode+" " +date+" "+time+" "+temperature+" "+pressure+" "+city;
    }
}

