package com.hyuga.xinlv.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.hyuga.xinlv.MainApplication;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttp类
 * Created by: Android Studio.
 * PackageName: com.ww7h.ww.common.apis.http.okhttp
 * User: ww
 * DateTime: 2019/3/22 20:49
 */
public class OkHttpUtil {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient client;

    private OkHttpUtil() {
        client = new OkHttpClient();
    }

    private static class HttpOkHttpInstance {
        private final static OkHttpUtil INSTANCE = new OkHttpUtil();
    }

    public static OkHttpUtil getInstance() {
        return HttpOkHttpInstance.INSTANCE;
    }

    private String getToken() {
        SharedPreferences sp = MainApplication.mainApplication.getSharedPreferences("react-native-sharepreference",
                Context.MODE_PRIVATE);
        String value = sp.getString("user_info_key", null);
        if (value != null) {
            Gson gson = new Gson();
            JSONObject userData = gson.fromJson(value, JSONObject.class);
            try {
                return userData.getString("token");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private String getRequestFlag() {
        SharedPreferences sp = MainApplication.mainApplication.getSharedPreferences("react-native-sharepreference",
                Context.MODE_PRIVATE);
        return sp.getString("user_info_key", null);
    }

    /**
     * GET请求
     *
     * @param url            请求地址
     * @param okHttpCallBack 请求回调
     */
    public void requestGet(@NotNull String url, String json, @NotNull final OkHttpCallBack okHttpCallBack) {
        if (!TextUtils.isEmpty(json)) {
            StringBuffer sb = new StringBuffer();
            sb.append(url).append("?");
            try {
                JSONObject jsonObject = new JSONObject(json);
                Iterator<String> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    String value = jsonObject.getString(key);
                    sb.append(key + "=" + value + "&");
                }
                url = sb.substring(0, sb.length() - 1);//截取到最后一位的&
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String token = getToken();
        Request.Builder builder = new Request.Builder()
                .url(url);
        if (!TextUtils.isEmpty(token)) {
            builder.addHeader("Authorization", token);
        }
        String requestFlag = getRequestFlag();
        if (!TextUtils.isEmpty(requestFlag)) {
            builder.addHeader("request-flag", requestFlag);
        }
        Request request = builder.build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                okHttpCallBack.requestFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String body = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        int code = jsonObject.getInt("code");
                        if (code == 200) {
                            Object data = jsonObject.get("data");
                            okHttpCallBack.requestSuccess(data.toString());
                        } else {
                            String msg = jsonObject.getString("msg");
                            okHttpCallBack.requestFailure(msg);
                        }

                    } catch (JSONException e) {
                        okHttpCallBack.requestFailure(e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    okHttpCallBack.requestFailure(response.message());
                }
            }
        });
    }

    /**
     * POST请求
     *
     * @param url            请求地址
     * @param json           请求参数 json 格式
     * @param okHttpCallBack 请求回调
     */
    public void requestPost(@NotNull String url, @NotNull String json, @NotNull final OkHttpCallBack okHttpCallBack) {
        RequestBody body = RequestBody.create(JSON, json);
        String token = getToken();
        Request.Builder builder = new Request.Builder()
                .url(url).post(body);
        String requestFlag = getRequestFlag();
        if (!TextUtils.isEmpty(token)) {
            builder.addHeader("Authorization", token);
        }
        if (!TextUtils.isEmpty(requestFlag)) {
            builder.addHeader("request-flag", requestFlag);
        }
        Request request = builder.build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                okHttpCallBack.requestFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String body = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        int code = jsonObject.getInt("code");
                        if (code == 200) {
                            Object data = jsonObject.get("data");
                            okHttpCallBack.requestSuccess(data.toString());
                        } else {
                            String msg = jsonObject.getString("msg");
                            okHttpCallBack.requestFailure(msg);
                        }
                    } catch (JSONException e) {
                        okHttpCallBack.requestFailure(e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    okHttpCallBack.requestFailure(response.message());
                }
            }
        });
    }


    public interface OkHttpCallBack {
        void requestSuccess(String result);

        /**
         * 请求失败回调
         *
         * @param message 回调返回失败消息
         */
        void requestFailure(String message);
    }
//
//    例示
//    OkHttpUtil.getInstance().requestGet("http://dev.blitzcrank.beiru168.com/api/v1/app/faq/list",null, new OkHttpUtil.OkHttpCallBack() {
//      @Override
//      public void requestSuccess(String result) {
//        Log.d(TAG,result);
//        try {
//          JSONArray jsonObject=new JSONArray(result);
//          Log.d(TAG,jsonObject.getJSONObject(0).getString("link"));
//        } catch (JSONException e) {
//          e.printStackTrace();
//        }
//      }
//
//      @Override
//      public void requestFailure(String message) {
//        Log.d(TAG,message);
//      }
//    });
//    JSONObject jsonObject=new JSONObject();
//    try {
//      jsonObject.put("type","GRADE_DESCRIPTION");
//    } catch (JSONException e) {
//      e.printStackTrace();
//    }
//    OkHttpUtil.getInstance().requestPost("http://dev.blitzcrank.beiru168.com/api/v1/app/link",jsonObject.toString(), new OkHttpUtil.OkHttpCallBack() {
//      @Override
//      public void requestSuccess(String result) {
//        Log.d(TAG,result);
//        try {
//          JSONObject jsonObject=new JSONObject(result);
//          Log.d(TAG,jsonObject.getString("link"));
//        } catch (JSONException e) {
//          e.printStackTrace();
//        }
//      }
//
//      @Override
//      public void requestFailure(String message) {
//        Log.d(TAG,message);
//      }
//    });
}