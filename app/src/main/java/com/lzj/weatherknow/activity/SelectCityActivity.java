package com.lzj.weatherknow.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.lzj.weatherknow.adapter.CityListAdapter;
import com.lzj.weatherknow.R;
import com.lzj.weatherknow.entity.CityEntity;
import com.lzj.weatherknow.helper.ConstantHelper;
import com.lzj.weatherknow.helper.DBOperationHelper;
import com.lzj.weatherknow.helper.JsonHelper;
import com.lzj.weatherknow.helper.SharePreferenceHelper;
import com.lzj.weatherknow.listener.HttpCallbackListener;
import com.lzj.weatherknow.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2/24 0024.
 * 选择城市页
 */
public class SelectCityActivity extends Activity implements View.OnClickListener{

    private EditText mEdtSearch;
    private TextView mTxvCancel;
    private ListView mLsvCity;
    List<String> mCityList;
    List<String> mNewCityList;
    List<CityEntity> mCityEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_select_city);
        if (SharePreferenceHelper.getIntSP(this, "flag", "flag", 0) == 0){
            //防止多次从数据库加载数据，加载一次后不再加载
            getCityList();
        }
        initUI();
    }

    /**
     * 初始化视图
     */
    public void initUI(){
        mEdtSearch = (EditText)findViewById(R.id.edt_search);
        mTxvCancel = (TextView)findViewById(R.id.txv_cancel);
        mTxvCancel.setOnClickListener(this);
        mLsvCity = (ListView)findViewById(R.id.lsv_city);
        mEdtSearch.addTextChangedListener(watcher);
        mCityEntity = DBOperationHelper.getInstance(SelectCityActivity.this).getCityList();
        mCityList = new ArrayList<>();
        mNewCityList = new ArrayList<>();
        for (int i=0;i<mCityEntity.size();i++){
            String cityName = mCityEntity.get(i).getCityName();
            mCityList.add(cityName);
        }
        mLsvCity.setAdapter(new CityListAdapter(this, mCityList));
        mLsvCity.setOnItemClickListener(mOnItemClick);

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.txv_cancel:
                //取消后直接返回到天气页
                Intent intent = new Intent(SelectCityActivity.this, WeatherActivity.class);
                SharePreferenceHelper.setStringSP(SelectCityActivity.this, "cancel_or_click", "click", "cancel");
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private AdapterView.OnItemClickListener mOnItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String cityName = mCityList.get(position);
            Intent intent = new Intent(SelectCityActivity.this, WeatherActivity.class);
            SharePreferenceHelper.setStringSP(SelectCityActivity.this, "cancel_or_click", "click", "click");
            intent.putExtra("city_name", cityName);
            startActivity(intent);
        }
    };

    /**
     * 先根据url向服务器请求数据，成功后把数据保存在本地
     */
    public void getCityList(){
        HttpUtil.sendHttpRequest(ConstantHelper.URL_CITY_LIST, new HttpCallbackListener() {
            @Override
            public void onResponseSuccess(String response) {
                Log.e("MainActivity1", "onResponseSuccess");
                JsonHelper.handleCityListResponse(SelectCityActivity.this, response);
//                initUI();
            }

            @Override
            public void onResponseError(Exception e) {
                Log.e("MainActivity1", "onResponseError");
            }
        });
    }

    /**
     * 文本观察者，EditText内容实时改变时ListView也实时改变
     */
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mNewCityList.clear();
            String cityName = mEdtSearch.getText().toString();
            for (int i=0;i<mCityList.size();i++){
                if (mCityList.get(i).contains(cityName)){
                    mNewCityList.add(mCityList.get(i));
                }
            }
            mCityList = mNewCityList;
            mLsvCity.setAdapter(new CityListAdapter(SelectCityActivity.this, mCityList));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
