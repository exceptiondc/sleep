/**
 * appuser 页面使用javascript
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 14:42:38
 */
var userId = null;

function seeFenxiao(id){
    userId = id;
    $.post('/yingpu/system/appuser/fenxiaoList/json', {"userId":id}, function(str){
//        console.log(str.data);
        var list = getList(str);
        console.log(list);
        var index =layer.open({
            type: 1,
            skin: 'layui-layer-demo', //样式类名
            closeBtn: 1, //显示关闭按钮
            anim: 2,
            id:'layerOpen',
            resize :false,
            area: ['800px', '800px'],
            title:'所有下级用户',
            shadeClose: true, //开启遮罩关闭
            content: list, //注意，如果str是object，那么需要字符拼接。
            success: function(layero, index){

                open(index);
            }
        });
    });
}

function getList(str) {
    var list=" <table id=\"fenxiaoDataTable\" border=\"1\" class=\"table table-striped table-bordered table-hover\" style='width: 800px;margin-top: 10px' ><thead><tr>" +
        "<th>真实姓名</th><th id=\"th_phone\" >手机号</th><th>级别</th><th>邀请人数</th></tr></thead>" +
        "<tbody>";
    if(undefined != str.data){
        var data = str.data;
        for (var int = 0; int < data.length; int++) {
            list = list + "<tr >";
            if(undefined==data[int].realName){
                list = list + "<td >未填写真实姓名用户</td>";
            }else {
                list = list + "<td >"+data[int].realName+"</td>";
            }
            if(undefined==data[int].phone){
                list = list + "<td >未填写手机号</td>";
            }else {
                list = list + "<td >"+data[int].phone+"</td>";
            }
            list = list + "<td >"+data[int].level+"</td>";
            list = list + "<td >"+data[int].inviteNum+"</td>";
            list = list + "</tr >";
        }
    }
    list = list + "<label for='search_phone'style='margin-left: 20px'><b>手机号:</b></label>";
    list = list + "<input type='text' id='search_phone'  name='phone' placeholder='请填写手机号'  >";
    list = list + "<label for='search_userName'><b>姓名:</b></label>";
    list = list + "<input type='text' id='search_userName'  name='userName' placeholder='请填写姓名'  >";
    list = list + "<label for='search_level'><b>等级:</b></label>";
    list = list + "<select id='search_level' name='level' class='col-10' >";
    list = list + " <option  value=''>全部</option>";
    list = list + " <option  value='1'>第1级</option>";
    list = list + " <option  value='2'>第2级</option>";
    list = list + " <option  value='3'>第3级</option>";
    list = list + " <option  value='4'>第4级</option>";
    list = list + " <option  value='5'>第5级</option>";
    list = list + " <option  value='6'>第6级</option>";
    list = list + " </select>";
    list = list + "<button class='btn btn-mini  btn-info' id = 'searchButton'>查询</button></tbody></table>";
    return list;

}

