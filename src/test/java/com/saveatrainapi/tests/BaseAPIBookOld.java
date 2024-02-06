package com.saveatrainapi.tests;

import com.saveatrainapi.pojoApiBook.*;
import com.saveatrainapi.utils.ConfigLoader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;

public class BaseAPIBookOld {
    @Test
    public void searchPricesApiBook(Method m) {
        String orgStation = "SAT_CH_ZU_TDHRA";
        String endStation = "SAT_DE_FR_OKZDY";
        String ddate = "2024-03-08 09:00";

        System.out.println("STARTING TEST: " + m.getName());
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.setBaseUri(ConfigLoader.getInstance().getApiBook());
        requestSpecBuilder.addHeader(ConfigLoader.getInstance().getHeaderE(), ConfigLoader.getInstance().getREmail());
        requestSpecBuilder.addHeader(ConfigLoader.getInstance().getHeaderT(), ConfigLoader.getInstance().getTRafal());
        requestSpecBuilder.log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();

        PassengerTypeAttributes passengerTypeAttributes = new PassengerTypeAttributes();
        passengerTypeAttributes.setType("Search::PassengerType::Adult");

        _0 _0 = new _0();
        _0.setAge(21);
        _0.setPassengerTypeAttributes(passengerTypeAttributes);

        SearchesPassengersAttributes searchesPassengersAttributes = new SearchesPassengersAttributes();
        searchesPassengersAttributes.set0(_0);

        OriginStationAttributes originStationAttributes = new OriginStationAttributes();
        originStationAttributes.setUid(orgStation);

        DestinationStationAttributes destinationStationAttributes = new DestinationStationAttributes();
        destinationStationAttributes.setUid(endStation);

        RouteAttributes routeAttributes = new RouteAttributes();
        routeAttributes.setOriginStationAttributes(originStationAttributes);
        routeAttributes.setDestinationStationAttributes(destinationStationAttributes);

        Example example = new Example();
        example.setDepartureDatetime(ddate);
        example.setReturnDepartureDatetime(ddate);
        example.setSearchesPassengersAttributes(searchesPassengersAttributes);
        example.setRouteAttributes(routeAttributes);

        Response response = given().
                body(example).
                when().
                post("/searches").
                then().
                spec(responseSpecification).
                assertThat().
                statusCode(200).
                extract().response();

        List<String> departureTimes = response.jsonPath().getList("results.departure_datetime");
        System.out.println(departureTimes.size());
        for (int i = 0; i < departureTimes.size(); i ++) {
            System.out.println(i + "." + "departure_time=" + departureTimes.get(i));
        }

        List<Object> prices = response.jsonPath().getList("results.best_price");
        System.out.println(prices.size());
        for (int i = 0; i < prices.size(); i ++) {
            System.out.println(i + "." + "second_class=" + prices.get(i));
        }
    }
}
