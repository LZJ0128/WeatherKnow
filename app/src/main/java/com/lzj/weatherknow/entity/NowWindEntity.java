package com.lzj.weatherknow.entity;

/**
 * Created by Administrator on 2/23 0023.
 * 当前风力状况
 */
public class NowWindEntity {
    private String deg;//风向角度
    private String dir;//风向
    private String sc;//风力等级
    private String spd;//风速

    public String getDeg() {
        return deg;
    }

    public void setDeg(String deg) {
        this.deg = deg;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getSpd() {
        return spd;
    }

    public void setSpd(String spd) {
        this.spd = spd;
    }
}
