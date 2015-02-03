
package com.kanishk.yahoo.pojo.weather;

import com.google.gson.annotations.Expose;

public class Results {

    @Expose
    private Rss rss;

    /**
     * 
     * @return
     *     The rss
     */
    public Rss getRss() {
        return rss;
    }

    /**
     * 
     * @param rss
     *     The rss
     */
    public void setRss(Rss rss) {
        this.rss = rss;
    }

}
