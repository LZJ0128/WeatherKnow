package com.lzj.weatherknow.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2/23 0023.
 */
public class HeWeatherDataEntity {

    private AqiEntity aqi;//空气质量
    private BasicEntity basic;//城市基本信息
    @SerializedName("daily_forecast")
    private List<DailyForecastEntity> dailyForecastList;//每天天气预报
    @SerializedName("hourly_forecast")
    private List<HourlyForecastEntity> hourlyForecastList;//每小时天气预报
    private NowEntity now;//当前天气状况
    private SuggestionEntity suggestion;//生活指数

    public AqiEntity getAqi() {
        return aqi;
    }

    public void setAqi(AqiEntity aqi) {
        this.aqi = aqi;
    }

    public BasicEntity getBasic() {
        return basic;
    }

    public void setBasic(BasicEntity basic) {
        this.basic = basic;
    }

    public List<DailyForecastEntity> getDailyForecastList() {
        return dailyForecastList;
    }

    public void setDailyForecastList(List<DailyForecastEntity> dailyForecastList) {
        this.dailyForecastList = dailyForecastList;
    }

    public List<HourlyForecastEntity> getHourlyForecastList() {
        return hourlyForecastList;
    }

    public void setHourlyForecastList(List<HourlyForecastEntity> hourlyForecastList) {
        this.hourlyForecastList = hourlyForecastList;
    }

    public NowEntity getNow() {
        return now;
    }

    public void setNow(NowEntity now) {
        this.now = now;
    }

    public SuggestionEntity getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(SuggestionEntity suggestion) {
        this.suggestion = suggestion;
    }
}
