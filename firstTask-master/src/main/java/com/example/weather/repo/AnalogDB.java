package com.example.weather.repo;

import com.example.weather.model.Weather;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

    public class AnalogDB {
        private static AnalogDB instance;
        private static List<Weather> data;

        public static AnalogDB getInstance() {
            if (instance == null) {
                instance = new AnalogDB();
                initData();
            }

            return instance;
        }

        private static void initData() {
            data = new ArrayList<>();

            for (int i = 0; i < 15; i++) {
              //  data.add(new Weather(LocalDate.now(), LocalTime.now(), String.valueOf(i + 15), String.valueOf(i * 15), "aaaaaa " + i));
            }
        }

        public List<Weather> getData() {
            return data;
        }

        private AnalogDB() {
        }

        ;

    }
