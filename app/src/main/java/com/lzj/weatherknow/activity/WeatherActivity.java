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
import com.lzj.weatherknow.entity.HeWeatherDataEntity;
import com.lzj.weatherknow.entity.NowEntity;
import com.lzj.weatherknow.entity.ObjectEntity;
import com.lzj.weatherknow.helper.ConstantHelper;
import com.lzj.weatherknow.helper.DBOperationHelper;
import com.lzj.weatherknow.helper.JsonHelper;
import com.lzj.weatherknow.helper.SharePreferenceHelper;
import com.lzj.weatherknow.listener.HttpCallbackListener;
import com.lzj.weatherknow.util.HttpUtil;

import java.util.List;

public class WeatherActivity extends Activity implements View.OnClickListener{

    private TextView mTxvText;
    DBOperationHelper dbOperationHelper;
    private String cancelOrClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        mTxvText = (TextView)findViewById(R.id.txv_text);
        dbOperationHelper = DBOperationHelper.getInstance(this);
        cancelOrClick = SharePreferenceHelper.getStringSP(WeatherActivity.this, "cancel_or_click", "click", "");
        getCityWeather();
    }

    public void onClick(View v){
        switch (v.getId()){
            default:
                break;
        }
    }

    public void getCityWeather(){

        String cityName = "";

        if (cancelOrClick.equals("click")){
            Intent intent = getIntent();
            cityName = intent.getStringExtra("city_name");
        }else if (cancelOrClick.equals("cancel")){

        }

        String cityId = DBOperationHelper.getInstance(this).getCityId(cityName);

        HttpUtil.sendHttpRequest(ConstantHelper.cityInfoUrl(cityId), new HttpCallbackListener() {
            @Override
            public void onResponseSuccess(String response) {
                Log.e("MainActivity2", "onResponseSuccess");
                ObjectEntity objectEntity = JsonHelper.fromJson(response, ObjectEntity.class);
                if (objectEntity == null){
                    Log.e("MainActivity", "ObjectEntityError");
                    return;
                }
                List<HeWeatherDataEntity> heWeatherDataEntity = objectEntity.getWeatherDataList();
                NowEntity nowEntity = heWeatherDataEntity.get(0).getNow();
                fillData(nowEntity);

            }

            @Override
            public void onResponseError(Exception e) {
                e.printStackTrace();
                Log.e("MainActivity2", "onResponseError");
            }
        });
    }

    public void fillData(final NowEntity nowEntity){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTxvText.setText(nowEntity.getTmp());
            }
        });

    }


}
