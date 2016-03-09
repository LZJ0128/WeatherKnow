package com.lzj.weatherknow.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lzj.weatherknow.R;
import com.lzj.weatherknow.entity.WeatherEntity;

import java.util.List;

/**
 * Created by Administrator on 3/9 0009.
 * 天气列表适配器
 */
public class WeatherListAdapter extends BaseAdapter {

    private Context mContext;
    private List<WeatherEntity> mList;

    public WeatherListAdapter(Context context, List<WeatherEntity> list){
        this.mContext = context;
        this.mList = list;
        Log.e("Adapter", "" + mList.size());
    }

    @Override
    public int getCount(){
        return mList.size();
    }

    @Override
    public WeatherEntity getItem(int position){
        return mList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View concertView, ViewGroup parent){
        WeatherHolder holder = null;
        if (concertView == null){
            holder = new WeatherHolder();
            concertView = LayoutInflater.from(mContext).inflate(R.layout.item_weather_list, null);
            holder.mTxvCityName = (TextView)concertView.findViewById(R.id.txv_city_name);
            holder.mTxvTemp = (TextView)concertView.findViewById(R.id.txv_temp);
            holder.mTxvWeather = (TextView)concertView.findViewById(R.id.txv_weather);
            concertView.setTag(holder);
        }else {
            holder = (WeatherHolder)concertView.getTag();
        }

        WeatherEntity entity = getItem(position);
        holder.mTxvCityName.setText(entity.getCityName());
        holder.mTxvWeather.setText(entity.getWeather());
        holder.mTxvTemp.setText(entity.getTemp());

        return concertView;
    }

    static class WeatherHolder{
        private TextView mTxvCityName;
        private TextView mTxvWeather;
        private TextView mTxvTemp;
    }


}
