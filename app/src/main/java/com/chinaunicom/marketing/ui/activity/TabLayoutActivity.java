package com.chinaunicom.marketing.ui.activity;

import android.app.Activity;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;

import com.chinaunicom.marketing.bl.OkHttpDemoBL;
import com.chinaunicom.marketing.helper.ConstantsUtil;
import com.hjq.demo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabLayoutActivity extends Activity {

    ListView listview1 = null;

    ListView listview2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tab_layout);

        TabHost tab = findViewById(android.R.id.tabhost);
        //初始化TabHost容器
        tab.setup();
        //在TabHost创建标签，然后设置：标题／图标／标签页布局
        tab.addTab(tab.newTabSpec("tab1").setIndicator("全部通话", null).setContent(R.id.tab1));
        tab.addTab(tab.newTabSpec("tab2").setIndicator("意向通话", null).setContent(R.id.tab2));


        listview1 = findViewById(R.id.listview1);//获取列表视图
        listview2 = findViewById(R.id.listview2);//获取列表视图


        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> var1, View view, int pos, long l) {
                Map<String, String> map = new HashMap();
//                Map<String,String> map2= new HashMap();
                map = (Map) var1.getItemAtPosition(pos);
//                map2.put("createUser",map.get("createUser"));

                Intent it = new Intent(
                        TabLayoutActivity.this,
                        CallDetailActivity.class);
                it.putExtra("recordId", map.get("recordId"));
//                it.putExtra("taskId", map.get("taskId"));
//                it.putExtra("startTime", map.get("startTime"));
//                it.putExtra("endTime", map.get("endTime"));
//                it.putExtra("resultCode", map.get("resultCode"));
//                it.putExtra("remark", map.get("remark"));
                startActivity(it);
            }
        });
        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> var1, View view, int pos, long l) {
                Map<String, String> map = new HashMap();
//                Map<String,String> map2= new HashMap();
                map = (Map) var1.getItemAtPosition(pos);
//                map2.put("createUser",map.get("createUser"));

                Intent it = new Intent(
                        TabLayoutActivity.this,
                        CallDetailActivity.class);
                it.putExtra("recordId", map.get("recordId"));
//                it.putExtra("taskId", map.get("taskId"));
//                it.putExtra("startTime", map.get("startTime"));
//                it.putExtra("endTime", map.get("endTime"));
//                it.putExtra("resultCode", map.get("resultCode"));
//                it.putExtra("remark", map.get("remark"));
                startActivity(it);
            }
        });
        //使用Selected未能实现
//        listview1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
//            @Override
//            public void onItemSelected(AdapterView<?> var1, View var2, int pos, long var4){
//                Map<String,String> map = new HashMap();
////                Map<String,String> map2= new HashMap();
//                map=(Map)var1.getItemAtPosition(pos);
////                map2.put("createUser",map.get("createUser"));
//
//                Intent it = new Intent(
//                        TabLayoutActivity.this,
//                        CallDetailActivity.class);
//                it.putExtra("serialNumber", map.get("serialNumber"));
//
//                startActivity(it);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> var1){}
//
//        });

        // 动态注册
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("abc");
        registerReceiver(mReceiver, intentFilter);


        //调逻辑层取后台数据
        OkHttpDemoBL okHttpDemoBL = new OkHttpDemoBL();
        //传递参数
        Map param = new HashMap<>();
//        param.put("username", "张三");
        okHttpDemoBL.getUserInfoAllAsyn(param, this.getApplicationContext());


    }

    private BroadcastReceiver mReceiver = new DataReceiver();

    class DataReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<Map<String, String>> list = (List) intent.getExtras().get("list");
            System.out.println("size=" + list.size());

            SimpleAdapter adapter1 = new SimpleAdapter(TabLayoutActivity.this, list, R.layout.activity_record,
                    new String[]{"serialNumber", "startTime"}, new int[]{R.id.call_number, R.id.call_time});

            listview1.setAdapter(adapter1);


            List<Map<String, String>> list2 = new ArrayList();

            for (int i = 0; i < list.size(); i++) {

                if ((list.get(i).get("resultCode") == "2") | (list.get(i).get("resultCode").equals("2"))) {

                    list2.add(list.get(i));
                }

            }
            SimpleAdapter adapter2 = new SimpleAdapter(TabLayoutActivity.this, list2, R.layout.activity_record,
                    new String[]{"serialNumber", "startTime"}, new int[]{R.id.call_number, R.id.call_time});

            listview2.setAdapter(adapter2);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);     //注销广播接收器
    }
}
