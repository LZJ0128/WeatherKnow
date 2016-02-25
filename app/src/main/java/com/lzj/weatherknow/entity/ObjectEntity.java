package com.lzj.weatherknow.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2/23 0023.
 */
public class ObjectEntity {
    @SerializedName("HeWeather data service 3.0")
    private List<HeWeatherDataEntity> weatherDataList;

    public List<HeWeatherDataEntity> getWeatherDataList() {
        return weatherDataList;
    }
    public void setWeatherDataList(List<HeWeatherDataEntity> weatherDataList) {
        this.weatherDataList = weatherDataList;
    }
}
