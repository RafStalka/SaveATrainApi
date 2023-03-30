package com.saveatrainapi.api;

public class Route {

    public static final String originStation = "SAT_UK_LO_DXLIF";
    public static final String destinStation = "SAT_NL_AM_WGRFL";
    public static final String BASEURI_PROD = "https://apibook.saveatrain.com/api";
    public static final String BASEURI_STAGE = "https://apibook.saveatrain.com/api";
    public static final String BASEURI_PRICES = "https://apisearch.saveatrain.com/search/" + originStation + "/" + destinStation;
    public static final String VENDOR_STATIONS = "/vendor_stations";
}
