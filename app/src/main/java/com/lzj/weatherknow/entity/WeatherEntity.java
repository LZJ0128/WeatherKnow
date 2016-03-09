package com.lzj.weatherknow.entity;

/**
 * Created by Administrator on 3/9 0009.
 * 天气列表实体
 */
public class WeatherEntity {

    private String cityName;
    private String weather;
    private String temp;

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
