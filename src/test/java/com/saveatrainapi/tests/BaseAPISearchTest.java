package com.saveatrainapi.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static io.restassured.RestAssured.*;

public class BaseAPISearchTest {

    String orgStation = "SAT_FR_PA_WXNGQ";
    String endStation = "SAT_NL_AM_WGRFL";
    String ddate = "2023-05-20";

    @BeforeMethod()
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
                expectContentType(ContentType.TEXT).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void getPricesUpdatedAPISEARCH() {
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

    @DataProvider(name ="excel-data")
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
    }
}
