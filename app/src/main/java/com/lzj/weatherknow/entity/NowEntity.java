package com.lzj.weatherknow.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2/23 0023.
 * 实况天气
 */
public class NowEntity {
    @SerializedName("cond")
    private NowCondEntity nowCond;//当前天气状况
    @SerializedName("wind")
    private NowWindEntity nowWind;//当前风力状况
    private String hum;//	湿度(%)
    private String pcpn;//降雨量(mm)
    private String tmp;//当前温度

    public NowCondEntity getNowCond() {
        return nowCond;
    }

    public void setNowCond(NowCondEntity nowCond) {
        this.nowCond = nowCond;
    }

    public NowWindEntity getNowWind() {
        return nowWind;
    }

    public void setNowWind(NowWindEntity nowWind) {
        this.nowWind = nowWind;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }
}
