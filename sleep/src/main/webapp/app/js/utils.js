function accDiv(arg1,arg2){ 
var t1=0,t2=0,r1,r2; 
try{t1=arg1.toString().split(".")[1].length}catch(e){} 
try{t2=arg2.toString().split(".")[1].length}catch(e){} 

with(Math){ 
r1=Number(arg1.toString().replace(".","")) 
r2=Number(arg2.toString().replace(".","")) 
return (r1/r2)*pow(10,t2-t1); 
} 
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


var pageIndex=1,pageCount=1;
function go(act) {
	pageIndex = parseInt(pageIndex) || 1;
	switch(act) {
		case 'home':
		case 'tail':
			pageIndex = act == 'home' ? 1 : pageCount;
			break;
			
		case 'next':
		case 'prev':
			pageIndex += act == 'next' ? 1 : -1;
			break;
			
		default:
			pageIndex = act;
	}
	
	pageIndex = Math.max(1, Math.min(pageIndex, pageCount));
	typeof loadList == 'function' && loadList();
}


/**
 * 调用这个生成分页导航，
 * @param pageIndex 页码
 * @param pageCount 最大页数
 * @sel 元素选择器
 */
function generatePaginationNav(pageIndex, pageCount, sel) {
	var html = '', btnNum = 7, halfNum = btnNum >> 1,
	pe = Math.min(pageCount, pageIndex + halfNum),
	ps = pageIndex + halfNum > pe ? Math.max(1, pe - btnNum + 1) : Math.max(1, pageIndex - halfNum);
	
	for (var i = 0; i < btnNum && ps + i <= pageCount ; i++) {
		html += '<a href="javascript:void(go($index))">$index</a>'.formatStr({index:ps + i});
	}
	
	$(sel).html(html)
		  .find('a').filter(function(){ return $(this).html() == pageIndex; }).addClass('active');
}


var gUserProfile = {},
	 gIsProfileLoaded = false,
	 gPromise = {
		 promise : {},
		 done : function(callback) {
			 this.promise = callback;
		 }
	},
	loadUserProfile = function(callback, isRefresh) {
	 	var promise = Object.create(gPromise);
		var invoke = function(c, d) {
			typeof c == 'function' && c(d);
		};
		
		isRefresh = isRefresh || callback == true; 
		if (gIsProfileLoaded && !isRefresh) {
			setTimeout(function() {
		 		invoke(callback || promise.promise, gUserProfile);
			}, 100);
			return promise;
		}
		
		 $.get('/yingpu/pc/myaccount/accountproile', {}, function(data) {
			 var profile = data.data && data.data[0] || null;
			 if (profile) {
				 gUserProfile = profile;
				 gIsProfileLoaded = true;
				 invoke(callback || promise.promise, gUserProfile);
			 }
		 }, 'json');
		 
		 return promise;
	};	
	
	
function useLoadingShade() {
	var showShade = function() {
			layer && (layerIndex = layer.load(2, { shade: [0.4, '#fff'],
				offset: [(($(window).height() - 32) >> 1) + 'px', (($(window).width() - 32) >> 1) + 'px']
			}));
		},
		hideShade = function() {
			layer && layer.close(layerIndex);
		},
		layerIndex;
		
	$ && $.ajaxSetup({
		beforeSend : showShade,
		complete : hideShade,
		error : hideShade
	});
}


function useLoadingShade() {
	var showShade = function() {
			layer && (layerIndex = layer.load(2, { shade: [0.4, '#fff'],
				offset: [(($(window).height() - 32) >> 1) + 'px', (($(window).width() - 32) >> 1) + 'px']
			}));
		},
		hideShade = function() {
			layer && layer.close(layerIndex);
		},
		layerIndex;
		
	$ && $.ajaxSetup({
		beforeSend : showShade,
		complete : hideShade,
		error : hideShade
	});
}


function cancelLoadingShade() {
	var func = function() {};
	$ && $.ajaxSetup({
		beforeSend : func,
		complete : func,
		error : func
	});
}


function fmtDate(obj){
    var date =  new Date(obj);
    var y = 1900+date.getYear();
    var m = "0"+(date.getMonth()+1);
    var d = "0"+date.getDate();
    return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length);
}


function getEncodedCurrentURL() {
	return encodeURIComponent(location.href);
}

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

onLoadMore = function(){};
hasMore = true;
function loadMore() {
	onLoadMore && onLoadMore();
}

function loadComplete(finish) {
	hasMore = finish;
	lock = false;
}

var func = function() {
	lock = false;
	$(window).scroll(function() {
		var $w = $(window);
		if (hasMore && $('body').height() - $w.scrollTop() < $w.height() * 1.2) {
			if (!lock) {
				console.log(lock);
				lock = true;
				loadMore && loadMore();
			}
		}
	});
};

$ && $(func) || (window.onload = func);


