
package com.kanishk.yahoo.pojo.weather;

import com.google.gson.annotations.Expose;

public class Rss {

    
    private String version;
    
    private String geo;
    
    private String yweather;
    @Expose
    private Channel channel;

    /**
     * 
     * @return
     *     The version
     */
    public String getVersion() {
        return version;
    }

    /**
     * 
     * @param version
     *     The version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 
     * @return
     *     The geo
     */
    public String getGeo() {
        return geo;
    }

    /**
     * 
     * @param geo
     *     The geo
     */
    public void setGeo(String geo) {
        this.geo = geo;
    }

    /**
     * 
     * @return
     *     The yweather
     */
    public String getYweather() {
        return yweather;
    }

    /**
     * 
     * @param yweather
     *     The yweather
     */
    public void setYweather(String yweather) {
        this.yweather = yweather;
    }

    /**
     * 
     * @return
     *     The channel
     */
    public Channel getChannel() {
        return channel;
    }

    /**
     * 
     * @param channel
     *     The channel
     */
    public void setChannel(Channel channel) {
        this.channel = channel;
    }

}
