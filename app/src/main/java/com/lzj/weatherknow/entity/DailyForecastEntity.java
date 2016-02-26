package com.lzj.weatherknow.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2/23 0023.
 * 每日预报
 */
public class DailyForecastEntity {

    private AstroEntity astro;    //天文数值
    private ConditionEntity cond;    //天气状况
    private TempEntity tmp;    //温度
    private WindEntity wind;    //风力状况
    private String date;    //日期
    private String hum;     //湿度
    private String pcpn;    //降雨量
    private String pop;     //降雨概率
    private String pres;    //气压
    private String vis;     //能见度（km）

    public AstroEntity getAstro() {
        return astro;
    }

    public void setAstro(AstroEntity astro) {
        this.astro = astro;
    }

    public ConditionEntity getCond() {
        return cond;
    }

    public void setCond(ConditionEntity cond) {
        this.cond = cond;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TempEntity getTmp() {
        return tmp;
    }

    public void setTmp(TempEntity tmp) {
        this.tmp = tmp;
    }

    public WindEntity getWind() {
        return wind;
    }

    public void setWind(WindEntity wind) {
        this.wind = wind;
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

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }
}
