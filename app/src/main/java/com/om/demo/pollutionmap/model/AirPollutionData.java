package com.om.demo.pollutionmap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;


/**
 * Created by om on 19/05/16.
 */

@Generated("org.jsonschema2pojo")
public class AirPollutionData {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("loc")
    @Expose
    private String loc;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("deviceId")
    @Expose
    private String deviceId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("aqi")
    @Expose
    private Integer aqi;

    /**
     *
     * @return
     * The label
     */
    public String getLabel() {
        return label;
    }

    /**
     *
     * @param label
     * The label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     *
     * @return
     * The loc
     */
    public String getLoc() {
        return loc;
    }

    /**
     *
     * @param loc
     * The loc
     */
    public void setLoc(String loc) {
        this.loc = loc;
    }

    /**
     *
     * @return
     * The latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     * The latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     * The longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     * The longitude
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     * The deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     *
     * @param deviceId
     * The deviceId
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The aqi
     */
    public Integer getAqi() {
        return aqi;
    }

    /**
     *
     * @param aqi
     * The aqi
     */
    public void setAqi(Integer aqi) {
        this.aqi = aqi;
    }
}
