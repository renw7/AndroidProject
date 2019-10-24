package com.chinaunicom.marketing.mvp.copy;

import java.util.List;

/**
 *    author : Android
 *    github : https://github.com/renw7/AndroidProject
 *    time   : 2018/11/17
 *    desc   : 可进行拷贝的监听器
 */
public interface CopyOnListener {

    void onSucceed(List<String> data);

    void onFail(String msg);
}