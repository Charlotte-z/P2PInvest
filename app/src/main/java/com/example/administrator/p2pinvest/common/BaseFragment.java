package com.example.administrator.p2pinvest.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.p2pinvest.R;
import com.example.administrator.p2pinvest.ui.LoadingPage;
import com.example.administrator.p2pinvest.util.UiUtils;
import com.loopj.android.http.RequestParams;

public abstract class BaseFragment extends Fragment {

    private LoadingPage loadingPage;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //                Log.d("id", "layoutId: " + getLayoutId());

        //1
        loadingPage = new LoadingPage(container.getContext()) {
            //6
            @Override
            public int layoutId() {
//                Log.d("id", "layoutId: " + getLayoutId());
                return getLayoutId();
            }

            @Override
            protected void onSuccess(ResultState state, View view_success) {
//                View view = UiUtils.getView(getLayoutId());
                Log.d("id", "layoutId: invoked");
                Log.d("lifeCycle", "4");
                init(view_success);
                initData(view_success, state.getContent());
            }

            @Override
            protected RequestParams params() {
                return getParams();
            }

            @Override
            protected String url() {
                return getUrl();
            }
        };
        Log.d("lifeCycle", "1");

//        initData(view);
//        show(); 经测试可以放这儿
        return loadingPage;  //此时默认loading状态，返回loading页面
    }

    //为了保证loadingPage不为null
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show();
    }

    protected abstract RequestParams getParams();

    protected abstract String getUrl();

    //初始化title
    protected abstract void init(View view);

    //初始化界面
    protected abstract void initData(View view, String content);

    public abstract int getLayoutId();

    public void show(){
        loadingPage.show();
    }
}
