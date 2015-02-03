
package com.kanishk.yahoo.pojo.places;

import com.google.gson.annotations.Expose;

public class Place {

    @Expose
    private String lang;
    @Expose
    private String uri;
    @Expose
    private String woeid;
    @Expose
    private PlaceTypeName placeTypeName;
    @Expose
    private String name;
    @Expose
    private Country country;
    @Expose
    private Admin1 admin1;
    @Expose
    private Admin2 admin2;
    @Expose
    private Object admin3;
    @Expose
    private Locality1 locality1;
    @Expose
    private Object locality2;
    @Expose
    private Postal postal;
    @Expose
    private Centroid centroid;
    @Expose
    private BoundingBox boundingBox;
    @Expose
    private String areaRank;
    @Expose
    private String popRank;
    @Expose
    private Timezone timezone;

    /**
     * 
     * @return
     *     The lang
     */
    public String getLang() {
        return lang;
    }

    /**
     * 
     * @param lang
     *     The lang
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * 
     * @return
     *     The uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * 
     * @param uri
     *     The uri
     */
    public void setUri(String uri) {
        this.uri = uri;
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
     *     The placeTypeName
     */
    public PlaceTypeName getPlaceTypeName() {
        return placeTypeName;
    }

    /**
     * 
     * @param placeTypeName
     *     The placeTypeName
     */
    public void setPlaceTypeName(PlaceTypeName placeTypeName) {
        this.placeTypeName = placeTypeName;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * 
     * @param country
     *     The country
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * 
     * @return
     *     The admin1
     */
    public Admin1 getAdmin1() {
        return admin1;
    }

    /**
     * 
     * @param admin1
     *     The admin1
     */
    public void setAdmin1(Admin1 admin1) {
        this.admin1 = admin1;
    }

    /**
     * 
     * @return
     *     The admin2
     */
    public Admin2 getAdmin2() {
        return admin2;
    }

    /**
     * 
     * @param admin2
     *     The admin2
     */
    public void setAdmin2(Admin2 admin2) {
        this.admin2 = admin2;
    }

    /**
     * 
     * @return
     *     The admin3
     */
    public Object getAdmin3() {
        return admin3;
    }

    /**
     * 
     * @param admin3
     *     The admin3
     */
    public void setAdmin3(Object admin3) {
        this.admin3 = admin3;
    }

    /**
     * 
     * @return
     *     The locality1
     */
    public Locality1 getLocality1() {
        return locality1;
    }

    /**
     * 
     * @param locality1
     *     The locality1
     */
    public void setLocality1(Locality1 locality1) {
        this.locality1 = locality1;
    }

    /**
     * 
     * @return
     *     The locality2
     */
    public Object getLocality2() {
        return locality2;
    }

    /**
     * 
     * @param locality2
     *     The locality2
     */
    public void setLocality2(Object locality2) {
        this.locality2 = locality2;
    }

    /**
     * 
     * @return
     *     The postal
     */
    public Postal getPostal() {
        return postal;
    }

    /**
     * 
     * @param postal
     *     The postal
     */
    public void setPostal(Postal postal) {
        this.postal = postal;
    }

    /**
     * 
     * @return
     *     The centroid
     */
    public Centroid getCentroid() {
        return centroid;
    }

    /**
     * 
     * @param centroid
     *     The centroid
     */
    public void setCentroid(Centroid centroid) {
        this.centroid = centroid;
    }

    /**
     * 
     * @return
     *     The boundingBox
     */
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    /**
     * 
     * @param boundingBox
     *     The boundingBox
     */
    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    /**
     * 
     * @return
     *     The areaRank
     */
    public String getAreaRank() {
        return areaRank;
    }

    /**
     * 
     * @param areaRank
     *     The areaRank
     */
    public void setAreaRank(String areaRank) {
        this.areaRank = areaRank;
    }

    /**
     * 
     * @return
     *     The popRank
     */
    public String getPopRank() {
        return popRank;
    }

    /**
     * 
     * @param popRank
     *     The popRank
     */
    public void setPopRank(String popRank) {
        this.popRank = popRank;
    }

    /**
     * 
     * @return
     *     The timezone
     */
    public Timezone getTimezone() {
        return timezone;
    }

    /**
     * 
     * @param timezone
     *     The timezone
     */
    public void setTimezone(Timezone timezone) {
        this.timezone = timezone;
    }

}
