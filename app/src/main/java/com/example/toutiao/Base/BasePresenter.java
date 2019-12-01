package com.example.toutiao.Base;

public class BasePresenter<V extends  IBaseView> implements IBasePresenter {
    private V mView;

    @SuppressWarnings("unchecked")
    @Override
    public void attach(IBaseView view) {
        mView = (V) view;
    }

    @Override
    public void detach() {
        mView = null;
    }
}
