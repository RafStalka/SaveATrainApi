package com.saveatrainapi.utils;

import java.util.Properties;

public class DataLoader {
    private final Properties properties;
    private static DataLoader dataLoader;

    private DataLoader() {
        properties = PropertyUtlis.propertyLoader("src/test/resources/data.properties");
    }

    public static DataLoader getInstance() {
        if (dataLoader == null) {
            dataLoader = new DataLoader();
        }
        return dataLoader;
    }

    public String getClientId() {
        String prop = properties.getProperty("client_id");
        if (prop != null) return prop;
        else
            throw new RuntimeException(
                    "Property client_id is not specified in the config.properties file");
    }
}
