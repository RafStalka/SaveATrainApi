package com.saveatrainapi.utils;

import java.util.Properties;

public class DataLoader {
    private final Properties properties;
    private static DataLoader dataLoader;

    private DataLoader() {
        properties = PropertyUtils.propertyLoader("src/test/resources/data.properties");
    }

    public static DataLoader getInstance() {
        if (dataLoader == null) {
            dataLoader = new DataLoader();
        }
        return dataLoader;
    }

    public String getTotalPrice() {
        String prop = properties.getProperty("totalPricePath");
        if (prop != null) return prop;
        else
            throw new RuntimeException(
                    "Property total price is not specified in the file");
    }

    public String getTotalOrders() {
        String prop = properties.getProperty("totalOrderPath");
        if (prop != null) return prop;
        else
            throw new RuntimeException(
                    "Property total order is not specified in the file");
    }

    public String getTotalPurchases() {
        String prop = properties.getProperty("totalPurchases");
        if (prop != null) return prop;
        else
            throw new RuntimeException(
                    "Property total purchases is not specified in the file");
    }
}
