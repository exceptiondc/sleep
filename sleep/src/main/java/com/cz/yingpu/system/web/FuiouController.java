package com.cz.yingpu.system.web;

import com.cz.yingpu.frame.controller.BaseController;
import com.cz.yingpu.frame.util.DateUtils;
import com.cz.yingpu.frame.util.fuyou.ConfigReader;
import com.cz.yingpu.system.fuyoudata.*;
import com.cz.yingpu.system.service.FuiouService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by lenovo on 2017/3/15.
 */
@Controller
@RequestMapping(value="/system/fuiou")
public class FuiouController extends BaseController {

    @Resource
    private FuiouService fuiouService;

    /**
     * 新用户注册富友APP接口
     * @param request
     * @param response
     * @throws Exception
     *
     * /system/fuiou/appWebReg/json
     */
    @RequestMapping("/appWebReg/json")
    public void  appWebReg(HttpServletRequest request, HttpServletResponse response) throws Exception{
        AppRegReqData appRegReqData = new AppRegReqData();
        appRegReqData.setVer(ConfigReader.getConfig("ver"));
        appRegReqData.setMchnt_cd(ConfigReader.getConfig("mchnt_cd"));
        appRegReqData.setMchnt_txn_ssn(DateUtils.setDateFormat(new Date(),"yyyyMMddHHmmss")+getRand());
        appRegReqData.setUser_id_from("46");
        appRegReqData.setMobile_no("18000000005");
        appRegReqData.setCertif_tp("0");
        appRegReqData.setCust_nm("测试05");
        appRegReqData.setCertif_id("410526198903279074");
        appRegReqData.setBack_notify_url(ConfigReader.getConfig("webReg_notify_url"));
        appRegReqData.setPage_notify_url(ConfigReader.getConfig("webReg_page_notify_url"));
        FuiouService.appWebReg(appRegReqData,response);
    }

    public String getRand(){
        String x = "";
        Random r = new Random();
        x = r.nextInt(9999) + "";
        if (x.length() < 5) {
            for (int i = 0; i <= 5 - x.length(); i++) {
                x += "0";
            }
        }
        return x;
    }

    /**
     * 用户快速充值富友APP接口
     * @param request
     * @param response
     * @throws Exception
     * /system/fuiou/appWebRech1/json
     */
    @RequestMapping("/appWebRech1/json")
    public void  appWebRech1(HttpServletRequest request, HttpServletResponse response) throws Exception{
        AppTransReqData appTransReqData = new AppTransReqData();
        appTransReqData.setMchnt_cd(ConfigReader.getConfig("mchnt_cd"));
        appTransReqData.setMchnt_txn_ssn(DateUtils.setDateFormat(new Date(),"yyyyMMddHHmmss"));
        appTransReqData.setLogin_id("18539136976");
        appTransReqData.setAmt("100");
        appTransReqData.setBack_notify_url(ConfigReader.getConfig("webRech_notify_url"));
        appTransReqData.setPage_notify_url(ConfigReader.getConfig("page_notify_url"));
        FuiouService.app500001(appTransReqData,response);
    }

    /**
     * 用户快捷充值富友APP接口
     * @param request
     * @param response
     * @throws Exception
     * /system/fuiou/appWebRech2/json
     */
    @RequestMapping("/appWebRech2/json")
    public void  appWebRech2(HttpServletRequest request, HttpServletResponse response) throws Exception{
        AppTransReqData appTransReqData = new AppTransReqData();
        appTransReqData.setMchnt_cd(ConfigReader.getConfig("mchnt_cd"));
        appTransReqData.setMchnt_txn_ssn(DateUtils.setDateFormat(new Date(),"yyyyMMddHHmmss"));
        appTransReqData.setLogin_id("18538095675");
        appTransReqData.setAmt("10000000");
        appTransReqData.setBack_notify_url(ConfigReader.getConfig("webRech_notify_url"));
        appTransReqData.setPage_notify_url(ConfigReader.getConfig("webRech_page_notify_url"));
        FuiouService.app500002(appTransReqData,response);
    }

    /**
     * 用户在富友提现APP接口
     * @param request
     * @param response
     * @throws Exception
     * /system/fuiou/appWebWith/json
     */
    @RequestMapping("/appWebWith/json")
    public void  appWebWith(HttpServletRequest request, HttpServletResponse response) throws Exception{
        AppTransReqData appTransReqData = new AppTransReqData();
        appTransReqData.setMchnt_cd(ConfigReader.getConfig("mchnt_cd"));
        appTransReqData.setMchnt_txn_ssn(DateUtils.setDateFormat(new Date(),"yyyyMMddHHmmss"));
        appTransReqData.setLogin_id("18539136976");
        appTransReqData.setAmt("100");
        appTransReqData.setBack_notify_url(ConfigReader.getConfig("webRech_notify_url"));
        appTransReqData.setPage_notify_url(ConfigReader.getConfig("page_notify_url"));
        FuiouService.app500003(appTransReqData,response);
    }

