package com.hyuga.xinlv.rnModule.shouhuan;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.hyuga.xinlv.R;


import java.util.ArrayList;
import java.util.UUID;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.data.JPushLocalNotification;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import polar.com.sdk.api.PolarBleApi;
import polar.com.sdk.api.PolarBleApiCallback;
import polar.com.sdk.api.PolarBleApiDefaultImpl;
import polar.com.sdk.api.errors.PolarInvalidArgument;
import polar.com.sdk.api.model.PolarDeviceInfo;
import polar.com.sdk.api.model.PolarHrBroadcastData;
import polar.com.sdk.api.model.PolarHrData;

// 桥接手环sdk
public class WristbandMoudle extends ReactContextBaseJavaModule {
    private static final String TAG = "WristbandMoudle";
    private final ReactApplicationContext reactContext;
    private PolarBleApi api;
    private Disposable broadcastDisposable;
    private ArrayList<String> devices;
    private PolarHrData polarHrData;
    private Handler handler= new Handler(Looper.getMainLooper());
    private boolean disConnectFlag = false;


    @NonNull
    @Override
    public String getName() {
        return "WristbandMoudle";
    }

    public WristbandMoudle(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        api = PolarBleApiDefaultImpl.defaultImplementation(reactContext, PolarBleApi.ALL_FEATURES);
        api.setPolarFilter(false);

    }

