package com.mrc.douyinapp.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.mrc.douyinapp.R;


public class ReleaseActivity extends AppCompatActivity {
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
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release2);
        fitsSystemWindows(this);
        fullScreen(this);
    }
}
