package com.mrc.douyinapp.home.recommend.view;


import com.mrc.douyinapp.home.recommend.bean.BannerBeanlist;
import com.mrc.douyinapp.home.recommend.bean.RecommendBean;

public interface IHomeView {
    void onHomeSuccess(RecommendBean recommendBean);
    void onHomeFailed(String 数据错误);
    //轮播图请求
    void onBannerSuccess(BannerBeanlist bannerBean);
    void onBannerFailed(String 数据错误);
}
