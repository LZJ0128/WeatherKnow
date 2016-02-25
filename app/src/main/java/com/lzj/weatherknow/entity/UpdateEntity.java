package com.lzj.weatherknow.entity;

/**
 * Created by Administrator on 2/23 0023.
 * 更新时间
 */
public class UpdateEntity {

    private String localTime;
    private String utcTime;

    public String getLocalTime() {
        return localTime;
    }

    public void setLocalTime(String localTime) {
        this.localTime = localTime;
    }

    public String getUtcTime() {
        return utcTime;
    }

    public void setUtcTime(String utcTime) {
        this.utcTime = utcTime;
    }
}
