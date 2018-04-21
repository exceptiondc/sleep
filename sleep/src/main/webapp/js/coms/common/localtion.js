

function initCity(localtion,provinceId,cityId){
	//初始化
	$('#'+localtion+'').html("<div style=\"display:inline-block;\"><div style=\"display:inline-block;\" ><select id=\"city\"><option value=\"\">请选择市</option></select></div></div>");
        $.ajax({
        url : '/yingpu/system/city/list/json',
        secureuri : false,
        dataType : 'json',
        data : {},
        success : function(data, status) {
            var obj1 = eval(data.data);

            var pro="<option value=\"\">请选择市</option>";

            for (var int = 0; int < obj1.length; int++) {

                pro += "<option value='"+obj1[int].id+"'>"+obj1[int].name+"</option>";

            }

            jQuery("#city").html(pro);
        },
        error : function(data, status, e) {
            console.log(data);
        }
		});

}




