package com.example.weather.view;

import com.example.weather.model.Weather;
import com.example.weather.repo.WeatherInfoRepository;
import com.example.weather.service.CityService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import com.example.weather.repo.AnalogDB;
import com.example.weather.repo.CustomRepository;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("")
public class MainVIew extends VerticalLayout {

    //   private final CustomRepository repo;
    private Grid<Weather> tableInfo;

    private TextField cityF;

    private CityService cityService;
    private WeatherInfoRepository infoRepo;
    private TextField fromF;
    private TextField toF;

    //public MainVIew(CustomRepository repo) {
    @Autowired
    public MainVIew(CityService cityService, WeatherInfoRepository infoRepo) {
        //  this.repo = repo;

        this.cityService = cityService;
        this.infoRepo = infoRepo;

        //  this.repo = repo;
        this.tableInfo = new Grid<>(Weather.class);
        add(tableInfo);
        listCustomers();

        Label cityL = new Label("My city is = ");
        Label filterL = new Label("Get the forecast from A to B date: ");

        HorizontalLayout div1 = new HorizontalLayout();
        cityF = new TextField();
        div1.add(cityL, cityF);

        HorizontalLayout div2 = new HorizontalLayout();
        fromF = new TextField();
        toF = new TextField();
        Button filterBut = new Button("Filter", this::getWeekWeather);

        div2.add(fromF, toF, filterBut);

        HorizontalLayout div3 = new HorizontalLayout();
        Button weatherOnToday = new Button("Today", this::getWeatherToday);
        Label tdyWeatherL = new Label("What for today? ");

        div3.add(tdyWeatherL, weatherOnToday);

        HorizontalLayout div4 = new HorizontalLayout();
        Button save = new Button("Save");
        Label saveL = new Label("Issue a report");

        div4.add(saveL, save);

//            tableInfo.addItemClickListener(listener ->
//                    {
//                        if (listener.getClickCount() == 2) {
//                            Weather a = listener.getItem();
//
//                            List<String> list = new ArrayList<String>();
//                            Map<String, List<String>> parametersMap = new HashMap<String, List<String>>();
//                            // list.add(a.getDate());
//                            // list.add(a.getTime());
//                            // list.add(a.getTemp());
//                            //list.add(a.getDescr());
//                            parametersMap.put("prm_1", list);
//                            QueryParameters qp = new QueryParameters(parametersMap);
//
//                            RestTemplate restTemplate = new RestTemplate();
//
//                       /* final String baseUrl = "http://localhost:" + 8080 + "/info?date=" + a.getDate() + "&time=" + a.getTime();
//                        URI uri = null;
//                        try {
//                            uri = new URI(baseUrl);
//                        } catch (URISyntaxException e) {
//                            e.printStackTrace();
//                        }*/
//
//                            // ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
//
//                            //      getUI().ifPresent(ui -> ui.navigate(result));
//
//
//                            // getUI().ifPresent(ui -> ui.navigate("info"+"/?id=100"));
//                        }
//
//                    }
//            );

        add(div1, filterL, div2, div3, div4, tableInfo);
    }




        private void listCustomers() {
            tableInfo.setItems(cityService.findAll());
        }

        // TODO:: do for intarval of date!!!!! IMPORTANT
        private void getWeekWeather (ClickEvent event){

            List<Weather> answer;
            String searchCity = cityF.getValue();

            String dataFrom = fromF.getValue();
            String[] partOfData = dataFrom.split("\\.");

            LocalDate begin = LocalDate.of(Integer.valueOf(partOfData[2]), Integer.valueOf(partOfData[1]), Integer.valueOf(partOfData[0]));

            answer = (searchCity.isEmpty()) ? cityService.findAll() : cityService.getCityForecastByZIP(searchCity, begin);

            tableInfo.setItems(answer);
        }

        private void getWeatherToday(ClickEvent event){
            List<Weather> answer;
            String searchCity = cityF.getValue();

            answer = (searchCity.isEmpty()) ? cityService.findAll() : cityService.getCityForecastByZIP(searchCity, LocalDate.now());
            for (Weather x : answer)
                System.out.println(x.toString());

            tableInfo.setItems(answer);
            // ???

        }
    }