function getProjectList(str) {
    var list=" <table id=\"fenxiaoProjectDataTable\" border=\"1\" class=\"table table-striped table-bordered table-hover\" style='width: 800px;margin-top: 10px'  ><thead><tr>" +
        "<th>真实姓名</th><th>级别</th><th>投资项目名称</th><th>投资金额</th><th>投资时间</th><th>投资期限</th></tr></thead>" +
        "<tbody>";
    if(undefined != str.data){
        var data = str.data;
        for (var int = 0; int < data.length; int++) {
            list = list + "<tr >";
            if(undefined==data[int].userRealName){
                list = list + "<td >未填写真实姓名用户</td>";
            }else {
                list = list + "<td >"+data[int].userRealName+"</td>";
            }
//            list = list + "<td >"+str[int].userRealName+"</td>";
            list = list + "<td >"+data[int].level+"</td>";
            list = list + "<td >"+data[int].projectName+"</td>";
            list = list + "<td >"+data[int].money+"</td>";
            list = list + "<td >"+data[int].createTime+"</td>";
            list = list + "<td >"+data[int].deadLine+"</td>";
            list = list + "</tr >";
        }
    }

    list = list + "<label for='search_userName' style='margin-left: 20px'><b>姓名:</b></label>";
    list = list + "<input type='text' id='search_userName'  name='userName' placeholder='请填写姓名'  >";
    list = list + "<label for='search_level'><b>等级:</b></label>";
    list = list + "<select id='search_level' name='level' class='col-10' >";
    list = list + " <option  value=''>全部</option>";
    list = list + " <option  value='1'>第1级</option>";
    list = list + " <option  value='2'>第2级</option>";
    list = list + " <option  value='3'>第3级</option>";
    list = list + " <option  value='4'>第4级</option>";
    list = list + " <option  value='5'>第5级</option>";
    list = list + " <option  value='6'>第6级</option>";
    list = list + " </select>";
    list = list + " <label for=‘search_startTime’><b>交易时间:</b></label>";
    list = list + "<input name='startTime'  class='date-picker'  style='width:100px;' id='search_startTime'  type='text'/>";
    list = list + " -";
    list = list + " <label for='search_endTime'><b></b></label>";
    list = list + " <input name='endTime'  class='date-picker'  style='width:100px;' id='search_endTime'   type='text'/>";
    list = list + " <label for='search_isRecommend'><b>投资期限:</b></label>";
    list = list + "<select id='search_deadLine' name='deadLine' class='col-10' >";
    list = list + " <option  value=''>全部</option>";
    list = list + " <option  value='3'>3</option>";
    list = list + " <option  value='6'>6</option>";
    list = list + " <option  value='9'>9</option>";
    list = list + " </select>";
    list = list + "<button class='btn btn-mini  btn-info' id = 'searchProjectButton'>查询</button></tbody></table>";
    list = list + " 总计："+str.queryBean.money;
    return list;

}
function open(index){
    $(document).on("click", "#searchButton", function() {
        layer.close(index);
        var phone=jQuery("#search_phone").val();
        var userName=jQuery("#search_userName").val();
        var level=jQuery("#search_level").val();
        console.log(userId);
        $.post('/yingpu/system/appuser/fenxiaoList/json', {"userId":userId,"phone":phone,"userName":userName,"level":level}, function(str){
//        console.log(str.data);
            var list = getList(str);
            console.log(list);
            layer.open({
                type: 1,
                skin: 'layui-layer-demo', //样式类名
                closeBtn: 1, //显示关闭按钮
                anim: 2,
                id:'layerOpen'+index,
                resize :false,
                area: ['800px', '800px'],
                title:'所有下级用户',
                shadeClose: true, //开启遮罩关闭
                content: list, //注意，如果str是object，那么需要字符拼接。
                success: function(layero, index){
                    open(index);
                }
            });
        });
    });

    $(document).on("click", "#searchProjectButton", function() {
//        console.log(index);
        layer.close(index);
        var userName=jQuery("#search_userName").val();
        var level=jQuery("#search_level").val();
        var startTime=jQuery("#search_startTime").val();
        var endTime=jQuery("#search_endTime").val();
        var deadLine=jQuery("#search_deadLine").val();
        // console.log(id);
        $.post('/yingpu/system/userproject/findList/json', {"userId":userId,"userRealName":userName,"level":level,"startTime":startTime,"endTime":endTime,"deadLine":deadLine}, function(str){
//        console.log(str.data);
            var list = getProjectList(str);
            console.log(list);
            layer.open({
                type: 1,
                skin: 'layui-layer-demo', //样式类名
                closeBtn: 1, //显示关闭按钮
                anim: 2,
                id:'layerOpen'+index,
                resize :false,
                area: ['800px', '800px'],
                shadeClose: true, //开启遮罩关闭
                content: list, //注意，如果str是object，那么需要字符拼接。
               success: function(layero, index){
                   open(index);
                   $('.date-picker').datepicker({
                       autoclose: true,
                       format: 'yyyy-mm-dd',
                       language: 'zh-CN',
                       todayBtn: 'linked',
                       startDate:new Date(2014,10,27),
                       endDate:'+6000d',
                       startDate: '-6000d',
                   }).next().on(ace.click_event, function(){
                       $(this).prev().focus();
                   });
               }
            });
        });
    });
}

function seeFenxiaoProject(id){
    userId = id;
    $.post('/yingpu/system/userproject/findList/json', {"userId":userId}, function(str){
       console.log(str.data);
        var list = getProjectList(str);
        // console.log(list);
        var index =layer.open({
            type: 1,
            skin: 'layui-layer-demo', //样式类名
            closeBtn: 1, //显示关闭按钮
            anim: 2,
            id:'layerOpen',
            resize :false,
            area: ['800px', '800px'],
            title:'所有下级用户投资记录',
            shadeClose: true, //开启遮罩关闭
            content: list, //注意，如果str是object，那么需要字符拼接。
            success: function(layero, index){
//                console.log("111111111");
                open(index);
                $('.date-picker').datepicker({
                    autoclose: true,
                    format: 'yyyy-mm-dd',
                    language: 'zh-CN',
                    todayBtn: 'linked',
                    startDate:new Date(2014,10,27),
                    endDate:'+6000d',
                    startDate: '-6000d',
                }).next().on(ace.click_event, function(){
                    $(this).prev().focus();
                });
            }
        });
    });
}
