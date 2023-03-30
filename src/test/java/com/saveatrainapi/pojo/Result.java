
package com.saveatrainapi.pojo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {

    @JsonProperty("outbound")
    private List<Outbound> outbound;

    @JsonProperty("outbound")
    public List<Outbound> getOutbound() {
        return outbound;
    }

    @JsonProperty("outbound")
    public void setOutbound(List<Outbound> outbound) {
        this.outbound = outbound;
    }

}
