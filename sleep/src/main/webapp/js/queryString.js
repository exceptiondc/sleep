/**
/**
 * 
 */
function queryString(name) {
	
	
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r =window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}




function request(url,paras) {
    var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
    var paraObj = {}
    for (i = 0; j = paraString[i]; i++) {
        paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);
    }
    var returnValue = paraObj[paras.toLowerCase()];
    if (typeof (returnValue) == "undefined") {
        return "";
    } else {
        return (unescape(returnValue.replace(/\\u/gi, '%u')));
    }
}


function setParam(_arg, _val){
	    var pattern=_arg+'=([^&]*)';
	    var replaceText=_arg+'='+_val, url;
	    if(arguments && arguments[2]) {
	    	url = arguments[2];
	    } else {
	    	url = window.location.href;
	    }
	    
	    if(url.match(pattern)){
	        var tmp=url.replace(eval('/('+ _arg+'=)([^&]*)/gi'),replaceText);
	        return tmp;
	    }else{
	        if(url.match('[\?]')){
	            return url+'&'+replaceText;
	        }else{
	            return url+'?'+replaceText;
	        }
	    }
	    return url+'\n'+_arg+'\n'+_val;
	}


if (!String.prototype.includes) {
  String.prototype.includes = function() {'use strict';
    return String.prototype.indexOf.apply(this, arguments) !== -1;
  };
}