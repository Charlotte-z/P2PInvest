package com.example.administrator.p2pinvest.ui;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import com.example.administrator.p2pinvest.common.MyApplication;

/**
 * 处理UI相关的工具类，避免重复操作
 */
public class UiUtils {
    public static Context getContext(){
        return MyApplication.context;
    }

    public static Handler getHandler(){
        return MyApplication.handler;
    }

    public static int getColor(int colorId){
        return getContext().getResources().getColor(colorId);
    }

    public static View getView(int viewId){
        View inflate = View.inflate(getContext(), viewId, null);
        return inflate;
    }

    public static String[] getStringArrayArr(int strArrid){
        String[] stringArray = getContext().getResources().getStringArray(strArrid);
        return stringArray;
    }


    public static int dpToPx(int dp){
        //获取手机密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);//四舍五入，如5.6+0.5
    }


    public static int pxToDp(int px){
        //获取手机密度
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int)(px/density);
    }
}
