# cc-push-app
# 前端接口 
## 新增推送module:ccPush
```
const push=weex.requireModule('ccPush')
//获取个推下发的cid 同步调用 因为个推下发cid的过程是异步的 所以这个方法可能会获取到空 建议增加重试机制
push.getClientId()
```
## 新增全局事件
当消息通知被用户点击时，客户端会向前端发送globalEvent事件，传递推送参数
### 事件名:OnReceivePush
### 事件参数:param 该Key对应的值为端上收到的全量透传消息参数，类型为String
建议创建如下文件push.js,并将它应用到所有页面中
```
const globalEvent = weex.requireModule('globalEvent')

/**
 * 消息推送
 * options 客户端个推推送的所有消息
 */
globalEvent.addEventListener('OnReceivePush', function (options) {
   var pushParma=options.param
   var paramObj=JSON.parse(pushParma)
   //根据参数字段处理相应逻辑
})

```
# 服务端接口
## 消息通道
个推有消息通知和透传消息两个消息通道，消息通知无法携带参数且自定义能力较差，我们这里走透传消息payload的通道
## 透传消息体
端上约定的消息体如下:
```
{
  "title":“{消息通知标题}”，
  “content”:”{消息通知内容}“,
  “extraData”:“{额外参数，用于业务逻辑处理等 端上不关注 直接透传给前端 类型为JSON}”
 }
```

# 本地调试
## 通过Logcat查看端上收到的消息
在logcat中过滤GT-PUSH 查看cid下发和收到的消息内容
