package com.saveatrainapi.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static io.restassured.RestAssured.*;

public class BaseAPISearchTest {

    String orgStation = "SAT_DE_FR_OKZDY";
    String endStation = "SAT_CH_ZU_TDHRA";
    String ddate = "2023-05-25";

    /*@BeforeMethod()
    public void beforeMethod(Method m) {
        System.out.println("STARTING TEST: " + m.getName());
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.setBaseUri("https://apisearch.saveatrain.com/search/" + orgStation + "/" + endStation);
        requestSpecBuilder.addParam("triptype", "1");
        requestSpecBuilder.addParam("passengers", "1");
        requestSpecBuilder.addParam("ddate", ddate);
        requestSpecBuilder.addParam("email", "test"+"@"+"saveatrain.com");
        requestSpecBuilder.addParam("password", "bALJat8279B");
        requestSpecBuilder.log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.TEXT);
                //log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }*/

    @Test(priority = 1)
    public void getFirstDatePricesUpdatedAPISEARCH(Method m) {
        System.out.println("STARTING TEST: " + m.getName());
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.setBaseUri("https://apisearch.saveatrain.com/search/" + orgStation + "/" + endStation);
        requestSpecBuilder.addParam("triptype", "1");
        requestSpecBuilder.addParam("passengers", "1");
        requestSpecBuilder.addParam("ddate", ddate);
        requestSpecBuilder.addParam("email", "test"+"@"+"saveatrain.com");
        requestSpecBuilder.addParam("password", "bALJat8279B");
        //requestSpecBuilder.log(LogDetail.ALL);

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
        System.out.println(departureTimes.size());
        for (int i = 0; i < departureTimes.size(); i ++) {
            System.out.println(i + "." + "departure_time=" + departureTimes.get(i));
        }

        List<Object> prices = response.jsonPath().getList("result.outbound.price.second_class");
        System.out.println(prices.size());
        for (int i = 0; i < prices.size(); i ++) {
            System.out.println(i + "." + "second_class=" + prices.get(i));
        }
    }

/*    @BeforeMethod()
    public void beforeSecondMethod(Method m) {
        System.out.println("STARTING TEST: " + m.getName());
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.setBaseUri("https://apisearch.saveatrain.com/search/" + orgStation + "/" + endStation);
        requestSpecBuilder.addParam("triptype", "1");
        requestSpecBuilder.addParam("passengers", "1");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(sdf.parse(ddate));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        calendar.add(Calendar.DAY_OF_MONTH, 3);
        String threeDays = sdf.format(calendar.getTime());

        requestSpecBuilder.addParam("ddate", threeDays);
        requestSpecBuilder.addParam("email", "test"+"@"+"saveatrain.com");
        requestSpecBuilder.addParam("password", "bALJat8279B");
        requestSpecBuilder.log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.TEXT);
                //log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }*/

    @Test(priority = 2)
    public void getSecondDatePricesUpdatedAPISEARCH(Method m) {
        System.out.println("STARTING TEST: " + m.getName());
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.setBaseUri("https://apisearch.saveatrain.com/search/" + orgStation + "/" + endStation);
        requestSpecBuilder.addParam("triptype", "1");
        requestSpecBuilder.addParam("passengers", "1");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(sdf.parse(ddate));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        calendar.add(Calendar.DAY_OF_MONTH, 3);
        String threeDays = sdf.format(calendar.getTime());

        requestSpecBuilder.addParam("ddate", threeDays);
        requestSpecBuilder.addParam("email", "test"+"@"+"saveatrain.com");
        requestSpecBuilder.addParam("password", "bALJat8279B");
        //requestSpecBuilder.log(LogDetail.ALL);

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
        System.out.println(departureTimes.size());
        for (int i = 0; i < departureTimes.size(); i ++) {
            System.out.println(i + "." + "departure_time=" + departureTimes.get(i));
        }

        List<Object> prices = response.jsonPath().getList("result.outbound.price.second_class");
        System.out.println(prices.size());
        for (int i = 0; i < prices.size(); i ++) {
            System.out.println(i + "." + "second_class=" + prices.get(i));
        }
    }

/*    @BeforeMethod()
    public void beforeThirdMethod(Method m) {
        System.out.println("STARTING TEST: " + m.getName());
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.setBaseUri("https://apisearch.saveatrain.com/search/" + orgStation + "/" + endStation);
        requestSpecBuilder.addParam("triptype", "1");
        requestSpecBuilder.addParam("passengers", "1");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(sdf.parse(ddate));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        calendar.add(Calendar.DAY_OF_MONTH, 3);
        String threeDays = sdf.format(calendar.getTime());

        try {
            calendar.setTime(sdf.parse(threeDays));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        calendar.add(Calendar.DAY_OF_MONTH, 14);
        String fifteenDays = sdf.format(calendar.getTime());

        requestSpecBuilder.addParam("ddate", fifteenDays);
        requestSpecBuilder.addParam("email", "test"+"@"+"saveatrain.com");
        requestSpecBuilder.addParam("password", "bALJat8279B");
        requestSpecBuilder.log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.TEXT);
                //log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }*/

