package com.lzj.weatherknow.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzj.weatherknow.entity.CityEntity;
import com.lzj.weatherknow.entity.HeWeatherDataEntity;
import com.lzj.weatherknow.entity.NowEntity;
import com.lzj.weatherknow.entity.ObjectEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Administrator on 2/16 0016.
 * 要根据断点得到的数据进行JSON解析，因为有可能网站的JSON数据多了一个头部而导致解析异常
 * <p>JSON帮助类(基于Gson)</p>
 * <p>可使用@SerializedName为POJO类中的成员变量起别名</p>
 */
public class JsonHelper {

    private static Gson mGson = null;

    /**
     * 取得gson对象
     * @return gson对象
     */

    public static Gson getGson(){
        if (mGson == null){
            mGson = new Gson();
        }
        return mGson;
    }

    /**
     * 从JSON字符串反序列化为指定类型的对象
     * @param json JSON字符串
     * @param typeOfT 对象类型
     * @return 实体
     */
    public static <T> T fromJson(String json, Type typeOfT){
        try{
            return getGson().fromJson(json, typeOfT);
        }catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 处理城市ID的response,将数据全部保存到本地数据库中
     * @param context
     * @param response
     */
    public static void handleCityListResponse(Context context, String response){

        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray cityInfo = jsonObject.getJSONArray("city_info");

            for (int i=0;i<cityInfo.length();i++){
                CityEntity cityEntity = new CityEntity();
                cityEntity.setCityName(cityInfo.getJSONObject(i).getString("city"));
                cityEntity.setCityId(cityInfo.getJSONObject(i).getString("id"));
                DBOperationHelper.getInstance(context).saveCity(cityEntity);
            }
            Log.e("LZJJsonHelper", "保存成功");
            SharePreferenceHelper.setIntSP(context, "flag", "flag", 1);
        }catch (JSONException e){
            e.printStackTrace();
            Log.e("LZJJsonHelper", "JsonException");
        }
    }

    /**
     * 处理天气的response
     * @param context
     * @param response
     */
    public static void handleCityWeatherResponse(Context context, String response){

        ObjectEntity objectEntity = JsonHelper.fromJson(response, ObjectEntity.class);
        List<HeWeatherDataEntity> heWeatherDataEntity = objectEntity.getWeatherDataList();
        NowEntity nowEntity = heWeatherDataEntity.get(0).getNow();
        String tmp = nowEntity.getTmp();
        String cond = nowEntity.getNowCond().getTxt();
        String hum = nowEntity.getHum();
        Log.e("JsonHelper", "当前温度是：" + tmp);
        Log.e("JsonHelper", "当前天气是：" + cond);
        Log.e("JsonHelper", "当前湿度是：" + hum);


    }

}
