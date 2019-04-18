# 流程
1. 客户端创建`MediaBrowserCompat`连接`MediaBrowserServiceCompat`服务端
2. `MediaBrowserServiceCompat`响应，回调`onGetRoot`检测包名，允许通过返回`rootId`
3. `MediaBrowserCompat`通过`MediaBrowserConnectionCallback`拿到`onGetRoot`返回`rootId`
4. `MediaBrowserCompat`拿着`rootId`去` mediaBrowser.subscribe`订阅`MediaBrowserServiceCompat`
5. `MediaBrowserServiceCompat`回调`onLoadChildren`，`onLoadChildren`获取到传过来的`rootId`,然后操作数据请求，获取这个`rootId`的数据，然后传回给客户端。
