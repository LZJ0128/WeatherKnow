package com.lzj.weatherknow.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lzj.weatherknow.R;
import com.lzj.weatherknow.entity.BasicEntity;
import com.lzj.weatherknow.entity.HeWeatherDataEntity;
import com.lzj.weatherknow.entity.NowEntity;
import com.lzj.weatherknow.entity.ObjectEntity;
import com.lzj.weatherknow.entity.UpdateEntity;
import com.lzj.weatherknow.helper.ConstantHelper;
import com.lzj.weatherknow.helper.DBOperationHelper;
import com.lzj.weatherknow.helper.JsonHelper;
import com.lzj.weatherknow.helper.SharePreferenceHelper;
import com.lzj.weatherknow.listener.HttpCallbackListener;
import com.lzj.weatherknow.util.HttpUtil;

import java.util.List;

public class WeatherActivity extends Activity implements View.OnClickListener{

    /**
     * 分别为：城市名，天气，温度
     */
    private TextView mTxvCity, mTxvWeather, mTxvTemp, mTxvUpdate;
    DBOperationHelper dbOperationHelper;
    private String cancelOrClick;
    private String mCityName;//城市名

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        dbOperationHelper = DBOperationHelper.getInstance(this);
        cancelOrClick = SharePreferenceHelper.getStringSP(WeatherActivity.this, "cancel_or_click", "click", "");
        initUI();
        getCityWeather();
    }

    public void initUI(){
        mTxvCity = (TextView)findViewById(R.id.txv_city);
        mTxvWeather = (TextView)findViewById(R.id.txv_weather);
        mTxvTemp = (TextView)findViewById(R.id.txv_temp);
        mTxvUpdate = (TextView)findViewById(R.id.txv_update);
    }

    public void onClick(View v){
        switch (v.getId()){
            default:
                break;
        }
    }

    /**
     * 获取天气
     */
    public void getCityWeather(){

        if (cancelOrClick.equals("click")){
            mCityName = SharePreferenceHelper.getStringSP(this, "city_name", "city_name", "福州");
        }else if (cancelOrClick.equals("cancel")){

        }
        //城市ID转换为城市ID
        String cityId = DBOperationHelper.getInstance(this).getCityId(mCityName);
        //由城市ID获取天气信息
        HttpUtil.sendHttpRequest(ConstantHelper.cityInfoUrl(cityId), new HttpCallbackListener() {
            @Override
            public void onResponseSuccess(String response) {
                ObjectEntity objectEntity = JsonHelper.fromJson(response, ObjectEntity.class);
                if (objectEntity == null){
                    Log.e("MainActivity", "ObjectEntityError");
                    return;
                }
                //heWeatherDataEntity虽是列表，但只有一条
                List<HeWeatherDataEntity> heWeatherDataEntity = objectEntity.getWeatherDataList();

                //当前天气信息
                NowEntity nowEntity = heWeatherDataEntity.get(0).getNow();
                fillNowData(nowEntity);
                //基础天气信息
                BasicEntity basicEntity = heWeatherDataEntity.get(0).getBasic();
                //获取更新时间
                UpdateEntity updateEntity = basicEntity.getUpdate();
                fillUpdateData(updateEntity);
            }

            @Override
            public void onResponseError(Exception e) {
                e.printStackTrace();
                Log.e("MainActivity2", "onResponseError");
            }
        });
    }

    /**
     * 填充当前的天气
     * @param nowEntity
     */
    public void fillNowData(final NowEntity nowEntity){
        //UI界面只能在主线程中改变
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTxvTemp.setText(nowEntity.getTmp() + "℃");
                mTxvCity.setText(mCityName);
                mTxvWeather.setText(nowEntity.getNowCond().getTxt());
            }
        });
    }

    /**
     * 填充更新时间
     * @param updateEntity
     */
    public void fillUpdateData(final UpdateEntity updateEntity){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTxvUpdate.setText(updateEntity.getLocalTime());
            }
        });
    }


}
