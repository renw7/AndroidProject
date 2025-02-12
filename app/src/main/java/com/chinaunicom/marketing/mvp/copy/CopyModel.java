package com.chinaunicom.marketing.mvp.copy;

import com.chinaunicom.marketing.mvp.MvpModel;

/**
 *    author : Android
 *    github : https://github.com/renw7/AndroidProject
 *    time   : 2018/11/17
 *    desc   : 可进行拷贝的接口实现类
 */
public final class CopyModel extends MvpModel<CopyOnListener> {

    private String mAccount;
    private String mPassword;

    public CopyModel() {
        // 在这里做一些初始化操作
    }

    public void setAccount(String account) {
        this.mAccount = account;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public void login() {
        // 为了省事，这里直接回调成功
        if ("账户".equals(mAccount) && "密码".equals(mPassword)) {
            getListener().onSucceed(null);
        } else {
            getListener().onFail("账户或密码不对哦");
        }
    }
}