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
            concertView.setTag(holder);
        }else {
            holder = (WeatherHolder)concertView.getTag();
        }

        final WeatherEntity entity = getItem(position);
        holder.mTxvCityName.setText(entity.getCityName());
        holder.mTxvWeather.setText(entity.getWeather());
        holder.mTxvTemp.setText(entity.getTemp());
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
        private TextView mTxvCityName;
        private TextView mTxvWeather;
        private TextView mTxvTemp;
        private TextView mTxvDelete;
    }


}
