package top.ttxxly.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

/**
 * @author ttxxly
 * @date 2017年11月1日16:53:49
 */
public class SplashActivity extends AppCompatActivity {

    private RelativeLayout splash;
    private RotateAnimation rotateAnimation;
    private AlphaAnimation alphaAnimation;
    private ScaleAnimation scaleAnimation;
    private AnimationSet animationSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //设置 全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        splash = findViewById(R.id.root_splash);

        //设置旋转动画
        rotateAnimation = new RotateAnimation(0, 360, Animation
                .RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        //设置旋转动画时间
        rotateAnimation.setDuration(1000);
        //保持动画结束的状态
        rotateAnimation.setFillAfter(true);

        //设置缩放动画
        scaleAnimation = new ScaleAnimation(0.5f, 1, 0.5f, 1, Animation
                .RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);

        //设置渐变动画
        alphaAnimation = new AlphaAnimation(0.5f, 1);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setFillAfter(true);

        //动画集合， 使得三种动画同时运行
        animationSet = new AnimationSet(false);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);

        splash.setAnimation(animationSet);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束
                startActivity(new Intent(getApplicationContext(), GuideActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


}
