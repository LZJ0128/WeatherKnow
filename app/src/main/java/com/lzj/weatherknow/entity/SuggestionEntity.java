package com.lzj.weatherknow.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2/23 0023.
 * 生活指数
 */
public class SuggestionEntity {
    @SerializedName("comf")
    private SugComfEntity comfEntity;   //舒适指数
    @SerializedName("drsg")
    private SugDressingEntity dressingEntity;  //穿衣指数
    @SerializedName("uv")
    private SugUVEntity uvEntity;  //紫外线指数
    @SerializedName("cw")
    private SugCarEntity carEntity;    //洗车指数
    @SerializedName("trav")
    private SugTravelEntity travelEntity;  //旅游指数
    @SerializedName("flu")
    private SugFluEntity fluEntity;    //感冒指数
    @SerializedName("sport")
    private SugSportEntity sportEntity;    //运动指数

    public SugComfEntity getComfEntity() {
        return comfEntity;
    }

    public void setComfEntity(SugComfEntity comfEntity) {
        this.comfEntity = comfEntity;
    }

    public SugDressingEntity getDressingEntity() {
        return dressingEntity;
    }

    public void setDressingEntity(SugDressingEntity dressingEntity) {
        this.dressingEntity = dressingEntity;
    }

    public SugSportEntity getSportEntity() {
        return sportEntity;
    }

    public void setSportEntity(SugSportEntity sportEntity) {
        this.sportEntity = sportEntity;
    }

    public SugFluEntity getFluEntity() {
        return fluEntity;
    }

    public void setFluEntity(SugFluEntity fluEntity) {
        this.fluEntity = fluEntity;
    }

    public SugTravelEntity getTravelEntity() {
        return travelEntity;
    }

    public void setTravelEntity(SugTravelEntity travelEntity) {
        this.travelEntity = travelEntity;
    }

    public SugUVEntity getUvEntity() {
        return uvEntity;
    }

    public void setUvEntity(SugUVEntity uvEntity) {
        this.uvEntity = uvEntity;
    }

    public SugCarEntity getCarEntity() {
        return carEntity;
    }

    public void setCarEntity(SugCarEntity carEntity) {
        this.carEntity = carEntity;
    }
}
