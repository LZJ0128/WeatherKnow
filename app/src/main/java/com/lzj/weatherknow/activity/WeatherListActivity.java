package com.lzj.weatherknow.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.lzj.weatherknow.R;
import com.lzj.weatherknow.adapter.WeatherListAdapter;
import com.lzj.weatherknow.entity.WeatherEntity;
import com.lzj.weatherknow.helper.DBOperationHelper;
import com.lzj.weatherknow.helper.SharePreferenceHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 3/9 0009.
 * 天气列表
 */
public class WeatherListActivity extends Activity {

    private ListView mListView;
    private List<WeatherEntity> mList = new ArrayList<>();

    private ImageView mImvAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather_list);
        mList = DBOperationHelper.getInstance(this).getWeathers();
        initUI();
    }

    public void initUI(){
        mListView = (ListView)findViewById(R.id.lsv_weather_list);
        mListView.setAdapter(new WeatherListAdapter(this, mList));
        mListView.addFooterView(footerView());
        mListView.setOnItemClickListener(mOnItemClick);
    }


    AdapterView.OnItemClickListener mOnItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == mList.size()){
                return;
            }
            String cityName = mList.get(position).getCityName();
            SharePreferenceHelper.setStringSP(WeatherListActivity.this, "city_name", "city_name", cityName);
            Intent intent = new Intent(WeatherListActivity.this, WeatherActivity.class);
            startActivity(intent);
            finish();
        }
    };

    public View footerView(){
        View footerView = LayoutInflater.from(this).inflate(R.layout.footer_weather_list, null);
        mImvAdd = (ImageView)footerView.findViewById(R.id.imv_add);
        mImvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherListActivity.this, SelectCityActivity.class);
                intent.putExtra("from_weather_list", true);
                startActivity(intent);
            }
        });
        return footerView;
    }
}
