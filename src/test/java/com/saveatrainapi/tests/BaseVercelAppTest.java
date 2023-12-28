package com.saveatrainapi.tests;

import com.saveatrainapi.utils.ConfigLoader;
import com.saveatrainapi.utils.DataLoader;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import java.util.List;

public class BaseVercelAppTest {
    @Test
    public void testAdminSales() {
        RestAssured.useRelaxedHTTPSValidation(); // ignore SSL-related validations

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.setBaseUri(ConfigLoader.getInstance().getSATOrder());
        requestSpecBuilder.addHeader(ConfigLoader.getInstance().getHeaderEVercel(), ConfigLoader.getInstance().getIEmailVercel());
        requestSpecBuilder.addHeader(ConfigLoader.getInstance().getHeaderTVercel(), ConfigLoader.getInstance().getTIdanVercel());
        requestSpecBuilder.addQueryParam(ConfigLoader.getInstance().getParamPage(), ConfigLoader.getInstance().getParamPageValue());
        requestSpecBuilder.addQueryParam(ConfigLoader.getInstance().getParamPerPage(), ConfigLoader.getInstance().getParamPerPageValue());
        requestSpecBuilder.log(LogDetail.ALL);
        RequestSpecification requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON);
        ResponseSpecification responseSpecification = responseSpecBuilder.build();

        RestAssured.defaultParser = Parser.JSON;

        Response response = RestAssured
                .given(requestSpecification)
                .when().get()
                .then().spec(responseSpecification)
                .extract().response();

        List<String> totalPrice = response.path(DataLoader.getInstance().getTotalPrice());

        double total = totalPrice.stream()
                .mapToDouble(Double::parseDouble)
                .sum();

        System.out.println("Total price of completed orders: " + total);

        // Get total_order_count property from response.
        int total_order_count = response.path(DataLoader.getInstance().getTotalOrders());

        // Print total_order_count property to console.
        System.out.println("Total order count from response: " + total_order_count);
    }

    @Test
    public void testAdminBalance() {
        RestAssured.useRelaxedHTTPSValidation(); // ignore SSL-related validations

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.setBaseUri(ConfigLoader.getInstance().getSATBalance());
        requestSpecBuilder.addHeader(ConfigLoader.getInstance().getHeaderEVercel(), ConfigLoader.getInstance().getIEmailVercel());
        requestSpecBuilder.addHeader(ConfigLoader.getInstance().getHeaderTVercel(), ConfigLoader.getInstance().getTIdanVercel());
        requestSpecBuilder.log(LogDetail.ALL);
        RequestSpecification requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON);
        ResponseSpecification responseSpecification = responseSpecBuilder.build();

        RestAssured.defaultParser = Parser.JSON;

        Response response = RestAssured
                .given(requestSpecification)
                .when().get()
                .then().spec(responseSpecification)
                .extract().response();

        List<Float> purchases = response.path(DataLoader.getInstance().getTotalPurchases());

        double totalPurchases = purchases.stream()
                .mapToDouble(Float::doubleValue)
                .sum();

        System.out.println("Sum of purchases: " + totalPurchases);
    }
}


