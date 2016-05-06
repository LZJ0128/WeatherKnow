package com.lzj.weatherknow.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzj.weatherknow.R;
import com.lzj.weatherknow.entity.DailyForecastEntity;
import com.lzj.weatherknow.entity.HeWeatherDataEntity;
import com.lzj.weatherknow.entity.HourlyForecastEntity;
import com.lzj.weatherknow.helper.ConstantHelper;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2/26 0026.
 * WeatherActivity适配器
 */
public class WeatherAdapter extends BaseAdapter{

    private Context mContext;
    List<DailyForecastEntity> mDaily;

    public WeatherAdapter(Context context, List<DailyForecastEntity> daily){
        this.mContext = context;
        this.mDaily = daily;
    }

    @Override
    public int getCount(){
        return mDaily.size()-1;
    }

    @Override
    public DailyForecastEntity getItem(int position){
        return mDaily.get(position);
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
            concertView = LayoutInflater.from(mContext).inflate(R.layout.item_weather_2, null);
            holder.mTxvDate = (TextView)concertView.findViewById(R.id.txv_date1);
            holder.mTxvWeek = (TextView)concertView.findViewById(R.id.txv_weekday1);
            holder.mTxvTemp = (TextView)concertView.findViewById(R.id.txv_temp);
            holder.mImageView = (ImageView)concertView.findViewById(R.id.img_weather1);
        }else {
            holder = (WeatherHolder)concertView.getTag();
        }

        DailyForecastEntity entity = getItem(position + 1);
        String date = entity.getDate().substring(5);//日期
        holder.mTxvDate.setText(date);
        holder.mTxvWeek.setText(ConstantHelper.getWeekday(position));
        holder.mTxvTemp.setText(entity.getTmp().getMax() + " ~ " + entity.getTmp().getMin() + "°");

        return concertView;
    }

    static class WeatherHolder{
        TextView mTxvDate, mTxvWeek, mTxvTemp;
        ImageView mImageView;
    }

}
