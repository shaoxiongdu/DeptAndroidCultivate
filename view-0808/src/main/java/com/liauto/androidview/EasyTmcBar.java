package com.liauto.androidview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author deshui
 * @date 2022/8/6
 */
public class EasyTmcBar extends View {
    public static final int TRAFFIC_STATUS_PASSED = -1;
    public static final int TRAFFIC_STATUS_UNBLOCKED = 0;
    public static final int TRAFFIC_STATUS_AMBLE = 1;
    public static final int TRAFFIC_STATUS_JAM = 2;
    // 假数据
    private Map<Integer, Integer> tmcLinkMap = new LinkedHashMap<>();

    // 画画得有画笔
    private final Paint paint;

    // 属性变量
    private Drawable carDrawable;
    private int attrsWidth;
    private int jamColor;
    private int ambleColor;
    private int unblockedColor;
    private int passedColor;
    // 车图标的宽高
    private int carWidth;
    private int carHeight;

    // tmc和bar的坐标点：左上右下
    private final RectF tmcBarRect = new RectF();
    private final Rect carRect = new Rect();

    public EasyTmcBar(Context context) {
        this(context, null);
    }

    public EasyTmcBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasyTmcBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 假数据
        tmcLinkMap.put(TRAFFIC_STATUS_UNBLOCKED, 200);
        tmcLinkMap.put(TRAFFIC_STATUS_AMBLE, 300);
        tmcLinkMap.put(TRAFFIC_STATUS_JAM, 500);
        tmcLinkMap.put(TRAFFIC_STATUS_PASSED, 300);

        // 获取自定义的属性
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.EasyTmcBar);

            carDrawable = ta.getDrawable(R.styleable.EasyTmcBar_tmc_car_drawable);
            attrsWidth = ta.getDimensionPixelSize(R.styleable.EasyTmcBar_tmc_width, 200);
            jamColor = ta.getColor(R.styleable.EasyTmcBar_tmc_jam_color, Color.WHITE);
            ambleColor = ta.getColor(R.styleable.EasyTmcBar_tmc_amble_color, Color.WHITE);
            unblockedColor = ta.getColor(R.styleable.EasyTmcBar_tmc_unblocked_color, Color.WHITE);
            passedColor = ta.getColor(R.styleable.EasyTmcBar_tmc_passed_color, Color.WHITE);

            // 车的宽度
            carWidth = carDrawable.getIntrinsicWidth();
            carHeight = carDrawable.getIntrinsicHeight();

            ta.recycle();
        }

        paint = new Paint();
        // 抗锯齿
        paint.setAntiAlias(true);
        // 实心
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (1 == 1){
            horizontalMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        // 最终大小，宽度
        int finalWidth = carWidth;
        int finalHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);

        // 进度条的左上右下
        tmcBarRect.left = (carWidth - attrsWidth) * 1.00f / 2;
        tmcBarRect.top = 0;
        tmcBarRect.right = tmcBarRect.left + attrsWidth;
        tmcBarRect.bottom = finalHeight;

        // 车图标的左上右下
        carRect.left = 0;
        carRect.top = finalHeight - carHeight;
        carRect.right = carWidth;
        carRect.bottom = finalHeight;


        // 最终生成计算好的大小，传入super中测量
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(finalWidth, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 横向测绘
     */
    private void horizontalMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 最终大小，宽度 光条宽度自适应 高度应该是车的宽度
        int finalWidth = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        int finalHeight = carHeight;

        // 进度条的左上右下
        tmcBarRect.left = 0;
        tmcBarRect.top = (carWidth - attrsWidth) * 1.00f / 2;
        tmcBarRect.right = finalWidth;
        tmcBarRect.bottom = tmcBarRect.top + attrsWidth;

        // 车图标的左上右下
        carRect.left = (int) (finalWidth - carWidth * 0.5);
        carRect.top = 0;
        carRect.right = (int) (finalWidth + carWidth * 0.5);
        carRect.bottom = finalHeight;

        // 最终生成计算好的大小，传入super中测量
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY);
//        widthMeasureSpec = MeasureSpec.makeMeasureSpec(finalWidth, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onDraw(Canvas canvas) {


        if (1 == 1){
            horizontalDraw(canvas);
            return;
        }

        // 画进度
        int sumDistance = 0;
        for (Map.Entry<Integer, Integer> entry : tmcLinkMap.entrySet()) {
            int status = entry.getKey();
            int distance = entry.getValue();

            RectF rectF = new RectF(
                    tmcBarRect.left,
                    sumDistance,
                    tmcBarRect.right,
                    sumDistance + distance
            );

            paint.setColor(getStatusColor(status));
            canvas.drawRect(rectF, paint);

            sumDistance += distance;
        }

        //1. 用paint给光柱图加一个白色边框；
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        canvas.drawRect(tmcBarRect, paint);

        //2. 调整车标位置，让它在分割线上居中显示；
//        carRect.left = (int) (tmcBarRect.right - tmcLinkMap.getOrDefault(TRAFFIC_STATUS_PASSED, 0) - 50);
//        carRect.top = carRect.bottom - carHeight;
        carDrawable.setBounds(carRect);
        carDrawable.setAlpha(100);
        carDrawable.draw(canvas);
        super.onDraw(canvas);
    }

    /**
     *  横向显示
     */
    private void horizontalDraw(Canvas canvas) {
        // 画进度
        int sumDistance = 0;
        for (Map.Entry<Integer, Integer> entry : tmcLinkMap.entrySet()) {
            int status = entry.getKey();
            int distance = entry.getValue();

            RectF rectF = new RectF(
                    sumDistance,
                    tmcBarRect.top,
                    sumDistance + distance,
                    tmcBarRect.bottom
            );
            paint.setColor(getStatusColor(status));
            canvas.drawRect(rectF, paint);

            sumDistance += distance;
            tmcBarRect.right = sumDistance;
        }

        //1. 用paint给光柱图加一个白色边框；
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        canvas.drawRect(tmcBarRect, paint);

        //2. 调整车标位置，让它在分割线上居中显示；
        carRect.left = sumDistance;
        carRect.top = (int) tmcBarRect.top - (int)(carHeight * 1.0f / 2);
        carDrawable.setBounds(carRect);
        carDrawable.draw(canvas);
        super.onDraw(canvas);
    }

    private int getStatusColor(int status) {
        switch (status) {
            case TRAFFIC_STATUS_UNBLOCKED: {
                return unblockedColor;
            }
            case TRAFFIC_STATUS_AMBLE: {
                return ambleColor;
            }
            case TRAFFIC_STATUS_JAM: {
                return jamColor;
            }
            default: {
                return passedColor;
            }
        }
    }
}
