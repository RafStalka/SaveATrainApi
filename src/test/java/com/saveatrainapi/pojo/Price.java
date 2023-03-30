
package com.saveatrainapi.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Price {

    @JsonProperty("second_class")
    private Integer secondClass;
    @JsonProperty("first_class")
    private Integer firstClass;
    @JsonProperty("business_class")
    private Double businessClass;

    @JsonProperty("second_class")
    public Integer getSecondClass() {
        return secondClass;
    }

    @JsonProperty("second_class")
    public void setSecondClass(Integer secondClass) {
        this.secondClass = secondClass;
    }

    @JsonProperty("first_class")
    public Integer getFirstClass() {
        return firstClass;
    }

    @JsonProperty("first_class")
    public void setFirstClass(Integer firstClass) {
        this.firstClass = firstClass;
    }

    @JsonProperty("business_class")
    public Double getBusinessClass() {
        return businessClass;
    }

    @JsonProperty("business_class")
    public void setBusinessClass(Double businessClass) {
        this.businessClass = businessClass;
    }

}
