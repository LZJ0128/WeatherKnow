package com.lzj.weatherknow.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2/23 0023.
 * 天气状况
 */
public class ConditionEntity {
    @SerializedName("code_d")
    private String dayCode;
    @SerializedName("code_n")
    private String nightCode;
    @SerializedName("txt_d")
    private String dayTxt;
    @SerializedName("txt_n")
    private String nightTxt;

    public String getDayCode() {
        return dayCode;
    }

    public void setDayCode(String dayCode) {
        this.dayCode = dayCode;
    }

    public String getDayTxt() {
        return dayTxt;
    }

    public void setDayTxt(String dayTxt) {
        this.dayTxt = dayTxt;
    }

    public String getNightTxt() {
        return nightTxt;
    }

    public void setNightTxt(String nightTxt) {
        this.nightTxt = nightTxt;
    }

    public String getNightCode() {
        return nightCode;
    }

    public void setNightCode(String nightCode) {
        this.nightCode = nightCode;
    }
}
