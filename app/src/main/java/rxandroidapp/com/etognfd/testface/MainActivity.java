package rxandroidapp.com.etognfd.testface;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rxandroidapp.com.etognfd.testface.page.util.DepthPageTransformer;
import rxandroidapp.com.etognfd.testface.page.util.ViewPagerScroller;
import rxandroidapp.com.etognfd.testface.util.BaseAdapter;
import rxandroidapp.com.etognfd.testface.vo.RotateBean;

/**

 */
public class MainActivity extends AppCompatActivity {

    Button btn, bqs, zxyBtn, faceBtn, sjmhBtn, btn_360;
    ViewPager view_pager;
    List<RotateBean> list;
    BaseAdapter baseAdapter;
    /**
     * 开始轮播循环
     */
    Runnable rotateRunnable;
    Handler handler;
    private static final int TIME = 3000;
    private boolean isCycles = true;

    LinearLayout point_group;


    private void startRotate() {
        rotateRunnable = new Runnable() {
            @Override
            public void run() {
                int nowIndex = view_pager.getCurrentItem();
                view_pager.setCurrentItem(++nowIndex);
                handler.postDelayed(rotateRunnable, TIME);
            }
        };
        handler.postDelayed(rotateRunnable, TIME);
    }

    BaseAdapter.OnItemClickListener vpItemClickListener = new BaseAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(String str, int position) {
            Toast.makeText(MainActivity.this, "--" + position, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        initData();
        initPoint();
        initAnimatin();
        handler = new Handler();
        startRotate();
        btnClick();
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            int lastPosition;

            @Override
            public void onPageSelected(int position) {
                Animation animMin = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_point_min);
                animMin.setFillAfter(true);
                Animation animMax = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_point_max);
                animMax.setFillAfter(true);
                Log.i("tag", point_group.getChildCount() + "==" + position % list.size());
                // 页面被选中
                // 设置当前页面选中
                point_group.getChildAt(position % list.size()).setSelected(true);
                point_group.getChildAt(position % list.size()).startAnimation(animMax);
                // 设置前一页不选中
                point_group.getChildAt(lastPosition).setSelected(false);
                point_group.getChildAt(lastPosition).startAnimation(animMin);
                // 替换位置
                lastPosition = position % list.size();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initAnimatin() {

    }

    private void initPoint() {
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.shape_point_selector);
            int pointSize = getResources().getDimensionPixelSize(R.dimen.point_size);
            int pointMargin = getResources().getDimensionPixelSize(R.dimen.point_margin);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pointSize, pointSize);
            params.setMargins(pointMargin,pointMargin,pointMargin,pointMargin);
            if (i > 0) {
                imageView.setSelected(false);
            } else {
                imageView.setSelected(true);
            }
            imageView.setLayoutParams(params);
            point_group.addView(imageView, i);
        }
    }

    private void btnClick() {
        /**
         * 数据魔盒
         */
        sjmhBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShujvMoHeActivity.class);
                startActivity(intent);
            }
        });
        bqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BaiQiShiActivity.class);
                startActivity(intent);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoadingActivity.class);
                startActivity(intent);
            }
        });
        /**
         * 白骑士
         */
        zxyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BaiQiShiInfoActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 活体检测
         */
        faceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FaceIdActivity.class);
                startActivity(intent);
            }
        });
        btn_360.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RongActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new RotateBean(R.mipmap.ic_launcher));
        list.add(new RotateBean(R.mipmap.ic_launcher_round));
        list.add(new RotateBean(R.mipmap.ic_launcher));
        list.add(new RotateBean(R.mipmap.ic_launcher_round));
        baseAdapter = new BaseAdapter(this, list);
        view_pager.setAdapter(baseAdapter);
        view_pager.setCurrentItem(list.size() * 100);
        baseAdapter.setItemClickListener(vpItemClickListener);
        view_pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        handler.removeCallbacks(rotateRunnable);
                        break;
                    case MotionEvent.ACTION_UP:
                        handler.postDelayed(rotateRunnable, TIME);
                        break;
                }
                return false;
            }
        });

    }

    private void initview() {
        point_group = (LinearLayout) findViewById(R.id.point_group);
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        ViewPagerScroller pagerScroller = new ViewPagerScroller(view_pager.getContext());
        pagerScroller.setScrollDuration(2000 * 1);//设置时间，时间越长，速度越慢
        pagerScroller.initViewPagerScroll(view_pager);
        view_pager.setPageTransformer(true, new DepthPageTransformer());
        btn = (Button) findViewById(R.id.btn);
        bqs = (Button) findViewById(R.id.bqs);
        zxyBtn = (Button) findViewById(R.id.zxyBtn);
        faceBtn = (Button) findViewById(R.id.faceBtn);
        sjmhBtn = (Button) findViewById(R.id.sjmhBtn);
        btn_360 = (Button) findViewById(R.id.btn_360);
    }
}
