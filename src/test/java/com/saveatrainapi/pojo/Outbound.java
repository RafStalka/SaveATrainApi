
package com.saveatrainapi.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Outbound {

    @JsonProperty("origin_station")
    private String originStation;
    @JsonProperty("destin_station")
    private String destinStation;
    @JsonProperty("departure_date")
    private String departureDate;
    @JsonProperty("departure_time")
    private String departureTime;
    @JsonProperty("duration")
    private String duration;
    @JsonProperty("arrival_date")
    private String arrivalDate;
    @JsonProperty("arrival_time")
    private String arrivalTime;
    @JsonProperty("changes")
    private Integer changes;
    @JsonProperty("operator")
    private String operator;
    @JsonProperty("connections")
    private String connections;
    @JsonProperty("price")
    private Price price;
    @JsonProperty("train_numbers")
    private String trainNumbers;
    @JsonProperty("departure_connections")
    private String departureConnections;
    @JsonProperty("arrival_connections")
    private String arrivalConnections;
    @JsonProperty("layover_connections")
    private String layoverConnections;

    @JsonProperty("origin_station")
    public String getOriginStation() {
        return originStation;
    }

    @JsonProperty("origin_station")
    public void setOriginStation(String originStation) {
        this.originStation = originStation;
    }

    @JsonProperty("destin_station")
    public String getDestinStation() {
        return destinStation;
    }

    @JsonProperty("destin_station")
    public void setDestinStation(String destinStation) {
        this.destinStation = destinStation;
    }

    @JsonProperty("departure_date")
    public String getDepartureDate() {
        return departureDate;
    }

    @JsonProperty("departure_date")
    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    @JsonProperty("departure_time")
    public String getDepartureTime() {
        return departureTime;
    }

    @JsonProperty("departure_time")
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    @JsonProperty("duration")
    public String getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(String duration) {
        this.duration = duration;
    }

    @JsonProperty("arrival_date")
    public String getArrivalDate() {
        return arrivalDate;
    }

    @JsonProperty("arrival_date")
    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    @JsonProperty("arrival_time")
    public String getArrivalTime() {
        return arrivalTime;
    }

    @JsonProperty("arrival_time")
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @JsonProperty("changes")
    public Integer getChanges() {
        return changes;
    }

    @JsonProperty("changes")
    public void setChanges(Integer changes) {
        this.changes = changes;
    }

    @JsonProperty("operator")
    public String getOperator() {
        return operator;
    }

    @JsonProperty("operator")
    public void setOperator(String operator) {
        this.operator = operator;
    }

    @JsonProperty("connections")
    public String getConnections() {
        return connections;
    }

    @JsonProperty("connections")
    public void setConnections(String connections) {
        this.connections = connections;
    }

    @JsonProperty("price")
    public Price getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(Price price) {
        this.price = price;
    }

    @JsonProperty("train_numbers")
    public String getTrainNumbers() {
        return trainNumbers;
    }

    @JsonProperty("train_numbers")
    public void setTrainNumbers(String trainNumbers) {
        this.trainNumbers = trainNumbers;
    }

    @JsonProperty("departure_connections")
    public String getDepartureConnections() {
        return departureConnections;
    }

    @JsonProperty("departure_connections")
    public void setDepartureConnections(String departureConnections) {
        this.departureConnections = departureConnections;
    }

    @JsonProperty("arrival_connections")
    public String getArrivalConnections() {
        return arrivalConnections;
    }

    @JsonProperty("arrival_connections")
    public void setArrivalConnections(String arrivalConnections) {
        this.arrivalConnections = arrivalConnections;
    }

    @JsonProperty("layover_connections")
    public String getLayoverConnections() {
        return layoverConnections;
    }

    @JsonProperty("layover_connections")
    public void setLayoverConnections(String layoverConnections) {
        this.layoverConnections = layoverConnections;
    }

}
