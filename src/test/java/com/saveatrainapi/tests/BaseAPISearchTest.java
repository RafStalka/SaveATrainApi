package com.saveatrainapi.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

import static io.restassured.RestAssured.*;

public class BaseAPISearchTest {

    @BeforeMethod
    public void beforeMethod(Method m) {
        System.out.println("STARTING TEST: " + m.getName());
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.setBaseUri("https://apisearch.saveatrain.com/search/SAT_FR_PA_WXNGQ/SAT_UK_LO_DXLIF");
        requestSpecBuilder.addParam("triptype", "1");
        requestSpecBuilder.addParam("passengers", "1");
        requestSpecBuilder.addParam("ddate", "2023-05-08");
        requestSpecBuilder.addParam("email", "test"+"@"+"saveatrain.com");
        requestSpecBuilder.addParam("password", "bALJat8279B");
        requestSpecBuilder.log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.TEXT).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void getPricesUpdatedAPISEARCH() {
        RestAssured.defaultParser = Parser.JSON;

        Response response = given(requestSpecification).
                when().get().
                then().spec(responseSpecification).
                assertThat().
                statusCode(200).
                extract().response();

        /*List<Object> outbounds = response.jsonPath().getList("result.outbound");
        System.out.println(outbounds.size());
        for (int i = 0; i < outbounds.size(); i ++) {
            System.out.println(outbounds.get(i));
        }

        String originStation = response.jsonPath().getString("result.outbound.origin_station[0]");
        System.out.println(originStation);

        List<String> originStations = response.jsonPath().getList("result.outbound.origin_station");
        System.out.println(originStations.size());
        for (int i = 0; i < originStations.size(); i ++) {
            System.out.println("Origin station ==> " + originStations.get(i));
        }

        String destinStation = response.jsonPath().getString("result.outbound.destin_station[0]");
        System.out.println(destinStation);

        List<String> destinStations = response.jsonPath().getList("result.outbound.destin_station");
        System.out.println(destinStations.size());
        for (int i = 0; i < destinStations.size(); i ++) {
            System.out.println("Destin station ==> " + destinStations.get(i));
        }*/

        String departuereTime = response.jsonPath().getString("result.outbound.departure_time[0]");
        //System.out.println(departuereTime);

        List<String> departureTimes = response.jsonPath().getList("result.outbound.departure_time");
        System.out.println(departureTimes.size());
        for (int i = 0; i < departureTimes.size(); i ++) {
            System.out.println("departure_time=" + departureTimes.get(i));
        }

        /*String departuereDate = response.jsonPath().getString("result.outbound.departure_date[0]");
        System.out.println(departuereDate);

        List<String> departureDates = response.jsonPath().getList("result.outbound.departure_date");
        System.out.println(departureDates.size());
        for (int i = 0; i < departureDates.size(); i ++) {
            System.out.println("departure_date=" + departureDates.get(i));
        }*/

        List<Object> prices = response.jsonPath().getList("result.outbound.price.second_class");
        System.out.println(prices.size());
        for (int i = 0; i < prices.size(); i ++) {
            System.out.println("second_class=" + prices.get(i));
        }

    }

    @Test
    public void getPricesUpdatedAPIBOOK() {
        RestAssured.defaultParser = Parser.JSON;

        Response responsePOST = given(requestSpecification).
                when().post("/searches").
                then().spec(responseSpecification).
                assertThat().
                statusCode(200).
                extract().response();



        Response responseGET = given(requestSpecification).
                when().get().
                then().spec(responseSpecification).
                assertThat().
                statusCode(200).
                extract().response();

    }
}
