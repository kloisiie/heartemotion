package com.hyuga.xinlv;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyReceiver extends JPushMessageReceiver {

    /**
     * TODO 连接极光服务器
     */
    @Override
    public void onConnected(Context context, boolean b) {
        super.onConnected(context, b);
        Log.e("连接极光服务器", "onConnected");
    }

    /**
     * TODO 注册极光时的回调
     */
    @Override
    public void onRegister(Context context, String s) {
        super.onRegister(context, s);
        Log.e("注册极光时的回调", "onRegister" + s);
        if (s != null) {
            SharedPreferences sp = context.getSharedPreferences("react-native-sharepreference",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("clientId_key", s);
            editor.commit();
        }
    }

    /**
     * TODO 注册以及解除注册别名时回调
     */
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onAliasOperatorResult(context, jPushMessage);
        Log.e("注册以及解除注册别名时回调", jPushMessage.toString());
    }

    /**
     * TODO 接收到推送下来的通知
     * 可以利用附加字段（notificationMessage.notificationExtras）来区别Notication,指定不同的动作,附加字段是个json字符串
     * 通知（Notification），指在手机的通知栏（状态栏）上会显示的一条通知信息
     */
    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageArrived(context, notificationMessage);
        Log.e("接收到推送下来的通知", notificationMessage.toString());
    }

    /**
     * TODO 打开了通知
     * notificationMessage.notificationExtras(附加字段)的内容处理代码
     * 比如打开新的Activity， 打开一个网页等..
     */
    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage notificationMessage) {
        super.onNotifyMessageOpened(context, notificationMessage);
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        Log.e("打开了通知", notificationMessage.notificationExtras);
    }

    /**
     * TODO 接收到推送下来的自定义消息
     * 自定义消息不是通知，默认不会被SDK展示到通知栏上，极光推送仅负责透传给SDK。其内容和展示形式完全由开发者自己定义。
     * 自定义消息主要用于应用的内部业务逻辑和特殊展示需求
     */
    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        super.onMessage(context, customMessage);
        // 收到消息 显示通知
        Log.d("接收到推送下来的自定义消息", "onMessage: ");
//        processCustomMessage(context, customMessage.extra);
    }

    //通知
    private void processCustomMessage(Context context, String message) {

        String channelID = "1";
        String channelName = "channel_name";

        // 跳转的Activity
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        //适配安卓8.0的消息渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(context, channelID);

        notification.setAutoCancel(true)
                .setContentText(message)
                .setContentTitle("我是Title")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent);

        notificationManager.notify((int) (System.currentTimeMillis() / 1000), notification.build());
    }
}