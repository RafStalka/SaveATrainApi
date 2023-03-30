
package com.saveatrainapi.pojoApiBook;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouteAttributes {

    @JsonProperty("origin_station_attributes")
    private OriginStationAttributes originStationAttributes;
    @JsonProperty("destination_station_attributes")
    private DestinationStationAttributes destinationStationAttributes;

    @JsonProperty("origin_station_attributes")
    public OriginStationAttributes getOriginStationAttributes() {
        return originStationAttributes;
    }

    @JsonProperty("origin_station_attributes")
    public void setOriginStationAttributes(OriginStationAttributes originStationAttributes) {
        this.originStationAttributes = originStationAttributes;
    }

    @JsonProperty("destination_station_attributes")
    public DestinationStationAttributes getDestinationStationAttributes() {
        return destinationStationAttributes;
    }

    @JsonProperty("destination_station_attributes")
    public void setDestinationStationAttributes(DestinationStationAttributes destinationStationAttributes) {
        this.destinationStationAttributes = destinationStationAttributes;
    }

}
