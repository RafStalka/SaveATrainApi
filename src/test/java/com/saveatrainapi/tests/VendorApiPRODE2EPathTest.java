package com.saveatrainapi.tests;

import com.saveatrainapi.utils.ConfigLoader;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
public class VendorApiPRODE2EPathTest {

    // Global variable for usage in other tests
    public static String connectionIdentifierGlobal;
    public static String extractedIdGlobal;
    public static String transferIdGlobal;
    public static String faresIdGlobal;
    public static String email = "test@saveatrain.com";

    @Test
    public void searchConnectionOutboundVendor() {
        String orgStation = "SAT_FR_PA_WXNGQ";
        String endStation = "SAT_FR_HE_TWOHA";
        String ddate = "2023-12-28 16:00";

        RestAssured.useRelaxedHTTPSValidation(); // ignore SSL-related validations

        String requestBody = "{" +
                "\"search\": {" +
                "\"departure_datetime\": \"2024-01-29 07:00\"," +
                "\"searches_passengers_attributes\": {" +
                "\"0\": {" +
                "\"age\": 21," +
                "\"passenger_type_attributes\": {" +
                "\"type\": \"Search::PassengerType::Adult\"" +
                "}" +
                "}" +
                "}," +
                "\"route_attributes\": {" +
                "\"origin_station_attributes\": {" +
                "\"uid\": \"SAT_CH_ZU_TDHRA\"" +
                "}," +
                "\"destination_station_attributes\": {" +
                "\"uid\": \"SAT_DE_FR_OKZDY\"" +
                "}" +
                "}" +
                "}" +
                "}";

        Response response = given().
                header("X-Agent-Email", ConfigLoader.getInstance().getREmail()).
                header("X-Agent-Token", ConfigLoader.getInstance().getTRafal()).
                header("Content-Type", "application/json").
                body(requestBody).
                when().
                post(ConfigLoader.getInstance().getVendor() + "/searches").
                then().
                assertThat().
                statusCode(200).
                extract().
                response();

        // Extract a value given its path in the JSON response
        JsonPath jsonPath = new JsonPath(response.asString());
        String connectionIdentifierValue = jsonPath.getString("identifier");
        connectionIdentifierGlobal = connectionIdentifierValue;

        String resultsIdValue = jsonPath.getString("results.id[0]");
        extractedIdGlobal = resultsIdValue;


        System.out.println("Extracted SEARCH IDENTIFIER value: " + connectionIdentifierValue);
        System.out.println("Extracted RESULT ID value: " + resultsIdValue);

        System.out.println("Full response body: " + response.prettyPrint());
    }


    @Test(dependsOnMethods = {"searchConnectionOutboundVendor"})
    public void searchConnectionTransfersAndFaresOutboundVendor() {
        RestAssured.useRelaxedHTTPSValidation(); // ignore SSL-related validations

        Response response = given().
                header("X-Agent-Email", ConfigLoader.getInstance().getREmail()).
                header("X-Agent-Token", ConfigLoader.getInstance().getTRafal()).
                header("Content-Type", "application/json").
                when().
                get(ConfigLoader.getInstance().getVendor() + "/searches/" + connectionIdentifierGlobal + "/results/" + extractedIdGlobal + "/sub_routes").
                then().
                assertThat().
                statusCode(200).
                extract().
                response();

        // Extract a value given its path in the JSON response
        JsonPath jsonPath = new JsonPath(response.asString());
        String transferIdValue = jsonPath.getString("transfers[0].id");
        transferIdGlobal = transferIdValue;

        String faresIdValue = jsonPath.getString("transfers[0].fares[0].id");
        faresIdGlobal = faresIdValue;


        System.out.println("Extracted TRANSFER value: " + transferIdValue);
        System.out.println("Extracted FARE value: " + faresIdValue);

        System.out.println("Full response body: " + response.prettyPrint());
    }

    @Test(dependsOnMethods = {"searchConnectionTransfersAndFaresOutboundVendor"})
    public void confirmSelectionOutboundVendor() {
        RestAssured.useRelaxedHTTPSValidation(); // ignore SSL-related validations
        String requestBody = String.format(
                "{" +
                        "\"select_results_attributes\": {" +
                        "\"search_identifier\": \"%s\"," +
                        "\"result_id\": %d," +
                        "\"transfers_attributes\": {" +
                        "\"0\": {" +
                        "\"id\": %d," +
                        "\"fare_id\": %d" +
                        "}" +
                        "}" +
                        "}" +
                        "}",
                connectionIdentifierGlobal, Integer.parseInt(extractedIdGlobal), Integer.parseInt(transferIdGlobal), Integer.parseInt(faresIdGlobal)
        );

        Response response = given().
                header("X-Agent-Email", ConfigLoader.getInstance().getREmail()).
                header("X-Agent-Token", ConfigLoader.getInstance().getTRafal()).
                header("Content-Type", "application/json").
                body(requestBody).
                log().all().
                when().
                post(ConfigLoader.getInstance().getVendor() + "/searches/" + connectionIdentifierGlobal + "/confirm_selection").
                then().
                log().ifValidationFails().
                assertThat().
                statusCode(200).
                extract().
                response();

        System.out.println("Full response body: " + response.prettyPrint());
    }

    @Test(dependsOnMethods = {"confirmSelectionOutboundVendor"})
    public void createBookingOutboundVendor() {
        RestAssured.useRelaxedHTTPSValidation(); // ignore SSL-related validations
        String requestBody = String.format(
                "{" +
                        "\"booking\": {" +
                        "\"search_identifier\": \"%s\"," +
                        "\"order_customer_attributes\": {" +
                        "\"email\": \"%s\"," +
                        "\"fname\": \"tester\"," +
                        "\"lname\": \"automation\"," +
                        "\"gender\": \"M\"," +
                        "\"mobile\": \"93242412\"" +
                        "}," +
                        "\"passengers_attributes\": {" +
                        "\"0\": {" +
                        "\"title\": \"Mr\"," +
                        "\"fname\": \"ror\"," +
                        "\"lname\": \"dev\"," +
                        "\"birthdate\": \"1980-12-25\"," +
                        "\"country\": \"Germany\"," +
                        "\"gender\": \"M\"," +
                        "\"passenger_type_attributes\": {" +
                        "\"age\": 29," +
                        "\"type\": \"Search::PassengerType::Adult\"}" +
                        "}" +
                        "}," +
                        "\"seat_preference_attributes\": {" +
                        "\"seat_preference_outbound\": null," +
                        "\"seat_preference_inbound\": null" +
                        "}" +
                        "}" +
                        "}", connectionIdentifierGlobal, email);

        Response response = given().
                header("X-Agent-Email", ConfigLoader.getInstance().getREmail()).
                header("X-Agent-Token", ConfigLoader.getInstance().getTRafal()).
                header("Content-Type", "application/json").
                body(requestBody).
                log().all().
                when().
                post(ConfigLoader.getInstance().getVendor() + "/bookings").
                then().
                log().ifValidationFails().
                assertThat().
                statusCode(200).
                extract().
                response();

        System.out.println("Full response body: " + response.prettyPrint());
    }

}
