package com.saveatrainapi.tests;

import com.saveatrainapi.utils.ConfigLoader;
import com.saveatrainapi.utils.ExcelReaderConnections;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

import static io.restassured.RestAssured.given;

public class ConnectionsTest {
    private final List<String> allIdentifiers = new ArrayList<>();
    private final Map<String, List<String>> identifierToIdsMap = new HashMap<>();
    private final List<String> allIds = new ArrayList<>();

    @DataProvider(name = "excelDataProvider")
    public Object[][] excelDataProvider() {
        List<Object[]> dataList = new ArrayList<>();
        try {
            String filePath = "/Users/rafalst/IdeaProjects/SaveATrainAPI/src/test/resources/Connections.xlsx";
            Iterator<Object[]> iterator = ExcelReaderConnections.readExcelData(filePath);

            while (iterator.hasNext()) {
                Object[] objects = iterator.next();
                if (objects.length != 3 || objects[0] == null || objects[1] == null || objects[2] == null
                        || objects[0].toString().equals("BLANK") || objects[1].toString().equals("BLANK") || objects[2].toString().equals("BLANK")) {

                    //System.out.println("Skipping invalid row: " + Arrays.toString(objects));
                    continue;
                }
                dataList.add(objects);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dataList.toArray(new Object[0][]);
    }
    @Test(dataProvider = "excelDataProvider")
    public void testGetResponse(String originUID, String destinationUID, String departureDatetime) throws JSONException {
        RestAssured.useRelaxedHTTPSValidation();

        JSONObject passengerAttributes = new JSONObject();
        passengerAttributes.put("age", 21);
        passengerAttributes.put("passenger_type_attributes", new JSONObject().put("type", "Search::PassengerType::Adult"));

        JSONObject routeAttributes = new JSONObject();
        routeAttributes.put("origin_station_attributes", new JSONObject().put("uid", originUID));
        routeAttributes.put("destination_station_attributes", new JSONObject().put("uid", destinationUID));

        JSONObject searchParams = new JSONObject();
        searchParams.put("departure_datetime", departureDatetime);
        searchParams.put("searches_passengers_attributes", new JSONObject().put("0", passengerAttributes));
        searchParams.put("route_attributes", routeAttributes);

        JSONObject requestParams = new JSONObject();
        requestParams.put("search", searchParams);

        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://vendor-production.saveatrain.com/api/searches")
                .setContentType("application/json")
                .addHeader("X-Agent-Email", ConfigLoader.getInstance().getREmail())
                .addHeader("X-Agent-Token", ConfigLoader.getInstance().getTRafal())
                .setBody(requestParams.toString())
                .build();

        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();

        Response response = given().spec(requestSpec).when().post().then().spec(responseSpec).extract().response();

        JSONObject jsonResponse = new JSONObject(response.getBody().asString());

        // Get the origin_station name
        JSONObject originStation = jsonResponse.getJSONObject("route").getJSONObject("origin_station");
        JSONArray originNames = originStation.getJSONArray("names");
        String originStationName = "";
        for (int i = 0; i < originNames.length(); i++) {
            JSONObject originName = originNames.getJSONObject(i);
            String languageName = originName.getJSONObject("language").getString("name");
            if (languageName.equals("English")) {
                originStationName = originName.getString("name");
                break;
            }
        }

        // Get the destination_station name
        JSONObject destinationStation = jsonResponse.getJSONObject("route").getJSONObject("destination_station");
        JSONArray destinationNames = destinationStation.getJSONArray("names");
        String destinationStationName = "";
        for (int i = 0; i < destinationNames.length(); i++) {
            JSONObject destinationName = destinationNames.getJSONObject(i);
            String languageName = destinationName.getJSONObject("language").getString("name");
            if (languageName.equals("English")) {
                destinationStationName = destinationName.getString("name");
                break;
            }
        }

        // Storing identifiers and corresponding ids separately
        if (jsonResponse.has("identifier")) {
            String identifier = jsonResponse.getString("identifier");
            allIdentifiers.add(identifier);
            System.out.println("Identifier: " + identifier);
        }

        // Get IDs and if possible, departure_datetime for each result
        JSONArray results = jsonResponse.getJSONArray("results");
        int totalIds = 0;
        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);
            if (result.has("id")) {
                totalIds++;
                String idValue = result.getString("id");
                allIds.add(idValue);
                System.out.println("ID: " + result.getInt("id"));

                // Storing identifier related ids
                if (jsonResponse.has("identifier")) {
                    String identifier = jsonResponse.getString("identifier");
                    identifierToIdsMap.putIfAbsent(identifier, new ArrayList<>());
                    identifierToIdsMap.get(identifier).add(idValue);
                }

                String departureDatetimeResult = "";
                if (result.has("departure_datetime")) {
                    departureDatetimeResult = result.getString("departure_datetime");
                    System.out.println("Departure DateTime: " + departureDatetimeResult);
                } else {
                    System.out.println("Departure DateTime not present in response. Using date from Excel: " + departureDatetime);
                }
            }
        }

        System.out.println(originStationName + " --> " + destinationStationName + " "
                + "Departure request: " + departureDatetime + " " + "Total ids: " + totalIds);
    }

