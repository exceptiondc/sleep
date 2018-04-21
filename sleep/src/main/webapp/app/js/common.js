/** JS 公共库 */

function callFunction(func, ctx, args) {
    return isFunction(func) && func.apply(ctx || {}, args);
}


function isFunction(func) {
    return ({}).toString.call(func) == '[object Function]';
}


function isObject(func) {
    return ({}).toString.call(func) == '[object Object]';
}


function isArray(func) {
    return ({}).toString.call(func) == '[object Array]';
}


function isNumber(str) {
    return /^\d+(\.\d+)?$/.test(new String(str));
}


function isEmptyObject(obj) {
    if (isObject(obj)) {
        for (var k in obj) {
            return false;
        }
    }

    return true;
}


function mergeMap(map1, map2) {
    if (isObject(map1) && isObject(map2)) {
        var mergedMap = {},
            mapArr = [map1, map2];

        for (var idx in mapArr) {
            var map = mapArr[idx];
            for (var k in map) {
                mergedMap[k] = map[k];
            }
        }

        return mergedMap;
    }

    return {};
}


/** 显示一个浮动消息提示 */
function showMsg(msg, time) {
    layer && layer.open({ skin : 'msg', content : msg, time: time || 2, shadeClose: false });
}

/** 显示一个带确认按钮的提示框 */
function showAlert(msg, btn, end) {
    layer && (layer.open({ content : msg, btn : btn || '确定', shadeClose: false, end: end  }), true)
    || alert(msg);
}

/** 显示一个带多个按钮的提示框 */
function showConfirm(msg, btns, yes, no) {
    layer && (layer.open({
        content: msg,
        btn: btns,
        yes: yes,
        no: no
    }), true) || ~function() {
        callFunction(this, confirm(msg) ? yes : no, []);
    }();
}

/** 显示一个带遮罩的加载动画 */
var __loadingIndex;
function showLoading(msg, time, end) {
    layer && (__loadingIndex = layer.open({
        type: 2,
        content: msg,
        time: time || 30,
        shadeClose: false,
        shade: 'background-color: rgba(255,255,255,.5)',
        end: end
    }), true) || ~function() {
        alert(msg);
    }();
}

/** 隐藏加载动画 */
function hideLoading() {
    if (isNumber(__loadingIndex)) {
        layer && layer.close(__loadingIndex);
    }
}

/** 根据name获取表单域的值 */
function getInputValue(name) {
    var $input = $('[name="' + name + '"]'),
        value = $input.val();

    switch(($input[0] || {}).type) {
        case 'checkbox':
            value = $input.filter(function() {
                return this.checked;
            }).map(function(i, e) {
                return e.value;
            }).get().join(',');
            break;

        case 'radio':
            value = $input.filter(function() {
                return this.checked;
            }).val();
            break;
    }

    return value || '';
}

// 通用下拉加载实现
pageIndex = 1;
onLoadMore = function(){};
hasMore = true;
function loadMore() {
    onLoadMore && onLoadMore();
}

function setHasMore(more) {
    hasMore = more;
}

function loadComplete(finish) {
    hasMore = finish;
    lock = false;
}

~function() {
    var func = function () {
        lock = false,
            $(window).scroll(function (e) {
                var $w = $(window);
                if (hasMore && $('body').height() - $w.scrollTop() < $w.height() * 1.2) {
                    if (!lock) {
                        lock = true;
                        loadMore && loadMore();
                    }
                }
            });
    }

    typeof $ != 'undefined' && $(func)
}();

/** 格式化字符串 */
String.prototype.formatStr = function() {
    var regex = /\\$[a-zA-Z_]\w*/g,
        regexQuotes = /([$\/{}()?*.\[\]+^])/,
        args = arguments,
        matched = [],
        str = this,
        tempArr = [],
        isKvValue = false;

    while((tempArr = regex.exec(this)) != null) {
        matched.push(tempArr[0]);
    }

    if (typeof args[0] == 'object' && args[0].constructor == Object) {
        var i = 0;
        for (var key in args[0]) {
            matched[i] = '$' + key;
            args[i++ + 1] = args[0][key];
        }
        args.length = i;
        isKvValue = true;
    }

    var value;
    for (var i = 0; i < Math.min(matched.length, args.length); i++) {
        str = str.replace(new RegExp('\\' + matched[i], 'g'),
            args[isKvValue ? i + 1 : i] == null ? '' : args[isKvValue ? i + 1 : i]);
    }

    return str;
};


Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };

    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));

    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));

    return fmt;
}


//计算乘法
function accMul(arg1, arg2) {
    var m = 0,
        s1 = arg1.toString(),
        s2 = arg2.toString();
    try {
        m += s1.split(".")[1].length
    } catch (e) {}
    try {
        m += s2.split(".")[1].length
    } catch (e) {}
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
}
//计算加法
function accAdd(arg1, arg2) {
    var r1, r2, m, c;
    try {
        r1 = arg1.toString().split(".")[1].length;
    } catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    } catch (e) {
        r2 = 0;
    }
    c = Math.abs(r1 - r2);
    m = Math.pow(10, Math.max(r1, r2));
    if (c > 0) {
        var cm = Math.pow(10, c);
        if (r1 > r2) {
            arg1 = Number(arg1.toString().replace(".", ""));
            arg2 = Number(arg2.toString().replace(".", "")) * cm;
        } else {
            arg1 = Number(arg1.toString().replace(".", "")) * cm;
            arg2 = Number(arg2.toString().replace(".", ""));
        }
    } else {
        arg1 = Number(arg1.toString().replace(".", ""));
        arg2 = Number(arg2.toString().replace(".", ""));
    }
    return (arg1 + arg2) / m;
}


/** 启用ajax请求时显示loading加载层 */
function enableAjaxLoading() {
    var before = function() {
            showLoading('', 9999);
        },
        after = function() {
            hideLoading();
        };

    $.ajaxSetup({
        beforeSend : before,
        complete : after
    });
}

/** 禁用ajax请求时显示loading加载层 */
function disableAjaxLoading() {
    $.ajaxSetup({
        beforeSend : $.noop,
        complete : $.noop
    });
}