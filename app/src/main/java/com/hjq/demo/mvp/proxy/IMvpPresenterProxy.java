package com.hjq.demo.mvp.proxy;

/**
 *    author : Android
 *    github : https://github.com/renw7/AndroidProject
 *    time   : 2019/05/11
 *    desc   : 逻辑层代理接口
 */
public interface IMvpPresenterProxy {
    /**
     * 绑定 Presenter
     */
    void bindPresenter();

    /**
     * 解绑 Presenter
     */
    void unbindPresenter();
}