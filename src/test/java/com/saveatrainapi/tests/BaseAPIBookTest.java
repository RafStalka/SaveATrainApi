package com.saveatrainapi.tests;

import com.saveatrainapi.pojoApiBook.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
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
        requestSpecBuilder.addHeader("X-Agent-Token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpbnN0YW5zZV9jbGFzcyI6IlNhbGVzQWdlbnQiLCJpbnN0YW5jZV9pZCI6MzAsImV4cGlyYXRpb25fdGltZSI6IjIwMjQtMDQtMjQgMTU6MTQ6NTIgVVRDIn0.CImLWJtI_dahynJmbtDk5ko13z1qQaLeQ2COnx7ZCok");
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
        String orgStation = "SAT_FR_PA_WXNGQ";
        String endStation = "SAT_NL_AM_WGRFL";
        String ddate = "2023-05-25 08:00";

        PassengerTypeAttributes passengerTypeAttributes = new PassengerTypeAttributes();
        passengerTypeAttributes.setType("Search::PassengerType::Adult");

        _0 _0 = new _0();
        _0.setAge(21);
        _0.setPassengerTypeAttributes(passengerTypeAttributes);

        SearchesPassengersAttributes searchesPassengersAttributes = new SearchesPassengersAttributes();
        searchesPassengersAttributes.set0(_0);

        OriginStationAttributes originStationAttributes = new OriginStationAttributes();
        originStationAttributes.setUid(orgStation);

        DestinationStationAttributes destinationStationAttributes = new DestinationStationAttributes();
        destinationStationAttributes.setUid(endStation);

        RouteAttributes routeAttributes = new RouteAttributes();
        routeAttributes.setOriginStationAttributes(originStationAttributes);
        routeAttributes.setDestinationStationAttributes(destinationStationAttributes);



        Example example = new Example();
        example.setDepartureDatetime(ddate);
        example.setReturnDepartureDatetime(ddate);
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

    @Test(dataProvider ="excel-data")
    public void searchWithDataProvPricesApiBook(String orginStation1, String destStation1) {
        String orgStation = "SAT_AT_AU_EQRKO";
        String endStation = "SAT_DE_GI_CTAQI";
        String ddate = "2023-05-27 07:00";

        PassengerTypeAttributes passengerTypeAttributes = new PassengerTypeAttributes();
        passengerTypeAttributes.setType("Search::PassengerType::Adult");

        _0 _0 = new _0();
        _0.setAge(21);
        _0.setPassengerTypeAttributes(passengerTypeAttributes);

        SearchesPassengersAttributes searchesPassengersAttributes = new SearchesPassengersAttributes();
        searchesPassengersAttributes.set0(_0);

        OriginStationAttributes originStationAttributes = new OriginStationAttributes();
        originStationAttributes.setUid(orginStation1);

        DestinationStationAttributes destinationStationAttributes = new DestinationStationAttributes();
        destinationStationAttributes.setUid(destStation1);

        RouteAttributes routeAttributes = new RouteAttributes();
        routeAttributes.setOriginStationAttributes(originStationAttributes);
        routeAttributes.setDestinationStationAttributes(destinationStationAttributes);



        Example example = new Example();
        example.setDepartureDatetime(ddate);
        example.setReturnDepartureDatetime(ddate);
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
            System.out.println("departure_time=" + departureTimes.get(i));
        }

        List<Object> prices = response.jsonPath().getList("results.best_price");
        System.out.println(prices.size());
        for (int i = 0; i < prices.size(); i ++) {
            System.out.println("second_class=" + prices.get(i));
        }
    }
    @DataProvider(name ="excel-data")
    public Object[][] excelDP() throws IOException {
        //We are creating an object from the excel sheet data by calling a method that reads data from the excel stored locally in our system
        Object[][] arrObj = getExcelData("/Users/rafalst/Downloads/ApiSearchData.xlsx","Sheet1");
        return arrObj;
    }
    //This method handles the excel - opens it and reads the data from the respective cells using a for-loop & returns it in the form of a string array
    public String[][] getExcelData(String fileName, String sheetName){

        String[][] data = null;
        try
        {
            FileInputStream fis = new FileInputStream(fileName);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sh = wb.getSheet(sheetName);
            XSSFRow row = sh.getRow(0);
            int noOfRows = sh.getPhysicalNumberOfRows();
            int noOfCols = row.getLastCellNum();
            Cell cell;
            data = new String[noOfRows-1][noOfCols];

            for(int i =1; i<noOfRows;i++){
                for(int j=0;j<noOfCols;j++){
                    row = sh.getRow(i);
                    cell= row.getCell(j);
                    data[i-1][j] = cell.getStringCellValue();
                }
            }
        }
        catch (Exception e) {
            System.out.println("The exception is: " +e.getMessage());
        }
        return data;
    }
}
