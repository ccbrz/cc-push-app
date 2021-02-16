package com.eros.plugin.getui.manager;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.eros.framework.manager.ManagerFactory;
import com.eros.framework.manager.impl.ParseManager;
import com.eros.plugin.getui.BuildConfig;
import com.eros.plugin.getui.model.PayloadEntity;
import com.eros.plugin.getui.ui.ProxyActivity;
import com.igexin.sdk.IUserLoggerInterface;
import com.igexin.sdk.PushManager;

public class MyPushManger {
    private String sClientId = "";
    private int iconId = -1;
    private final Object sLock = new Object();
    private static final Handler mUiHander = new Handler(Looper.getMainLooper());

    public void initPush(Context context) {
        PushManager.getInstance().initialize(context);
        if (BuildConfig.DEBUG) {
            //切勿在 release 版本上开启调试日志
            PushManager.getInstance().setDebugLogger(context, new IUserLoggerInterface() {

                @Override
                public void log(String s) {
                    Log.i("pushDebugger", "msg:" + s);
                }
            });
        }
    }

    public String getClientId() {
        synchronized (sLock) {
            return sClientId;
        }
    }


    public void setClientId(String cid) {
        synchronized (sLock) {
            sClientId = cid;
        }
    }

    public void handlePayload(final Context context, final String payload) {
        mUiHander.post(new Runnable() {
            @Override
            public void run() {
                ParseManager parseManager = ManagerFactory.getManagerService(ParseManager.class);
                PayloadEntity payloadEntity = parseManager.parseObject(payload, PayloadEntity.class);
                //在后台  显示通知
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setContentTitle(payloadEntity.getTitle())
                    .setSmallIcon(iconId)
                    .setTicker(payloadEntity.getTitle())
                    .setContentText(payloadEntity.getContent()).setAutoCancel(true).setDefaults(Notification.DEFAULT_LIGHTS |
                        Notification.DEFAULT_VIBRATE);
                Intent resultIntent = new Intent(context, ProxyActivity.class);
                resultIntent.putExtra("pushData", payload);
                PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
                    0, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotifyMgr = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
                mNotifyMgr.notify(1, mBuilder.build());
            }
        });
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public static MyPushManger getInstance() {
        return Holder.sINSTANCE;
    }

    private static class Holder {
        static MyPushManger sINSTANCE = new MyPushManger();
    }
}
