package com.saveatrainapi.api;

import io.restassured.response.Response;

import static com.saveatrainapi.api.SpecBuilder.getRequestSpecApi;
import static com.saveatrainapi.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public class RestResource {
    public static Response putApi(String path, Object requestExample) {
        return given(getRequestSpecApi())
                .body(requestExample)
                .when()
                .put(path)
                .then()
                .spec(getResponseSpec())
                .extract()
                .response();
    }
    public static Response postApi(String path, Object requestExample) {
        return given(getRequestSpecApi())
                .body(requestExample)
                .when()
                .post(path)
                .then()
                .spec(getResponseSpec())
                .extract()
                .response();
    }
    public static Response getApi(String path){
        return given(getRequestSpecApi()).
                when().get(path).
                then().spec(getResponseSpec()).
                extract().
                response();
        }

    public static Response updateApi(String path, Object requestPlaylist){
        return given(getRequestSpecApi()).
                body(requestPlaylist).
                when().put(path).
                then().spec(getResponseSpec()).
                extract().
                response();
        }
}
