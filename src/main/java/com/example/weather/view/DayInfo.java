package com.example.weather.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Route("info")
@RequestMapping(value = "/info", method = GET)
@ResponseBody
public class DayInfo extends VerticalLayout{ //implements HasUrlParameter<String>{
    private TextField dateF, timeF, tempF, presF, dirWF, relatHumF;

    public DayInfo(){

        Text dateL = new Text("Data: ");
        Text timeL = new Text("Time: ");
        Text tempL = new Text("Temperature");
        Text presL = new Text("Press: ");
        Text dirWL = new Text("Direction of wind: ");
        Text relatHumL = new Text("Relat...:");

        dateF = new TextField();
        timeF = new TextField();
        tempF = new TextField();
        presF = new TextField();
        dirWF = new TextField();
        relatHumF = new TextField();

        HorizontalLayout dateLayout = new HorizontalLayout(dateL, dateF);
        HorizontalLayout timeLayout = new HorizontalLayout(timeL, timeF);
        HorizontalLayout tempLayout = new HorizontalLayout(tempL, tempF);
        HorizontalLayout pressLayout = new HorizontalLayout(presL, presF);
        HorizontalLayout dirWLayout = new HorizontalLayout(dirWL, dirWF);
        HorizontalLayout relatHumLayout = new HorizontalLayout(relatHumL, relatHumF);

        add(new H2("Info"), dateLayout, timeLayout,
                tempLayout, pressLayout, dirWLayout, relatHumLayout);

    }

    public String getBarBySimplePathWithRequestParam(
            @RequestParam("id") long id) {
        return "Get a specific Bar with id=" + id;
    }

//    @Override
//    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
//
//        Location location = event.getLocation();
//        QueryParameters queryParameters = location
//                .getQueryParameters();
//
//        Map<String, List<String>> parametersMap =
//                queryParameters.getParameters();
//
//        for(List<String> x : parametersMap.values()){
//            for(String y: x){
//                dateF.setValue(y);
//                System.out.println(y);
//
//            }
//        }
//
//   }
}
