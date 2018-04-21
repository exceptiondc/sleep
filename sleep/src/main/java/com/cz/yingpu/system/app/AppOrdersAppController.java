package com.cz.yingpu.system.app;

import com.cz.yingpu.frame.util.CalculationUtil;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.frame.util.pay.AliPayUtil;
import com.cz.yingpu.frame.util.pay.WxPayUtil;
import com.cz.yingpu.system.entity.AliPayNotify;
import com.cz.yingpu.system.entity.AppOrders;
import com.cz.yingpu.system.entity.Invoice;
import com.cz.yingpu.system.entity.WxPayNoify;
import com.cz.yingpu.system.service.IAppOrdersService;
import com.cz.yingpu.system.web.AppOrdersController;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value="/app/appOrders")
public class  AppOrdersAppController extends AppOrdersController{

	@Resource
	private IAppOrdersService appOrdersService;
	
	
	
	//保存用户开发票信息
	@RequestMapping("/invoice/json")
	@ResponseBody
	public ReturnDatas invoice(Invoice invoice,HttpServletRequest request){
		ReturnDatas returnDatas=new ReturnDatas();
		try {
			returnDatas=appOrdersService.invoice(invoice,request);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnDatas;
	}
	
	//下单
	@RequestMapping("/placeAnOrder/json")
	@ResponseBody
	public ReturnDatas placeAnOrder(AppOrders appOrders){
		ReturnDatas returnDatas=new ReturnDatas();
		try {
			returnDatas=appOrdersService.placeAnOrder(appOrders);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnDatas;
	}
	
	
	//加载订单列表
	@RequestMapping("/list/app/json")
	@ResponseBody
	public ReturnDatas AppOrdesList(HttpServletRequest request, Model model,AppOrders appOrders){
		ReturnDatas returnDatas=new ReturnDatas();
		try {
			Finder finder = new Finder("SELECT h.name hotelName, hh.houseName roomName, hs.name roomStatus, ho.*,hu.checkTime,hu.checkOutTime,")
				.append(" hs2.name roomType")
				.append(" FROM t_hotel h, t_hotel_user hu, t_hotelHouse hh, t_status hs, t_app_orders ho, t_status hs2")
				.append(" WHERE hu.orderid = ho.id AND hu.hotelHouseId = hh.id AND hu.hotelid = h.id")
				.append(" AND ho.status = hs.statusCode AND hh.type = hs2.statusCode");
			
			if (appOrders.getUserid() == null) {
				throw new NullPointerException("内部异常！");
			}
			finder.append(" AND hu.userid = :uid")
				  .setParam("uid", appOrders.getUserid());
			
			if(appOrders.getIsInvoice()!=null){
				finder.append(" AND ho.isInvoice = :isInvoice")
				  .setParam("isInvoice", appOrders.getIsInvoice());
			
			}
			System.out.println(finder.getSql());
			Page page = newPage(request);
			returnDatas.setData(appOrdersService.queryForList(finder, page));
			returnDatas.setPage(page);
		} catch (Exception e) {
			e.printStackTrace();
			returnDatas.setStatus(ReturnDatas.ERROR);
		}

		return returnDatas;
	}
	//查看订单及入住信息
	@RequestMapping("/AppOrdersLook/json")
	@ResponseBody
	public ReturnDatas AppOrdersLook(HttpServletRequest request, Model model,AppOrders appOrders){
		ReturnDatas returnDatas=new ReturnDatas();
		try {
			returnDatas=appOrdersService.AppOrdersLook(appOrders);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return returnDatas;
	}
	//支付宝支付回调
	@RequestMapping("/aliPayBack/json")
	@ResponseBody
	public String aliPayBack(HttpServletRequest request){
		try {
			// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("alipay");
			//Map<String, String> map = WxPayUtil.parseXml(request);
			
			
			
			Map<String, String> map =new HashMap<String, String>();
			
			Enumeration<String> s= request.getParameterNames();
			while (s.hasMoreElements()) {
				String string = (String) s.nextElement();
				map.put(string, request.getParameter(string));
			}
			System.out.println(map.toString());
			AliPayNotify aliPayNotify=new AliPayNotify();
			BeanUtils.populate(aliPayNotify, map);
			appOrdersService.save(aliPayNotify);
			//验证签名
			//if(AliPayUtil.getSignVeryfy(map, aliPayNotify.getSign())){
				// 商户订单号
				String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
				// 交易状态
				String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
				String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"), "UTF-8");
				System.out.println("支付回调");
				if (trade_status.equals("TRADE_SUCCESS")) {
					appOrdersService.payBackRes("SUCCESS", out_trade_no, total_fee, "zfb",aliPayNotify.getTrade_no(),aliPayNotify.getBuyer_email());
					return "success";
				} else {
					return "success";
				}
			/*}else{
				return "error";
			}*/
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return "success";
	}
	public static void main(String[] args) {
		//{transaction_id=4200000068201804105835247887, nonce_str=87a14e4085304cca93baa0a69e30a74c, bank_type=SPDB_CREDIT, openid=oU4By0kX7vIij6jQmJKrERmxSKqo, sign=19A558831065BADA63D0375FA1ED19C3, fee_type=CNY, mch_id=1500050152, cash_fee=1, out_trade_no=CZ2018041014154241740, appid=wx0169ca926b077946, total_fee=1, trade_type=APP, result_code=SUCCESS, attach=????, time_end=20180410141947, is_subscribe=N, return_code=SUCCESS}
		try {
			//192.168.1.187:8080/sleep/app/appOrders/wxPayBack/json?transaction_id=4200000068201804105835247887&nonce_str=87a14e4085304cca93baa0a69e30a74c&bank_type=SPDB_CREDIT&openid=oU4By0kX7vIij6jQmJKrERmxSKqo&sign=19A558831065BADA63D0375FA1ED19C3&fee_type=CNY&mch_id=1500050152&cash_fee=1&out_trade_no=CZ2018041014154241740&appid=wx0169ca926b077946&total_fee=1&trade_type=APP&result_code=SUCCESS&attach=????&time_end=20180410141947& is_subscribe=N&return_code=SUCCESS
			//192.168.1.187:8080/sleep/app/appOrders/aliPayBack/json?gmt_create=2018-04-10 15:27:57&buyer_email=187****6455&notify_time=2018-04-10 15:27:59&gmt_payment=2018-04-10 15:27:59&seller_email=1919279486@qq.com&quantity=1&subject=押金充值&use_coupon=N&sign=J1SFK8pC1cOnAnlf5BN/xbIgrKPwmoX5pm7DEjk14DXCxNdDpnD1zm5QtqT4jdG0upU8/pO+wdJk3BQv6uC1fRlb9rthAg5OBGrWt35qGbpFYj50L26+9gPsctj9y+FTAqFJp5wIyoT/M7y9nagjxSfy7UK43pzrSN+AxUO679Q=&discount=0.00&body=押金充值&buyer_id=2088212905140635&notify_id=feb510bb5e09073a5f3746646f38953kv5&notify_type=trade_status_sync&payment_type=1&out_trade_no=CZ2018041015254712760&price=0.01&trade_status=TRADE_SUCCESS&total_fee=0.01&trade_no=2018041021001004630526419157&sign_type=RSA&seller_id=2088921748625763&is_total_fee_adjust=N
			//{gmt_create=2018-04-10 15:27:57, buyer_email=187****6455, notify_time=2018-04-10 15:27:59, gmt_payment=2018-04-10 15:27:59, seller_email=1919279486@qq.com, quantity=1, subject=押金充值, use_coupon=N, sign=J1SFK8pC1cOnAnlf5BN/xbIgrKPwmoX5pm7DEjk14DXCxNdDpnD1zm5QtqT4jdG0upU8/pO+wdJk3BQv6uC1fRlb9rthAg5OBGrWt35qGbpFYj50L26+9gPsctj9y+FTAqFJp5wIyoT/M7y9nagjxSfy7UK43pzrSN+AxUO679Q=, discount=0.00, body=押金充值, buyer_id=2088212905140635, notify_id=feb510bb5e09073a5f3746646f38953kv5, notify_type=trade_status_sync, payment_type=1, out_trade_no=CZ2018041015254712760, price=0.01, trade_status=TRADE_SUCCESS, total_fee=0.01, trade_no=2018041021001004630526419157, sign_type=RSA, seller_id=2088921748625763, is_total_fee_adjust=N}

			System.out.println(">>>>>>>>>>>>>"+CalculationUtil.divide(Double.parseDouble("1"), 100.00).toString());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (com.sun.star.lang.NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//微信支付回调
	@RequestMapping("/wxPayBack/json")
	
	public void wxPayBack(HttpServletRequest request,HttpServletResponse response){
		ReturnDatas returnDatas=new ReturnDatas();
		try {
			System.out.println("支付回调");
		
			Map<String, String> map = WxPayUtil.parseXml(request);
			
			/*Map<String, String> map =new HashMap<String, String>();
			
			Enumeration<String> s= request.getParameterNames();
			while (s.hasMoreElements()) {
				String string = (String) s.nextElement();
				map.put(string, request.getParameter(string));
			}*/
			System.out.println(map.toString());
			WxPayNoify wxPayNoify=new WxPayNoify();
			BeanUtils.populate(wxPayNoify, map);
			appOrdersService.save(wxPayNoify);
			appOrdersService.payBackRes(map.get("result_code"),map.get("out_trade_no") ,CalculationUtil.divide(Double.parseDouble(map.get("total_fee")), 100.00).toString(),
					"wx",wxPayNoify.getTransaction_id(),wxPayNoify.getOpenid());
			returnDatas.setStatus(ReturnDatas.SUCCESS);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try {
			response.getWriter().print("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//微信支付预定酒店
	@RequestMapping("/wxPay/json")
	@ResponseBody
	public ReturnDatas wxPay(HttpServletRequest request,AppOrders appOrders){
		ReturnDatas returnDatas=new ReturnDatas();
		try {
			Map<String,Object> map =	appOrdersService.wxPay(appOrders, request);
			returnDatas.setData(map);
				returnDatas.setStatus(ReturnDatas.SUCCESS);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return returnDatas;
	}
	//支付宝支付预定酒店
	@RequestMapping("/aliPay/json")
	@ResponseBody
	public ReturnDatas aliPay(HttpServletRequest request,AppOrders appOrders){
		ReturnDatas returnDatas=new ReturnDatas();
		try {
				Map<String,String> map= appOrdersService.aliPay(appOrders);
				returnDatas.setData(map);
				returnDatas.setStatus(ReturnDatas.SUCCESS);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return returnDatas;
	}
	
	
	//充值去支付
	@RequestMapping("/rechargeGoPay/json")
	@ResponseBody
	public ReturnDatas rechargeGoPay(HttpServletRequest request,AppOrders appOrders){
		ReturnDatas returnDatas=new ReturnDatas();
		try {
				Map<String, Object> map= appOrdersService.rechargeGoPay(appOrders,request);
				returnDatas.setData(map);
				returnDatas.setStatus(ReturnDatas.SUCCESS);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return returnDatas;
	}
	
	//押金去支付
		@RequestMapping("/cashPledgeGoPay/json")
		@ResponseBody
		public ReturnDatas cashPledgeGoPay(HttpServletRequest request,AppOrders appOrders){
			ReturnDatas returnDatas=new ReturnDatas();
			try {
					Map<String, Object> map= appOrdersService.cashPledgeGoPay(appOrders,request);
					returnDatas.setData(map);
					returnDatas.setStatus(ReturnDatas.SUCCESS);
				
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			return returnDatas;
		}
		
		//押金退款
		@RequestMapping("/cashPledgeRefund/json")
		@ResponseBody
		public ReturnDatas cashPledgeRefund(HttpServletRequest request,AppOrders appOrders){
			ReturnDatas returnDatas=new ReturnDatas();
			try {
				returnDatas= appOrdersService.cashPledgeRefund(appOrders);
					
				
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			return returnDatas;
		}
		//用户退房并计算订单最终需要支付的价格
		@RequestMapping("/checkOut/json")
		@ResponseBody
		public ReturnDatas checkOut(HttpServletRequest request,AppOrders appOrders){
			ReturnDatas returnDatas=new ReturnDatas();
			try {
				returnDatas= appOrdersService.checkOut(appOrders);
					
				
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			return returnDatas;
		}
		//用户暂时离开房间
		@RequestMapping("/stepOut/json")
		@ResponseBody
		public ReturnDatas stepOut(HttpServletRequest request,AppOrders appOrders){
			ReturnDatas returnDatas=new ReturnDatas();
			try {
				returnDatas= appOrdersService.stepOut(appOrders);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			return returnDatas;
		}
		//结算入住
		@RequestMapping("/deblocking/json")
		@ResponseBody
		public ReturnDatas deblocking(HttpServletRequest request,AppOrders appOrders){
			ReturnDatas returnDatas=new ReturnDatas();
			try {
				returnDatas= appOrdersService.deblocking(appOrders);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return returnDatas;
		}
		
		//余额支付酒店房费
		@RequestMapping("/yuePay/json")
		@ResponseBody
		public ReturnDatas yuePay(HttpServletRequest request,AppOrders appOrders){
			ReturnDatas returnDatas=new ReturnDatas();
			try {
				returnDatas= appOrdersService.yuePay(appOrders);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return returnDatas;
		}
		//用户提现
		
		@RequestMapping("/withdrawDeposit/json")
		@ResponseBody
		public ReturnDatas withdrawDeposit(HttpServletRequest request,AppOrders appOrders){
			ReturnDatas returnDatas=new ReturnDatas();
			try {
				returnDatas= appOrdersService.withdrawDeposit(appOrders);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return returnDatas;
		}
}
