package com.hyuga.xinlv.helper;

import android.accounts.NetworkErrorException;
import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hyuga.xinlv.config.EnvConfig;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.qiyukf.unicorn.api.ImageLoaderListener;
import com.qiyukf.unicorn.api.UICustomization;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.UnicornImageLoader;
import com.qiyukf.unicorn.api.YSFOptions;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;


import cn.jpush.android.api.JPushInterface;

public class ApplicationHelper {
    private static Application mContext;
    private static ApplicationHelper mInstance = new ApplicationHelper();

    private ApplicationHelper() {

    }

    public static ApplicationHelper init(Application context) {
        if(mContext == null){
            mContext = context;
        }
        return mInstance;
    }

    public ApplicationHelper initQiYu() {
        YSFOptions options = new YSFOptions();
        options.uiCustomization = new UICustomization();
        options.uiCustomization.titleCenter = true;
        Unicorn.init(mContext, "appkey", options, new GlideImageLoader());
        return mInstance;
    }

    public ApplicationHelper initUM() {
        if (!TextUtils.isEmpty(EnvConfig.get().common.UMKey)) {
            String channelName = AnalyticsConfig.getChannel(mContext);
            UMConfigure.setLogEnabled(true);
            // 参数一：当前上下文context；
            // 参数二：应用申请的Appkey（需替换）；
            // 参数三：渠道名称；
            // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
            // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换
            UMConfigure.init(mContext, EnvConfig.get().common.UMKey, channelName, UMConfigure.DEVICE_TYPE_PHONE, null);
            // 选用AUTO页面采集模式
            MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        }
        return mInstance;
    }

    public ApplicationHelper initJiGuang(){
        //极光推送
        JPushInterface.setDebugMode(true);//打印log
        JPushInterface.init(mContext);
        return  mInstance;
    }

    private class GlideImageLoader implements UnicornImageLoader {

        @Nullable
        @Override
        public Bitmap loadImageSync(String uri, int width, int height) {
            return null;
        }

        @Override
        public void loadImage(String uri, int width, int height, ImageLoaderListener listener) {
            if (width <= 0 || height <= 0) {
                width = height = Integer.MIN_VALUE;
            }

            Glide.with(mContext).asBitmap().load(uri).into(new SimpleTarget<Bitmap>(width, height) {

                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    if (listener != null) {
                        listener.onLoadComplete(resource);
                    }
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    if (listener != null) {
                        listener.onLoadFailed(new NetworkErrorException());
                    }
                }
            });
        }
    }

}
