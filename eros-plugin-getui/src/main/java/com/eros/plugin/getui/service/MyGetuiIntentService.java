package com.eros.plugin.getui.service;

import android.content.Context;
import android.util.Log;

import com.eros.plugin.getui.manager.MyPushManger;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */
public class MyGetuiIntentService extends GTIntentService {

    @Override
    public void onReceiveServicePid(Context context, int i) {
        Log.d(TAG, "onReceiveServicePid -> " + i);
    }

    @Override
    public void onReceiveClientId(Context context, String s) {
        Log.d(TAG, "onReceiveClientId -> " + s);
        MyPushManger.getInstance().setClientId(s);
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {
        String appid = gtTransmitMessage.getAppid();
        String taskid = gtTransmitMessage.getTaskId();
        String messageid = gtTransmitMessage.getMessageId();
        byte[] payload = gtTransmitMessage.getPayload();
        String pkg = gtTransmitMessage.getPkgName();
        String cid = gtTransmitMessage.getClientId();

        // 第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
        boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
        Log.d(TAG, "call sendFeedbackMessage = " + (result ? "success" : "failed"));

        Log.d(TAG, "onReceiveMessageData -> " + "appid = " + appid + "\ntaskid = " + taskid + "\nmessageid = " + messageid + "\npkg = " + pkg
            + "\ncid = " + cid);

        if (payload == null) {
            Log.e(TAG, "receiver payload = null");
        } else {
            String data = new String(payload);
            Log.d(TAG, "receiver payload = " + data);
            MyPushManger.getInstance().handlePayload(context, data);
        }
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
        Log.d(TAG, "onReceiveOnlineState -> " + online);
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {

    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage onNotificationMessageArrived) {
        Log.d(TAG, "onNotificationMessageArrived -> " + onNotificationMessageArrived);
    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {
        Log.d(TAG, "onNotificationMessageClicked -> " + gtNotificationMessage);
    }
}
