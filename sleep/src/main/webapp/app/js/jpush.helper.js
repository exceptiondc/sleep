/** JPush 辅助类 */

~function(window) {

    var base = window.base,
        jPushHelper = function () {},
        JPushHelper = {};

    if (!window.JPush) {
        return;
    }

    JPushHelper = new jPushHelper();
    for (var key in JPush) {
        JPushHelper[key] = (function(name) {
            return function() {
                JPush[name].apply(JPush, arguments);
            }
        })(key);
    }


    /** 初始化并开启极光推送 */
    JPushHelper.init = function() {
        window.JPush.init();
        return this;
    };

    /** 关闭极光推送 */
    JPushHelper.stopPush = function() {
        window.JPush.stopPush();
        return this;
    };

    /** 恢复极光推送 */
    JPushHelper.resumePush = function() {
        window.JPush.resumePush();
        return this;
    };

    /**
     * 为程序用户设置别名
     * @param sequence 用户自定义的操作序列号
     * @param alias 别名
     */
    JPushHelper.setAlias = function(sequence, alias) {
        window.JPush.setAlias({ sequence: sequence, alias: alias }, function(result) {
            base.debug(result);
        }, function(error) {
            base.debug(error);
        });
        return this;
    };

    /**
     * 为程序用户设置标签
     * @param sequence 用户自定义的操作序列号
     * @param tags 标签数组
     */
    JPushHelper.setTags = function(sequence, tags) {
        window.JPush.setTags({ sequence: sequence, alias: tags }, function(result) {
            base.debug(result);
        }, function(error) {
            base.debug(error);
        });
        return this;
    };

    /**
     * 注册点击通知进入应用事件的监听器
     * @param listener 事件监听器
     */
    JPushHelper.registerOpenNotificationListener = function(listener) {
        document.addEventListener('jpush.openNotification', listener, false);
        return this;
    };

    /**
     * 注册收到消息通知事件的监听器
     * @param listener 消息监听器
     */
    JPushHelper.registerReceiveNotification = function(listener) {
        document.addEventListener('jpush.receiveNotification', listener, false);
        return this;
    };

    /**
     * 注册收到自定义消息通知事件的监听器
     * @param listener 消息监听器
     */
    JPushHelper.registerReceiveMessage = function(listener) {
        document.addEventListener('jpush.receiveMessage', listener, false);
        return this;
    };

    /** 恢复极光推送 */
    JPushHelper.resumePush = function() {
        window.JPush.resumePush();
    };


    // checkJPush();
    window.JPushHelper = JPushHelper;

}(window);