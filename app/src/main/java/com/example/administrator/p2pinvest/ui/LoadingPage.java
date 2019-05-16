package com.example.administrator.p2pinvest.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.administrator.p2pinvest.R;
import com.example.administrator.p2pinvest.util.UiUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.logging.Handler;

public abstract class LoadingPage extends FrameLayout {

//    Providing four states for LoadingPage
    private static final int STATE_LOADING = 0;
    private static final int STATE_FAILED = 1;
    private static final int STATE_SUCCESS = 2;
    private static final int STATE_SUCCESS_EMPTY = 3;

    private int state_current = STATE_LOADING;

//    Providing four views
    private View view_loading = null;
    private View view_error = null;
    private View view_empty = null;
    private View view_success = null;
    private LayoutParams params;


    public LoadingPage(@NonNull Context context) {
        this(context, null);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //2
    private void init() {
//        Providing params of layout
        params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if(view_loading == null){
            view_loading = UiUtils.getView(R.layout.page_loading);
            //Add to current frameLayout
            addView(view_loading, params);
        }

        if(view_empty == null){
            view_empty = UiUtils.getView(R.layout.page_empty);
            //Add to current frameLayout
            addView(view_empty, params);
        }

        if(view_error == null){
            view_error = UiUtils.getView(R.layout.page_error);
            //Add to current frameLayout

            addView(view_error, params);
        }

        showSafePage();
    }

    //Make sure the operation is done in main thread, update layout
    //3
    private void showSafePage() {
        UiUtils.runOnUiThread(new Runnable(){
            @Override
            public void run() {
                //Make sure execute in main thread
                showPage();
            }
        });
    }

    //4
    private void showPage() {
        view_loading.setVisibility(state_current == STATE_LOADING?View.VISIBLE:View.INVISIBLE);
        view_error.setVisibility(state_current == STATE_FAILED?View.VISIBLE:View.INVISIBLE);
        view_empty.setVisibility(state_current == STATE_SUCCESS_EMPTY?View.VISIBLE:View.INVISIBLE);

        if(view_success == null){
            //因为具体的Fragment不一样  5
            Log.d("lifeCycle", "3");
            view_success = UiUtils.getView(layoutId()); //得到fragment的布局
            addView(view_success, params);
            Log.d("debug", "init: success" + layoutId());
        }

        view_success.setVisibility(state_current == STATE_SUCCESS?View.VISIBLE:View.INVISIBLE);
    }

    public abstract int layoutId();

    private ResultState state;
    //在show方法中实现联网加载数据
    public void show(){
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(url(), params(), new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
               if(TextUtils.isEmpty(content)){
//                   state_current = STATE_SUCCESS_EMPTY;
                   state = ResultState.EMPTY;
                   state.setContent("");
               }else{
//                   state_current = STATE_SUCCESS;
                   state = ResultState.SUCCESS;
                   state.setContent(content);
                   Log.d("loading", "onSuccess: " + content);

               }
               loadImage();
            }

            @Override
            public void onFailure(Throwable error, String content) {
//                state_current = STATE_FAILED;
                state = ResultState.ERROR;
                state.setContent("");
//                showSafePage();
            }
        });
    }

    private void loadImage(){
        switch (state){
            case EMPTY:
                state_current = STATE_SUCCESS_EMPTY;
                break;
            case ERROR:
                state_current = STATE_FAILED;
                break;
            case SUCCESS:
                state_current = STATE_SUCCESS;
                break;
        }
        showSafePage();

        if(state_current == STATE_SUCCESS){
            onSuccess(state, view_success);
        }
    }

    //回传数据
    protected abstract void onSuccess(ResultState state, View view_success);

    //请求联网参数
    protected abstract RequestParams params();
    //地址
    protected abstract String url();

    //封装联网以后的状态与数据
    public enum ResultState{
        ERROR(2), EMPTY(3), SUCCESS(4);
        int state;
        ResultState(int state){
            this.state = state;
        }

        private String content;
        public String getContent(){
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
