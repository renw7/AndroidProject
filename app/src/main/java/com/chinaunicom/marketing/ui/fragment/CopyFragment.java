package com.chinaunicom.marketing.ui.fragment;

import com.hjq.demo.R;
import com.chinaunicom.marketing.common.MyLazyFragment;
import com.chinaunicom.marketing.ui.example.CopyActivity;

/**
 *    author : Android
 *    github : https://github.com/renw7/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 可进行拷贝的副本
 */
public final class CopyFragment extends MyLazyFragment<CopyActivity> {

    public static CopyFragment newInstance() {
        return new CopyFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_copy;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}