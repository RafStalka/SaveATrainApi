
package com.saveatrainapi.pojoApiBook;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Example {

    @JsonProperty("departure_datetime")
    private String departureDatetime;
    @JsonProperty("return_departure_datetime")
    private String returnDepartureDatetime;
    @JsonProperty("searches_passengers_attributes")
    private SearchesPassengersAttributes searchesPassengersAttributes;
    @JsonProperty("route_attributes")
    private RouteAttributes routeAttributes;

    @JsonProperty("departure_datetime")
    public String getDepartureDatetime() {
        return departureDatetime;
    }

    @JsonProperty("departure_datetime")
    public void setDepartureDatetime(String departureDatetime) {
        this.departureDatetime = departureDatetime;
    }

    @JsonProperty("return_departure_datetime")
    public String getReturnDepartureDatetime() {
        return returnDepartureDatetime;
    }

    @JsonProperty("return_departure_datetime")
    public void setReturnDepartureDatetime(String returnDepartureDatetime) {
        this.returnDepartureDatetime = returnDepartureDatetime;
    }

    @JsonProperty("searches_passengers_attributes")
    public SearchesPassengersAttributes getSearchesPassengersAttributes() {
        return searchesPassengersAttributes;
    }

    @JsonProperty("searches_passengers_attributes")
    public void setSearchesPassengersAttributes(SearchesPassengersAttributes searchesPassengersAttributes) {
        this.searchesPassengersAttributes = searchesPassengersAttributes;
    }

    @JsonProperty("route_attributes")
    public RouteAttributes getRouteAttributes() {
        return routeAttributes;
    }

    @JsonProperty("route_attributes")
    public void setRouteAttributes(RouteAttributes routeAttributes) {
        this.routeAttributes = routeAttributes;
    }

}
