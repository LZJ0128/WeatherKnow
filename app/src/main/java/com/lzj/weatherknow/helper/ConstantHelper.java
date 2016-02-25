package com.lzj.weatherknow.helper;

/**
 * Created by Administrator on 2/16 0016.
 * 常量帮助类
 */
public class ConstantHelper {

    /**
     * 和风天气网国内城市ID接口
     */
    public static String URL_CITY_LIST = "https://api.heweather.com/x3/citylist?search=allchina&key=a5b8008680104b2ab6923cfbdb8b2c9e";

    public static String URL_CITY_INFO = "https://api.heweather.com/x3/weather?cityid=城市ID&key=a5b8008680104b2ab6923cfbdb8b2c9e";

    /**
     * 和风天气网天气接口
     * @param cityId 城市ID
     * @return
     */
    public static String cityInfoUrl(String cityId){
        String a = "https://api.heweather.com/x3/weather?cityid=";
        String b = "&key=a5b8008680104b2ab6923cfbdb8b2c9e";
        return a.concat(cityId).concat(b);
    }
}
