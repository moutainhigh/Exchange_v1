package com.exchange_v1.app.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.view.View;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huanghh on 2016/9/20.
 */
public class ActivityUtils {

    public static final Class thisC = ActivityUtils.class;
    public static final String TAG = thisC.getSimpleName();

    /**
     * 获取栈中最顶端的Activity
     * @return
     */
    public static Activity getCurrentActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            if (activityThread!=null&&activitiesField!=null) {
                if (activitiesField.get(activityThread) instanceof Map) {
                    Map activities = (Map) activitiesField.get(activityThread);
                    for (Object activityRecord : activities.values()) {
                        Class activityRecordClass = activityRecord.getClass();
                        Field pausedField = activityRecordClass.getDeclaredField("paused");
                        pausedField.setAccessible(true);
                        if (!pausedField.getBoolean(activityRecord)) {
                            Field activityField = activityRecordClass.getDeclaredField("activity");
                            activityField.setAccessible(true);
                            Activity activity = (Activity) activityField.get(activityRecord);
                            return activity;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 判断app是否运行
     * @param context
     * true 表示程序启动了运行中  false 程序没有启动
     * @return
     */
    public static boolean isAppRunable(Context context){
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE) ;
        //获得当前正在运行的activity
        List<ActivityManager.RunningTaskInfo> appList3 = mActivityManager
                .getRunningTasks(1000);
        String packageName = context.getApplicationContext().getPackageName();
//        System.out.println("================");
        for (ActivityManager.RunningTaskInfo running : appList3) {
            if(running.baseActivity.getPackageName().equals(packageName)){
                return  true;
            }
        }
        return  false;
    }

    /**
     * 最后活动的activity名称
     * @param context
     * @return
     */
    public static String lastActivityName(Context context){
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE) ;
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获得当前正在运行的activity
        List<ActivityManager.RunningTaskInfo> appList3 = mActivityManager
                .getRunningTasks(1000);
        String packageName = context.getApplicationContext().getPackageName();
        List<String> activityList =  new ArrayList<>();
        for (ActivityManager.RunningTaskInfo running : appList3) {
            if(running.topActivity.getPackageName().equals(packageName)){
                activityList.add(running.topActivity.getClassName());
            }
        }
        String className=null;
        if(activityList.size()>0){
            className = activityList.get(activityList.size()-1);
            return className;
        }
        return null;
    }


    /**
     * 最后活动的activity名称
     * @param context
     * @return
     */
    public static ComponentName lastComponentName(Context context){
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE) ;
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获得当前正在运行的activity
        List<ActivityManager.RunningTaskInfo> appList3 = mActivityManager
                .getRunningTasks(1000);
        String packageName = context.getApplicationContext().getPackageName();
        List<ComponentName> activityList =  new ArrayList<>();
        for (ActivityManager.RunningTaskInfo running : appList3) {


            if(running.topActivity.getPackageName().equals(packageName)){
                activityList.add(running.topActivity);
            }
        }
        ComponentName componentName=null;
        if(activityList.size()>0){
            componentName=activityList.get(activityList.size()-1);
            return componentName;
        }
        return null;
    }


    public static boolean isDestroyed(View v) {
        if (v == null) {
            return true;
        }
        return isDestroyed(v.getContext());
    }
    public static boolean isDestroyed(Context c) {
        if (c == null) {
            return true;
        }

        Context realContext = ContextUtils.getRealContext(c);

        if (realContext instanceof Activity) {
            return isDestroyed((Activity)realContext);
        }else{
            return true;
        }
    }
    /**
     * 判断是否 activity还存在，避免打开dialog问题
     * @param activity
     * @return
     */
    public static boolean isDestroyed(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return activity.isDestroyed();
        }else{
            return isAppRunable(activity);
        }
    }
}
