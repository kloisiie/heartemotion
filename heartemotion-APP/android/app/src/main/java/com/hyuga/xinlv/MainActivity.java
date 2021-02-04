package com.hyuga.xinlv;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;


import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.ReactRootView;
import com.hyuga.xinlv.utils.AndroidBug5497Workaround;
import com.mob.MobSDK;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.swmansion.gesturehandler.react.RNGestureHandlerEnabledRootView;
import org.devio.rn.splashscreen.SplashScreen;

public class MainActivity extends ReactActivity {

  private final static String TAG=MainActivity.class.getSimpleName();

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return "xinlv";
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    if(!BuildConfig.DEBUG){
      SplashScreen.show(this, R.style.SplashScreenTheme);
    }
    super.onCreate(savedInstanceState);
    QMUIStatusBarHelper.translucent(this);
    QMUIStatusBarHelper.setStatusBarLightMode(this);
    requestPermission();
    AndroidBug5497Workaround.assistActivity(this);
    initChannel();
  }

  private void requestPermission(){
    MobSDK.submitPolicyGrantResult(true,null);
    if(Build.VERSION.SDK_INT>=23){
      String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
      ActivityCompat.requestPermissions(this,mPermissionList,123);
    }

  }

  @Override
  protected ReactActivityDelegate createReactActivityDelegate() {
    return new ReactActivityDelegate(this, getMainComponentName()) {
      @Override
      protected ReactRootView createRootView() {
        return new RNGestureHandlerEnabledRootView(MainActivity.this);
      }
    };
  }

  private void initChannel(){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      NotificationManager nm = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
      if (nm != null){
        NotificationChannelGroup notificationChannelGroup = new NotificationChannelGroup("MyGroupId", "自定义通知组");
        nm.createNotificationChannelGroup(notificationChannelGroup);

        NotificationChannel notificationChannel = new NotificationChannel("MyChannelId", "自定义通知", NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setGroup("MyGroupId");
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        nm.createNotificationChannel(notificationChannel);
      }
    }
  }



}
