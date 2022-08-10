package cn.shaoxiongdu.animation0810;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startAnimXml(View view) {
        Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale);
        view.startAnimation(scale);
    }

    public void startAnimJava(View view) {
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(300);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(-1);
        view.startAnimation(rotateAnimation);
    }

    /**
     * 1. 自定义属性动画练习
     * @param view
     */
    public void startAnim(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0, 500);
        // 动画持续3s
        animator.setDuration(3000);
        // 一直播放
        animator.setRepeatCount(-1);
        // 结束之后反着来
        animator.setRepeatMode(ValueAnimator.REVERSE);
        // 速率曲线
        animator.setInterpolator((Interpolator) x -> x < 0.5 ? 4 * x * x * x : (float) (1 - Math.pow(-2 * x + 2, 3) / 2));
        // 启动
        animator.start();
    }
}