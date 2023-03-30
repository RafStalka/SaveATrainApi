package com.saveatrainapi.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;

public class BaseAPIBookTest {
    @BeforeMethod
    public void beforeMethod(Method m) {
        System.out.println("STARTING TEST: " + m.getName());
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.setBaseUri("https://apibook.saveatrain.com/api");
        requestSpecBuilder.addHeader("X-Agent-Email", "rafal@saveatrain.com");
        requestSpecBuilder.addHeader("X-Agent-Token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbnN0YW5zZV9jbGFzcyI6IlNhbGVzQWdlbnQiLCJpbnN0YW5jZV9pZCI6bnVsbCwiZXhwaXJhdGlvbl90aW1lIjoiMjAyMy0wNC0yMSAxOTowMjoyMCBVVEMifQ.6o5xnjCw5DVgqs7dOlbb-nCHncn9rszXIVCugi-8ziA");
        requestSpecBuilder.log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void searchPricesApiBook() {
        String payload = "{\n" +
                "    \"departure_datetime\": \"2023-03-31 07:00\",\n" +
                "    \"return_departure_datetime\": \"2023-03-31 07:00\",\n" +
                "    \"searches_passengers_attributes\": {\n" +
                "      \"0\": {\n" +
                "        \"age\": 21,\n" +
                "        \"passenger_type_attributes\": {\n" +
                "          \"type\": \"Search::PassengerType::Adult\"\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"route_attributes\": {\n" +
                "      \"origin_station_attributes\": {\n" +
                "        \"uid\": \"SAT_FR_PA_WXNGQ\"\n" +
                "      },\n" +
                "      \"destination_station_attributes\": {\n" +
                "        \"uid\": \"SAT_FR_CH_IFSUK\"\n" +
                "      }\n" +
                "    }\n" +
                "  }";
        Response response = given().
                body(payload).
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
            System.out.println("departure_time=" + departureTimes.get(i));
        }

        List<Object> prices = response.jsonPath().getList("results.best_price");
        System.out.println(prices.size());
        for (int i = 0; i < prices.size(); i ++) {
            System.out.println("second_class=" + prices.get(i));
        }
    }
}
