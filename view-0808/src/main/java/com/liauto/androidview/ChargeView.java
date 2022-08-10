package com.liauto.androidview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * @author deshui
 * @date 2022/8/6
 */
public class ChargeView extends LinearLayout {
    private final TextView showCount;
    private final TextView fastCount;

    public ChargeView(Context context) {
        this(context, null);
    }

    public ChargeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChargeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 解析自定义属性
        int fastBgColor = Color.RED;
        int slowBgColor = Color.RED;
        if (attrs != null) {
            // 获取到ChargeView的styleable
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.ChargeView);

            // 获取到color值
            fastBgColor = ta.getColor(R.styleable.ChargeView_charge_fast_bg_color, Color.RED);
            slowBgColor = ta.getColor(R.styleable.ChargeView_charge_slow_bg_color, Color.RED);

            // 一定要回收
            ta.recycle();
        }

        // 加载布局到ChargeView中, 这取决于第三个参数：
        // true表示自动加载到ChargeView；
        // false则要通过addView，view加载上去；
        View view = LayoutInflater.from(context).inflate(R.layout.layout_charge, this, true);

//        addView(view);

        // find 两个textView
        showCount = view.findViewById(R.id.slow_count);
        fastCount = view.findViewById(R.id.fast_count);

        // 设置颜色
        showCount.setBackgroundColor(slowBgColor);
        fastCount.setBackgroundColor(fastBgColor);
    }


    // 对外暴露更新方法：简单实现隐藏和显示
    public void update(boolean isShowSlow, boolean isShowFast) {
        showCount.setVisibility(isShowSlow ? View.VISIBLE : View.GONE);
        fastCount.setVisibility(isShowFast ? View.VISIBLE : View.GONE);
    }
}
