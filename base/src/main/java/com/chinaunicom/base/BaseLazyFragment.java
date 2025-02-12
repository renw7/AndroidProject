package com.chinaunicom.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Random;

/**
 *    author : Android
 *    github : https://github.com/renw7/AndroidProject
 *    time   : 2018/10/18
 *    desc   : Fragment 懒加载基类
 */
public abstract class BaseLazyFragment<A extends BaseActivity> extends Fragment {

    /** Activity对象 */
    private A mActivity;
    /** 根布局 */
    private View mRootView;
    /** 是否进行过懒加载 */
    private boolean isLazyLoad;
    /** Fragment 是否可见 */
    private boolean isFragmentVisible;
    /** 是否是 replace Fragment 的形式 */
    private boolean isReplaceFragment;

    /**
     * 获得全局的 Activity
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (A) requireActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mRootView == null && getLayoutId() > 0) {
            mRootView = inflater.inflate(getLayoutId(), null);
        }

        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }

        return mRootView;
    }

    @Override
    public View getView() {
        return mRootView;
    }

    /**
     * 获取绑定的 Activity，防止出现 getActivity 为空
     */
    public A getAttachActivity() {
        return mActivity;
    }

    /**
     * 是否进行了懒加载
     */
    protected boolean isLazyLoad() {
        return isLazyLoad;
    }

    /**
     * 当前 Fragment 是否可见
     */
    public boolean isFragmentVisible() {
        return isFragmentVisible;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isReplaceFragment) {
            if (isFragmentVisible) {
                initLazyLoad();
            }
        } else {
            initLazyLoad();
        }
    }

    /** replace Fragment时使用，ViewPager 切换时会回调此方法 */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isReplaceFragment = true;
        this.isFragmentVisible = isVisibleToUser;
        if (isVisibleToUser && mRootView != null) {
            if (!isLazyLoad) {
                initLazyLoad();
            } else {
                // 从不可见到可见
                onRestart();
            }
        }
    }

    /**
     * 初始化懒加载
     */
    protected void initLazyLoad() {
        if (!isLazyLoad) {
            isLazyLoad = true;
            initFragment();
        }
    }

    /**
     * 从可见的状态变成不可见状态，再从不可见状态变成可见状态时回调
     */
    @SuppressWarnings("all")
    protected void onRestart() {}

    protected void initFragment() {
        initView();
        initData();
    }

    /** 引入布局 */
    protected abstract int getLayoutId();

    /** 初始化控件 */
    protected abstract void initView();

    /** 初始化数据 */
    protected abstract void initData();

    /**
     * 根据资源 id 获取一个 View 对象
     */
    protected <V extends View> V findViewById(@IdRes int id) {
        return mRootView.findViewById(id);
    }

    protected <V extends View> V findActivityViewById(@IdRes int id) {
        return mActivity.findViewById(id);
    }

    /**
     * startActivity 方法优化
     */

    public void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(mActivity, cls));
    }

    public void startActivityFinish(Class<? extends Activity> cls) {
        startActivityFinish(new Intent(mActivity, cls));
    }

    public void startActivityFinish(Intent intent) {
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult 方法优化
     */

    private BaseActivity.ActivityCallback mActivityCallback;
    private int mActivityRequestCode;

    public void startActivityForResult(Class<? extends Activity> cls, BaseActivity.ActivityCallback callback) {
        startActivityForResult(new Intent(mActivity, cls), null, callback);
    }

    public void startActivityForResult(Intent intent, BaseActivity.ActivityCallback callback) {
        startActivityForResult(intent, null, callback);
    }

    public void startActivityForResult(Intent intent, Bundle options, BaseActivity.ActivityCallback callback) {
        // 回调还没有结束，所以不能再次调用此方法，这个方法只适合一对一回调，其他需求请使用原生的方法实现
        if (mActivityCallback == null) {
            mActivityCallback = callback;
            // 随机生成请求码，这个请求码在 0 - 255 之间
            mActivityRequestCode = new Random().nextInt(255);
            startActivityForResult(intent, mActivityRequestCode, options);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (mActivityCallback != null && mActivityRequestCode == requestCode) {
            mActivityCallback.onActivityResult(resultCode, data);
            mActivityCallback = null;
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 销毁当前 Fragment 所在的 Activity
     */
    public void finish() {
        mActivity.finish();
        mActivity = null;
    }

    /**
     * 获取系统服务
     */
    public <T> T getSystemService(@NonNull Class<T> serviceClass) {
        return ContextCompat.getSystemService(mActivity, serviceClass);
    }

    /**
     * Fragment 返回键被按下时回调
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 默认不拦截按键事件，回传给 Activity
        return false;
    }
}