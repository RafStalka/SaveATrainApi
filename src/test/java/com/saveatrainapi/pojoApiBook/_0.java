
package com.saveatrainapi.pojoApiBook;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class _0 {

    @JsonProperty("age")
    private Integer age;
    @JsonProperty("passenger_type_attributes")
    private PassengerTypeAttributes passengerTypeAttributes;

    @JsonProperty("age")
    public Integer getAge() {
        return age;
    }

    @JsonProperty("age")
    public void setAge(Integer age) {
        this.age = age;
    }

    @JsonProperty("passenger_type_attributes")
    public PassengerTypeAttributes getPassengerTypeAttributes() {
        return passengerTypeAttributes;
    }

    @JsonProperty("passenger_type_attributes")
    public void setPassengerTypeAttributes(PassengerTypeAttributes passengerTypeAttributes) {
        this.passengerTypeAttributes = passengerTypeAttributes;
    }

}
