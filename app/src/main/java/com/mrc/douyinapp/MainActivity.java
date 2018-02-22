package com.mrc.douyinapp;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mrc.douyinapp.attention.view.AttentionPage;
import com.mrc.douyinapp.home.view.HomePage;
import com.mrc.douyinapp.view.MessagePage;
import com.mrc.douyinapp.view.MinePage;
import com.mrc.douyinapp.view.ReleaseActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView tvHome;//首页
    private ImageView imgRefresh;//刷新
    private TextView tvAttention;//关注
    private ImageView imgRelease;//发布
    private TextView tvMessage;//消息
    private TextView tvMine;//个人主页
    private LinearLayout lyBottom;//底部栏
    private FrameLayout frameLayout;//FramLayout
    private View line1;
    private View line01;
    private View line02;
    private View line04;
    private View line05;

    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    private void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
//                attributes.flags |= flagTranslucentStatus;
                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }
        /**
         * 设置页面最外层布局 FitsSystemWindows 属性
         *
         * @param activity
         */
        private void fitsSystemWindows(Activity activity){
            ViewGroup contentFrameLayout = (ViewGroup) activity.findViewById(android.R.id.content);
            View parentView = contentFrameLayout.getChildAt(0);
            if (parentView != null && Build.VERSION.SDK_INT >= 14) {
                //布局预留状态栏高度的 padding
                parentView.setFitsSystemWindows(true);
                if (parentView instanceof DrawerLayout) {
                    DrawerLayout drawer = (DrawerLayout) parentView;
                    //将主页面顶部延伸至status bar;虽默认为false,但经测试,DrawerLayout需显示设置
                    drawer.setClipToPadding(false);
                }
            }
        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        fullScreen(this);
        fitsSystemWindows(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化默认页面
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomePage()).commit();
        //初始化化控件
        initView();
        //点击事件
        tvHome.setOnClickListener(this);
        imgRefresh.setOnClickListener(this);
        tvAttention.setOnClickListener(this);
        imgRelease.setOnClickListener(this);
        tvMessage.setOnClickListener(this);
        tvMine.setOnClickListener(this);

    }

    //初始化控件
    private void initView() {
        tvHome = (TextView) findViewById(R.id.tvHome);
        imgRefresh = (ImageView) findViewById(R.id.imgRefresh);
        tvAttention = (TextView) findViewById(R.id.tvAttention);
        imgRelease = (ImageView) findViewById(R.id.imgRelease);
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        tvMine = (TextView) findViewById(R.id.tvMine);
        lyBottom = (LinearLayout) findViewById(R.id.lyBottom);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        line1 = (View) findViewById(R.id.line1);
        line01 = (View) findViewById(R.id.line01);
        line02 = (View) findViewById(R.id.line02);
        line04 = (View) findViewById(R.id.line04);
        line05 = (View) findViewById(R.id.line05);
    }
    private long exitTime = 0;//初始时间变量LONG

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
//                finish();
//                System.exit(0);
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //点击事件#00000000
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvHome:
                tvHome.setTextColor(Color.parseColor("#FFFFFF"));
                tvAttention.setTextColor(Color.parseColor("#787878"));
                tvMessage.setTextColor(Color.parseColor("#787878"));
                tvMine.setTextColor(Color.parseColor("#787878"));
                /**
                 * 分割线&下划线的显示与隐藏
                 */
                line01.setVisibility(View.VISIBLE);
                line02.setVisibility(View.GONE);
                line04.setVisibility(View.GONE);
                line05.setVisibility(View.GONE);
                line1.setVisibility(View.VISIBLE);
                tvHome.setVisibility(View.GONE);
                imgRefresh.setVisibility(View.VISIBLE);
                lyBottom.setBackgroundColor(000000);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new HomePage()).commit();
                break;
            case R.id.imgRefresh:
                /**
                 * 分割线&下划线的显示与隐藏
                 */
                line01.setVisibility(View.VISIBLE);
                line02.setVisibility(View.GONE);
                line04.setVisibility(View.GONE);
                line05.setVisibility(View.GONE);
                line1.setVisibility(View.VISIBLE);
                /**
                 * 选中/非选中的字体颜色更换
                 */
                tvHome.setTextColor(Color.parseColor("#FFFFFF"));
                tvAttention.setTextColor(Color.parseColor("#787878"));
                tvMessage.setTextColor(Color.parseColor("#787878"));
                tvMine.setTextColor(Color.parseColor("#787878"));
                Toast.makeText(this, "刷新", Toast.LENGTH_SHORT).show();
                //这个是按照某一点进行旋转，默认是view的
                ObjectAnimator ra = ObjectAnimator.ofFloat(imgRefresh, "rotation", 0, -1080);
                //将动画添加
                ra.setDuration(2000);
                ra.start();
                break;
            case R.id.tvAttention:
                /**
                 * 分割线&下划线的显示与隐藏
                 */
                line01.setVisibility(View.GONE);
                line02.setVisibility(View.VISIBLE);
                line04.setVisibility(View.GONE);
                line05.setVisibility(View.GONE);
                line1.setVisibility(View.GONE);
                tvHome.setTextColor(Color.parseColor("#787878"));
                tvAttention.setTextColor(Color.parseColor("#FFFFFF"));
                tvMessage.setTextColor(Color.parseColor("#787878"));
                tvMine.setTextColor(Color.parseColor("#787878"));
                tvHome.setVisibility(View.VISIBLE);
                imgRefresh.setVisibility(View.GONE);
                lyBottom.setBackgroundColor(Color.BLACK);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new AttentionPage()).commit();
                break;
            case R.id.imgRelease:
                /**
                 * 跳转Activity
                 */
                line1.setVisibility(View.GONE);
                tvHome.setVisibility(View.VISIBLE);
                imgRefresh.setVisibility(View.GONE);
                lyBottom.setBackgroundColor(Color.BLACK);
                //getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ReleasePage()).commit();
                Intent intent = new Intent(MainActivity.this, ReleaseActivity.class);
                startActivity(intent);
                //进入退出同事进行动画
                overridePendingTransition(R.anim.in,R.anim.out);
                break;
            case R.id.tvMessage:
                /**
                 * 分割线&下划线的显示与隐藏
                 */
                line01.setVisibility(View.GONE);
                line02.setVisibility(View.GONE);
                line04.setVisibility(View.VISIBLE);
                line05.setVisibility(View.GONE);
                line1.setVisibility(View.GONE);
                tvHome.setTextColor(Color.parseColor("#787878"));
                tvAttention.setTextColor(Color.parseColor("#787878"));
                tvMessage.setTextColor(Color.parseColor("#FFFFFF"));
                tvMine.setTextColor(Color.parseColor("#787878"));
                tvHome.setVisibility(View.VISIBLE);
                imgRefresh.setVisibility(View.GONE);
                lyBottom.setBackgroundColor(Color.BLACK);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new MessagePage()).commit();
                break;
            case R.id.tvMine:
                /**
                 * 分割线&下划线的显示与隐藏
                 */
                line01.setVisibility(View.GONE);
                line02.setVisibility(View.GONE);
                line04.setVisibility(View.GONE);
                line05.setVisibility(View.VISIBLE);
                line1.setVisibility(View.GONE);
                tvHome.setTextColor(Color.parseColor("#787878"));
                tvAttention.setTextColor(Color.parseColor("#787878"));
                tvMessage.setTextColor(Color.parseColor("#787878"));
                tvMine.setTextColor(Color.parseColor("#FFFFFF"));
                tvHome.setVisibility(View.VISIBLE);
                imgRefresh.setVisibility(View.GONE);
                lyBottom.setBackgroundColor(Color.BLACK);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new MinePage()).commit();
                break;
        }
    }
}
