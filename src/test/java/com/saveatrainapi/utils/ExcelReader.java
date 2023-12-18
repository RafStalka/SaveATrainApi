package com.saveatrainapi.utils;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelReader {
    public static Object[][] readExcelData(String filePath, String sheetName, String dateFormat) {
        List<List<String>> data = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            Sheet sheet = workbook.getSheet(sheetName);

            // Create a SimpleDateFormat object with the desired date format
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

            // Start iterating from the first data row (index 1) to skip the header row
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    // Skip empty rows
                    continue;
                }

                // Create a List to store the data of the current row
                List<String> rowData = new ArrayList<>();

                // Iterate over cells in the current row
                for (Cell cell : row) {
                    // Extract the cell value based on the cell type
                    switch (cell.getCellType()) {
                        case STRING:
                            String cellValue = cell.getStringCellValue();
                            rowData.add(cellValue);
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                Date dateCellValue = cell.getDateCellValue();
                                String formattedDate = sdf.format(dateCellValue);
                                rowData.add(formattedDate);
                            } else {
                                double numericCellValue = cell.getNumericCellValue();
                                rowData.add(String.valueOf(numericCellValue));
                            }
                            break;
                        case BOOLEAN:
                            boolean booleanCellValue = cell.getBooleanCellValue();
                            rowData.add(String.valueOf(booleanCellValue));
                            break;
                        case BLANK:
                            rowData.add("BLANK");
                            break;
                        // Add more cases as needed for other cell types
                        default:
                            rowData.add("UNKNOWN");
                    }
                }
                // Add the current row data to the data List
                data.add(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert the List of Lists to a 2D array (Object[][])
        Object[][] dataArray = new Object[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            dataArray[i] = data.get(i).toArray(new Object[0]);
        }

        return dataArray;
    }
}
