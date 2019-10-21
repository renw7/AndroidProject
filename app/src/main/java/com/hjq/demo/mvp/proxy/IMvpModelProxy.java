package com.hjq.demo.mvp.proxy;

/**
 *    author : Android
 *    github : https://github.com/renw7/AndroidProject
 *    time   : 2019/05/11
 *    desc   : 模型层代理接口
 */
public interface IMvpModelProxy {
    /**
     * 绑定 Model
     */
    void bindModel();

    /**
     * 解绑 Model
     */
    void unbindModel();
}