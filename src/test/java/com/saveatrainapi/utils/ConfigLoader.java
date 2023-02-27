package com.saveatrainapi.utils;

import java.util.Properties;

public class ConfigLoader {
  private final Properties properties;
  private static ConfigLoader configLoader;

  private ConfigLoader() {
    properties = PropertyUtlis.propertyLoader("src/test/resources/config.properties");
  }

  public static ConfigLoader getInstance() {
    if (configLoader == null) {
      configLoader = new ConfigLoader();
    }
    return configLoader;
  }

  public String getClientId() {
    String prop = properties.getProperty("client_id");
    if (prop != null) return prop;
    else
      throw new RuntimeException(
          "Property client_id is not specified in the config.properties file");
  }
}
