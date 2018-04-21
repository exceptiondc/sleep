
// 极光推送初始化
if (!base.isWeb()) {
    JPushHelper.setDebugMode(base.isDebug());
    JPushHelper.init();
    /*JPushHelper.isPushStopped(function (r) {
        if (r != 0) {
            JPushHelper.resumePush();
        }
    });*/
    JPushHelper.registerReceiveMessage(function(msg) {
        alert(JSON.stringify(msg));
    });
    JPushHelper.registerOpenNotificationListener(function(msg) {
        alert(JSON.stringify(msg));
    });
    JPushHelper.registerReceiveNotification(function(msg) {
        alert(JSON.stringify(msg));
    });
}





























