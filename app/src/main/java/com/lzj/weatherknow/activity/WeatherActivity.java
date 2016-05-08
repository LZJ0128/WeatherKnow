package com.lzj.weatherknow.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzj.weatherknow.R;
import com.lzj.weatherknow.adapter.SugListAdapter;
import com.lzj.weatherknow.adapter.WeatherAdapter;
import com.lzj.weatherknow.entity.AqiEntity;
import com.lzj.weatherknow.entity.BasicEntity;
import com.lzj.weatherknow.entity.DailyForecastEntity;
import com.lzj.weatherknow.entity.HeWeatherDataEntity;
import com.lzj.weatherknow.entity.HourlyForecastEntity;
import com.lzj.weatherknow.entity.NowEntity;
import com.lzj.weatherknow.entity.ObjectEntity;
import com.lzj.weatherknow.entity.SugListEntity;
import com.lzj.weatherknow.entity.SuggestionEntity;
import com.lzj.weatherknow.entity.UpdateEntity;
import com.lzj.weatherknow.helper.ActivityManagerHelper;
import com.lzj.weatherknow.helper.ConstantHelper;
import com.lzj.weatherknow.helper.DBOperationHelper;
import com.lzj.weatherknow.helper.JsonHelper;
import com.lzj.weatherknow.helper.SharePreferenceHelper;
import com.lzj.weatherknow.listener.HttpCallbackListener;
import com.lzj.weatherknow.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends Activity implements View.OnClickListener{

    /**
     * 分别为：城市名，ID, 天气，温度
     */
    private TextView mTxvCity, mTxvWeather, mTxvTemp;
    private String mCityName;//城市名
    private String mCityId;
    private ListView mLsvWeather;
    List<DailyForecastEntity> mDailyList = new ArrayList<>();
    List<HourlyForecastEntity> mHourlyList = new ArrayList<>();
    /**
     * 更新时间
     */
    private UpdateEntity mUpdateEntity;
    private ImageView mImvRefresh;
    private TextView mTxvRefresh;
    /**
     * 生活指数
     */
    private TextView mSug;
    private SuggestionEntity mSugEntity;
    /**
     * 切换
     */
    private ImageView mImvChange;
    /**
     * 背景图片
     */
    private RelativeLayout mRelBg;
    //退出间隔
    private long mExitTime = 0;
    //进度条
    private ProgressDialog mProgressDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ActivityManagerHelper.getInstance().addActivity(this);
        initUI();
        getCityWeather();
    }

    public void initUI(){
        mTxvCity = (TextView)findViewById(R.id.txv_city);
        mTxvWeather = (TextView)findViewById(R.id.txv_weather);
        mTxvTemp = (TextView)findViewById(R.id.txv_temp);
        mLsvWeather = (ListView)findViewById(R.id.lsv_weather);
        mImvRefresh = (ImageView)findViewById(R.id.imv_refresh);
        mTxvRefresh = (TextView)findViewById(R.id.txv_refresh);
        mRelBg = (RelativeLayout)findViewById(R.id.rel_bg);
//        mLsvWeather.setAdapter(new WeatherAdapter(this, mDailyList));

        mSug = (TextView)findViewById(R.id.txv_sug);
        mSug.setOnClickListener(this);
        mImvChange = (ImageView)findViewById(R.id.imv_change);
        mImvChange.setOnClickListener(this);
        mImvRefresh.setOnClickListener(this);

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.txv_sug:
                showSugDialog(mSugEntity);
                break;
            case R.id.imv_change:
                Intent intent = new Intent(WeatherActivity.this, WeatherListActivity.class);
                startActivity(intent);
                break;
            case R.id.imv_refresh:
                Toast.makeText(WeatherActivity.this, "已更新信息！", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    /**
     * 获取天气
     */
    public void getCityWeather(){

        mCityName = SharePreferenceHelper.getStringSP(this, "city_name", "city_name", "福州");

        //城市名转换为城市ID
        mCityId = DBOperationHelper.getInstance(this).getCityId(mCityName);
        //显示进度条
        mProgressDialog = ProgressDialog.show(WeatherActivity.this, "请稍等。。。", "获取数据中。。。", true);
        //由城市ID获取天气信息
        HttpUtil.sendHttpRequest(ConstantHelper.cityInfoUrl(mCityId), new HttpCallbackListener() {
            @Override
            public void onResponseSuccess(String response) {
                ObjectEntity objectEntity = JsonHelper.fromJson(response, ObjectEntity.class);
                if (objectEntity == null) {
                    return;
                }
                //heWeatherDataEntity虽是列表，但只有一条
                List<HeWeatherDataEntity> heWeatherDataEntity = objectEntity.getWeatherDataList();

                //当前天气信息
                NowEntity nowEntity = heWeatherDataEntity.get(0).getNow();
                fillNowData(nowEntity);

                //基础天气信息
                BasicEntity basicEntity = heWeatherDataEntity.get(0).getBasic();

                //获取更新时间
                mUpdateEntity = basicEntity.getUpdate();
                fillUpdateData(mUpdateEntity);

                //获取每日天气列表
                mDailyList = heWeatherDataEntity.get(0).getDailyForecastList();
                mHourlyList = heWeatherDataEntity.get(0).getHourlyForecastList();
                fillDailyData(mDailyList, mHourlyList);
                AqiEntity aqiEntity = heWeatherDataEntity.get(0).getAqi();

                //获取底部信息
                getFooterView(aqiEntity, nowEntity, mDailyList);

                //生活指数
                mSugEntity = heWeatherDataEntity.get(0).getSuggestion();

                //取消进度条
                mProgressDialog.dismiss();
            }

            @Override
            public void onResponseError(Exception e) {
                e.printStackTrace();
                Log.e("WeatherActivity", "onResponseError");
            }
        });
    }

    /**
     * 填充当前的天气
     * @param nowEntity
     */
    public void fillNowData(final NowEntity nowEntity){
        //UI界面只能在主线程中改变
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mTxvTemp.setText(nowEntity.getTmp() + "°");
                mTxvCity.setText(mCityName);
                mTxvWeather.setText(nowEntity.getNowCond().getTxt());

                //加载天气对应的背景图片
                switch (nowEntity.getNowCond().getTxt()){
                    case "晴":
                    case "晴间多云":
                        mRelBg.setBackgroundResource(R.drawable.bg_qing);
                        break;
                    case "多云":
                    case "少云":
                        mRelBg.setBackgroundResource(R.drawable.bg_duoyun);
                        break;
                    case "小雨":
                        mRelBg.setBackgroundResource(R.drawable.bg_xiaoyu);
                        break;
                    case "大雨":
                    case "暴雨":
                    case "大暴雨":
                    case "特大暴雨":
                        mRelBg.setBackgroundResource(R.drawable.bg_dayu);
                        break;
                    case "阵雨":
                    case "强阵雨":
                        mRelBg.setBackgroundResource(R.drawable.bg_zhenyu);
                            break;
                    case "雪":
                    case "暴雪":
                    case "小雪":
                    case "大雪":
                        mRelBg.setBackgroundResource(R.drawable.bg_xue);
                        break;
                    case "阴":
                        mRelBg.setBackgroundResource(R.drawable.bg_yin);
                        break;
                    case "雾":
                    case "霾":
                        mRelBg.setBackgroundResource(R.drawable.bg_wumai);
                        break;
                    case "雷阵雨":
                        mRelBg.setBackgroundResource(R.drawable.bg_leidian);
                        break;
                    default:
                        break;
                }


                //把城市名、天气、温度存进数据库，以便在WeatherListActivity中使用
                DBOperationHelper.getInstance(getBaseContext()).addWeather(mCityName, nowEntity.getNowCond().getTxt(), nowEntity.getTmp() + "°");
            }
        });
    }

    /**
     * 填充近一周和未来几个小时的天气
     * @param dailyList
     * @param hourlyList
     */
    public void fillDailyData(final List<DailyForecastEntity> dailyList, final List<HourlyForecastEntity> hourlyList){
        final View headView = LayoutInflater.from(this).inflate(R.layout.item_weather_1, null);
        final TextView date = (TextView)headView.findViewById(R.id.txv_date0);
        final TextView weekday = (TextView)headView.findViewById(R.id.txv_weekday0);
        final TextView tempMax = (TextView)headView.findViewById(R.id.txv_temp_max);
        final TextView tempMin = (TextView)headView.findViewById(R.id.txv_temp_min);

        final TextView time0 = (TextView)headView.findViewById(R.id.txv_time0);
        final TextView time1 = (TextView)headView.findViewById(R.id.txv_time1);
        final TextView time2 = (TextView)headView.findViewById(R.id.txv_time2);
        final TextView temp0 = (TextView)headView.findViewById(R.id.txv_temp0);
        final TextView temp1 = (TextView)headView.findViewById(R.id.txv_temp1);
        final TextView temp2 = (TextView)headView.findViewById(R.id.txv_temp2);
        final TextView pop0 = (TextView)headView.findViewById(R.id.txv_pop0);
        final TextView pop1 = (TextView)headView.findViewById(R.id.txv_pop1);
        final TextView pop2 = (TextView)headView.findViewById(R.id.txv_pop2);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                date.setText(dailyList.get(0).getDate().substring(5));
                weekday.setText("星期" + ConstantHelper.getWeekday(-1));
                tempMax.setText(dailyList.get(0).getTmp().getMax());
                tempMin.setText(dailyList.get(0).getTmp().getMin() + "°");

                //把当天天气和每小时天气作为头部添加到ListView中
                mLsvWeather.addHeaderView(headView);

                time0.setText(hourlyList.get(0).getDate().substring(11));
                temp0.setText(hourlyList.get(0).getTmp() + "°");
                pop0.setText(hourlyList.get(0).getPop() + "%");

                if (hourlyList.size() == 1){
                    time1.setText("01:00");
                    temp1.setText(Integer.valueOf(hourlyList.get(0).getTmp()) - 1 + "°");
                    pop1.setText(hourlyList.get(0).getPop() + "%");
                    time2.setText("04:00");
                    temp2.setText(dailyList.get(0).getTmp().getMin() + "°");
                    pop2.setText(hourlyList.get(0).getPop() + "%");
                    return;
                }

                time1.setText(hourlyList.get(1).getDate().substring(11));
                temp1.setText(hourlyList.get(1).getTmp() + "°");
                pop1.setText(hourlyList.get(1).getPop() + "%");

                if (hourlyList.size() == 2){
                    time2.setText("01:00");
                    temp2.setText(Integer.valueOf(hourlyList.get(1).getTmp()) - 2 + "°");
                    pop2.setText(hourlyList.get(1).getPop() + "%");
                    return;
                }

                time2.setText(hourlyList.get(2).getDate().substring(11));
                temp2.setText(hourlyList.get(2).getTmp() + "°");
                pop2.setText(hourlyList.get(2).getPop() + "%");

            }
        });

    }

    /**
     * 添加ListView尾部信息
     * @param aqiEntity
     * @param nowEntity
     * @param dailyList
     */
    public void getFooterView(final AqiEntity aqiEntity, final NowEntity nowEntity, final List<DailyForecastEntity> dailyList){
        final View footView = LayoutInflater.from(this).inflate(R.layout.item_weather_3, null);
        final TextView qity = (TextView)footView.findViewById(R.id.txv_qity);
        final TextView aqi = (TextView)footView.findViewById(R.id.txv_aqi);
        final TextView sr = (TextView)footView.findViewById(R.id.txv_sr);
        final TextView ss = (TextView)footView.findViewById(R.id.txv_ss);
        final TextView windDir = (TextView)footView.findViewById(R.id.txv_wind_dir);
        final TextView windSc = (TextView)footView.findViewById(R.id.txv_wind_sc);
        final TextView windSpd = (TextView)footView.findViewById(R.id.txv_wind_spd);
        final TextView hum = (TextView)footView.findViewById(R.id.txv_hum);
        final TextView pcpn = (TextView)footView.findViewById(R.id.txv_pcpn);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (aqiEntity != null){
                    qity.setText(aqiEntity.getAqiCity().getQuality());
                    aqi.setText(aqiEntity.getAqiCity().getAqi());
                }else {
                    qity.setText("暂无数据");
                    aqi.setText("暂无数据");
                }
                sr.setText(dailyList.get(0).getAstro().getSunRise());
                ss.setText(dailyList.get(0).getAstro().getSunSet());
                windDir.setText(nowEntity.getNowWind().getDir());
                windSc.setText(nowEntity.getNowWind().getSc()  + " 级");
                windSpd.setText(nowEntity.getNowWind().getSpd() + " km/h");
                hum.setText(nowEntity.getHum() + " %");
                pcpn.setText(nowEntity.getPcpn() + " mm");

                mLsvWeather.addFooterView(footView);
                //未来六天的天气
                mLsvWeather.setAdapter(new WeatherAdapter(WeatherActivity.this, mDailyList));

            }
        });
    }

    /**
     * 填充更新时间
     * @param updateEntity
     */
    public void fillUpdateData(final UpdateEntity updateEntity){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTxvRefresh.setText(updateEntity.getLocalTime().substring(11));
            }
        });
    }


    /**
     * SuggestionEntity里面的数据是几个对象，而不是一个数组
     * 把SuggestionEntity转为List<SugListEntity>存储
     * @param entity
     * @return
     */
    public List<SugListEntity> getSugList(SuggestionEntity entity){
        List<SugListEntity> sugList = new ArrayList<>();

        //SuggestionEntity里面的数据
        sugList.add(getSug("舒适指数", entity.getComfEntity().getBrf(), entity.getComfEntity().getTxt()));
        sugList.add(getSug("穿衣指数", entity.getDressingEntity().getBrf(), entity.getDressingEntity().getTxt()));
        sugList.add(getSug("洗车指数", entity.getCarEntity().getBrf(), entity.getCarEntity().getTxt()));
        sugList.add(getSug("感冒指数", entity.getFluEntity().getBrf(), entity.getFluEntity().getTxt()));
        sugList.add(getSug("运动指数", entity.getSportEntity().getBrf(), entity.getSportEntity().getTxt()));
        sugList.add(getSug("旅游指数", entity.getTravelEntity().getBrf(), entity.getTravelEntity().getTxt()));
        sugList.add(getSug("紫外线指数", entity.getUvEntity().getBrf(), entity.getUvEntity().getTxt()));

        return sugList;
    }

    /**
     * 添加单个SugList
     * @param title
     * @param brf
     * @param txt
     * @return
     */
    public SugListEntity getSug(String title, String brf, String txt){
        SugListEntity sug = new SugListEntity();
        sug.setTitle(title);
        sug.setBrf(brf);
        sug.setTxt(txt);
        return sug;
    }

    /**
     * 显示生活指数窗口
     * @param entity
     */
    public void showSugDialog(SuggestionEntity entity){
        Dialog dialog = new Dialog(WeatherActivity.this, R.style.SugDialog);
        dialog.setContentView(R.layout.dialog_suggestion);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setTitle("生活指数");
        Window window = dialog.getWindow();
        WindowManager manager = window.getWindowManager();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);

        ListView listView = (ListView)dialog.findViewById(R.id.lsv_suggestion);
        listView.setAdapter(new SugListAdapter(getBaseContext(), getSugList(entity)));
        listView.setClickable(false);
        dialog.show();
    }

    /**
     * 再按一次退出程序
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis() - mExitTime > 2000){
                Toast.makeText(WeatherActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            }else {
                ActivityManagerHelper.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
