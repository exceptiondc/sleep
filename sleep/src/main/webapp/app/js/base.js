;

~function(W) {
	var handlers = { ios : [], android : [] },
		ajaxQueue = [],
		isCordovaLoaded = false,
		isCordovaHttpLoaded = false,
		isDeviceReady = false,
		isIOS = false,
		isWechatInstalled = false,
		isAndroid = false,
		isDebug = true,
		isLocal = true,
		isWeb = /https?/.test(location.protocol),
		isSuperDebug = false, 
		IOS = 'iOS',
		ANDROID = 'Android',
        SERVER_CONIFG = {
            server : (isLocal ? {
                scheme : 'http',					// 协议类型
                host   : '192.168.1.187',			// 服务器主机地址
                port   : '8080',						// 服务器端口

                path   : 'sleep'					// 项目路径
            } : {
                scheme : 'http',					// 协议类型
                host   : '39.106.27.146',	// 服务器主机地址
                port   : '80',						// 服务器端口
                path   : 'sleep'				// 项目路径
            })
        };

	
	function Base() {
		if (!this instanceof Base || !!W.base)
			return;

		this.init()
            .loadCordova()
            .onCordovaLoad(function() {
                this.afterCordovaLoad();
                this.loadJsLater();
                this.runHandlers();
            });
	};
	
	
	Base.getInstance = function() {
		if (W.base)
			return W.base;
		
		return W.base = new Base();
	};

	Base.isValidVar = function(v) {
		return typeof v != 'undefined' && v != null;
	};
	
	
	Base.parseJSON = function(jsonStr) {
		var obj = null, err = null;
		jsonStr = jsonStr || '{}';
		
		try {
			obj = JSON.parse(jsonStr);
		}
		catch(e) {
			try {
				obj = eval('(' + jsonStr + ')');
			}
			catch(e1) {
				err = e1;
			}
		}
		if (isDebug && W.base && err) {
			W.base.error(err);
		}

		return obj || {};
	};
	
	
	Base.prototype = Base.fn = {
		
		constructor : Base,
		
		platforms : {
			ios : IOS,
			android : ANDROID
		},
		
		ajaxBeforeHandler : [],
		
		cordovaListener : [],
		
		wechatCheckQueue : [],
		
		configure : {},
		
		user : {},
		
		LOGIN_TYPE : {
			SMS : 'sms',
			PWD : 'pwd',
			TOKEN : 'token'
		},
		
		loginFields : {
			sms : 'code',
			pwd : 'password',
			token : 'token'
		},
		
		
		isAndroid : function() {
			return isAndroid;
		},
		
		
		isIOS : function() {
			return isIOS;
		},
		
		
		isLocal : function() {
			return isLocal;
		},


        isWeb : function() {
            return isWeb;
        },


        isDebug : function() {
		    return isDebug;
        },
	
		
		isAllReady : function() {
			return isWeb || isDeviceReady && isCordovaLoaded && isCordovaHttpLoaded;
		},
		
		
		addHandler : function(type, handler) {
			if (typeof handler != 'function')
				return;
		
			if (!this.isAllReady()) {
				switch(type) {
					case IOS: 		handlers.ios.push(handler); 	break;
					case ANDROID:	handlers.android.push(handler);	break;
				}
			}
			else {
				if (isIOS && type == 'ios' || isAndroid && type == 'android') {
					handler.call(this);
				}
			}
			
			return this;
		},
		
		
		addAjaxTask : function() {
			ajaxQueue.push(Array.prototype.slice.call(arguments, 0));
			return this;
		},
		
		
		addAjaxBeforeHandler : function(handler) {
			if (handler) {
				this.ajaxBeforeHandler.push(handler);
			}
		},
		
		
		doAjaxBeforeHandler : function() {
			for (var i in this.ajaxBeforeHandler) {
				var handler = this.ajaxBeforeHandler[i];
				if (isFunction(handler)) {
					handler.apply(this, arguments[0]);
				}
			}
		},

		
		ajaxHandler : function(type, url, param, success, error, method) {
			success = success || function() {};
			error = error || function() {};
			method = method || 'post';
			type = isWeb && !isLocal ? 'jsonp' : type;
			
			var $this = this, onSuccess = function(resp) {
					var data = (false ? (isJSON || isJSONP ? Base.parseJSON(resp.data) : resp.data) : resp) || {};
					resp = resp || {};
					success.call($this, data, resp.status, resp.XMLHttpRequest);
					$this.debug('Executes a success callabck with data: ', data,
							' total took ' + getUsedTime() + 'ms.');
				},
				onError = function(data, b, c) {
					error.call($this, !isWeb ? data.XMLHttpRequest : data);
					$this.error('Failed to executing a request for "' + url + '?' + $.param(param)
							+ ' total took ' + getUsedTime() + 'ms.', arguments);
				},
				getUsedTime = function() {
					return new Date().getTime() - startTime;
				},
				isJSON = type == 'json',
				isJSONP = type == 'jsonp',
				startTime = new Date().getTime();

			param.device = isIOS ? IOS : ANDROID;
			this.doAjaxBeforeHandler(arguments);
			if (false) {
				if (typeof cordovaHTTP == 'undefined') {
					this.error('Not found cordovaHTTP!');
					return;
				}

				cordovaHTTP[method](url, param, {}, onSuccess, onError);
			}else{
				$.ajax({
					url : url,
					type : method,
					data : param,
					dataType : type,
					success : onSuccess,
					error : onError
				});
			}
		},

        /** 初始化 */
        init : function() {
            // 配置项目服务器信息
            this.setConfigure(SERVER_CONIFG);
            this.loadJsEarly();
            this.loadUser();
            return this;
        },
		
		
		afterCordovaLoad : function() {
			isDeviceReady = typeof device != 'undefined';
			isCordovaLoaded = typeof cordova != 'undefined';
			isCordovaHttpLoaded = typeof cordovaHTTP != 'undefined';
			isIOS = !!W.device && device.platform == IOS;
			isAndroid = !!W.device && device.platform == ANDROID;
		},

		
		runHandlers : function() {
			var hds = isIOS ? handlers.ios : handlers.android,
				aq = ajaxQueue;
		
			for (var key in hds) {
				var hd = hds[key];
				typeof hd == 'function' && hd.call(this);
			}
		
			for (var key in aq) {
				var args = aq[key];
				if (args == null || args.constructor != Array)
					continue;
		
				this.ajaxHandler.apply(this, args);
			}
			
			this.wechatHandler();
		},
		
		
		loadCordova : function() {
			if (!isWeb && !W.cordova) {
			    var basePath = location.pathname.substring(0, location.pathname.indexOf('www') + 4);
				var jslist = ['cordova.js', 'cordova_plugins.js'];
				for (var i = 0; i < jslist.length; i++) {
					$('head').append('<script src="' + basePath + jslist[i] + '" />');
				}
			}

			var self = this,
				timer = setInterval(function() {
				if (!isWeb && (!Base.isValidVar(W.cordova) || !Base.isValidVar(W.device)
						|| !Base.isValidVar(W.cordovaHTTP) || !Base.isValidVar(W.device.platform)
						|| self.cordovaListener.length == 0))
					return;

				if (!isWeb) {
					var lis = self.cordovaListener;
					for (var idx in lis) {
					    try {
                            lis[idx] && lis[idx].call(self);
                        }
                        catch(e) {
					        self.error(e);
                        }
					}
				}
				clearInterval(timer);
			}, 100);
			
			return this;
		},
		
		
		onCordovaLoad : function(lt) {
			if (!this.isAllReady())
				lt && this.cordovaListener.push(lt);
			else
				lt && lt.call(this);
		},


		log : function() {
			var type = arguments[0];
			if (!isDebug || !console || !console[type]) return;
		
			console[type].apply(console, Array.prototype.concat.apply(['[' + type.toUpperCase() + ']'],
					Array.prototype.slice.call(arguments, 1)));
		},


		debug : function() {
			this.log.apply(null, Array.prototype.concat.apply(['debug'], Array.prototype.slice.call(arguments, 0)));
		},


		error : function() {
			this.log.apply(null, Array.prototype.concat.apply(['error'], Array.prototype.slice.call(arguments, 0)));
		},
		
	
		getFileName : function() {
			var path = location.pathname.split('/');
			return path ? path[path.length - 1] : '';
		},
		
		
		onWechatInstalled : function(cb) {
			if (isWechatInstalled) {
				cb && cb();
			}else if (!this.isAllReady()){
				cb && this.wechatCheckQueue.push({success : cb});
			}
			
			return this;
		},
		
		
		onWechatNotInstall : function(cb) {
			if (!this.isAllReady()) {
				cb && this.wechatCheckQueue.push({fail : cb});
			} else {
				if (!isWechatInstalled)
					cb && cb();
			}
			
			return this;
		},
		
		
		wechatHandler : function(cb) {
            if (!Base.isValidVar(W.Wechat)) return;
			
			var $this = this, res = function(res) {
				isWechatInstalled = res == 1;
				$.each($this.wechatCheckQueue, function(k, v) {
					var callback = isWechatInstalled ? v.success : v.fail;
					callback && callback();
				});
			};
			Wechat.isInstalled(res, res);
		},
		
		
		onEvent : function(event, listener) {
			if (!event || typeof listener != 'function')
				return this;
			
			if (W.attachEvent)
				W.attachEvent(event, listener);
			else
				W.addEventListener(event, listener, false);
			
			return this;
		},
		
		
		offEvent : function(event, lis) {
			if (!event || typeof lis != 'function')
				return this;
			
			if (W.detachEvent)
				W.detachEvent(event, lis);
			else
				W.removeEventListener(event, lis);
			
			return this;
		},
		
		/** 获取项目URL */
		getProjectURL : function() {
			var server = this.configure.server || {};
			return server.scheme + '://' + server.host + ':' + server.port + '/' + server.path;
		},
		
		
		jsonp : function(url, param, success, error) {
			this.ajax('jsonp', 'get', url, param, success, error);
		},


		jsonGet : function(url, param, success, error) {
			this.ajax('json', 'get', url, param, success, error);
		},
		
		
		jsonPost : function(url, param, success, error) {
			this.ajax('json', 'post', url, param, success, error);
		},
	
	
		ajaxGet : function(url, param, success, error) {
			this.ajax('text', 'get', url, param, success, error);
		},
		
		
		ajaxPost : function(url, param, success, error) {
			this.ajax('text', 'post', url, param, success, error);
		},

		
		ajax : function(dataType, method, url, param, success, error) {
			url = this.getProjectURL() + (url.indexOf('/') == 0 ? '' : '/') + url;
			var params = param ? param : {};
			
			if (!this.isAllReady())
				this.addAjaxTask(dataType, url, params, success, error, method);   
			else
				this.ajaxHandler(dataType, url, params, success, error, method);
		},
		
		/** 添加或修改配置参数 */
		setConfigure : function(configure) {
			if (configure && ({}).toString.call(configure) == '[object Object]') {
				for (var k in configure) {
					this.configure[k] = configure[k];
				}
			}
		},
		
		/** 获取配置参数 */
		getConfigure : function() {
			return this.configure;
		},

        /** 加载用户信息 */
        loadUser : function() {
            this.user = Base.parseJSON(sessionStorage.user);
        },
		
		/** 用户是否登录 */
		isLogin : function() {
			return !!this.user && !!this.user.id;
		},
		
		/** 用户是否实名认证 */
		isIdentity : function() {
			if(!this.user.isIdCard||this.user.isIdCard=='否'){
				location.href='identity.html';
				return false;
			}
			return true;
		},
		
		/** 设置是否在用户未登录时跳转至登录页面 */
		setNotLoginRedirect : function(isRedirect) {
			if (isRedirect && !isLogin()) {
				this.gotoLogin();
			}
		},
		
		/** 检查如果为未登录状态便调用传入的callback函数 */
		checkIfNotLogin : function(callback) {
			if (!this.isLogin()) {
				try {
					callback && callback();
				}
				catch(e) {
					this.error(e);
				}
			}
		},

		/** 跳转至登录页面 */
		gotoLogin: function(backUrl) {
			location.href = this.getRootPath() + '/login.html?backUrl='
                    + this.getEncodedURL(backUrl || location.href);
		},

		/** 获取编码后的URL */
		getEncodedURL : function(url) {
			return encodeURIComponent(url);
		},

		/** 获取用户信息 */
		getUser : function() {
			return this.user || {};
		},

        /** 获取用户ID */
        getUserId : function() {
		    return this.getUser().id;
        },

		/** 用户信息持久化 */
		storeUser : function(user) {
			sessionStorage.user = JSON.stringify(user || this.user || {});
			localStorage.userInfo = JSON.stringify({
				username : user.phone,
				token : user.token,
				loginTime : new Date().getTime()
			});
		},
		
		/** 使用账号密码进行用户登录并获取用户信息 */
		userLogin : function(name, fieldValue, type, success, fail, error) {
			var self = this,
				param = {
					phone : name,
					loginType : type,
					type : 1
				};
			
			param[this.loginFields[type]] = fieldValue;
			this.jsonGet('/app/appUsers/login/json', param, function(data) {
			    if (data.status == 'success') {
                    callFunction(success, self, [data.data, data]);
                }
                else {
                    callFunction(fail, self, [data]);
                }
			}, function() {
				callFunction(error, self, []);
			});
		},
		
		/** 刷新用户信息/使用用户凭证进行登陆 */
		refreshUser : function(complete, fail, error) {
			var self = this,
				userInfo = this.getStoredUserInfo();

			this.userLogin(userInfo.username, userInfo.token, this.LOGIN_TYPE.TOKEN, function(user) {
				self.storeUser(user);
				self.loadUser();
				callFunction(complete, self, [user]);
			}, fail, error);
		},
		
		/** 使用用户凭证进行登陆 */
		loginByToken : null,

		/** 用户退出登录并清理登录信息 */
		userLogout : function() {
		    this.clearStoredUserInfo();
			this.user = null;
		},
		
		/** 获取存储的用户登录信息 */
        getStoredUserInfo : function() {
            return Base.parseJSON(localStorage.userInfo);
        },

        /** 清除存储的用户登录信息 */
        clearStoredUserInfo : function() {
            delete sessionStorage.user;
            delete localStorage.userInfo;
        },

        /** 获取项目根路径 */
        getRootPath : function() {
            return isWeb ? '/' + this.getConfigure().server.path + '/' + 'app'
                        : location.pathname.substring(0, location.pathname.indexOf('www') + 3)
        },

        /** 加载Js脚本 */
        loadJs : function(basePath, jss) {
            if (({}).toString.call(jss) !== '[object Array]') {
                jss = [jss];
            }

            for (var i in jss) {
                var js = jss[i];
                $('head').append('<script src="' + basePath + '/' + js + '">' + '</' + 'script>' );
            }
        },

        /** 加载需要的Js文件在加载Cordova之前 */
        loadJsEarly : function() {
            var basePath = this.getRootPath(),
                jss = ['js/common.js'];

            this.loadJs(basePath, jss);
        },

        /** 加载需要的Js文件在加载Cordova之后 */
        loadJsLater : function() {
            var basePath = this.getRootPath(),
                jss = ['js/jpush.helper.js'];

            this.loadJs(basePath, jss);
        }
	};

	Base.fn.loginByToken = Base.fn.refreshUser;
	Base.getInstance();
	W.Base = Base;

	// 添加于ajax发送前的handler
	base.addAjaxBeforeHandler(function(type, url, param) {
		param.token = this.getStoredUserInfo().token;
		this.debug('Executes a request for "' + url + '?' + $.param(param) + '".');
	});

	// 添加对于IOS平台的相关处理
	base.addHandler(base.platforms.ios, function() {
		var file = base.getFileName(),
			overlay = false;
		
		StatusBar.overlaysWebView(overlay);
		StatusBar.show();
	});

	// 使用储存的用户登录信息进行登录
    if (!base.isLogin() && !isEmptyObject(base.getStoredUserInfo())) {
        base.loginByToken(null, function() {
            base.clearStoredUserInfo();
        });
    }

    base.onCordovaLoad(function() {
        // 加载通用处理
        base.loadJs(base.getRootPath(), 'js/init.all.js');
    });

}(window);


//拦截跳转login.html
if(!base.isLogin()){
    // 不需要登录即可访问的页面
    var arrs=[
        'index.html','search.html','hotel_lists.html','hotel_details.html','order_con.html',
        'hotel_facilities.html','login.html', 'register.html', 'user_comment.html',
        'login2.html','retrieve_password.html'
    ];

    for(var i=0;i<arrs.length;i++){
        if(location.href.indexOf(arrs[i])>0){
            break;
        }else{
            if(i==arrs.length-1){
                base.gotoLogin();
            }

        }
    }

}



