package com.chinaunicom.marketing.mvp;

import android.content.Context;

import com.chinaunicom.marketing.common.MyActivity;
import com.chinaunicom.marketing.mvp.proxy.IMvpPresenterProxy;
import com.chinaunicom.marketing.mvp.proxy.MvpPresenterProxyImpl;

/**
 *    author : Android
 *    github : https://github.com/renw7/AndroidProject
 *    time   : 2018/11/17
 *    desc   : MVP Activity 基类
 */
public abstract class MvpActivity extends MyActivity implements IMvpView {

    private IMvpPresenterProxy mMvpProxy;

    @Override
    public void initActivity() {
        mMvpProxy = createPresenterProxy();
        mMvpProxy.bindPresenter();
        super.initActivity();
    }

    protected IMvpPresenterProxy createPresenterProxy() {
        return new MvpPresenterProxyImpl(this);
    }

    @Override
    protected void onDestroy() {
        mMvpProxy.unbindPresenter();
        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onLoading() {
        showLoading();
    }

    @Override
    public void onComplete() {
        showComplete();
    }

    @Override
    public void onEmpty() {
        showEmpty();
    }

    @Override
    public void onError() {
        showError();
    }
}