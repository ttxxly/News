package top.ttxxly.news;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 新手引导页面
 * @author ttxxly
 * @date 2017年11月1日18:13:29
 */
public class GuideActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        viewPager = findViewById(R.id.vp_guide);
        
    }
}
