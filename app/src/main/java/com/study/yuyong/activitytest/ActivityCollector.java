package com.study.yuyong.activitytest;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @date on 2018/7/10
 * @author yuyong
 * @Email yu1183688986@163.com
 * @describe 该类用于管理所有活动
 */
public class ActivityCollector {
    //创建集合用于存储所有活动
    public static List<Activity> activities = new ArrayList<>();
    //添加活动到集合的方法
    public static void addActivity(Activity activity){
        activities.add(activity);
    }
    //从集合中删除活动
    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }
    //销毁所有集合中的活动
    public static void finishAll(){
        for(Activity activity : activities){
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
