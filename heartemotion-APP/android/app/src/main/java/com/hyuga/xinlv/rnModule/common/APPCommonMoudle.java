package com.hyuga.xinlv.rnModule.common;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.hyuga.xinlv.MainApplication;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.gson.Gson;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.YSFUserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class APPCommonMoudle extends ReactContextBaseJavaModule {
    private static final String TAG = "APPCommonMoudle";
    private ReactApplicationContext reactContext;

    @NonNull
    @Override
    public String getName() {
        return "APPCommonMoudle";
    }

    public APPCommonMoudle(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }


    @ReactMethod
    public void conectService() {
        SharedPreferences sp = MainApplication.mainApplication.getSharedPreferences("react-native-sharepreference",
                Context.MODE_PRIVATE);
        String value = sp.getString("user_info_key", null);
        Log.d(TAG, "conectService: user"+value);
        String flag = sp.getString("request_flag_key", null);
        Log.d(TAG, "conectService: request_flag"+flag);
        if (flag != null) {
            try {
                flag = URLDecoder.decode(flag, "utf-8");
                Log.d(TAG, "conectService: request_flag2222"+flag);
                JSONObject flagObj = new JSONObject(flag);
                if (flagObj != null) {
                    YSFUserInfo userInfo = new YSFUserInfo();
                    userInfo.userId = flagObj.getString("machine");
                    if (value != null) {
                        userInfo.data = getCRMString(value);
                    }
                    Unicorn.setUserInfo(userInfo);
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String title = "聊天窗口的标题";
        ConsultSource source = new ConsultSource("", "", "");
        Unicorn.openServiceActivity(reactContext, title, source);
    }

    @ReactMethod
    public void logoutService() {
        Unicorn.logout();
    }

    private String getCRMString(String jsonStr) {
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonStr);
            Iterator<String> keyIter = jsonObject.keys();
            String key;
            Object value;
            while (keyIter.hasNext()) {
                key = keyIter.next();
                value = jsonObject.get(key);
                Map<String, Object> valueMap = new HashMap<String, Object>();
                valueMap.put("key", key);
                valueMap.put("value", value);
                list.add(valueMap);
            }

            String crm = new Gson().toJson(list);
            Log.d(TAG, "getCRMString: " + crm);
            return crm;

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    @ReactMethod
    public void checkBlueStatus(Promise promise){//查询蓝牙是否打开
        BluetoothAdapter blueadapter=BluetoothAdapter.getDefaultAdapter();
        if(blueadapter != null){
            promise.resolve(blueadapter.isEnabled());
        }else{
            //手机设备不支持蓝牙
            promise.reject("","");
        }
    }

    @ReactMethod
    public void openBlue(Promise promise){//打开蓝牙
        BluetoothAdapter blueadapter=BluetoothAdapter.getDefaultAdapter();
        boolean flag = blueadapter.enable();
        promise.resolve(flag);

    }
}