    @Test()
    public void testGetSubRoutes() {
        System.out.println("Test started");
        RestAssured.useRelaxedHTTPSValidation();
        String originStationName = "";
        String destinationStationName = "";
        String departureDatetime = "";
        String identifier = "";
        String id = "";
        List<String> fareNamesList = new ArrayList<>();

        int limit = 10000; // Set your limit here
        try {
            for (Map.Entry<String, List<String>> entry : identifierToIdsMap.entrySet()) {
                identifier = entry.getKey();
                List<String> ids = entry.getValue();

                if (limit-- <= 0) break;
                for (String idElement : ids) {
                    id = idElement;
                    if (limit-- <= 0) break;
                    String url = "https://vendor-production.saveatrain.com/api/searches/" + identifier + "/results/" + id + "/sub_routes";
                    RequestSpecification requestSpec = new RequestSpecBuilder()
                            .setBaseUri(url)
                            .setContentType("application/json")
                            .addHeader("X-Agent-Email", ConfigLoader.getInstance().getREmail())
                            .addHeader("X-Agent-Token", ConfigLoader.getInstance().getTRafal())
                            .build();

                    ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();

                    Response response = given().spec(requestSpec).when().get().then().spec(responseSpec).extract().response();

                    JSONObject jsonResponse = new JSONObject(response.getBody().asString());
                    JSONArray transfers = jsonResponse.getJSONArray("transfers");

                    for (int i = 0; i < transfers.length(); i++) {
                        JSONObject transfer = transfers.getJSONObject(i);
                        JSONArray changes = transfer.getJSONArray("changes");

                        for (int j = 0; j < changes.length(); j++) {
                            JSONObject change = changes.getJSONObject(j);
                            JSONArray originStationNames = change.getJSONArray("origin_station_names");
                            JSONArray destinationStationNames = change.getJSONArray("destination_station_names");

                            originStationName = originStationNames.getJSONObject(0).getString("name");
                            destinationStationName = destinationStationNames.getJSONObject(0).getString("name");
                            departureDatetime = change.getString("departure_datetime");
                        }

                        if(transfer.has("fares")) {
                            JSONArray fares = transfer.getJSONArray("fares");
                            fareNamesList.clear();
                            for (int k = 0; k < fares.length(); k++) {
                                fareNamesList.add(fares.getJSONObject(k).getString("name"));
                            }
                        }
                        System.out.println(originStationName + " > " + destinationStationName + " > "
                                + departureDatetime + " > " + String.join(", ", fareNamesList));
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error occurred at: " + originStationName + " > " + destinationStationName + " > " + departureDatetime);
            System.err.println("Failed at identifier: " + identifier + " and id: " + id);
            System.err.println("Current remaining limit count: " + limit);
        }
    }
}
