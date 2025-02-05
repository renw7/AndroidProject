package com.chinaunicom.marketing.ui.example;

import androidx.core.content.ContextCompat;

import com.chinaunicom.base.BaseDialog;
import com.hjq.demo.R;
import com.chinaunicom.marketing.common.MyActivity;
import com.chinaunicom.marketing.ui.dialog.MenuDialog;

/**
 *    author : Android
 *    github : https://github.com/renw7/AndroidProject
 *    time   : 2019/04/17
 *    desc   : 加载使用案例
 */
public final class StatusActivity extends MyActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_status;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        new MenuDialog.Builder(this)
                .setCancelable(false)
                //.setAutoDismiss(false) // 设置点击按钮后不关闭对话框
                .setList("加载中", "请求错误", "空数据提示", "自定义提示")
                .setListener(new MenuDialog.OnListener() {

                    @Override
                    public void onSelected(BaseDialog dialog, int position, Object object) {
                        switch (position) {
                            case 0:
                                showLoading();
                                postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        showComplete();
                                    }
                                }, 2000);
                                break;
                            case 1:
                                showError();
                                break;
                            case 2:
                                showEmpty();
                                break;
                            case 3:
                                showLayout(ContextCompat.getDrawable(getActivity(), R.drawable.icon_hint_address), "还没有添加地址");
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {}
                })
                .show();
    }
}