package com.saveatrainapi.tests;

import com.saveatrainapi.utils.ExcelReader;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

import static io.restassured.RestAssured.*;

public class BaseAPISearchTest {

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
    public void probaAPISEARCH(Method m, String col1, String col2, String col3) {
        stepStartTest(m.getName());
        stepPerformApiRequest(col1, col2, col3);
    }

    @Step("Starting test: {testName}")
    public void stepStartTest(String testName) {
        System.out.println("STARTING TEST: " + testName);
        Allure.addAttachment("Test Log", "STARTING TEST: " + testName);
    }

    @Step("Performing API request for col1={col1}, col2={col2}, col3={col3}")
    public void stepPerformApiRequest(String col1, String col2, String col3) {
        System.out.println("Performing API request for col1=" + col1 + ", col2=" + col2 + ", col3=" + col3);
        Allure.addAttachment("Test Log", "Performing API request for col1=" + col1 + ", col2=" + col2 + ", col3=" + col3);
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.setBaseUri("https://apisearch.saveatrain.com/search/" + col1 + "/" + col2);
        requestSpecBuilder.addParam("triptype", "1");
        requestSpecBuilder.addParam("passengers", "1");
        requestSpecBuilder.addParam("ddate", col3);
        requestSpecBuilder.addParam("email", "test"+"@"+"saveatrain.com");
        requestSpecBuilder.addParam("password", "bALJat8279B");
        requestSpecBuilder.log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.TEXT);
        RestAssured.responseSpecification = responseSpecBuilder.build();

        RestAssured.defaultParser = Parser.JSON;

        Response response = given(requestSpecification).
                when().get().
                then().spec(responseSpecification).
                assertThat().
                statusCode(200).
                extract().response();

        List<String> departureTimes = response.jsonPath().getList("result.outbound.departure_time");
        stepExtractDepartureTimes(departureTimes, col3);

        List<Object> prices = response.jsonPath().getList("result.outbound.price.second_class");
        stepExtractPrices(prices);
    }

    @Step("Extracting departure times from the API response")
    public void stepExtractDepartureTimes(List<String> departureTimes, String col3) {
        System.out.println("Number of times: ==> " + departureTimes.size() + " from date: " + col3);
        Allure.addAttachment("Test Log", "Number of times: ==> " + departureTimes.size() + " from date: " + col3);
        for (int i = 0; i < departureTimes.size(); i ++) {
            System.out.println(i + "." + "departure_time=" + departureTimes.get(i));
        }
    }

    @Step("Extracting prices from the API response")
    public void stepExtractPrices(List<Object> prices) {
        System.out.println(prices.size());
        Allure.addAttachment("Test Log", String.valueOf(prices.size()));
        for (int i = 0; i < prices.size(); i ++) {
            System.out.println(i + "." + "second_class=" + prices.get(i));
        }
    }
}

    /*@Test(dataProvider = "excelData")
    public void probaAPISEARCH(Method m, String col1, String col2, String col3) {
        System.out.println("STARTING TEST: " + m.getName());
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.setBaseUri("https://apisearch.saveatrain.com/search/" + col1 + "/" + col2);
        requestSpecBuilder.addParam("triptype", "1");
        requestSpecBuilder.addParam("passengers", "1");
        requestSpecBuilder.addParam("ddate", col3);
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
        System.out.println("Number of times: ==> " + departureTimes.size() + " from date: " + col3);
        for (int i = 0; i < departureTimes.size(); i ++) {
            System.out.println(i + "." + "departure_time=" + departureTimes.get(i));
        }

        List<Object> prices = response.jsonPath().getList("result.outbound.price.second_class");
        System.out.println(prices.size());
        for (int i = 0; i < prices.size(); i ++) {
            System.out.println(i + "." + "second_class=" + prices.get(i));
        }

    }

}*/
