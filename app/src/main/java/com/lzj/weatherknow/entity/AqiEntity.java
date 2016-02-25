package com.lzj.weatherknow.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2/23 0023.
 */
public class AqiEntity {
    @SerializedName("city")
    private AqiCityEntity aqiCity;

    public AqiCityEntity getAqiCity() {
        return aqiCity;
    }

    public void setAqiCity(AqiCityEntity aqiCity) {
        this.aqiCity = aqiCity;
    }
}
