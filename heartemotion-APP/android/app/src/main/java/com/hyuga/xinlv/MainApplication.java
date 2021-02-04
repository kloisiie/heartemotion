package com.hyuga.xinlv;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;

import com.hyuga.xinlv.config.EnvConfig;
import com.hyuga.xinlv.helper.ApplicationHelper;
import com.hyuga.xinlv.rnModule.MyRNPackage;
import com.facebook.react.PackageList;
import com.facebook.react.ReactApplication;

import com.microsoft.codepush.react.CodePush;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.soloader.SoLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {

    public static MainApplication mainApplication = null;

    private final ReactNativeHost mReactNativeHost =
            new ReactNativeHost(this) {
                @Override
                public boolean getUseDeveloperSupport() {
                    return BuildConfig.DEBUG;
                }

                @Override
                protected List<ReactPackage> getPackages() {
                    @SuppressWarnings("UnnecessaryLocalVariable")
                    List<ReactPackage> packages = new PackageList(this).getPackages();
                    for (ReactPackage rp : packages) {
                        if (rp instanceof CodePush) {
                            packages.remove(rp);
                            break;
                        }
                    }
                    packages.add(new CodePush(BuildConfig.CODEPUSH_KEY, getApplicationContext(), BuildConfig.DEBUG));
                    packages.add(new MyRNPackage());
                    return packages;
                }

                @Override
                protected String getJSMainModuleName() {
                    return "index";
                }

                @Nullable
                @Override
                protected String getJSBundleFile() {
                    if (BuildConfig.DEBUG) {
                        return super.getJSBundleFile();
                    }

                    return CodePush.getJSBundleFile();
                }
            };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationHelper.init(this).initQiYu();
        if(inMainProcess(this)){
            mainApplication = this;
            SoLoader.init(this, /* native exopackage */ false);
           // initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
            try {//配置环境
                EnvConfig.init(this, "dev");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            ApplicationHelper.init(this).initJiGuang();
        }

    }

    /**
     * Loads Flipper in React Native templates. Call this in the onCreate method with something like
     * initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
     *
     * @param context
     * @param reactInstanceManager
     */
    private static void initializeFlipper(
            Context context, ReactInstanceManager reactInstanceManager) {
        if (BuildConfig.DEBUG) {
            try {
        /*
         We use reflection here to pick up the class that initializes Flipper,
        since Flipper library is not available in release mode
        */
                Class<?> aClass = Class.forName("com.hyuga.ReactNativeFlipper");
                aClass
                        .getMethod("initializeFlipper", Context.class, ReactInstanceManager.class)
                        .invoke(null, context, reactInstanceManager);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean inMainProcess(Context context) {
        String mainProcessName = context.getApplicationInfo().processName;
        String processName = processName();
        return TextUtils.equals(mainProcessName, processName);
    }

    /**
     * 获取当前进程名
     */
    private static String processName() {
        BufferedReader reader = null;
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            reader = new BufferedReader(new FileReader(file));
            return reader.readLine().trim();
        } catch (IOException e) {
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
