package com.chinaunicom.marketing.ui.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import androidx.annotation.Nullable;

import com.chinaunicom.marketing.bl.OkHttpDemoBL;
import com.chinaunicom.marketing.helper.ConstantsUtil;
import com.hjq.demo.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *    author : Android
 *    github : https://github.com/renw7/AndroidProject
 *    time   : 2018/10/31
 *    desc   : http请求demo ui层
 */
public final class HttpDemoActivity extends Activity {

    ListView listview = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_httpdemo);

        listview = findViewById(R.id.listview1);//获取列表视图

        // 动态注册
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConstantsUtil.ACTION_APP_INNER_BROADCAST);
        registerReceiver(mReceiver, intentFilter);


        OkHttpDemoBL okHttpDemoBL = new OkHttpDemoBL();
        Map param = new HashMap<>();
//        okHttpDemoBL.getUserInfoAllAsyn(param, this.getApplicationContext());

    }





    private BroadcastReceiver mReceiver = new DataReceiver();
    class DataReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<Map<String, String>> list = (List)intent.getExtras().get("list");
            System.out.println("size="+list.size());

            SimpleAdapter adapter = new SimpleAdapter(HttpDemoActivity.this, list, R.layout.activity_httpitemdemo,
                    new String[]{"staffId", "staffName", "staffNo", "staffPwd"}, new int[]{R.id.userId, R.id.userName, R.id.userSex, R.id.userBirthday});
            listview.setAdapter(adapter);

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);     //注销广播接收器
    }
}