package com.eros.plugin.getui.module;

import com.alibaba.weex.plugin.annotation.WeexModule;
import com.eros.plugin.getui.manager.MyPushManger;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.common.WXModule;

@WeexModule(name = "ccPush", lazyLoad = true)
public class MyGetuiModule extends WXModule {

    @JSMethod(uiThread = false)
    public String getClientId() {
        return MyPushManger.getInstance().getClientId();
    }

    @JSMethod
    public void initPush() {
        MyPushManger.getInstance().initPush(mWXSDKInstance.getContext());
    }
}
