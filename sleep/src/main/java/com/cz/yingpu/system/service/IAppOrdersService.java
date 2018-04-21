package com.cz.yingpu.system.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.system.entity.AppOrders;
import com.cz.yingpu.system.entity.HotelHouse;
import com.cz.yingpu.system.entity.Invoice;

public interface IAppOrdersService extends IBaseSpringrainService{
	List<Map<String, Object>> list(Page page,AppOrders appOrders) throws Exception;
	AppOrders findAppOrdersById(Integer id) throws Exception;
	
	//查看订单详情和入住信息
	ReturnDatas AppOrdersLook(AppOrders appOrders) throws Exception;
	
	//开发票
	ReturnDatas invoice(Invoice invoice,HttpServletRequest request) throws Exception;
	//下单
	ReturnDatas placeAnOrder(AppOrders orders) throws Exception;
	
	//预定酒店创建微信支付
	Map<String, Object> wxPay(AppOrders orders,HttpServletRequest request)throws Exception;
	//预定酒店支付宝支付
	Map<String, String> aliPay(AppOrders orders)throws Exception;
	//支付异步回调 微信支付宝统一处理
	void payBackRes(String status,String orderNumber,String amount,String PayType,String PayNo,String account) throws Exception;
	//创建订单
	Integer createOrder(AppOrders orders)throws Exception;
	//充值去支付
	Map<String, Object> rechargeGoPay(AppOrders orders,HttpServletRequest request)throws Exception;
	
	//押金去支付
	Map<String, Object> cashPledgeGoPay(AppOrders orders,HttpServletRequest request)throws Exception;
	
	//用户押金退款
	ReturnDatas cashPledgeRefund(AppOrders orders) throws Exception;
	//用户退房并计算订单最终需要支付的价格
	ReturnDatas checkOut(AppOrders orders)  throws Exception;
	
	//用户暂时离开房间
	ReturnDatas stepOut(AppOrders orders)  throws Exception;
	//解锁入住
	ReturnDatas deblocking(AppOrders orders) throws Exception;
	
	//用户余额支付
	ReturnDatas yuePay(AppOrders orders) throws Exception;
	
	//用户提现
	ReturnDatas withdrawDeposit(AppOrders orders) throws Exception;
	
}
