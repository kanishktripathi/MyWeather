
package com.kanishk.yahoo.pojo.weather;

import com.google.gson.annotations.Expose;

public class Forecast_ {

    @Expose
    private String code;
    
    private String date;
    @Expose
    private String day;
    @Expose
    private String high;
    @Expose
    private String low;
    @Expose
    private String text;

    /**
     * 
     * @return
     *     The code
     */
    public String getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The day
     */
    public String getDay() {
        return day;
    }

    /**
     * 
     * @param day
     *     The day
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * 
     * @return
     *     The high
     */
    public String getHigh() {
        return high;
    }

    /**
     * 
     * @param high
     *     The high
     */
    public void setHigh(String high) {
        this.high = high;
    }

    /**
     * 
     * @return
     *     The low
     */
    public String getLow() {
        return low;
    }

    /**
     * 
     * @param low
     *     The low
     */
    public void setLow(String low) {
        this.low = low;
    }

    /**
     * 
     * @return
     *     The text
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    public void setText(String text) {
        this.text = text;
    }

}
