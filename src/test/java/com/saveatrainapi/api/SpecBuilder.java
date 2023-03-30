package com.saveatrainapi.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.saveatrainapi.api.Route.*;

public class SpecBuilder {
    public static RequestSpecification getRequestSpecApi() {
        return new RequestSpecBuilder()
                .setBaseUri(BASEURI_PRICES)
                .addParam("triptype", 1)
                .addParam("passengers", 1)
                .addParam("ddate", "2023-3-13")
                .addParam("email", "test@saveatrain.com")
                .addParam("password", "bALJat8279B")
                .setContentType(ContentType.JSON)
                /*.addHeader("X-Agent-Email","paviel@saveatrain.com")
                .addHeader("X-Agent-Token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbnN0YW5zZV9jbGFzcyI6IlNhbGVzQWdlbnQiLCJpbnN0YW5jZV9pZCI6MTksImV4cGlyYXRpb25fdGltZSI6IjIwMjMtMDEtMDQgMjE6NTA6NTQgVVRDIn0.5nkvb8KVtMaBlAj2skmqOpiRXlhJcG1bKnOI8z1EE9M")*/
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
    }
    public static ResponseSpecification getResponseSpec() {
        return new ResponseSpecBuilder().log(LogDetail.ALL).build();
    }
}
