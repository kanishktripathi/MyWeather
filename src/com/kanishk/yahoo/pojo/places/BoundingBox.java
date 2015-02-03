
package com.kanishk.yahoo.pojo.places;

import com.google.gson.annotations.Expose;

public class BoundingBox {

    @Expose
    private SouthWest southWest;
    @Expose
    private NorthEast northEast;

    /**
     * 
     * @return
     *     The southWest
     */
    public SouthWest getSouthWest() {
        return southWest;
    }

    /**
     * 
     * @param southWest
     *     The southWest
     */
    public void setSouthWest(SouthWest southWest) {
        this.southWest = southWest;
    }

    /**
     * 
     * @return
     *     The northEast
     */
    public NorthEast getNorthEast() {
        return northEast;
    }

    /**
     * 
     * @param northEast
     *     The northEast
     */
    public void setNorthEast(NorthEast northEast) {
        this.northEast = northEast;
    }

}
