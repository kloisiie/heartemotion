package com.hyuga.xinlv.rnModule.shouhuan;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

public class HeartRateChartManager extends ViewGroupManager<HeartRateChart> {
    private final ReactApplicationContext reactContext;

    @NonNull
    @Override
    public String getName() {
        return "HeartRateChartComponent";
    }

    @NonNull
    @Override
    protected HeartRateChart createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new HeartRateChart(reactContext);
    }

    public HeartRateChartManager(ReactApplicationContext reactContext) {
        this.reactContext = reactContext;
    }

    @ReactProp(name = "type")
    public void setType(HeartRateChart view, int type) {
       view.type = type;
    }

    @ReactProp(name = "data")
    public void setData(HeartRateChart view, ReadableMap data) {
       view.setData1(data);
    }

}
