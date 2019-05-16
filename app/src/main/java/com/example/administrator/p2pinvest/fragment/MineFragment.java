package com.example.administrator.p2pinvest.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.p2pinvest.R;
import com.example.administrator.p2pinvest.common.BaseFragment;
import com.example.administrator.p2pinvest.util.UiUtils;
import com.loopj.android.http.RequestParams;

public class MineFragment extends BaseFragment {


    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void init(View view) {

    }

    @Override
    protected void initData(View view, String content) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }
}
