
package com.kanishk.yahoo.pojo.location;

import com.google.gson.annotations.Expose;

public class Location {

    @Expose
    private Query query;

    /**
     * 
     * @return
     *     The query
     */
    public Query getQuery() {
        return query;
    }

    /**
     * 
     * @param query
     *     The query
     */
    public void setQuery(Query query) {
        this.query = query;
    }

}
