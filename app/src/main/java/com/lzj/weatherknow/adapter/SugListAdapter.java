package com.lzj.weatherknow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lzj.weatherknow.R;
import com.lzj.weatherknow.entity.SugListEntity;

import java.util.List;

/**
 * Created by Administrator on 3/8 0008.
 */
public class SugListAdapter extends BaseAdapter {

    private Context mContext;
    private List<SugListEntity> mSugList;

    public SugListAdapter(Context context, List<SugListEntity> list){
        this.mContext = context;
        this.mSugList = list;
    }

    @Override
    public int getCount(){
        return mSugList.size();
    }

    @Override
    public SugListEntity getItem(int position){
        return mSugList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View concertView, ViewGroup parent){
        SugHolder holder = null;
        if (concertView == null){
            holder = new SugHolder();
            concertView = LayoutInflater.from(mContext).inflate(R.layout.item_suggestion, null);
            holder.mTitle = (TextView)concertView.findViewById(R.id.txv_sug_title);
            holder.mBrf = (TextView)concertView.findViewById(R.id.txv_sug_brf);
            holder.mTxt = (TextView)concertView.findViewById(R.id.txv_sug_txt);
            concertView.setTag(holder);
        }else {
            holder = (SugHolder)concertView.getTag();
        }

        SugListEntity entity = getItem(position);
        holder.mTitle.setText(entity.getTitle());
        holder.mBrf.setText(entity.getBrf());
        holder.mTxt.setText(entity.getTxt());

        return concertView;
    }

    static class SugHolder{
        private TextView mTitle;
        private TextView mBrf;
        private TextView mTxt;
    }
}
