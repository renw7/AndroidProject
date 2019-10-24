package com.chinaunicom.marketing.mvp.copy;

import android.view.View;

import com.chinaunicom.marketing.mvp.MvpActivity;
import com.chinaunicom.marketing.mvp.MvpInject;
import com.hjq.demo.R;

import java.util.List;

/**
 *    author : Android
 *    github : https://github.com/renw7/AndroidProject
 *    time   : 2018/11/17
 *    desc   : 可进行拷贝的MVP Activity 类
 */
public final class CopyMvpActivity extends MvpActivity implements CopyContract.View {

    @MvpInject
    CopyPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_copy;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    public void onLogin(View view) {
        // 登录操作
        mPresenter.login("账户", "密码");
    }

    /**
     * {@link CopyContract.View}
     */

    @Override
    public void loginError(String msg) {
        toast(msg);
    }

    @Override
    public void loginSuccess(List<String> data) {
        toast("登录成功了");
    }
}