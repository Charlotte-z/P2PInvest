package com.example.administrator.p2pinvest.common;

import android.os.Build;
import android.os.Looper;
import android.widget.Toast;

import com.example.administrator.p2pinvest.util.UiUtils;

/**
 * 捕获全局未捕获的异常，防止程序崩溃，后台及时收集数据
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    //系统默认的处理未捕获异常的处理器
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    //单例模式:(懒汉式)
    //本身实例化未捕获异常的处理器操作就是在系统的一个专门的线程中执行的，所以不涉及多线程问题，所以使用懒汉式更好
    private CrashHandler(){

    }

    private static CrashHandler crashHandler = null;

    public static CrashHandler getInstance(){
        if(crashHandler == null){
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }

    public void init(){
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //将当前类设置为出现未捕获异常的处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 一旦系统出现未捕获的异常，就会调用如下的回调方法
     * @param t
     * @param e
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                //在android系统中，默认情况下一个线程中是不可以调用looper进行消息的处理的，除非是主线程
                Toast.makeText(UiUtils.getContext(), "出现异常", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();

        //收集异常信息
        collectionException(e);

        try {
            Thread.sleep(2000);

            //移除当前activity
            AppManager.getInstance().removeCurrent();
            //结束当前的进程
            android.os.Process.killProcess(android.os.Process.myPid());
            //结束虚拟机
            System.exit(0);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }


    }

    private void collectionException(Throwable e){
        String exMessage = e.getMessage();
        //收集手机以及系统的信息
        String phoneMsg = Build.DEVICE + "  " + Build.MODEL + " " + Build.VERSION.SDK_INT;
        //发送信息给后台
        new Thread(){
            @Override
            public void run() {
                //需要按照指定的URL，访问后台的Servlet,将异常信息发送过去
            }
        }.start();
    }
}
