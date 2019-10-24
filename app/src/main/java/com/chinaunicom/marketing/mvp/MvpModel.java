package com.chinaunicom.marketing.mvp;

/**
 *    author : Android
 *    github : https://github.com/renw7/AndroidProject
 *    time   : 2018/11/17
 *    desc   : MVP 模型基类
 */
public abstract class MvpModel<L> {

    private L mListener;

    public void setListener(L listener) {
        mListener = listener;
    }

    public L getListener() {
        return mListener;
    }
}