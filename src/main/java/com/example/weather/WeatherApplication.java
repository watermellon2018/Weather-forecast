package com.example.weather;

import com.example.weather.model.InfoWeather;
import com.example.weather.model.Weather;
//import com.example.weather.repo.CityWeatherByZipRepository;
import com.example.weather.repo.CityWeatherByZipRepository;
import com.example.weather.repo.WeatherInfoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;

@Configuration
@SpringBootApplication
public class WeatherApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(WeatherApplication.class, args);

        WeatherInfoRepository repository = context.getBean(WeatherInfoRepository.class);
        CityWeatherByZipRepository reposCity = context.getBean(CityWeatherByZipRepository.class);

        // save a couple of customers
        InfoWeather a = new InfoWeather("Cloudly", "cloudly.png");
        InfoWeather b = new InfoWeather("Snowly", "snow.png");
        InfoWeather c = new InfoWeather("Thundershtorm", "rain.png");
        InfoWeather d = new InfoWeather("Sunny", "sun.png");
        InfoWeather e = new InfoWeather("Murky", "murky.png");
        InfoWeather g = new InfoWeather("Foggy", "foggy.png");

        repository.save(a);
        repository.save(b);
        repository.save(c);
        repository.save(d);
        repository.save(e);
        repository.save(g);

        reposCity.save(new Weather( LocalDate.of(2019, 9,2), LocalTime.now(), 11, 759,
                "Moscow",  1 ));
        reposCity.save(new Weather( LocalDate.now(), LocalTime.now(), 11, 759,
                "Moscow",  1 ));
        reposCity.save(new Weather( LocalDate.now(), LocalTime.now(), 9,  758,
                "Petersburg",  3));
        reposCity.save(new Weather( LocalDate.now(), LocalTime.now(), 11, 750,
                "Ufa",  1 ));
        reposCity.save(new Weather( LocalDate.now(), LocalTime.now(), 5,  760,
                "Ekaterenburg",  2 ));
        reposCity.save(new Weather( LocalDate.now(), LocalTime.now(), 2,  756,
                "Birsk",  5 ));
        reposCity.save(new Weather( LocalDate.now(), LocalTime.now(), 25, 753,
                "Vladivostok",  4 ));
        reposCity.save(new Weather( LocalDate.of(2019, 10,25), LocalTime.now(), 20, 760,
                "Moscow",  3 ));
        reposCity.save(new Weather( LocalDate.of(2019, 10,26), LocalTime.now(), 15, 759,
                "Moscow",  1 ));






        //context.close();
    }
/*
    @Bean
    public CommandLineRunner loadData(CustomRepository repository) {
        return (args) -> {

            // save a couple of customers
            repository.save(new Weather(LocalDate.now(), LocalTime.now(), String.valueOf(5 + 15), String.valueOf(2 * 15), "Холодно "));
            repository.save(new Weather(LocalDate.now(), LocalTime.now(), String.valueOf(5 + 15), String.valueOf(2 * 15), "Тепло "));
            repository.save(new Weather(LocalDate.now(), LocalTime.now(), String.valueOf(5 + 15), String.valueOf(2 * 15), "Душно "));
            repository.save(new Weather(LocalDate.now(), LocalTime.now(), String.valueOf(5 + 15), String.valueOf(2 * 15), "Жарко "));

            // fetch all customers
//            log.info("Customers found with findAll():");
//            log.info("-------------------------------");
//            for (Weather customer : repository.findAll()) {
//                log.info(customer.toString());
//            }
//            log.info("");

            // fetch an individual customer by ID

         //   Weather customer = repository.findById(1).get();
//            log.info("Customer found with findOne(1L):");
//            log.info("--------------------------------");
//            log.info(customer.toString());
//            log.info("");
//
//            // fetch customers by last name
//            log.info("Customer found with findByLastNameStartsWithIgnoreCase('Bauer'):");
//            log.info("--------------------------------------------");
//            for (Weather bauer : repository
//                    .findByLastNameStartsWithIgnoreCase("Bauer")) {
//                log.info(bauer.toString());
//            }
//            log.info("");
        };
    }
*/
}
