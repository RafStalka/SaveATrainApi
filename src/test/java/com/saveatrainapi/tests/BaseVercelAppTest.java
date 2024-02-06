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
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.List;

public class BaseVercelAppTest {

    private static String token;

    @Test
    public void testAuth() throws JSONException {

        // Create request specification
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.setBaseUri("https://apibook.saveatrain.com/api");

        // Build request specification
        RequestSpecification requestSpec = requestSpecBuilder.build();

        // Create request body as a JSONObject
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", "idan@saveatrain.com");
        requestBody.put("password", "Aa123456");

        // Execute request and store response
        Response response = RestAssured
                .given()
                .spec(requestSpec)
                .body(requestBody.toString())
                .post("/admin/sessions");

        // Create response specification
        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectBody("access_token", Matchers.notNullValue())
                .build();

        // Validate response
        response.then().spec(responseSpec);

        // Parse response JSON
        JSONObject jsonResponse = new JSONObject(response.asString());

        // Extract token and store it in the static field
        try {
            // Set the token
            token = jsonResponse.getString("access_token");
            System.out.println("Access Token: " + token); // Log token
        } catch(JSONException e) {
            // Handle exception here
            System.out.println("Error parsing access token from response");
            e.printStackTrace();
        }
    }

    @Test
    public void testAdminSales() throws InterruptedException {
        RestAssured.useRelaxedHTTPSValidation(); // ignore SSL-related validations

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.setBaseUri(ConfigLoader.getInstance().getPRODOrder());
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
                .when().get();
        Thread.sleep(30000);
                response.then().spec(responseSpecification)
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
    public void testAdminBalance() throws InterruptedException {
        RestAssured.useRelaxedHTTPSValidation(); // ignore SSL-related validations

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.setBaseUri(ConfigLoader.getInstance().getPRODBalance());
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
                .when().get();
        Thread.sleep(15000);
                response.then().spec(responseSpecification)
                .extract().response();

        List<Float> purchases = response.path(DataLoader.getInstance().getTotalPurchases());

        double totalPurchases = purchases.stream()
                .mapToDouble(Float::doubleValue)
                .sum();

        System.out.println("Sum of purchases: " + totalPurchases);
    }
}


