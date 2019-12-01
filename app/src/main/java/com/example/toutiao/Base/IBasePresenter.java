package com.example.toutiao.Base;

public interface IBasePresenter<V extends IBaseView> {
    void attach(V view);

    void detach();
}
