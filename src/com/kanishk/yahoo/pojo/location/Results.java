
package com.kanishk.yahoo.pojo.location;

import com.google.gson.annotations.Expose;

public class Results {

    @Expose
    private com.kanishk.yahoo.pojo.location.Result Result;

    /**
     * 
     * @return
     *     The Result
     */
    public com.kanishk.yahoo.pojo.location.Result getResult() {
        return Result;
    }

    /**
     * 
     * @param Result
     *     The Result
     */
    public void setResult(com.kanishk.yahoo.pojo.location.Result Result) {
        this.Result = Result;
    }

}