    @Test(priority = 3)
    public void getThirdDatePricesUpdatedAPISEARCH(Method m) {
        System.out.println("STARTING TEST: " + m.getName());
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.setBaseUri("https://apisearch.saveatrain.com/search/" + orgStation + "/" + endStation);
        requestSpecBuilder.addParam("triptype", "1");
        requestSpecBuilder.addParam("passengers", "1");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(sdf.parse(ddate));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        calendar.add(Calendar.DAY_OF_MONTH, 3);
        String threeDays = sdf.format(calendar.getTime());

        try {
            calendar.setTime(sdf.parse(threeDays));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        calendar.add(Calendar.DAY_OF_MONTH, 14);
        String fifteenDays = sdf.format(calendar.getTime());

        requestSpecBuilder.addParam("ddate", fifteenDays);
        requestSpecBuilder.addParam("email", "test"+"@"+"saveatrain.com");
        requestSpecBuilder.addParam("password", "bALJat8279B");
        //requestSpecBuilder.log(LogDetail.ALL);

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
        System.out.println(departureTimes.size());
        for (int i = 0; i < departureTimes.size(); i ++) {
            System.out.println(i + "." + "departure_time=" + departureTimes.get(i));
        }

        List<Object> prices = response.jsonPath().getList("result.outbound.price.second_class");
        System.out.println(prices.size());
        for (int i = 0; i < prices.size(); i ++) {
            System.out.println(i + "." + "second_class=" + prices.get(i));
        }
    }

/*    @BeforeMethod()
    public void beforeFourthMethod(Method m) {
        System.out.println("STARTING TEST: " + m.getName());
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.setBaseUri("https://apisearch.saveatrain.com/search/" + orgStation + "/" + endStation);
        requestSpecBuilder.addParam("triptype", "1");
        requestSpecBuilder.addParam("passengers", "1");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(sdf.parse(ddate));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        calendar.add(Calendar.DAY_OF_MONTH, 3);
        String threeDays = sdf.format(calendar.getTime());

        try {
            calendar.setTime(sdf.parse(threeDays));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        calendar.add(Calendar.DAY_OF_MONTH, 14);
        String fifteenDays = sdf.format(calendar.getTime());

        try {
            calendar.setTime(sdf.parse(fifteenDays));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        calendar.add(Calendar.DAY_OF_MONTH, 20);
        String thirtyDays = sdf.format(calendar.getTime());

        requestSpecBuilder.addParam("ddate", thirtyDays);
        requestSpecBuilder.addParam("email", "test"+"@"+"saveatrain.com");
        requestSpecBuilder.addParam("password", "bALJat8279B");
        requestSpecBuilder.log(LogDetail.ALL);

        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.TEXT);
                //log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }*/

    @Test(priority = 4)
    public void getFourthDatePricesUpdatedAPISEARCH(Method m) {
        System.out.println("STARTING TEST: " + m.getName());
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setUrlEncodingEnabled(false);
        requestSpecBuilder.setBaseUri("https://apisearch.saveatrain.com/search/" + orgStation + "/" + endStation);
        requestSpecBuilder.addParam("triptype", "1");
        requestSpecBuilder.addParam("passengers", "1");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(sdf.parse(ddate));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        calendar.add(Calendar.DAY_OF_MONTH, 3);
        String threeDays = sdf.format(calendar.getTime());

        try {
            calendar.setTime(sdf.parse(threeDays));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        calendar.add(Calendar.DAY_OF_MONTH, 14);
        String fifteenDays = sdf.format(calendar.getTime());

        try {
            calendar.setTime(sdf.parse(fifteenDays));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        calendar.add(Calendar.DAY_OF_MONTH, 20);
        String thirtyDays = sdf.format(calendar.getTime());

        requestSpecBuilder.addParam("ddate", thirtyDays);
        requestSpecBuilder.addParam("email", "test"+"@"+"saveatrain.com");
        requestSpecBuilder.addParam("password", "bALJat8279B");
        //requestSpecBuilder.log(LogDetail.ALL);

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
        System.out.println(departureTimes.size());
        for (int i = 0; i < departureTimes.size(); i ++) {
            System.out.println(i + "." + "departure_time=" + departureTimes.get(i));
        }

        List<Object> prices = response.jsonPath().getList("result.outbound.price.second_class");
        System.out.println(prices.size());
        for (int i = 0; i < prices.size(); i ++) {
            System.out.println(i + "." + "second_class=" + prices.get(i));
        }
    }

    /*@DataProvider(name ="excel-data")
    public Object[][] excelDP() throws IOException {
        //We are creating an object from the excel sheet data by calling a method that reads data from the excel stored locally in our system
        Object[][] arrObj = getExcelData("Location of the excel file in your local system","");
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
    }*/
}
