package com.lzj.weatherknow.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzj.weatherknow.R;
import com.lzj.weatherknow.entity.WeatherEntity;
import com.lzj.weatherknow.helper.DBOperationHelper;

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
    public View getView(final int position, View concertView, ViewGroup parent){
        WeatherHolder holder = null;
        if (concertView == null){
            holder = new WeatherHolder();
            concertView = LayoutInflater.from(mContext).inflate(R.layout.item_weather_list, null);
            holder.mTxvCityName = (TextView)concertView.findViewById(R.id.txv_city_name);
            holder.mTxvTemp = (TextView)concertView.findViewById(R.id.txv_temp);
            holder.mTxvWeather = (TextView)concertView.findViewById(R.id.txv_weather);
            holder.mTxvDelete = (TextView)concertView.findViewById(R.id.txv_delete);
            holder.mRelBg = (RelativeLayout)concertView.findViewById(R.id.rel_bg_item);
            concertView.setTag(holder);
        }else {
            holder = (WeatherHolder)concertView.getTag();
        }

        final WeatherEntity entity = getItem(position);
        holder.mTxvCityName.setText(entity.getCityName());
        holder.mTxvWeather.setText(entity.getWeather());
        holder.mTxvTemp.setText(entity.getTemp());

        //加载天气对应的背景图片
        switch (entity.getWeather()){
            case "晴":
            case "晴间多云":
                holder.mRelBg.setBackgroundResource(R.drawable.bg_qing);
                break;
            case "多云":
            case "少云":
                holder.mRelBg.setBackgroundResource(R.drawable.bg_duoyun);
                break;
            case "小雨":
                holder.mRelBg.setBackgroundResource(R.drawable.bg_xiaoyu);
                break;
            case "大雨":
            case "暴雨":
            case "大暴雨":
            case "特大暴雨":
                holder.mRelBg.setBackgroundResource(R.drawable.bg_dayu);
                break;
            case "阵雨":
            case "强阵雨":
                holder.mRelBg.setBackgroundResource(R.drawable.bg_zhenyu);
                break;
            case "雪":
            case "暴雪":
            case "小雪":
            case "大雪":
                holder.mRelBg.setBackgroundResource(R.drawable.bg_xue);
                break;
            case "阴":
                holder.mRelBg.setBackgroundResource(R.drawable.bg_yin);
                break;
            case "雾":
            case "霾":
                holder.mRelBg.setBackgroundResource(R.drawable.bg_wumai);
                break;
            case "雷阵雨":
                holder.mRelBg.setBackgroundResource(R.drawable.bg_leidian);
                break;
            default:
                break;
        }

        holder.mTxvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCount() == 1){
                    Toast.makeText(mContext, "无法删除", Toast.LENGTH_SHORT).show();
                    return;
                }
                DBOperationHelper.getInstance(mContext).deleteWeather(entity.getCityName());
                mList.remove(position);
                notifyDataSetChanged();
            }
        });
        return concertView;
    }

    static class WeatherHolder{
        private RelativeLayout mRelBg;
        private TextView mTxvCityName;
        private TextView mTxvWeather;
        private TextView mTxvTemp;
        private TextView mTxvDelete;
    }


}
