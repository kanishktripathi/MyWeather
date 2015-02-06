
package com.kanishk.yahoo.pojo.weather;

import com.google.gson.annotations.Expose;

public class Condition {

    @Expose
    private String code;
    
    private String date;
    @Expose
    private String temp;
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
     *     The temp
     */
    public String getTemp() {
        return temp;
    }

    /**
     * 
     * @param temp
     *     The temp
     */
    public void setTemp(String temp) {
        this.temp = temp;
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