    /**
     * 用户快捷充值富友网页接口
     * @param request
     * @param response
     * @throws Exception
     * /system/fuiou/webP2PRech/json
     */
    @RequestMapping("/webP2PRech/json")
    public void  webP2PRech(HttpServletRequest request, HttpServletResponse response) throws Exception{
        P2p500405ReqData p2p500405ReqData = new P2p500405ReqData();
        p2p500405ReqData.setMchnt_cd(ConfigReader.getConfig("mchnt_cd"));
        p2p500405ReqData.setMchnt_txn_ssn(DateUtils.setDateFormat(new Date(),"yyyyMMddHHmmss"));
        p2p500405ReqData.setLogin_id("18539136976");
        p2p500405ReqData.setAmt("100");
        p2p500405ReqData.setBack_notify_url(ConfigReader.getConfig("webP2PRech_notify_url"));
        p2p500405ReqData.setPage_notify_url(ConfigReader.getConfig("page_notify_url"));
        FuiouService.p2p500405(p2p500405ReqData,response);
    }

    /**
     * 查询用户富友余额接口
     * @param request
     * @param response
     * @throws Exception
     * /system/fuiou/queryUserBalance/json
     */
    @RequestMapping("/queryUserBalance/json")
    public void  queryUserBalance(HttpServletRequest request, HttpServletResponse response) throws Exception{
        QueryBalanceReqData queryBalanceReqData = new QueryBalanceReqData();
        queryBalanceReqData.setMchnt_cd(ConfigReader.getConfig("mchnt_cd"));
        queryBalanceReqData.setMchnt_txn_ssn(DateUtils.setDateFormat(new Date(),"yyyyMMddHHmmss"));
        queryBalanceReqData.setCust_no("18539136976");
        queryBalanceReqData.setMchnt_txn_dt(DateUtils.setDateFormat(new Date(),"yyyyMMdd"));
        QueryBalanceRspData  queryBalanceRspData = FuiouService.balanceAction(queryBalanceReqData);
        List<QueryBalanceResultData> queryBalanceResultDatas = queryBalanceRspData.getResults();
        for (QueryBalanceResultData queryBalanceResultData:
                queryBalanceResultDatas) {
            System.out.println(queryBalanceResultData.toString());
        }

    }

    /**
            * 转账
     * @param request
     * @param response
     * @throws Exception
     * https://wap.yingpuwealth.com/yingpu/system/fuiou/transferBmu/json?phone=18538036976
     */
    @RequestMapping("/transferBmu/json")
    public void  transferBmu(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String phone = request.getParameter("phone");
        TransferBmuReqData transferBmuReqData = new TransferBmuReqData();
        transferBmuReqData.setVer(ConfigReader.getConfig("ver"));
        transferBmuReqData.setMchnt_cd(ConfigReader.getConfig("mchnt_cd"));
        transferBmuReqData.setMchnt_txn_ssn(DateUtils.setDateFormat(new Date(),"yyyyMMddHHmmss"));
        transferBmuReqData.setOut_cust_no(ConfigReader.getConfig("mchnt_no"));
        transferBmuReqData.setIn_cust_no(phone);
        transferBmuReqData.setAmt("500");
        CommonRspData commonRspData = FuiouService.transferBmu(transferBmuReqData);
        System.out.println(commonRspData.toString());
    }

    /**
     * 查询用户富友余额接口
     * @param request
     * @param response
     * @throws Exception
     * /system/fuiou/appResetPassWord/json
     */
    @RequestMapping("/appResetPassWord/json")
    public void  appResetPassWord(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ResetPassWordReqData resetPassWordReqData = new ResetPassWordReqData();
        resetPassWordReqData.setMchnt_cd(ConfigReader.getConfig("mchnt_cd"));
        resetPassWordReqData.setMchnt_txn_ssn(DateUtils.setDateFormat(new Date(),"yyyyMMddHHmmss"));
        resetPassWordReqData.setLogin_id("18539136976");
        resetPassWordReqData.setBusi_tp("3");
        resetPassWordReqData.setBack_url(ConfigReader.getConfig("page_notify_url"));
        FuiouService.appResetPassWord(resetPassWordReqData,response);
    }

    /**
     * 查询用户富友信息接口
     * @param request
     * @param response
     * @throws Exception
     * /system/fuiou/transferBmu/json
     */
    @RequestMapping("/queryUserInfs/json")
    public void  queryUserInfs(HttpServletRequest request, HttpServletResponse response) throws Exception{
        QueryUserInfs_v2ReqData queryUserInfs_v2ReqData = new QueryUserInfs_v2ReqData();
        queryUserInfs_v2ReqData.setVer(ConfigReader.getConfig("ver"));
        queryUserInfs_v2ReqData.setMchnt_cd(ConfigReader.getConfig("mchnt_cd"));
        queryUserInfs_v2ReqData.setMchnt_txn_ssn(DateUtils.setDateFormat(new Date(),"yyyyMMddHHmmss"));
        queryUserInfs_v2ReqData.setMchnt_txn_dt(DateUtils.setDateFormat(new Date(),"yyyyMMdd"));
        queryUserInfs_v2ReqData.setUser_ids("18539136976");
        QueryUserInfs_v2RspData QueryUserInfs_v2RspData = FuiouService.queryUserInfs_v2(queryUserInfs_v2ReqData);
        List<QueryUserInfs_v2RspDetailData> queryUserInfs_v2RspDetailDatas = QueryUserInfs_v2RspData.getResults();
        System.out.println(queryUserInfs_v2RspDetailDatas.size());
    }


}
