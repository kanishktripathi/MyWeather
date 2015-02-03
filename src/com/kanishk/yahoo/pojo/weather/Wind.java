
package com.kanishk.yahoo.pojo.weather;

import com.google.gson.annotations.Expose;

public class Wind {

    @Expose
    private String chill;
    @Expose
    private String direction;
    @Expose
    private String speed;

    /**
     * 
     * @return
     *     The chill
     */
    public String getChill() {
        return chill;
    }

    /**
     * 
     * @param chill
     *     The chill
     */
    public void setChill(String chill) {
        this.chill = chill;
    }

    /**
     * 
     * @return
     *     The direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * 
     * @param direction
     *     The direction
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * 
     * @return
     *     The speed
     */
    public String getSpeed() {
        return speed;
    }

    /**
     * 
     * @param speed
     *     The speed
     */
    public void setSpeed(String speed) {
        this.speed = speed;
    }

}
