package com.hyuga.xinlv.config;

import android.content.Context;
import android.util.Log;

import com.hyuga.xinlv.config.annotation.Value;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * 配置信息
 *
 * 包含两部分配置
 * common.properties
 * application.properties
 *
 * application.properties可根据环境进行变幻application-{active}.properties
 * 存在application.properties文件时，将忽略环境切换
 *
 * common 与 application 同时存在的配置，以application为准
 */
public class EnvConfig {

    private EnvConfig(){}
    private static EnvConfig config;
    public static Properties props;

    /**
     * 初始化配置
     * @param context  上下文
     * @param defActive    配置环境，如果config/*目录下存在application.properties，该参数指向的环境将被忽略
     */
    public static void init(Context context, String defActive) throws Exception {

        props = new Properties();
        InputStream ais;

        try {
            ais = context.getAssets().open("config/application.properties");
        } catch (FileNotFoundException e){
            Log.i(EnvConfig.class.getName(), "application.properties不存在正在寻找" + defActive + "环境");
            ais = context.getAssets()
                    .open("config/application-" + defActive +".properties");
        }
        props.load(context.getAssets().open("config/common.properties"));
        props.load(ais);


        config = new EnvConfig();
        initObject(config);
    }


    public static EnvConfig get(){
        if(config == null) throw new RuntimeException("请先使用Config.init将配置初始化");
        return config;
    }

    @Value(name = "active")
    public String active;
    @Value
    public UrlConfig url;
    @Value
    public CommonConfig common;

    public static class UrlConfig{
        @Value(name = "url.quote")
        public String quote;
        @Value(name = "url.api")
        public String api;

    }

    public static class CommonConfig{
        @Value(name = "common.UMKey")
        public String UMKey;
        @Value(name = "common.messageSecret")
        public String messageSecret;
        @Value(name = "common.Unicorn")
        public String Unicorn;
        @Value(name = "common.Bugly")
        public String Bugly;
        @Value(name = "common.aesKey")
        public String aesKey;
        @Value(name = "common.rnBranch")
        public String rnBranch;
        @Value(name = "common.QQzoneId")
        public String QQzoneId;
        @Value(name = "common.QQzoneKey")
        public String QQzoneKey;

        @Value(name = "common.wechatKey")
        public String wechatKey;

        @Value(name = "common.wechatSecret")
        public String wechatSecret;
        @Value(name = "common.sinaWeiboKey")
        public String sinaWeiboKey;
        @Value(name = "common.sinaWeiboSecret")
        public String sinaWeiboSecret;
        @Value(name = "common.AliAppId")
        public String AliAppId;
    }

    /**
     * 初始化对象中的配置
     * @param obj
     * @throws Exception
     */
    private static void initObject(Object obj) throws Exception {
        Field[] fields = obj.getClass().getFields();

        for (Field field : fields) {
            String typeName = field.getType().getName();
            Value valueAnnotation = field.getAnnotation(Value.class);
            if(valueAnnotation == null){
                continue;
            }

            //如果没有相关配置，直接抛出异常
            Object value = props.getProperty(valueAnnotation.name());
            if(!valueAnnotation.name().equals("")){
                if(value == null){
                    throw new RuntimeException("配置" + valueAnnotation.name() + "未初始化");
                }
            }


            if(typeName.equals(String.class.getName())){
                field.set(obj, value);
            } else if(typeName.equals(Integer.class.getName())){
                field.set(obj, value);
            } else if(typeName.equals(Double.class.getName())){
                field.set(obj, value);
            } else if(typeName.equals(Long.class.getName())){
                field.set(obj, value);
            } else {
                Object _obj = field.getType().newInstance();
                field.set(obj, _obj);
                initObject(_obj);
            }
        }
    }

}
