package com.example.administrator.p2pinvest.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.administrator.p2pinvest.R;
import com.example.administrator.p2pinvest.util.UiUtils;

import static android.content.ContentValues.TAG;


/**
 * 自定义视图
 *
 */
public class RoundProgress extends View{

    private int measuredHeight;
    private int measuredWidth;
    private Paint paint;

    private int cy;
    private int cx;
    private int roundColor;
    private int roundProgressColor;
    private int textColor;
    private float roundWidth;
    private float textSize;
    private int max;
    private int progress;

    public RoundProgress(Context context) {
        this(context, null);
    }

    public RoundProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, context);
    }

    private void init(AttributeSet attrs, Context context) {
        paint = new Paint();
        //坑点，不能把paint在此初始化，生命周期
        Log.d("T", "init: 1" );

        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgress);

        roundColor = typedArray.getColor(R.styleable.RoundProgress_roundColor, Color.GRAY);
        roundProgressColor = typedArray.getColor(R.styleable.RoundProgress_roundProgressColor, Color.RED);
        textColor = typedArray.getColor(R.styleable.RoundProgress_textColor, Color.BLACK);
        roundWidth = typedArray.getDimension(R.styleable.RoundProgress_roundWidth, UiUtils.dpToPx(10));
        textSize = typedArray.getDimension(R.styleable.RoundProgress_textSize, UiUtils.dpToPx(30));
        max = typedArray.getInteger(R.styleable.RoundProgress_max, 100);
        progress = typedArray.getInteger(R.styleable.RoundProgress_progress, 70);

        //回收处理
        typedArray.recycle();
    }

    //获取子视图


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    //获取当前视图宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredHeight = this.getMeasuredHeight();
        measuredWidth = this.getMeasuredWidth();
    }

    //canvas画布，对应着视图在布局中的范围区间,范围左上定点即为坐标原点
    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: 1");
        cy = measuredHeight/2;
        cx = measuredWidth/2;
        //1.绘制圆环
        paint.setStrokeWidth(roundWidth);
        paint.setColor(roundColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        Log.d("T", "init: 2" + progress/max * 360);
        canvas.drawCircle(cx, cy, measuredWidth/2 - roundWidth/2, paint);
        //2.绘制圆弧
        paint.setColor(roundProgressColor);
        RectF oval = new RectF(roundWidth/2, roundWidth/2, cx*2 - roundWidth/2, cy*2 - roundWidth/2);
        canvas.drawArc(oval, 0, progress * 360/max, false,paint);
        //3.绘制文本
        String p = progress + "%";
        Rect bounds = new Rect();
        paint.getTextBounds(p, 0, p.length(), bounds);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(1);
        paint.setColor(textColor);
        paint.setStyle(Paint.Style.FILL);
        int x = measuredWidth/2 - bounds.width()/2;
        int y = measuredHeight/2 + bounds.height()/2;
        canvas.drawText(p, x, y, paint);
    }


    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
