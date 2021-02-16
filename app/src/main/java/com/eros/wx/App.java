package com.eros.wx;

import android.app.Application;

import com.eros.framework.BMWXApplication;
import com.eros.plugin.getui.manager.MyPushManger;

/**
 * Created by Carry on 2017/8/23.
 */

public class App extends BMWXApplication {
    public Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        MyPushManger.getInstance().initPush(this);
        MyPushManger.getInstance().setIconId(R.mipmap.ic_launcher);

    }

}
