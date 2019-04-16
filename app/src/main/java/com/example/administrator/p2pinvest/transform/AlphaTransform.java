package com.example.administrator.p2pinvest.transform;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class AlphaTransform implements ViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View view, float v) {
        float position = v;
        float mMinAlpha = 0.2f;
        if (position < -1)
        {
            view.setAlpha(mMinAlpha);
        } else if (position <= 1)
        { // [-1,1]

            if (position < 0) //[0，-1]
            {
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 + position);
                view.setAlpha(factor);
            } else//[1，0]
            {
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 - position);
                view.setAlpha(factor);
            }
        } else
        { // (1,+Infinity]
            view.setAlpha(mMinAlpha);
        }
    }
}
