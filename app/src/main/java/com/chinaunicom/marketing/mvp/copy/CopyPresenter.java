package com.chinaunicom.marketing.mvp.copy;

import com.chinaunicom.marketing.mvp.MvpInject;
import com.chinaunicom.marketing.mvp.MvpPresenter;

import java.util.List;

/**
 *    author : Android
 *    github : https://github.com/renw7/AndroidProject
 *    time   : 2018/11/17
 *    desc   : 可进行拷贝的业务处理类
 */
public final class CopyPresenter extends MvpPresenter<CopyContract.View>
        implements CopyContract.Presenter, CopyOnListener {

    @MvpInject
    CopyModel mModel;

    /**
     * {@link CopyContract.Presenter}
     */

    @Override
    public void login(String account, String password) {
        mModel.setAccount(account);
        mModel.setPassword(password);
        mModel.setListener(this);
        mModel.login();
    }

    /**
     * {@link CopyOnListener}
     */

    @Override
    public void onSucceed(List<String> data) {
        getView().loginSuccess(data);
    }

    @Override
    public void onFail(String msg) {
        getView().loginError(msg);
    }
}