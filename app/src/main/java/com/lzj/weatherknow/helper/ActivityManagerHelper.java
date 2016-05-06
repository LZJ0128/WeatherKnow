package com.lzj.weatherknow.helper;


import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/5.
 * 活动管理帮助类，主要用于按返回键时推出程序
 */
public class ActivityManagerHelper {

    private List<Activity> activityList = new LinkedList<Activity>();
    private static ActivityManagerHelper instance;
    private ActivityManagerHelper(){
    }

    public static ActivityManagerHelper getInstance(){
        if (instance == null){
            instance = new ActivityManagerHelper();
        }
        return instance;
    }

    public void addActivity(Activity activity){
        activityList.add(activity);
    }

    public void exit(){
        for (Activity activity : activityList){
            activity.finish();
        }
        System.exit(0);
    }

}
