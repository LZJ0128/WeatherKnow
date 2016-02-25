package com.lzj.weatherknow.entity;

/**
 * Created by Administrator on 2/23 0023.
 * 当前天气状况
 */
public class NowCondEntity {
    private String code;
    private String txt;

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
