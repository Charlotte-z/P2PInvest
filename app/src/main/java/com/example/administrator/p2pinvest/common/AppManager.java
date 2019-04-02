package com.example.administrator.p2pinvest.common;

import android.app.Activity;

import java.util.Stack;

/**
 * 统一应用程序中所有的Activity的栈管理(单例)
 * 涉及到activity的添加，删除指定，删除当前，删除所有，返回栈大小的方法
 */
public class AppManager {
    //单例模式 - 饿汉式

    private AppManager(){

    }

    private static AppManager appManager = new AppManager();

    public static AppManager getInstance(){
        return appManager;
    }

    //提供栈对象
    private Stack<Activity> activityStack = new Stack<Activity>();

    public void add(Activity activity){
        if(activity == null){
            activityStack.add(activity);
        }
    }

    //删除指定的activity
    public void remove(Activity activity){
        //以下写法有问题
//        for (int i = 0; i < activityStack.size(); i++) {
//            Activity currActivity = activityStack.get(i);
//            if(currActivity.getClass().equals(activity.getClass())){
//                currActivity.finish();//销毁当前的activity
//                activityStack.remove(i);//从栈空间移除
//            }
//        }


        for (int i = activityStack.size() - 1; i > 0; i--) {
            Activity currActivity = activityStack.get(i);
            if(currActivity.getClass().equals(activity.getClass())){
                currActivity.finish();//销毁当前的activity
                activityStack.remove(i);//从栈空间移除
            }
        }
    }

    /**
     * 删除当前的activity
     */
    public void removeCurrent(){
        Activity activity = activityStack.get(activityStack.size() - 1);
        activity.finish();
        activityStack.remove(activityStack.size() - 1);
    }

    /**
     * 删除所有的activity
     */
    public void removeAll(){
        for (int i = activityStack.size() - 1; i > 0; i++) {
            Activity activity = activityStack.get(i);
            activity.finish();
            activityStack.remove(activity);
        }
    }

    /**
     * 返回栈的大小
     */
    public int size(){
        return activityStack.size();
    }
}
