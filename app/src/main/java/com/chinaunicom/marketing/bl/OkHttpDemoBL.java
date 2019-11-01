package com.chinaunicom.marketing.bl;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chinaunicom.marketing.helper.ConstantsUtil;
import com.chinaunicom.marketing.helper.OkHttpClientUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *    author : Android
 *    github : https://github.com/renw7/AndroidProject
 *    time   : 2018/10/31
 *    desc   : http请求demo bl层
 */
public class OkHttpDemoBL {

    //云服务器地址
    private String url = "http://10.52.200.150/tblstaffinfo/page";

    //本机测试地址
//    private String url = "http://10.52.200.150/tbluserinfo/page";

    /**
     * 异步http调用
     * @param param
     */
    public void getUserInfoAllAsyn(Map param, Context context){
        OkHttpClientUtils.getInstance().doGetAsyn(url, new OkHttpClientUtils.NetWorkCallBack(){
            @Override
            public void onSuccess(String response) {
                ArrayList<Map> list = json2List(response);
                //下面通过异常方式返回给ui层
                Intent intent = new Intent(ConstantsUtil.ACTION_APP_INNER_BROADCAST);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", list);
                intent.putExtras(bundle);       //向广播接收器传递数据
                context.sendBroadcast(intent);
            }

            @Override
            public void onFail(String response) {
                System.out.println(response);
            }
        });

    }




    private ArrayList<Map> json2List(String result){
        ArrayList<Map> recordList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(result);
            String code = jsonObject.getString("code");

            if ("200".equals(code)) {
                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("records");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject record = jsonArray.getJSONObject(i);
                    Map map = new HashMap();
                    map.put("staffId", record.getString("staffId"));
                    map.put("staffName", record.getString("staffName"));
                    map.put("staffNo", record.getString("staffNo"));
                    map.put("staffPwd", record.getString("staffPwd"));
                    recordList.add(map);
                }
            }
            else{
                throw new Exception("连接异常["+code+"]");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return recordList;
        }
    }


}