package com.hyuga.xinlv.rnModule.mob;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

public class ShareModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    @NonNull
    @Override
    public String getName() {
        return "MobShareModule";
    }

    public ShareModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @ReactMethod
    public void share(int type, String title, String content, String url, ReadableArray images, Callback callback){
        int size = images.size();
         String[] arr = new String[size];

         for (int i=0;i<size;i++){
             String path = images.getString(i);
             arr[i] = path;
         }

        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle(title);
        sp.setTitleUrl(url); // 标题的超链接
        sp.setText(content);
        sp.setImageArray(arr);
        sp.setSite(title);
        sp.setSiteUrl(url);

        Platform wechat = ShareSDK.getPlatform (Wechat.NAME);
// 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
        wechat.setPlatformActionListener (new PlatformActionListener() {
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
                callback.invoke(0);
            }
            public void onComplete(Platform arg0, int arg1, HashMap arg2) {
                //分享成功的回调
                callback.invoke(200);
            }
            public void onCancel(Platform arg0, int arg1) {
                //取消分享的回调
                callback.invoke(0);
            }
        });
// 执行图文分享
        wechat.share(sp);

    }


}
