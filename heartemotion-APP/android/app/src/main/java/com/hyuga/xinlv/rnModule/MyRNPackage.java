package com.hyuga.xinlv.rnModule;

import androidx.annotation.NonNull;

import com.hyuga.xinlv.rnModule.common.APPCommonMoudle;
import com.hyuga.xinlv.rnModule.mob.ShareModule;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.hyuga.xinlv.rnModule.shouhuan.HeartRateChartManager;
import com.hyuga.xinlv.rnModule.shouhuan.WristbandMoudle;

import java.util.ArrayList;
import java.util.List;

public class MyRNPackage implements ReactPackage {
    @NonNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        List<NativeModule> lists = new ArrayList<>();
        lists.add(new APPCommonMoudle(reactContext));
        lists.add(new ShareModule(reactContext));
        lists.add(new WristbandMoudle(reactContext));
        return lists;
    }

    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        List<ViewManager> viewManagerList = new ArrayList<>();
        viewManagerList.add(new HeartRateChartManager(reactContext));
        return viewManagerList;
    }


}
