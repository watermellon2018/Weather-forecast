package com.example.weather.view;

import com.example.weather.model.Weather;
import com.example.weather.repo.WeatherInfoRepository;
import com.example.weather.service.CityService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Route("")
public class MainVIew extends VerticalLayout {

    private Grid<Weather> tableInfo;

    private CityService cityService;
    private WeatherInfoRepository infoRepo;
    private TextField fromF;
    private TextField toF;
    private TextField cityF;

    @Autowired
    public MainVIew(CityService cityService, WeatherInfoRepository infoRepo) {

        this.cityService = cityService;
        this.infoRepo = infoRepo;

        //this.tableInfo = new Grid<>(Weather.class);
        this.tableInfo = new Grid<>();
        tableInfo.addColumn( Weather::getCity).setHeader("City");
        tableInfo.addColumn( Weather::getDate).setHeader("Date");
        tableInfo.addColumn( Weather::getTime).setHeader("Time");
        tableInfo.addColumn( Weather::getTemp).setHeader("Temperature");
        tableInfo.addColumn( Weather::getPress).setHeader("Pressure");



        add(tableInfo);
        tableInfo.setItems(cityService.findAll());

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

        Button allData = new Button("All", this::getAll);

        add(div1, filterL, div2, div3, div4, allData, tableInfo);
    }

    private void getAll(ClickEvent event){
        tableInfo.setItems(cityService.findAll());
    }

    /**
     *  Get the weather for interval days (default 7 days)
     * **/

    private void getWeekWeather(ClickEvent event) {

        if(cityF.isEmpty()){
            Notification.show("Input city, pls");
            return;
        }

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

        answer = cityService.getForecastToday(searchCity, LocalDate.now());
       // answer = (searchCity.isEmpty()) ? cityService.getForecastToday() : cityService.getForecastToday(searchCity, LocalDate.now());
        for (Weather x : answer)
            System.out.println(x.toString());

        tableInfo.setItems(answer);

    }

    /** Save in pdf file the data which is in table now**/

    private void save(ClickEvent event) {
        List<Weather> curData = tableInfo.getDataProvider()
                .fetch(new Query<>())
                .collect(Collectors.toList());

        String searchCity = cityF.getValue();
        String beginDate = fromF.getValue();
        String endDate = toF.getValue();

        Document document = new Document();
        try{

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Weather.pdf"));
            document.open();

            document.add(new Paragraph("Weather " + ((beginDate.isEmpty()) ? "" : " from " + beginDate) + ((endDate.isEmpty()) ? "" : " to " + endDate) +
                    ((searchCity.isEmpty()) ? "" : " in " + searchCity)));

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(20f);
            table.setSpacingAfter(10f);

            float[] columnWidths = {1f, 1f, 1f, 1f, 1f};
            table.setWidths(columnWidths);

            table.addCell(insertCell("Date"));
            table.addCell(insertCell("Time"));
            table.addCell(insertCell("Temp"));
            table.addCell(insertCell("Description"));
            table.addCell(insertCell("Picture"));

            for(Weather x : curData){

                String[] dataDay = new String[] {x.getDate(), x.getTime(), x.getTemp(),
                        infoRepo.findById(x.getWeather_id()).getDescription()};


                for (int i = 0; i < dataDay.length; i++)
                    table.addCell(insertCell(dataDay[i]));


                String pathStr = infoRepo.findById(x.getWeather_id()).getUrlPicture();
                Path path = Paths.get(ClassLoader.getSystemResource(pathStr).toURI());
                Image img = Image.getInstance(path.toAbsolutePath().toString());
                table.addCell(img);
            }

            document.add(table);
            writer.close();
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert some string in cell in the table
     * **/

    private PdfPCell insertCell(String data){
        PdfPCell cell = new PdfPCell(new Paragraph(data));
        cell.setPaddingLeft(10);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        return cell;
    }
}