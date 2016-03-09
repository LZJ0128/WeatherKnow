package com.lzj.weatherknow.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;

import com.lzj.weatherknow.R;
import com.lzj.weatherknow.adapter.WeatherListAdapter;
import com.lzj.weatherknow.entity.WeatherEntity;
import com.lzj.weatherknow.helper.DBOperationHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 3/9 0009.
 */
public class WeatherListActivity extends Activity {

    private ListView mListView;
    private List<WeatherEntity> mList = new ArrayList<>();

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
    }
}
