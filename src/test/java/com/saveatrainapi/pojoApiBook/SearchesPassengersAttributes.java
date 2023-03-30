
package com.saveatrainapi.pojoApiBook;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchesPassengersAttributes {

    @JsonProperty("0")
    private com.saveatrainapi.pojoApiBook._0 _0;

    @JsonProperty("0")
    public com.saveatrainapi.pojoApiBook._0 get0() {
        return _0;
    }

    @JsonProperty("0")
    public void set0(com.saveatrainapi.pojoApiBook._0 _0) {
        this._0 = _0;
    }

}
