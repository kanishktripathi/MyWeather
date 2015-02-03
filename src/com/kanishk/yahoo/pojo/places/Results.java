
package com.kanishk.yahoo.pojo.places;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Results {

    @Expose
    private List<Place> place = new ArrayList<Place>();

    /**
     * 
     * @return
     *     The place
     */
    public List<Place> getPlace() {
        return place;
    }

    /**
     * 
     * @param place
     *     The place
     */
    public void setPlace(List<Place> place) {
        this.place = place;
    }

}
