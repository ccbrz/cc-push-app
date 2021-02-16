package com.eros.plugin.getui.ui;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.eros.framework.activity.AbstractWeexActivity;
import com.eros.framework.adapter.router.RouterTracker;
import com.eros.framework.manager.impl.GlobalEventManager;
import com.taobao.weex.WXSDKInstance;

public class ProxyActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        String pushData = intent.getStringExtra("pushData");
        if (pushData == null) {
            finish();
            return;
        }
        Activity activity = RouterTracker.peekActivity();
        GlobalEventManager.sPendingMsg = pushData;
        if (activity instanceof AbstractWeexActivity) {
            WXSDKInstance wxsDkInstance = ((AbstractWeexActivity) activity).getWXSDkInstance();
            if (wxsDkInstance != null) {
                GlobalEventManager.pushMessageIfNeed(wxsDkInstance);
            } else {
                openApp(this);
            }
        } else {
            openApp(this);
        }
        finish();
    }

    private static void openApp(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        String packageName = context.getPackageName();
        ComponentName cn = new ComponentName(packageName,
            "com.eros.wx.activity.SplashActivity");
        intent.setComponent(cn);
        context.startActivity(intent);
    }
}
