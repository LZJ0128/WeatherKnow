package com.lzj.weatherknow.entity;

/**
 * Created by Administrator on 2/23 0023.
 * 每小时天气报告
 */
public class HourlyForecastEntity {
    private String date;
    private String hum;//湿度(%)
    private String pop;//降水概率
    private String pres;//气压
    private String tmp;
    private WindEntity wind;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public WindEntity getWind() {
        return wind;
    }

    public void setWind(WindEntity wind) {
        this.wind = wind;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }
}
