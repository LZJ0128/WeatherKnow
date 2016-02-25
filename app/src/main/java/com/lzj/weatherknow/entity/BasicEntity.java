package com.lzj.weatherknow.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2/23 0023.
 * 城市基本信息
 */
public class BasicEntity {
    //城市
    private String city;
    //国家
    @SerializedName("cnty")
    private String country;
    //城市ID
    private String cityId;
    //经度
    @SerializedName("lon")
    private String longitude;
    //纬度
    @SerializedName("lat")
    private String latitude;
    //更新时间
    private UpdateEntity update;

    public UpdateEntity getUpdate() {
        return update;
    }

    public void setUpdate(UpdateEntity update) {
        this.update = update;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
