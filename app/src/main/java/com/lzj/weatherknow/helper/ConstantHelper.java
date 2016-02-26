package com.lzj.weatherknow.helper;

import java.util.Calendar;

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

    /**
     * 星期几
     * @param position
     * @return
     */
    public static String getWeekday(int position){
        Calendar c = Calendar.getInstance();
        int weekday = c.get(Calendar.DAY_OF_WEEK) + position + 1;
        String dayOfWeek = "";
        switch (weekday){
            case 1:
            case 8:
                dayOfWeek = "星期日";
                break;
            case 2:
            case 9:
                dayOfWeek = "星期一";
                break;
            case 3:
            case 10:
                dayOfWeek = "星期二";
                break;
            case 4:
            case 11:
                dayOfWeek = "星期三";
                break;
            case 5:
            case 12:
                dayOfWeek = "星期四";
                break;
            case 6:
            case 13:
                dayOfWeek = "星期五";
                break;
            case 7:
            case 14:
                dayOfWeek = "星期六";
                break;
            default:
                break;
        }
        return dayOfWeek;
    }
}
