package com.chinaunicom.marketing.ui.dialog;

import android.view.Gravity;

import androidx.fragment.app.FragmentActivity;

import com.chinaunicom.marketing.common.MyDialogFragment;
import com.chinaunicom.base.BaseDialog;
import com.hjq.demo.R;

/**
 *    author : Android
 *    github : https://github.com/renw7/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 可进行拷贝的副本
 */
public final class CopyDialog {

    public static final class Builder
            extends MyDialogFragment.Builder<Builder> {

        public Builder(FragmentActivity activity) {
            super(activity);

            setContentView(R.layout.dialog_copy);
            setAnimStyle(BaseDialog.AnimStyle.BOTTOM);
            setGravity(Gravity.BOTTOM);
        }
    }
}