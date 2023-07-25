package com.saveatrainapi.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

import static io.restassured.RestAssured.*;

public class BaseAPISearchOld {
    String orgStation = "SAT_FR_NA_CZQYH";
    String endStation = "SAT_FR_BO_OMKAT";
    String ddate = "2023-07-24";

    @Test(priority = 1)
    public void getFirstDatePricesUpdatedAPISEARCH(Method m) {
        System.out.println("STARTING TEST: " + m.getName());
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.setBaseUri("https://apisearch.saveatrain.com/search/" + orgStation + "/" + endStation);
        requestSpecBuilder.addParam("triptype", "1");
        requestSpecBuilder.addParam("passengers", "1");
        requestSpecBuilder.addParam("ddate", ddate);
        requestSpecBuilder.addParam("email", "test"+"@"+"saveatrain.com");
        requestSpecBuilder.addParam("password", "bALJat8279B");
        requestSpecBuilder.log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.TEXT);
        //log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();


        RestAssured.defaultParser = Parser.JSON;

        Response response = given(requestSpecification).
                when().get().
                then().spec(responseSpecification).
                assertThat().
                statusCode(200).
                extract().response();

        List<String> departureTimes = response.jsonPath().getList("result.outbound.departure_time");
        System.out.println("Number of times: ==> " + departureTimes.size() + " from date: " + ddate);
        for (int i = 0; i < departureTimes.size(); i ++) {
            System.out.println(i + "." + "departure_time=" + departureTimes.get(i));
        }

        List<Object> prices = response.jsonPath().getList("result.outbound.price.second_class");
        System.out.println(prices.size());
        for (int i = 0; i < prices.size(); i ++) {
            System.out.println(i + "." + "second_class=" + prices.get(i));
        }
    }
}
