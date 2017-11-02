package top.ttxxly.news;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * 新手引导页面
 *
 * @author ttxxly
 * @date 2017年11月1日18:13:29
 */
public class GuideActivity extends AppCompatActivity {

    private ViewPager viewPager;
    /**
     * 图片ID集合
     */
    private int[] mImageIds = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
    private ArrayList<ImageView> imageViews;
    private ImageView imageView;
    private LinearLayout container;
    private ImageView point;
    private ImageView redPoint;
    private int pointDistance;
    private Button btn_guide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
        initData();
        viewPager.setAdapter(new GuideAdapter());
        //监听 ViewPager 的滑动， 更新小红点的位置
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
                //通过修改小红点的左边距， 更新 小红点的 位置
                int distance = (int) (pointDistance * (position + positionOffset));
                //获取小红点的布局参数
                RelativeLayout.LayoutParams redParams = (RelativeLayout.LayoutParams) redPoint
                        .getLayoutParams();
                redParams.leftMargin = distance;
                redPoint.setLayoutParams(redParams);
            }

            @Override
            public void onPageSelected(int position) {
                //设置 开始体验 按钮的显示与隐藏
                if (position == imageViews.size() - 1) {
                    btn_guide.setVisibility(View.VISIBLE);
                } else {
                    btn_guide.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //measure -> layout ——> draw， 必须在 OnCreate() 执行完后才会执行这三个方法
        //监听 Layout 执行结束的事件，一旦结束之后，去获取当前的left 位置
        redPoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {

            /**
             * 一旦视图的 layout 方法调用完成，就会调用此方法
             */
            @Override
            public void onGlobalLayout() {
                //从第一个小圆点到第二个小圆点之间的距离
                pointDistance = container.getChildAt(1).getLeft() - container.getChildAt(0)
                        .getLeft();
                //移除观察者模式
                redPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        //开始体验 按钮的点击事件
        btn_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在 SharedPreferences 中记录 是否访问过引导页的状态
                //1、打开Preferences，名称为setting，如果存在则打开它，否则创建新的Preferences
                SharedPreferences settings = getSharedPreferences("settings", 0);
                //2、让setting处于编辑状态
                SharedPreferences.Editor editor = settings.edit();
                //3、存放数据
                editor.putBoolean("isGuideShow", true);
                //4、完成提交
                editor.apply();

                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });
    }

    /**
     * 初始化数据
     * 1. ViewPager 数据集（三张引导页图片）
     */
    private void initData() {
        imageViews = new ArrayList<>();
        for (int i = 0; i < mImageIds.length; i++) {

            //初始化图片
            imageView = new ImageView(this);
            imageView.setBackgroundResource(mImageIds[i]);
            imageViews.add(imageView);

            //初始化小圆点
            point = new ImageView(this);
            point.setImageResource(R.drawable.shape_point_normal);
            //从第二个小圆点开始，设置布局
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup
                    .LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                //设置左边距为20
                params.leftMargin = 20;
            }
            point.setLayoutParams(params);
            container.addView(point);
        }
    }

    /**
     * 找到控件
     */
    private void initView() {
        viewPager = findViewById(R.id.vp_guide);
        container = findViewById(R.id.ll_container);
        redPoint = findViewById(R.id.iv_red_point);
        btn_guide = findViewById(R.id.btn_guide);
    }


    /**
     * 向导页的 ViePager 适配器
     */
    public class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            imageView = imageViews.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
