package com.mrc.douyinapp.attention.presenter;


import com.mrc.douyinapp.attention.model.AttentionModel;
import com.mrc.douyinapp.attention.view.IView;

public class AttentionPresenter extends IPresenter<IView>{
    private AttentionModel model;
    public AttentionPresenter(IView iView) {
        super(iView);
    }

    @Override
    protected void init() {
        model = new AttentionModel();
    }
}
