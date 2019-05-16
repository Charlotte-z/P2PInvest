package com.example.administrator.p2pinvest.fragment;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.administrator.p2pinvest.R;
import com.example.administrator.p2pinvest.bean.Index;
import com.example.administrator.p2pinvest.common.AppNetConfig;
import com.example.administrator.p2pinvest.common.BaseFragment;
import com.example.administrator.p2pinvest.ui.RoundProgress;
import com.example.administrator.p2pinvest.util.UiUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.youth.banner.transformer.ZoomOutSlideTransformer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.content.ContentValues.TAG;

public class HomeFragment extends BaseFragment {


    private TextView tv_home_product;
    private TextView tv_home_year_rate;
    private ViewPager view_pager;
    private Index index;
    private LinearLayout ll_point;
    private List<ImageView> images;
    private RoundProgress roundProgress;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i <= 90; i++) {
                roundProgress.setProgress(i);
                SystemClock.sleep(20);
                roundProgress.postInvalidate();
            }
        }
    };


    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return AppNetConfig.INDEX;
    }

    @Override
    public void init(View view) {
        tv_home_product = view.findViewById(R.id.tv_home_product);
        tv_home_year_rate = view.findViewById(R.id.tv_home_year_rate);
        view_pager = view.findViewById(R.id.view_pager);
        ll_point = view.findViewById(R.id.ll_point);
        roundProgress = view.findViewById(R.id.roundPro_home);
        Log.d("fragment", "onCreateView: running");
        Log.d("lifeCycle", "2");
    }

    @Override
    protected void initData(View view, String content) {

        new Thread(runnable).start();
        paraseData(content);
    }

//    @Override
//    public void initData(View view, String content) {
//        paraseData(content);
//        new Thread(runnable).start();
////        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
////        asyncHttpClient.post(AppNetConfig.INDEX, new AsyncHttpResponseHandler(){
////            @Override
////            public void onSuccess(String content) {
//////                super.onSuccess(content);
////                //解析数据：GSON、FASTJSON
////                //方法一：解析后分别得到class
//////                JSONObject jsonObject = JSON.parseObject(content);
//////                //解析数据json对象
//////                String proInfo = jsonObject.getString("proInfo");
//////                Index.ProInfoBean proInfoBean = JSON.parseObject(proInfo, Index.ProInfoBean.class);
//////                //解析json数组
//////                String imageArr = jsonObject.getString("imageArr");
//////                List<Index.ImageArrBean> imageArrBeans = jsonObject.parseArray(imageArr, Index.ImageArrBean.class);
////                //方法二：解析全部，并塞入一个类
////
////
////            }
////            @Override
////            public void onFailure(Throwable error, String content) {
////                super.onFailure(error, content);
////                Log.d(TAG, "onFailure: " + content);
////            }
////        });
//    }

    private void paraseData(String content) {
        index = JSON.parseObject(content, Index.class);
        Log.d(TAG, "onSuccess: " + index.getProInfo().getName());
        //更新数据
        tv_home_product.setText(index.getProInfo().getName());
        tv_home_year_rate.setText(index.getProInfo().getYearRate());



        //设置小圆圈
        images = new ArrayList<>();
        for (int i = 0; i < index.getImageArr().size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            if(i == 0){
                imageView.setBackgroundResource(R.drawable.red_point);
            }else{
                imageView.setBackgroundResource(R.drawable.white_point);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5, 10, 5, 10);
            imageView.setLayoutParams(layoutParams);
            ll_point.addView(imageView);
            images.add(imageView);
        }

        //设置ViewPager
        view_pager.setAdapter(new MyAdapter());

        //设置滑动监听
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Iterator<ImageView> iterator = images.iterator();
                while (iterator.hasNext()){
                    ImageView next = iterator.next();
                    next.setBackgroundResource(R.drawable.white_point);
                }

                ImageView imageView = images.get(i);
                imageView.setBackgroundResource(R.drawable.red_point);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //设置viewpager滑动效果
        view_pager.setPageTransformer(true, (ViewPager.PageTransformer) new ZoomOutSlideTransformer());
    }

    //7
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }


    class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return index.getImageArr().size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //1.ImageView需要有图片显示
            String imgUrl = index.getImageArr().get(position).getIMAURL();
            Log.d(TAG, "instantiateItem: " + imgUrl);
            Glide.with(UiUtils.getContext()).load(imgUrl).into(imageView);
            //2.添加到容器
            container.addView(imageView);
            //3.
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
