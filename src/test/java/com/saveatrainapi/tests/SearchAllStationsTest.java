package com.saveatrainapi.tests;

import com.saveatrainapi.utils.ConfigLoader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class SearchAllStationsTest {
    @Test()
    public void searchConnectionTransfersAndFaresOutboundVendor() throws JSONException {
        RestAssured.useRelaxedHTTPSValidation(); // ignore SSL-related validations

        Response response = given().
                header("X-Agent-Email", ConfigLoader.getInstance().getREmail()).
                header("X-Agent-Token", ConfigLoader.getInstance().getTRafal()).
                header("Content-Type", "application/json").
                when().
                get("https://vendor-production.saveatrain.com/api/vendor_stations").
                then().
                assertThat().
                statusCode(200).
                //test connection
                extract().
                response();
        String stringResponse = response.getBody().asString();
        JSONArray jsonArray = new JSONArray(stringResponse);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if (jsonObject.getBoolean("searchable")) {
                String uid = jsonObject.getString("uid");
                String name = jsonObject.getString("name");

                // print uid and name to console
                System.out.println(uid + "," + name);
                //System.out.println("Name: " + name);
            }
        }
    }
}
