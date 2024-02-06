package com.saveatrainapi.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelReaderConnections {
    public static Iterator<Object[]> readExcelData(String filePath) throws IOException {
        List<Object[]> data = new ArrayList<>();
        FileInputStream fis = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // Changed here

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }

            List<String> rowData = new ArrayList<>();

            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case STRING:
                        rowData.add(cell.getStringCellValue());
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
                    default:
                        rowData.add("UNKNOWN");
                }
            }
            data.add(rowData.toArray());
        }

        workbook.close();
        fis.close();

        return data.iterator();
    }
}
