package com.saveatrainapi.utils;

import java.util.Properties;

public class ConfigLoader {
  private final Properties properties;
  private static ConfigLoader configLoader;

  private ConfigLoader() {
    properties = PropertyUtils.propertyLoader("src/test/resources/config.properties");
  }

  public static ConfigLoader getInstance() {
    if (configLoader == null) {
      configLoader = new ConfigLoader();
    }
    return configLoader;
  }

  public String getHeaderEVercel() {
    String prop = properties.getProperty("userEHeaderVercel");
    if (prop != null) return prop;
    else
      throw new RuntimeException(
              "Property headerE is not specified in the config.properties file");
  }

  public String getHeaderTVercel() {
    String prop = properties.getProperty("userTHeaderVercel");
    if (prop != null) return prop;
    else
      throw new RuntimeException(
              "Property headerT is not specified in the config.properties file");
  }
  public String getHeaderE() {
    String prop = properties.getProperty("userEHeader");
    if (prop != null) return prop;
    else
      throw new RuntimeException(
              "Property headerE is not specified in the config.properties file");
  }

  public String getHeaderT() {
    String prop = properties.getProperty("userTHeader");
    if (prop != null) return prop;
    else
      throw new RuntimeException(
              "Property headerT is not specified in the config.properties file");
  }



  public String getParamPage() {
    String prop = properties.getProperty("paramPage");
    if (prop != null) return prop;
    else
      throw new RuntimeException(
              "Property paramP is not specified in the config.properties file");
  }

  public String getParamPageValue() {
    String prop = properties.getProperty("paramPageValue");
    if (prop != null) return prop;
    else
      throw new RuntimeException(
              "Property paramPValue is not specified in the config.properties file");
  }

  public String getParamPerPage() {
    String prop = properties.getProperty("paramPerPage");
    if (prop != null) return prop;
    else
      throw new RuntimeException(
              "Property paramPP is not specified in the config.properties file");
  }

  public String getParamPerPageValue() {
    String prop = properties.getProperty("paramPerPageValue");
    if (prop != null) return prop;
    else
      throw new RuntimeException(
              "Property paramPPValue is not specified in the config.properties file");
  }

  public String getIEmailVercel() {
    String prop = properties.getProperty("iEmailVercel");
    if (prop != null) return prop;
    else
      throw new RuntimeException(
              "Property iEmail is not specified in the config.properties file");
  }

  public String getREmail() {
    String prop = properties.getProperty("rEmail");
    if (prop != null) return prop;
    else
      throw new RuntimeException(
              "Property rEmail is not specified in the config.properties file");
  }

  public String getTRafal() {
    String prop = properties.getProperty("tRafal");
    if (prop != null) return prop;
    else
      throw new RuntimeException(
              "Property TRafal is not specified in the config.properties file");
  }

  public String getTIdanVercel() {
    String prop = properties.getProperty("tIdanVercel");
    if (prop != null) return prop;
    else
      throw new RuntimeException(
              "Property TIdan is not specified in the config.properties file");
  }

  public String getSATBalance() {
    String prop = properties.getProperty("satURIBalance");
    if (prop != null) return prop;
    else
      throw new RuntimeException(
              "Property SATBalance is not specified in the config.properties file");
  }

  public String getSATOrder() {
    String prop = properties.getProperty("satURIOrder");
    if (prop != null) return prop;
    else
      throw new RuntimeException(
              "Property SATOrder is not specified in the config.properties file");
  }

  public String getApiBook() {
    String prop = properties.getProperty("apiBookURI");
    if (prop != null) return prop;
    else
      throw new RuntimeException(
              "Property api book is not specified in the config.properties file");
  }

  public String getVendor() {
    String prop = properties.getProperty("vendorURI");
    if (prop != null) return prop;
    else
      throw new RuntimeException(
              "Property api book is not specified in the config.properties file");
  }

}
