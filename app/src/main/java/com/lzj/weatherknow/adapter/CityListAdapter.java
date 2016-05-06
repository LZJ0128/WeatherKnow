package com.lzj.weatherknow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lzj.weatherknow.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2/24 0024.
 * SelectCity适配器
 */
public class CityListAdapter extends BaseAdapter{

    private Context mContext;
    private List<String> mCityList = new ArrayList<>();

    public CityListAdapter(Context context, List<String> cityList){
        this.mContext = context;
        this.mCityList = cityList;
    }

    @Override
    public int getCount(){
        return mCityList.size();
    }

    @Override
    public String getItem(int position){
        return mCityList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View concertView, ViewGroup parent){
        ViewHolder holder = null;
        if (concertView == null){
            concertView = LayoutInflater.from(mContext).inflate(R.layout.item_city, null);
            holder = new ViewHolder();
            holder.mCityName = (TextView)concertView.findViewById(R.id.txv_item_city);
            concertView.setTag(holder);
        }else{
            holder = (ViewHolder)concertView.getTag();
        }
        String cityName = getItem(position);
        holder.mCityName.setText(cityName);

        return concertView;
    }

    static class ViewHolder{
        TextView mCityName;
    }

}
