
package com.kanishk.yahoo.pojo.places;

import com.google.gson.annotations.Expose;

public class Locality1 {

    @Expose
    private String type;
    @Expose
    private String woeid;
    @Expose
    private String content;

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The woeid
     */
    public String getWoeid() {
        return woeid;
    }

    /**
     * 
     * @param woeid
     *     The woeid
     */
    public void setWoeid(String woeid) {
        this.woeid = woeid;
    }

    /**
     * 
     * @return
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

}