    @ReactMethod
    public void findDevices() {
        devices = new ArrayList<>();
        broadcastDisposable = api.startListenForPolarHrBroadcasts(null).subscribe(
                new Consumer<PolarHrBroadcastData>() {
                    @Override
                    public void accept(PolarHrBroadcastData polarBroadcastData) throws Exception {
                        Log.d(TAG, "HR BROADCAST " +
                                polarBroadcastData.polarDeviceInfo.deviceId + " HR: " +
                                polarBroadcastData.hr + " batt: " +
                                polarBroadcastData.batteryStatus);
                        Log.d(TAG, "设备名称 "+ polarBroadcastData.polarDeviceInfo.name);
                        String deviceId = polarBroadcastData.polarDeviceInfo.deviceId;
                        if(!devices.contains(deviceId)){//去重
                            devices.add(deviceId);
                            WritableMap params = Arguments.createMap();
                            params.putString("id", polarBroadcastData.polarDeviceInfo.deviceId);
                            params.putString("name", polarBroadcastData.polarDeviceInfo.name);
                            reactContext
                                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                                    .emit("findDeivceEvent", params);
                        }

                    }
                },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "" + throwable.getLocalizedMessage());
                    }
                },
                new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, "complete");
                    }
                });

    }

    @ReactMethod
    public void stopFind() {
        if (broadcastDisposable != null) {
            broadcastDisposable.dispose();
            broadcastDisposable = null;
        }
    }


    @ReactMethod
    public void connectDeivce(String deviceId, Promise promise) {
        handler.removeCallbacks(runnable);
        this.polarHrData = null;
        disConnectFlag = false;
        api.setApiCallback(new PolarBleApiCallback() {
            @Override
            public void blePowerStateChanged(boolean powered) {
                super.blePowerStateChanged(powered);
                Log.d(TAG, "blePowerStateChanged: ");
            }

            @Override
            public void deviceConnected(PolarDeviceInfo polarDeviceInfo) {
                super.deviceConnected(polarDeviceInfo);
                promise.resolve(true);
                Log.d(TAG, "deviceConnected: ");
            }

            @Override
            public void deviceConnecting(PolarDeviceInfo polarDeviceInfo) {
                super.deviceConnecting(polarDeviceInfo);
                Log.d(TAG, "deviceConnecting: ");
            }

            @Override
            public void deviceDisconnected(PolarDeviceInfo polarDeviceInfo) {
                super.deviceDisconnected(polarDeviceInfo);
                Log.d(TAG, "deviceDisconnected: ");
                if(!disConnectFlag){//断开尝试重连一次
                    try {
                        api.connectToDevice(deviceId);
                    } catch (PolarInvalidArgument polarInvalidArgument) {
                        polarInvalidArgument.printStackTrace();
                    }
                }
            }

            @Override
            public void ecgFeatureReady(String identifier) {
                super.ecgFeatureReady(identifier);
            }

            @Override
            public void accelerometerFeatureReady(String identifier) {
                super.accelerometerFeatureReady(identifier);
            }

            @Override
            public void ppgFeatureReady(String identifier) {
                super.ppgFeatureReady(identifier);
            }

            @Override
            public void ppiFeatureReady(String identifier) {
                super.ppiFeatureReady(identifier);
            }

            @Override
            public void biozFeatureReady(String identifier) {
                super.biozFeatureReady(identifier);
            }

            @Override
            public void hrFeatureReady(String identifier) {
                super.hrFeatureReady(identifier);

            }

            @Override
            public void disInformationReceived(String identifier, UUID uuid, String value) {
                super.disInformationReceived(identifier, uuid, value);
            }

            @Override
            public void batteryLevelReceived(String identifier, int level) {
                super.batteryLevelReceived(identifier, level);
            }

            @Override
            public void hrNotificationReceived(String identifier, PolarHrData data) {
                Log.d(TAG,identifier + "HR value11111: " + data.hr + " rrsMs: " + data.rrsMs + " rr: " + data.rrs + " contact: " + data.contactStatus + "," + data.contactStatusSupported);
                if( WristbandMoudle.this.polarHrData == null){
                    WritableMap params = Arguments.createMap();
                    params.putInt("hr",data.hr);
                    reactContext
                            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit("hrUpdateEvent", params);
                    handler.postDelayed(WristbandMoudle.this.runnable, 5000);

                }
                WristbandMoudle.this.polarHrData = data;

            }

            @Override
            public void polarFtpFeatureReady(String identifier) {
                super.polarFtpFeatureReady(identifier);
            }
        });
        try {
            api.connectToDevice(deviceId);
        } catch (PolarInvalidArgument polarInvalidArgument) {
            polarInvalidArgument.printStackTrace();
            promise.reject("","");
        }
    }

    @ReactMethod
    public void disconnectDeivce(String deviceId, Promise promise) {
        disConnectFlag = true;
        try {
            api.disconnectFromDevice(deviceId);
            handler.removeCallbacks(runnable);
            this.polarHrData = null;
            promise.resolve(true);
        } catch (PolarInvalidArgument polarInvalidArgument) {
            polarInvalidArgument.printStackTrace();
            promise.reject("","");
        }
    }


    private Runnable runnable=new Runnable(){
        @Override
        public void run() {
            WritableMap params = Arguments.createMap();
            params.putInt("hr",WristbandMoudle.this.polarHrData.hr);
            reactContext
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit("hrUpdateEvent", params);
            handler.postDelayed(this, 5000);
        }
    };

    @ReactMethod
    public void sendLocalNotify(ReadableArray array){
        int size = array.size();
        if(size == 0){
            return;
        }
        for (int i = 0; i < size; i++) {
            ReadableMap map = array.getMap(i);
            String deviceId = map.getString("deviceId");
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getReactApplicationContext(), "MyChannelId");
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mBuilder.setDefaults(Notification.DEFAULT_ALL);
            mBuilder.setAutoCancel(true);
            mBuilder.setContentTitle("情绪报警");
            mBuilder.setContentText(deviceId);
          //  mBuilder.setNumber(1);
            final Notification notify = mBuilder.build();
            //随机id 1000-2000
            final int notifyId = (int) (Math.random() * 1000 + 1000);
            NotificationManager nm = (NotificationManager)getCurrentActivity().getSystemService(getCurrentActivity().NOTIFICATION_SERVICE);
            if (nm != null) {
                nm.notify(notifyId, notify);
            };
        }
    }

}
