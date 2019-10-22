package com.example.weather.view;

import com.example.weather.model.Weather;
import com.example.weather.repo.WeatherInfoRepository;
import com.example.weather.service.CityService;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
//
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Route("")
public class MainVIew extends VerticalLayout {

    //   private final CustomRepository repo;
    private Grid<Weather> tableInfo;


    private CityService cityService;
    private WeatherInfoRepository infoRepo;
    private TextField fromF;
    private TextField toF;
    private TextField cityF;


    @Autowired
    public MainVIew(CityService cityService, WeatherInfoRepository infoRepo) {
        //  this.repo = repo;

        this.cityService = cityService;
        this.infoRepo = infoRepo;

        //  this.repo = repo;
        this.tableInfo = new Grid<>(Weather.class);
        add(tableInfo);
        listCustomers();

        Label cityL = new Label("My city is = "); // have to imput the zip of the city
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
        Button save = new Button("Save", this::save);
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

    private void getWeekWeather(ClickEvent event) {

        if(cityF.isEmpty()){
            Notification.show("Input city, pls");
            return;
        }

        // default interval from current date to date+7
        List<Weather> answer;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String searchCity = cityF.getValue();
        String dateFrom = fromF.getValue();
        String dateTo = toF.getValue();

        LocalDate begin = (dateFrom.isEmpty()) ? LocalDate.now() : LocalDate.parse(dateFrom, dtf);
        LocalDate end = (dateTo.isEmpty()) ? begin.plusDays(7) : LocalDate.parse(dateTo, dtf);


        answer = cityService.getIntervalData(begin, end, searchCity);
        tableInfo.setItems(answer);
    }

    /**
     * If the city is not found then return all list
     * **/
    private void getWeatherToday(ClickEvent event) {
        List<Weather> answer;
        String searchCity = cityF.getValue();

        answer = (searchCity.isEmpty()) ? cityService.findAll() : cityService.getForecastToday(searchCity, LocalDate.now());
        for (Weather x : answer)
            System.out.println(x.toString());

        tableInfo.setItems(answer);

    }

    private void save(ClickEvent event) {
        List<Weather> curData = tableInfo.getDataProvider()
                .fetch(new Query<>())
                .collect(Collectors.toList());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String searchCity = cityF.getValue();
        String beginDate = fromF.getValue();
        String endDate = toF.getValue();

        LocalDate begin = (beginDate.isEmpty()) ? LocalDate.now() : LocalDate.parse(beginDate, dtf);
        LocalDate end = (endDate.isEmpty()) ? begin.plusDays(7) : LocalDate.parse(endDate, dtf);

        Document document = new Document();
        try
        {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Weather.pdf"));
            document.open();

            document.add(new Paragraph("Weather from " + beginDate + " to " + endDate +
                    " in " + searchCity));

            PdfPTable table = new PdfPTable(5); // 3 columns.
            table.setWidthPercentage(100); //Width 100%
            table.setSpacingBefore(10f); //Space before table
            table.setSpacingAfter(10f); //Space after table

            //Set Column widths
            float[] columnWidths = {1f, 1f, 1f, 1f, 1f};
            table.setWidths(columnWidths);

            for(Weather x : curData){
                String[] dataDay = new String[]{x.getDate(), x.getTime(), x.getTemp(),
                        infoRepo.findById(x.getWeather_id()).toString(), "later"};

                for (int i = 0; i < dataDay.length; i++) {

                    PdfPCell cell1 = new PdfPCell(new Paragraph(dataDay[i]));
                    cell1.setPaddingLeft(10);
                    cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cell1);
                }
            }

            document.add(table);
            writer.close();
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }

    }
}