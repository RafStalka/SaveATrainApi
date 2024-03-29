package com.saveatrainapi.tests;

import com.saveatrainapi.pojoApiBook.*;
import com.saveatrainapi.utils.ExcelReader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;

public class BaseAPIBookTest {

    @DataProvider(name = "excelData")
    public Object[][] getExcelData() {
        // Replace the file path, sheet name, and date format with your actual values
        String filePath = "/Users/rafalst/IdeaProjects/SaveATrainAPI/src/test/resources/automation.xlsx";
        String sheetName = "Sheet1";
        String dateFormat = "yyyy-MM-dd";

        // Call the readExcelData method to get the data from Excel
        return ExcelReader.readExcelData(filePath, sheetName, dateFormat);
    }

    @Test(dataProvider = "excelData")
    public void searchPricesApiBook(Method m, String col1, String col2, String col3, String col4) {
        /*String orgStation = "SAT_DE_FR_OKZDY";
        String endStation = "SAT_CH_ZU_TDHRA";
        String ddate = "2023-08-05 02:00";*/

        System.out.println("STARTING TEST: " + m.getName());
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.setBaseUri("https://apibook.saveatrain.com/api");
        requestSpecBuilder.addHeader("X-Agent-Email", "rafal@saveatrain.com");
        requestSpecBuilder.addHeader("X-Agent-Token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbnN0YW5zZV9jbGFzcyI6IlNhbGVzQWdlbnQiLCJpbnN0YW5jZV9pZCI6MzAsImV4cGlyYXRpb25fdGltZSI6IjIwMjQtMDQtMjQgMTU6MTQ6NTIgVVRDIn0.CImLWJtI_dahynJmbtDk5ko13z1qQaLeQ2COnx7ZCok");
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
        originStationAttributes.setUid(col1);

        DestinationStationAttributes destinationStationAttributes = new DestinationStationAttributes();
        destinationStationAttributes.setUid(col2);

        RouteAttributes routeAttributes = new RouteAttributes();
        routeAttributes.setOriginStationAttributes(originStationAttributes);
        routeAttributes.setDestinationStationAttributes(destinationStationAttributes);

        Example example = new Example();
        example.setDepartureDatetime(col3 + col4);
        example.setReturnDepartureDatetime(col3 + col4);
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
