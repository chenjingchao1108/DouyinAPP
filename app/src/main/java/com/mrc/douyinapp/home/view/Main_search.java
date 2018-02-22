package com.mrc.douyinapp.home.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.mrc.douyinapp.R;
import com.mrc.douyinapp.home.recommend.adapter.MySearchAdapter;
import com.mrc.douyinapp.home.recommend.bean.BannerBeanlist;
import com.mrc.douyinapp.home.recommend.bean.RecommendBean;
import com.mrc.douyinapp.home.recommend.presenter.HomePresenter;
import com.mrc.douyinapp.home.recommend.view.IHomeView;

import java.util.ArrayList;
import java.util.List;

public class Main_search extends AppCompatActivity implements IHomeView,SwipeRefreshLayout.OnRefreshListener{
    private HomePresenter homePresenter;
    private int count = 5;
    private int cursor = 1;
    private List<RecommendBean.CategoryListBean> reco_list = new ArrayList<>();
    private List<BannerBeanlist.BannerBean> banner_list = new ArrayList<>();
    private XRecyclerView xlv;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MySearchAdapter mySearchAdapter;
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
        setContentView(R.layout.activity_main_search);
        xlv = findViewById(R.id.search_xlv);
        swipeRefreshLayout = findViewById(R.id.search_sr1);
        swipeRefreshLayout.setOnRefreshListener(this);
        //实现p层关联
        homePresenter = new HomePresenter(this);
        homePresenter.getBanner();
        homePresenter.getHomeRecommend(cursor, count);
        //设置可上拉
        xlv.setPullRefreshEnabled(false);
        xlv.setLoadingMoreEnabled(true);
        fitsSystemWindows(this);
        fullScreen(this);
    }

    @Override
    public void onHomeSuccess(RecommendBean recommendBean) {
        List<RecommendBean.CategoryListBean> category_list = recommendBean.getCategory_list();
        reco_list.addAll(category_list);
        xlv.setLayoutManager(new LinearLayoutManager(Main_search.this, LinearLayoutManager.VERTICAL, false));
        mySearchAdapter = new MySearchAdapter(Main_search.this, reco_list, banner_list);
        xlv.setAdapter(mySearchAdapter);
    }

    @Override
    public void onHomeFailed(String 数据错误) {

    }

    @Override
    public void onBannerSuccess(BannerBeanlist bannerBean) {
        List<BannerBeanlist.BannerBean> banner = bannerBean.getBanner();
        banner_list.addAll(banner);
    }

    @Override
    public void onBannerFailed(String 数据错误) {

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                reco_list.clear();
                cursor = cursor + 1;
                count = count + 5;
                homePresenter.getHomeRecommend(cursor, count);
                mySearchAdapter.notifyDataSetChanged();
            }
        }, 1000);
    }
}
