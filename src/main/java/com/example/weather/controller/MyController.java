package com.example.weather.controller;

import com.example.weather.model.Weather;
import com.example.weather.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyController {

    @Autowired
    private ICityService cityService;

    //@GetMapping("/showCities")
    @RequestMapping("/hello")
    public String findCities(Model model) {

       List<Weather> cities = (List<Weather>) cityService.findAll();
       for(Weather x : cities)
           System.out.println(x.toString());

        model.addAttribute("cities", cities);

        return "hello";
    }
}
