//图片插件，
//by：wml
	function headOnc(filed){
		jQuery("#"+filed+"").click();
	}

	$(document).on("change",".filedImg", function() {
		
		var fiedId=$(this).attr("id");
		
		$.ajaxFileUpload({
            url : '/sleep/adminFileUpload',
            secureuri : false,
            fileElementId : $(this).attr("id"),
            dataType : 'text',
            data : {},
            success : function(data, status) {
            	$("#"+fiedId+"").prev().attr('src',data);
            	$("#"+fiedId+"").prev().attr('value',data);
            	$("#"+fiedId+"").next().attr('value',data);
            },
            error : function(data, status, e) {
           		console.log(data);
                alert('上传出错');
            }
        })

        return false;

	});
	
	//点击添加一个框，并没有什么逻辑
	$(document).on("click",".add_promise_li", function() {
		
		var divId=$(this).attr('div');
		
		
		$('#'+divId+'').append($("#photoTmpel").html());

	});
	
	//点击删除一个框，并没有什么逻辑
	$(document).on("click",".promise_deleteBt", function() {
		
		var src=$($(this).parent().prev().children()[0]).attr('src');
		
		var value=$($(this).parent().parent().prev()).val().replace(src+';','');
		
		$($(this).parent().parent().prev()).val(value);
		
		$(this).parent().parent().remove();

	});
	
	
	//点击删除一个框，并没有什么逻辑
	$(document).on("click",".promise_deleteBt1", function() {
		var src=$($(this).parent().prev().children()[0]).attr('src');
		
		var value=$($(this).parent().parent().parent().parent().children()[1]).val().replace(src+';','');
		
		$($(this).parent().parent().parent().parent().children()[1]).val(value);
		
		$(this).parent().parent().remove();

	});
	
	//点击删除一个框，并没有什么逻辑
	$(document).on("click",".promise_edit", function() {
		
		var wait=document.getElementsByName('waitPhoto');
    	if(wait.length>0){
    		for (var int = 0; int < wait.length; int++) {
    			$(wait[0]).attr('name','');
			}
    	}
		
		$(this).parent().parent().parent().parent().children()[0].click();
		$($(this).parent().parent().prev().children()[0]).attr('name','waitPhoto');
	});
	
	//点击删除一个框，并没有什么逻辑
	$(document).on("click",".promise_edit1", function() {
		
		var wait=document.getElementsByName('waitPhoto');
    	if(wait.length>0){
    		for (var int = 0; int < wait.length; int++) {
    			$(wait[0]).attr('name','');
			}
    	}
		
		$(this).parent().parent().parent().parent().parent().children()[0].click();
		$($(this).parent().parent().prev().children()[0]).attr('name','waitPhoto');
		
	});
	
	$(document).on("change",".filedImgs", function() {
		
		var fiedId=$(this).attr("id");
		
		$.ajaxFileUpload({
            url : '/sleep/adminFileUpload',
            secureuri : false,
            fileElementId : fiedId,
            dataType : 'text',
            data : {},
            success : function(data, status) {
            	$('#'+fiedId+'').next().val($('#'+fiedId+'').next().val()+data+';');
            	var wait=document.getElementsByName('waitPhoto');
            	if(wait.length>0){
            		
            		var src=$(wait[0]).attr('src');
            		
            		if(src!=undefined||src!=""){
            			console.log($('#'+fiedId+''));
            			console.log($('#'+fiedId+'').parent().children()[1]);
            			var value=$($('#'+fiedId+'').parent().children()[1]).val().replace(src+';','');
            			$($('#'+fiedId+'').parent().children()[1]).val(value);
            			var value1=$($('#'+fiedId+'').parent().children()[1]).val().replace(src,'');
            			$($('#'+fiedId+'').parent().children()[1]).val(value1);
            		}
            		
            		$(wait[0]).attr('src',data);
            	}
            },
            error : function(data, status, e) {
           		console.log(data);
                alert('上传出错');
            }
        })

        return false;

	});
	
	function initimages(divId,ulId){
		
		var imageVal=$($('#'+ulId+'').children()[1]).val();
		
		var imageVals=imageVal.split(";");
		
		if(imageVals.length>0){

			if(imageVals[0]!=""){
                $($('#'+ulId+'').children()[2]).remove();
            }

			for (var int = 0; int < imageVals.length; int++) {
				
				if(imageVals[int]!=""&&imageVals[int]!=undefined){
					$('#'+divId+'').append($("#photoTmpel").html());
					$($($($('#'+divId+'').children("li:last-child")[0]).children()[1]).children()[0]).attr('src',imageVals[int]);
				}
				
			}
		}
		
	}