package com.chinaunicom.marketing.helper;


import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 *    author : Android
 *    github : https://github.com/renw7/AndroidProject
 *    time   : 2018/10/31
 *    desc   : 网络请求框架okhttp工具类
 */
public final class OkHttpClientUtils {

    OkHttpClient mClient;

    Handler mHandler;

    public static String TAG = "";

    /**
     * 单例模式，必须要写一个无参的私有的构造方法，哪怕这个方法里什么事都不干
     */
    private OkHttpClientUtils() {
        initOkHttp();
    }

    private static class OkHttpManagerHolder {
        private static OkHttpClientUtils INSTANCE = new OkHttpClientUtils();
    }

    public static OkHttpClientUtils getInstance() {
        return OkHttpManagerHolder.INSTANCE;
    }

    /**
     * 初始化okhttp
     */
    private void initOkHttp() {
        //创建一个handler,用于从子线程把结果返回给主线程
        mHandler = new Handler();

        //通过构造者模式，拿到build实例，开始设置参数
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //我们设置一些超时，链接超时，读写超时，不是必要的
        //两个参数：1.时间长度  2.时间单位
        builder.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                //添加日志拦截器
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        mClient = builder.build();
    }

    public void doGetAsyn(String url, final NetWorkCallBack callBack) {
        //首先生成请求
        Request request = new Request.Builder()
                .url(url)
                .build();

        //调用client.newCall方法，把请求当做参数传入
        //通过enqueue发起异步请求，添加OKHttp提供的Callback回调的参数
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack != null) {
                            callBack.onFail("error");
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                //回调的方法执行在子线程。
                if (response.isSuccessful()) {
                    //记住！一定是.string
                    final String str = response.body().string();
                    Log.d(TAG, "获取数据成功了");
                    Log.d(TAG, "response.code()==" + response.code());
                    Log.d(TAG, "response.body().string()==" + str);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callBack != null) {
                                callBack.onSuccess(str);
                            }
                        }
                    });
                } else {
                    if (callBack != null) {
                        callBack.onFail("");
                    }
                }
            }
        });
    }


    public void doPostAsyn(String url, Map<String, String> map, final NetWorkCallBack callBack) {
        //创建表单请求体
        FormBody.Builder formBody = new FormBody.Builder();

        if (map != null) {
            //遍历map集合，将key，value放入FormBody中
            for (Map.Entry<String, String> entry : map.entrySet()) {
                //传递键值对参数
                formBody.add(entry.getKey(), entry.getValue());
            }
        }

        //创建Request 对象。
        Request request = new Request.Builder()
                .url(url)
                //传递请求体
                .post(formBody.build())
                .build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callBack != null) {
                            callBack.onFail("error");
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                //回调的方法执行在子线程。
                if (response.isSuccessful()) {
                    final String str = response.body().string();
                    Log.d(TAG, "获取数据成功了");
                    Log.d(TAG, "response.code()==" + response.code());
                    Log.d(TAG, "response.body().string()==" + str);

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callBack != null) {
                                callBack.onSuccess(str);
                            }
                        }
                    });
                } else {
                    if (callBack != null) {
                        callBack.onFail("");
                    }
                }
            }
        });
    }




    //自己定义接口回调
    public interface NetWorkCallBack {
        void onSuccess(String str);

        void onFail(String str);
    }

}