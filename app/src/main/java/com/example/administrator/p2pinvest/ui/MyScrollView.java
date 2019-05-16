package com.example.administrator.p2pinvest.ui;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import com.example.administrator.p2pinvest.R;
import com.example.administrator.p2pinvest.util.UiUtils;

public class MyScrollView extends ScrollView {

    private View child;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(getChildCount() > 0){
            child = getChildAt(0);
            child.findViewById(R.id.join_immediately);
        }
    }


    private int lastY;//上一次y轴的坐标位置
    private Rect normal = new Rect();//用于记录临界的上下左右
    private boolean isFinishAnimation = true;//动画是否结束
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (child == null || !isFinishAnimation) {
            return super.onTouchEvent(ev);
        }
        int y = (int) ev.getY();
        Log.d("y", "onTouchEvent: " + y);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = y;
                break;

            case MotionEvent.ACTION_MOVE:

                int dy = y - lastY;

                if (isNeedMove()) {
                    if(normal.isEmpty()){
                        //用于记录临界的左上右下
                        normal.set(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());
                    }
                    //重布局
//                    if(child.getMeasuredHeight() - this.getMeasuredHeight() < 30){
//                        child.layout(child.getLeft(), child.getTop() + dy, child.getRight(), child.getBottom() + dy);
//                    }else{
//                        child.layout(child.getLeft(), child.getTop() + dy/10, child.getRight(), child.getBottom() + dy/10);
//                    }
                        child.layout(child.getLeft(), child.getTop() + dy/3, child.getRight(), child.getBottom() + dy/3);
                    Log.d("move", "onTouchEvent: " + normal.left + "   " + normal.top + "  " + normal.right + "   " + normal.bottom);
                }
                lastY = y;
                break;

            case MotionEvent.ACTION_UP:
                int transalteY = child.getBottom() - normal.bottom;
                if(isNeedAnimation()){
                    TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -transalteY);
                    translateAnimation.setDuration(200);

                    translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            isFinishAnimation = false;
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            child.clearAnimation();
                            child.layout(normal.left, normal.top, normal.right, normal.bottom);
                            isFinishAnimation = true;
                            //清除normal的左上右下
                            normal.setEmpty();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

//                translateAnimation.setFillAfter(true); 动画的停留bug
                    child.startAnimation(translateAnimation);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private int lastX, downX, downY;

    /**
     * 拦截：实现父视图对子视图的拦截
     * 拦截是否成功取决于返回值，true拦截，false不拦截
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;
        int y = (int) ev.getY();
        int x = (int) ev.getX();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = downX = x;
                lastY = downY = y;
                break;
            case MotionEvent.ACTION_MOVE:

                int absX = Math.abs(x - downX);
                int absY = Math.abs(y - downY);

                if(absY > absX && absY >= UiUtils.dpToPx(10)){
                    isIntercept = true;
                }

                lastY = y;
                lastX = x;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return isIntercept;
    }

    public boolean isNeedMove() {
        int childMeasuredWidth = child.getMeasuredHeight();
        int scrollViewMeasureHeight = this.getMeasuredHeight();

        int dy = childMeasuredWidth - scrollViewMeasureHeight;
        int scrollY = this.getScrollY();//上+ 下-
        Log.d("s", "isNeedMove: " + scrollY + "   "  + childMeasuredWidth + "    "+ dy + "    "+ scrollViewMeasureHeight);

        if(scrollY <= 0 || scrollY >= dy){
            return true;
        }
        return false;
    }

    public boolean isNeedAnimation() {
        return !normal.isEmpty();
    }
}
