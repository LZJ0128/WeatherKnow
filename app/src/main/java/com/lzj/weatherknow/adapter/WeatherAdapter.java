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

        //天气对应图标
        switch (entity.getCond().getDayTxt()){
            case "晴":
                holder.mImageView.setImageResource(R.drawable.cond_qing);
                break;
            case "多云":
                holder.mImageView.setImageResource(R.drawable.cond_duoyun);
                break;
            case "少云":
                holder.mImageView.setImageResource(R.drawable.cond_shaoyun);
                break;
            case "晴间多云":
                holder.mImageView.setImageResource(R.drawable.cond_qingjianduoyun);
                break;
            case "阴":
                holder.mImageView.setImageResource(R.drawable.cond_yin);
                break;
            case "阵雨":
                holder.mImageView.setImageResource(R.drawable.cond_zhenyu);
                break;
            case "强阵雨":
                holder.mImageView.setImageResource(R.drawable.cond_qiangzhenyu);
                break;
            case "雷阵雨":
                holder.mImageView.setImageResource(R.drawable.cond_leizhenyu);
                break;
            case "小雨":
                holder.mImageView.setImageResource(R.drawable.cond_xiaoyu);
                break;
            case "中雨":
                holder.mImageView.setImageResource(R.drawable.cond_zhongyu);
                break;
            case "大雨":
                holder.mImageView.setImageResource(R.drawable.cond_dayu);
                break;
            case "暴雨":
                holder.mImageView.setImageResource(R.drawable.cond_baoyu);
                break;
            case "大暴雨":
                holder.mImageView.setImageResource(R.drawable.cond_dabaoyu);
                break;
            case "特大暴雨":
                holder.mImageView.setImageResource(R.drawable.cond_tedabaoyu);
                break;
            case "小雪":
                holder.mImageView.setImageResource(R.drawable.cond_xiaoxue);
                break;
            case "中雪":
                holder.mImageView.setImageResource(R.drawable.cond_zhongxue);
                break;
            case "大雪":
                holder.mImageView.setImageResource(R.drawable.cond_daxue);
                break;
            case "暴雪":
                holder.mImageView.setImageResource(R.drawable.cond_baoxue);
                break;
            case "雨夹雪":
                holder.mImageView.setImageResource(R.drawable.cond_yujiaxue);
                break;
            case "雾":
                holder.mImageView.setImageResource(R.drawable.cond_wu);
                break;
            case "霾":
                holder.mImageView.setImageResource(R.drawable.cond_mai);
                break;
            default:
                //未知
                holder.mImageView.setImageResource(R.drawable.cond_weizhi);
                break;
        }

        return concertView;
    }

    static class WeatherHolder{
        TextView mTxvDate, mTxvWeek, mTxvTemp;
        ImageView mImageView;
    }

}
