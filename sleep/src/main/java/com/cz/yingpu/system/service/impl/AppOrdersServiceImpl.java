package com.cz.yingpu.system.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.Hours;
import org.springframework.stereotype.Service;

import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.cz.yingpu.frame.service.IBaseService;
import com.cz.yingpu.frame.util.AliPayOpenApiUtils;
import com.cz.yingpu.frame.util.CalculationUtil;
import com.cz.yingpu.frame.util.DateUtils;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.frame.util.StringUtil;
import com.cz.yingpu.frame.util.pay.AliPayUtil;
import com.cz.yingpu.frame.util.pay.WxPayUtil;
import com.cz.yingpu.system.entity.AppOrders;
import com.cz.yingpu.system.entity.AppUser;
import com.cz.yingpu.system.entity.Hotel;
import com.cz.yingpu.system.entity.HotelHouse;
import com.cz.yingpu.system.entity.HotelUser;
import com.cz.yingpu.system.entity.Invoice;
import com.cz.yingpu.system.entity.UserAccountHistory;
import com.cz.yingpu.system.entity.UserCard;
import com.cz.yingpu.system.service.BaseSpringrainServiceImpl;
import com.cz.yingpu.system.service.IAppOrdersService;
import com.cz.yingpu.system.service.IAppUserService;
import com.cz.yingpu.system.service.IHotelHouseService;
import com.cz.yingpu.system.service.IUserAccountHistoryService;
import com.cz.yingpu.system.service.IUserCardService;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:43
 * @see com.cz.yingpu.system.service.impl.Announce
 */
@Service("appUserOrdersService")
public class AppOrdersServiceImpl extends BaseSpringrainServiceImpl implements IAppOrdersService {

	@Resource
	private IUserCardService userCardService;
	
	@Resource
	private IAppUserService appUserService;
	
	@Resource
	private IHotelHouseService hotelHouseService;
	
	@Resource
	private IBaseService hotelService;
	
	@Resource
	private IUserAccountHistoryService userAccountHistoryService;
	
	
	@Override
	public List<Map<String, Object>> list(Page page, AppOrders appOrders)
			throws Exception {
		Finder finder=new Finder("SELECT  ao.*,au.`realName`,au.`phone`,s.name FROM `t_app_orders`  ao,`t_app_user` au,`t_status` s WHERE ao.userid=au.`id` AND ao.`status`=s.`statusCode`");
		if(StringUtil.isValid(appOrders.getPhone())){
			finder.append(" and au.phone=:phone");
			finder.setParam("phone", appOrders.getPhone());
		}
		if(StringUtil.isValid(appOrders.getOrderNumber())){
			finder.append(" and ao.orderNumber=:orderNumber");
			finder.setParam("orderNumber", appOrders.getOrderNumber());
			
		}
		if(appOrders.getStartTime()!=null){
			finder.append(" and ao.createTime >= :startTime");
			finder.setParam("startTime", appOrders.getStartTime());
			
		}
		if(appOrders.getEndTime()!=null){
			finder.append(" and ao.createTime >= :endTime");
			finder.setParam("endTime", appOrders.getEndTime());
		}
		if(appOrders.getStatus()!=null&&appOrders.getStatus()>0){
			finder.append(" and ao.status = :status");
			finder.setParam("status", appOrders.getStatus());
		}
		return queryForList(finder, page);
	}
	@Override
	public AppOrders findAppOrdersById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return super.findById(id, AppOrders.class);
	}
	//创建住店订单
	@Override
	public ReturnDatas placeAnOrder(AppOrders orders) throws Exception {
		// TODO Auto-generated method stub
		ReturnDatas returnDatas=new ReturnDatas(ReturnDatas.SUCCESS);
		AppOrders appOrders=new AppOrders();
		AppUser user=appUserService.findAppUserById(orders.getUserid());
		if(!user.getIsIdCard().equals("是")){
			returnDatas.setStatus(ReturnDatas.ERROR);
			returnDatas.setMessage("您的账户尚未实名认证");
			return returnDatas;
		}
		
		if(user.getCashPledge()<200){
			returnDatas.setStatus(ReturnDatas.ERROR);
			returnDatas.setMessage("您的账户尚未缴纳押金");
			return returnDatas;
		}
		
		Finder finder=new Finder("select * from t_app_orders where userid = :userid and  (status =1030 or status =1031 or status =1032 or status =1034 ) and orderType=4");
		finder.setParam("userid", orders.getUserid());
		 List<AppOrders> aolist= super.queryForList(finder, AppOrders.class);
		if(aolist.size()>0){
			returnDatas.setStatus(ReturnDatas.ERROR);
			returnDatas.setMessage("您有尚未完成的订单，暂时不能预订房间");
			return returnDatas;
		}
		
		HotelHouse hotelHouse=hotelHouseService.findHotelHouseById(orders.getHotelHouseId());
		if(hotelHouse==null){
			returnDatas.setStatus(ReturnDatas.ERROR);
			returnDatas.setMessage("酒店房间不存在");
			return returnDatas;
		}
		if(hotelHouse.getStatus()!=1001){
			returnDatas.setStatus(ReturnDatas.ERROR);
			returnDatas.setMessage("改房间暂时不可用");
			return returnDatas;
		}
		Hotel hotel=hotelService.findById(hotelHouse.getHotelid(), Hotel.class);
		if(hotel==null){
			returnDatas.setStatus(ReturnDatas.ERROR);
			returnDatas.setMessage("酒店不存在");
			return returnDatas;
		}
		//判断当前选择预定的时间段是否被预定
	/*	Finder finder=new Finder("select * from t_app_orders where STATUS <= 1033 and userid=:userid  AND NOT ((reservationStartTime < :reservationEndTime AND reservationEndTime < :reservationEndTime) OR (reservationStartTime > :reservationEndTime AND reservationStartTime > :reservationEndTime))");
		finder.setParam("reservationStartTime", orders.getReservationStartTime());
		finder.setParam("reservationEndTime", orders.getReservationEndTime());
		finder.setParam("userid", orders.getUserid());
		List<AppOrders> aolist=super.queryForList(finder,AppOrders.class);
		if(aolist.size()>0){
			returnDatas.setStatus(ReturnDatas.ERROR);
			returnDatas.setMessage("该时间段已经被预定了");
			return returnDatas;
		}*/
		List<UserCard> ulist = null;
		//判断优惠券是否存在
		orders.setCardPrice(0.00);
		if(orders.getCardId()!=null){
			UserCard userCard=new UserCard();
			userCard.setUserId(orders.getUserid());
			userCard.setId(orders.getCardId());
			userCard.setStatus(1041);
			ulist=  userCardService.findListDataByFinder(null, null, UserCard.class, userCard);
			if(ulist==null||ulist.size()<=0){
				returnDatas.setStatus(ReturnDatas.ERROR);
				returnDatas.setMessage("优惠券不存在或者已经使用");
				return returnDatas;
			}
			if(userCard.getBrandId()!=null){
				if(userCard.getBrandId()!=hotel.getBrandId()){
					returnDatas.setStatus(ReturnDatas.ERROR);
					returnDatas.setMessage("该优惠券限制的酒店品牌和当前下单酒店品牌不一致");
					return returnDatas;
				}
			}
			if(userCard.getHotelId()!=null){
				if(userCard.getHotelId()!=hotel.getId()){
					returnDatas.setStatus(ReturnDatas.ERROR);
					returnDatas.setMessage("改优惠券限制的酒店和当前下单酒店不一致");
					return returnDatas;
				}
			}
			orders.setCardPrice(ulist.get(0).getMoney());
			appOrders.setCardId(orders.getCardId());
			appOrders.setCardPrice(orders.getCardPrice());
		}
		
		//创建订单分为两种情况全日房创建的时候已经计算最终支付金额，分时房只计算第一个小时的价钱，然后每个小时在定时器里更新，并在退房的时候计算出最终的价格
		if(orders.getOrderType()==1){
			appOrders.setAmount(hotelHouse.getPrice());
			appOrders.setPaymentAmount(CalculationUtil.subtract(orders.getAmount(),orders.getCardPrice()));
		}else if(orders.getOrderType()==4){
			//appOrders.setPaymentAmount(CalculationUtil.subtract(orders.getAmount(),orders.getCardPrice()));
			appOrders.setPaymentAmount(0.00);
			appOrders.setAmount(0.00);
		}
		appOrders.setUserid(orders.getUserid());
		appOrders.setStatus(1030);
		appOrders.setOrderNumber(DateUtils.getnumber("JD"));
		if(ulist!=null){
			appOrders.setCardPrice(ulist.get(0).getMoney());
			appOrders.setCardId(orders.getCardId());
		}
		appOrders.setCreateTime(new Date());
		appOrders.setReservationStartTime(orders.getReservationStartTime());
		appOrders.setOrderType(orders.getOrderType());
		appOrders.setHotelHouseId(hotelHouse.getId());
		appOrders.setReservationEndTime(orders.getReservationEndTime());
		Integer orderid= Integer.parseInt(super.save(appOrders).toString());
		//保存订单之后创建入住信息
		//创建用户入住信息
		HotelUser hotelUser=new HotelUser();
		hotelUser.setUserid(appOrders.getUserid());
		hotelUser.setOrderid(orderid);
		hotelUser.setStatus(1021);
		hotelUser.setHotelHouseId(hotelHouse.getId());
		hotelUser.setHotelid(hotelHouse.getHotelid());
	//	hotelUser.setLockid(hotelHouse.getLockNumber());
		hotelUser.setCreateTime(new Date());
		hotelUser.setReservationStartTime(appOrders.getReservationStartTime());
		hotelUser.setReservationEndTime(appOrders.getReservationEndTime());
		super.save(hotelUser);
		
		//修改房间状态
		hotelHouse.setStatus(1002);
		super.update(hotelHouse);
		returnDatas.setMessage("下单成功");
		returnDatas.setData(orderid);
		return returnDatas;
	}
	
	
	
	@Override
	public Map<String, Object> wxPay(AppOrders orders,HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		AppOrders ao=orders;
		
		orders=super.findById(orders.getId(), AppOrders.class);
		orders.setCardId(ao.getCardId());
		
		orders.setOrderNumber(DateUtils.getnumber("JD"));
		super.update(orders);
		List<UserCard> ulist = null;
		if(orders.getCardId()!=null){
			HotelHouse hotelHouse=hotelHouseService.findHotelHouseById(orders.getHotelHouseId());
			Hotel hotel=hotelService.findById(hotelHouse.getHotelid(), Hotel.class);
			//判断优惠券是否存在
			orders.setCardPrice(0.00);
			UserCard userCard=new UserCard();
			userCard.setUserId(orders.getUserid());
			userCard.setId(orders.getCardId());
			userCard.setStatus(1041);
			ulist=  userCardService.findListDataByFinder(null, null, UserCard.class, userCard);
		/*	if(ulist==null||ulist.size()<=0){
				datas.setStatus(ReturnDatas.ERROR);
				datas.setMessage("优惠券不存在或者已经使用");
				return datas;
			}
			if(userCard.getBrandId()!=null){
				if(userCard.getBrandId()!=hotel.getBrandId()){
					datas.setStatus(ReturnDatas.ERROR);
					datas.setMessage("该优惠券限制的酒店品牌和当前下单酒店品牌不一致");
					return datas;
				}
			}
			if(userCard.getHotelId()!=null){
				if(userCard.getHotelId()!=hotel.getId()){
					datas.setStatus(ReturnDatas.ERROR);
					datas.setMessage("该优惠券限制的酒店和当前下单酒店不一致");
					return datas;
				}
			}*/
			orders.setCardPrice(ulist.get(0).getMoney());
			orders.setPaymentAmount(CalculationUtil.subtract(orders.getAmount(), orders.getCardPrice()));
			orders.setCardId(orders.getCardId());
		}
		Map<String, String> map=new HashMap<String, String>();
		map.put("uesrid", request.getParameter("uesrid"));
		map.put("ordernumber", orders.getOrderNumber());
		map.put("attach","预定酒店");
		map.put("totalFee", CalculationUtil.multiply(orders.getPaymentAmount(), 100.00).toString());
		map.put("title", "预定酒店");
		ResourceBundle resource = ResourceBundle.getBundle("wechatpay");
		map.put("appid", resource.getString("AppId"));
		Map<String, Object> Signmap=new HashMap<String, Object>();
		Signmap.put("appid",resource.getString("AppId"));
		Signmap.put("partnerid", resource.getString("PayId"));
		Signmap.put("prepayid", WxPayUtil.parseXml(WxPayUtil.appWxPay(map,
				WxPayUtil.getIpAddr(request),true)).get("prepay_id"));
		Signmap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
		Signmap.put("noncestr", ((int)(Math.random()*900000+100000))+"");
		Signmap.put("package", "Sign=WXPay");
		Signmap.put("sign", WxPayUtil.getSign(Signmap));
		return Signmap;
	}
	@Override
	public Map<String, String>  aliPay(AppOrders orders) throws Exception {
		Map<String, String> map=new HashMap<String, String>();
		AppOrders ao=orders;
		
		orders=super.findById(orders.getId(), AppOrders.class);
		
		orders.setCardId(ao.getCardId());
		orders.setOrderNumber(DateUtils.getnumber("JD"));
		super.update(orders);
		List<UserCard> ulist = null;
		if(orders.getCardId()!=null){
			HotelHouse hotelHouse=hotelHouseService.findHotelHouseById(orders.getHotelHouseId());
			Hotel hotel=hotelService.findById(hotelHouse.getHotelid(), Hotel.class);
			//判断优惠券是否存在
			orders.setCardPrice(0.00);
			UserCard userCard=new UserCard();
			userCard.setUserId(orders.getUserid());
			userCard.setId(orders.getCardId());
			userCard.setStatus(1041);
			ulist=  userCardService.findListDataByFinder(null, null, UserCard.class, userCard);
		/*	if(ulist==null||ulist.size()<=0){
				datas.setStatus(ReturnDatas.ERROR);
				datas.setMessage("优惠券不存在或者已经使用");
				return datas;
			}
			if(userCard.getBrandId()!=null){
				if(userCard.getBrandId()!=hotel.getBrandId()){
					datas.setStatus(ReturnDatas.ERROR);
					datas.setMessage("该优惠券限制的酒店品牌和当前下单酒店品牌不一致");
					return datas;
				}
			}
			if(userCard.getHotelId()!=null){
				if(userCard.getHotelId()!=hotel.getId()){
					datas.setStatus(ReturnDatas.ERROR);
					datas.setMessage("该优惠券限制的酒店和当前下单酒店不一致");
					return datas;
				}
			}*/
			orders.setCardPrice(ulist.get(0).getMoney());
			orders.setPaymentAmount(CalculationUtil.subtract(orders.getAmount(), orders.getCardPrice()));
			orders.setCardId(orders.getCardId());
		}
		map= AliPayUtil.createAliPayOrder(orders.getOrderNumber(), "订单支付", "订单支付", orders.getPaymentAmount().toString());
		return map;
	}
	
	@Override
	public void payBackRes(String status, String orderNumber, String amount,
			String PayType,String PayNo,String account) throws Exception {
		// TODO Auto-generated method stub
		if ("SUCCESS".equals(status)){
			AppOrders appOrders=new AppOrders();
			appOrders.setOrderNumber(orderNumber);
			appOrders=super.queryForObject(appOrders);
			if(appOrders==null){
				return;
			}
			//订单金额与支付金额不匹配则订单出现异常
			if(!appOrders.getPaymentAmount().equals(Double.parseDouble(amount))){
				appOrders.setPayType(PayType);
				appOrders.setStatus(1033);
				super.update(appOrders);
				return;
			}
			//修改订单状态为交易成功
			appOrders.setPayType(PayType);
			appOrders.setStatus(1033);
			appOrders.setPaySerialNumber(PayNo);
			appOrders.setPaymentTime(new Date());
			super.update(appOrders);
			AppUser appUser=super.findById(appOrders.getUserid(), AppUser.class);
			//把用户 的支付宝账户放到用户表里
			/*if(PayType.equals("zfb")){
				if(appUser.getZfbAccount()==null||!appUser.getZfbAccount().equals(account)){
					appUser.setZfbAccount(account);
					super.update(appUser);
				}
			}else if(PayType.equals("wx")){
				if(appUser.getWxOpenId()==null||appUser.getWxOpenId().equals(account)){
					appUser.setWxOpenId(account);
					super.update(appUser);
				}
			}*/
			//酒店订单
			if(appOrders.getOrderType()==4){
				//修改酒店房间状态为已付款
				HotelHouse hotelHouse=super.findById(appOrders.getHotelHouseId(), HotelHouse.class);
				hotelHouse.setStatus(1001);
				super.update(hotelHouse);
				//如果订单使用了优惠券
				//修改优惠券状态为已使用
				if(appOrders.getCardId()!=null&&appOrders.getCardId()>0){
					UserCard userCard=super.findById(appOrders.getId(),UserCard.class );
					userCard.setStatus(1042);
					super.update(userCard);
				}
				//创建用户入住信息
				/*HotelUser hotelUser=new HotelUser();
				hotelUser.setUserid(appOrders.getUserid());
				hotelUser.setOrderid(appOrders.getId());
				hotelUser.setStatus(1021);
				hotelUser.setHotelHouseId(hotelHouse.getId());
				hotelUser.setHotelid(hotelHouse.getHotelid());
			//	hotelUser.setLockid(hotelHouse.getLockNumber());
				hotelUser.setCreateTime(new Date());
				hotelUser.setReservationStartTime(appOrders.getReservationStartTime());
				hotelUser.setReservationEndTime(appOrders.getReservationEndTime());
				super.save(hotelUser);*/
				//给推荐人发奖金(还没写)
				
			}else if(appOrders.getOrderType()==2){//押金订单
				appUser.setCashPledge(CalculationUtil.addition(appUser.getCashPledge(), Double.parseDouble(amount)));
				UserAccountHistory userAccountHistory=new UserAccountHistory();
				userAccountHistory.setUserId(appUser.getId());
				userAccountHistory.setType(3);
				userAccountHistory.setMoney(Double.parseDouble(amount));
				userAccountHistory.setAfterMoney(appUser.getCashPledge());
				userAccountHistory.setCreatetime(new Date());
				userAccountHistory.setStatus(2);
				userAccountHistory.setRemarkers("押金充值");
				userAccountHistory.setOrderId(appOrders.getId());
				userAccountHistory.setTradeNo(DateUtils.getnumber());
				userAccountHistoryService.save(userAccountHistory);
				super.update(appUser);
			}else if(appOrders.getOrderType()==3){//充值订单
				appUser.setBalance(CalculationUtil.addition(appUser.getBalance(), Double.parseDouble(amount)) );
				//appUser.setCashPledge(CalculationUtil.addition(appUser.getCashPledge(), Double.parseDouble(amount)));
				UserAccountHistory userAccountHistory=new UserAccountHistory();
				userAccountHistory.setUserId(appUser.getId());
				userAccountHistory.setType(1);
				userAccountHistory.setMoney(Double.parseDouble(amount));
				userAccountHistory.setAfterMoney(appUser.getBalance());
				userAccountHistory.setCreatetime(new Date());
				userAccountHistory.setStatus(2);
				userAccountHistory.setRemarkers("余额充值");
				userAccountHistory.setOrderId(appOrders.getId());
				userAccountHistory.setTradeNo(DateUtils.getnumber());
				userAccountHistoryService.save(userAccountHistory);
				super.update(appUser);
			}
		}
	}
	@Override
	public Integer createOrder(AppOrders orders) throws Exception {
		// TODO Auto-generated method stub
		orders.setOrderNumber(DateUtils.getnumber("CZ"));
		orders.setStatus(1031);
		orders.setAmount(orders.getAmount());
		orders.setPaymentAmount(orders.getAmount());
		orders.setCreateTime(new Date());
		return Integer.parseInt( super.save(orders).toString());
	}
	@Override
	public Map<String, Object> rechargeGoPay(AppOrders orders,
			HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> Signmap=new HashMap<String, Object>();
		orders.setOrderNumber(DateUtils.getnumber("CZ"));
		orders.setOrderType(3);
		
		DecimalFormat decimalFormat=new DecimalFormat("################0.00");
		
		BigDecimal b = new BigDecimal(orders.getAmount());   
		System.out.println((b.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue()));
		orders.setPaymentAmount(b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		orders.setId(this.createOrder(orders));
		if(orders.getPayType().equals("wx")){
			Map<String, String> map=new HashMap<String, String>();
			map.put("openid", request.getParameter("openid"));
			map.put("uesrid", request.getParameter("uesrid"));
			map.put("ordernumber", orders.getOrderNumber());
			map.put("attach","余额充值");
			map.put("totalFee", String.valueOf(CalculationUtil.multiply(orders.getPaymentAmount(), 100.00)));
			map.put("title", "余额充值");
			ResourceBundle resource = ResourceBundle.getBundle("wechatpay");
			map.put("appid", resource.getString("AppId"));
			
			Signmap.put("appid",resource.getString("AppId"));
			Signmap.put("partnerid", resource.getString("PayId"));
			Signmap.put("prepayid", WxPayUtil.parseXml(WxPayUtil.appWxPay(map,
			WxPayUtil.getIpAddr(request),true)).get("prepay_id"));
			Signmap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
			Signmap.put("noncestr", ((int)(Math.random()*900000+100000))+"");
			Signmap.put("package", "Sign=WXPay");
			Signmap.put("sign", WxPayUtil.getSign(Signmap));
		}else{
			Map<String, String> map=new HashMap<String, String>();
			orders=super.findById(orders.getId(), AppOrders.class);
			System.out.println(decimalFormat.format(orders.getPaymentAmount()));
			map= AliPayUtil.createAliPayOrder(orders.getOrderNumber(), "余额充值", "余额充值",  decimalFormat.format(orders.getPaymentAmount()));
			for (Map.Entry<String, String> entry : map.entrySet()) {  
				Signmap.put( entry.getKey(), entry.getValue());
			}  
		}
		return Signmap;
	}
	public static void main(String[] args) {
		DecimalFormat decimalFormat=new DecimalFormat("0.00");
		System.out.println(decimalFormat.format(0.01));
	}
	@Override
	public Map<String, Object> cashPledgeGoPay(AppOrders orders,
			HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> Signmap=new HashMap<String, Object>();
		orders.setOrderNumber(DateUtils.getnumber("YJ"));
		orders.setOrderType(2);
		orders.setPaymentAmount(orders.getAmount());
		orders.setId(this.createOrder(orders));
		if(orders.getPayType().equals("wx")){
			Map<String, String> map=new HashMap<String, String>();
			map.put("openid", request.getParameter("openid"));
			map.put("uesrid", request.getParameter("uesrid"));
			map.put("ordernumber", orders.getOrderNumber());
			map.put("attach","押金充值");
			map.put("totalFee", CalculationUtil.multiply(orders.getPaymentAmount(), 100.00).toString());
			map.put("title", "押金充值");
			
			ResourceBundle resource = ResourceBundle.getBundle("wechatpay");
			map.put("appid", resource.getString("AppId"));
			Signmap.put("appid",resource.getString("AppId"));
			Signmap.put("partnerid", resource.getString("PayId"));
			Signmap.put("prepayid", WxPayUtil.parseXml(WxPayUtil.appWxPay(map,
					WxPayUtil.getIpAddr(request),true)).get("prepay_id"));
			Signmap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
			Signmap.put("noncestr", ((int)(Math.random()*900000+100000))+"");
			Signmap.put("package", "Sign=WXPay");
			Signmap.put("sign", WxPayUtil.getSign(Signmap));
		}else{
			Map<String, String> map=new HashMap<String, String>();
			orders=super.findById(orders.getId(), AppOrders.class);
			map= AliPayUtil.createAliPayOrder(orders.getOrderNumber(), "押金充值", "押金充值", orders.getPaymentAmount().toString());
			for (Map.Entry<String, String> entry : map.entrySet()) {  
				Signmap.put( entry.getKey(), entry.getValue());
			}  
		}
		return Signmap;
	}
	//用户押金退款
	@Override
	public ReturnDatas cashPledgeRefund(AppOrders orders) throws Exception {
		// TODO Auto-generated method stub
		ReturnDatas data=new ReturnDatas(ReturnDatas.SUCCESS);
		AppUser appUser=super.findById(orders.getUserid(), AppUser.class);
		
		if(appUser.getCashPledge()<200){
			data.setMessage("尚未缴纳押金");
			data.setStatus(ReturnDatas.ERROR);
			return data;
		}
		
		Finder finder=new Finder("select * from t_app_orders where userid=:userid and orderType=4 and (status = 1030 or status = 1031 or status=1034)");
		finder.setParam("userid", orders.getUserid());
		List<AppOrders> aos= super.queryForList(finder,AppOrders.class);
		if(aos.size()>0){
			data.setMessage("您有订单还处于未完成状态，暂时不能退押金");
			data.setStatus(ReturnDatas.ERROR);
			return data;
		}
		//查询用户支付的押金退款订单
		
		orders.setStatus(1033);
		orders.setOrderType(2);
		if(orders.getUserid()==null){
			data.setMessage("userid不能为空");
			data.setStatus(ReturnDatas.ERROR);
			return data;
		}
		List<AppOrders> alist=super.findListDataByFinder(null, null, AppOrders.class, orders);
		if(alist.size()<=0){
			data.setMessage("没有查询到支付押金的订单");
			data.setStatus(ReturnDatas.ERROR);
			return data;
		}
		
		orders=alist.get(0);
		String liuhui= DateUtils.getnumber("TK");
		if(orders.getPayType().equals("wx")){
			//调用微信退款接口
		Map<String, String> map	=   WxPayUtil.refund(liuhui, orders.getOrderNumber(), CalculationUtil.multiply(orders.getPaymentAmount(), 100.00).intValue()+"",
					CalculationUtil.multiply(orders.getPaymentAmount(), 100.00).intValue()+""
					, "押金退款");
			System.out.println(map.get("return_code"));
			if(map.get("return_code").equals("SUCCESS")){
				
				if(map.get("result_code").equals("SUCCESS")){
					appUser.setCashPledge(0.00);
					super.update(appUser);
					//用户资金记录
					UserAccountHistory userAccountHistory=new UserAccountHistory();
					userAccountHistory.setUserId(appUser.getId());
					userAccountHistory.setType(4);
					userAccountHistory.setMoney(orders.getPaymentAmount());
					userAccountHistory.setAfterMoney(appUser.getCashPledge());
					userAccountHistory.setCreatetime(new Date());
					userAccountHistory.setStatus(2);
					userAccountHistory.setRemarkers("押金退款");
					userAccountHistory.setOrderId(orders.getId());
					userAccountHistory.setTradeNo(liuhui);
					userAccountHistoryService.save(userAccountHistory);
					orders.setStatus(1035);
					super.update(orders);
					data.setMessage("押金退还成功,可以在微信支付中查看进度。");
				}else{
					data.setMessage(map.get("err_code_des"));
					data.setStatus(ReturnDatas.ERROR);
					return data;
					
				}
				//调用微信退款查询接口
				/*Map<String, String> qmap= WxPayUtil.refundQuery(liuhui);
				if(qmap.get("return_code").equals("SUCCESS")){*/
					//if(qmap.get("refund_status_0").equals("SUCCESS")||qmap.get("refund_status_0").equals("PROCESSING")){
						//至此。表示退款成功或者退款正在处理中
						
				/*	}else{
						data.setMessage(map.get("return_msg"));
						data.setStatus(ReturnDatas.ERROR);
						return data;
					}*/
				/*}else{
					data.setMessage(map.get("return_msg"));
					data.setStatus(ReturnDatas.ERROR);
					return data;
				}*/
			}else{
				data.setMessage(map.get("return_msg"));
				data.setStatus(ReturnDatas.ERROR);
				return data;
			}
		}else if(orders.getPayType().equals("zfb")){
			Map<String, String> map=AliPayOpenApiUtils.ALiPayRefund(orders.getOrderNumber(), orders.getPaymentAmount(), "押金退款");
			if(map.get("fund_change").equals("Y")){
				//至此。表示退款成功或者退款正在处理中
				appUser.setCashPledge(0.00);
				super.update(appUser);
				//用户资金记录
				UserAccountHistory userAccountHistory=new UserAccountHistory();
				userAccountHistory.setUserId(appUser.getId());
				userAccountHistory.setType(4);
				userAccountHistory.setMoney(orders.getPaymentAmount());
				userAccountHistory.setAfterMoney(appUser.getCashPledge());
				userAccountHistory.setCreatetime(new Date());
				userAccountHistory.setStatus(2);
				userAccountHistory.setRemarkers(map.toString());
				userAccountHistory.setOrderId(orders.getId());
				userAccountHistory.setTradeNo(DateUtils.getnumber());
				//
				orders.setStatus(1035);
				super.update(orders);
				userAccountHistoryService.save(userAccountHistory);
				data.setMessage("押金退还成功,可以在支付宝中查看进度。");
			}else{
				data.setMessage(map.get("sub_msg"));
				data.setStatus(ReturnDatas.ERROR);
				return data;
			}
		}
		return data;
	}
	@Override
	public ReturnDatas checkOut(AppOrders orders) throws Exception {
		
		ReturnDatas datas=new ReturnDatas(ReturnDatas.SUCCESS);
		// TODO Auto-generated method stub

		
		orders=super.findById(orders.getId(), AppOrders.class);
		if(orders.getStatus()!=1030){
			datas.setStatus(ReturnDatas.ERROR);
			datas.setMessage("订单状态不是入住中");
			return datas;
		}
		orders.setStatus(1031);
		if(orders.getOrderType()==1){
		}else if(orders.getOrderType()==4){
			if(orders.getPrice()!=null&&orders.getPrice()>=0){
				orders.setPaymentAmount(CalculationUtil.subtract(orders.getAmount(), orders.getPrice()));
			}else{
				orders.setPaymentAmount(orders.getAmount());
			}
		}
		HotelUser hotelUser=new HotelUser();
		hotelUser.setOrderid(orders.getId());
		hotelUser=super.queryForObject(hotelUser);
		if(hotelUser.getStatus()==1024){
			datas.setStatus(ReturnDatas.ERROR);
			datas.setMessage("入住状态有问题");
			return datas;
		}
		hotelUser.setStatus(1024);
		hotelUser.setCheckOutTime(new Date());
		super.update(orders);
		super.update(hotelUser);
		
		HotelHouse hotelHouse=super.findById( orders.getHotelHouseId(), HotelHouse.class); 
		hotelHouse.setStatus(1001);
		super.update(hotelHouse);
		datas.setMessage("退房成功");
		return datas;
	}
	@Override
	public ReturnDatas stepOut(AppOrders orders) throws Exception {
		// TODO Auto-generated method stub
		ReturnDatas datas=new ReturnDatas(ReturnDatas.SUCCESS);
		HotelUser hotelUser=new HotelUser();
		hotelUser.setOrderid(orders.getId());
		hotelUser=super.queryForObject(hotelUser);
		hotelUser.setStatus(1023);
		super.update(hotelUser);
		datas.setMessage("暂时离开");
		return datas;
	}
	@Override
	public ReturnDatas AppOrdersLook(AppOrders appOrders) throws Exception {
		ReturnDatas returnDatas=new ReturnDatas(ReturnDatas.SUCCESS);
		AppOrders appOrders2=super.findById(appOrders.getId(), AppOrders.class);
		HotelUser hotelUser=new HotelUser();
		hotelUser.setOrderid(appOrders.getId());
		hotelUser=super.queryForObject(hotelUser);
		appOrders2.setHotelUser(hotelUser);
		appOrders2.setHotel( super.findById(hotelUser.getHotelid(), Hotel.class));
		appOrders2.setHotelHouse(super.findById(hotelUser.getHotelHouseId(), HotelHouse.class));
		returnDatas.setData(appOrders2);
		// TODO Auto-generated method stub
		return returnDatas;
	}
	
	@Override
	public ReturnDatas deblocking(AppOrders orders) throws Exception {
		ReturnDatas datas=new ReturnDatas(ReturnDatas.SUCCESS);
		orders=super.findById(orders.getId(),AppOrders.class);
		if(orders.getStatus()!=1030){
			datas.setMessage("订单状态不是入住中");
			datas.setStatus(ReturnDatas.ERROR);
			return datas;
		}
		// TODO Auto-generated method stub
	
		HotelUser hotelUser=new HotelUser();
		hotelUser.setOrderid(orders.getId());
		hotelUser=super.queryForObject(hotelUser);
		
		if(hotelUser.getStatus()==1021){
			hotelUser.setStatus(1022);
			hotelUser.setCheckTime(new Date());
			
			HotelHouse hotelHouse=super.findById(orders.getHotelHouseId(), HotelHouse.class);
			orders.setAmount(hotelHouse.getHourPrice());
			super.update(orders);
			
			super.update(hotelUser);
		}else if(hotelUser.getStatus()==1023){
			hotelUser.setStatus(1022);
			super.update(hotelUser);
		}else{
			datas.setMessage("当前状态并不能解锁");
			return datas;
		}
		//要去解锁虽然不知道怎么解
		datas.setMessage("入住成功");
		return datas;
	}
	@Override
	public ReturnDatas yuePay(AppOrders orders) throws Exception {
		ReturnDatas datas=new ReturnDatas(ReturnDatas.SUCCESS);
		// TODO Auto-generated method stub
		orders=super.findById(orders.getId(), AppOrders.class);
		AppUser appUser=super.findById(orders.getUserid(), AppUser.class);
		if(orders.getPaymentAmount()<0){
			datas.setStatus(ReturnDatas.ERROR);
			datas.setMessage("支付金额不能小于零");
			return datas;
		}
		if(orders.getPaymentAmount()>appUser.getBalance()){
			datas.setStatus(ReturnDatas.ERROR);
			datas.setMessage("账户余额不足");
			return datas;
		}
		if(orders.getStatus()!=1031){
			datas.setStatus(ReturnDatas.ERROR);
			datas.setMessage("订单状态不是待付款");
			return datas;
		}
		List<UserCard> ulist = null;
		if(orders.getCardId()!=null){
			HotelHouse hotelHouse=hotelHouseService.findHotelHouseById(orders.getHotelHouseId());
			Hotel hotel=hotelService.findById(hotelHouse.getHotelid(), Hotel.class);
			//判断优惠券是否存在
			orders.setCardPrice(0.00);
			UserCard userCard=new UserCard();
			userCard.setUserId(orders.getUserid());
			userCard.setId(orders.getCardId());
			userCard.setStatus(1041);
			ulist=  userCardService.findListDataByFinder(null, null, UserCard.class, userCard);
			if(ulist==null||ulist.size()<=0){
				datas.setStatus(ReturnDatas.ERROR);
				datas.setMessage("优惠券不存在或者已经使用");
				return datas;
			}
			if(userCard.getBrandId()!=null){
				if(userCard.getBrandId()!=hotel.getBrandId()){
					datas.setStatus(ReturnDatas.ERROR);
					datas.setMessage("该优惠券限制的酒店品牌和当前下单酒店品牌不一致");
					return datas;
				}
			}
			if(userCard.getHotelId()!=null){
				if(userCard.getHotelId()!=hotel.getId()){
					datas.setStatus(ReturnDatas.ERROR);
					datas.setMessage("该优惠券限制的酒店和当前下单酒店不一致");
					return datas;
				}
			}
			orders.setCardPrice(ulist.get(0).getMoney());
			orders.setPaymentAmount(CalculationUtil.subtract(orders.getAmount(), orders.getCardPrice()));
		}
		//减去账户余额并记录
		appUser.setBalance(CalculationUtil.subtract(appUser.getBalance(), orders.getPaymentAmount()));
		super.updateValidValue(appUser);
		
		UserAccountHistory userAccountHistory=new UserAccountHistory();
		userAccountHistory.setUserId(appUser.getId());
		userAccountHistory.setType(6);
		userAccountHistory.setMoney(orders.getPaymentAmount());
		userAccountHistory.setAfterMoney(appUser.getBalance());
		userAccountHistory.setCreatetime(new Date());
		userAccountHistory.setStatus(2);
		userAccountHistory.setRemarkers("余额支付");
		userAccountHistory.setOrderId(orders.getId());
		userAccountHistory.setTradeNo(DateUtils.getnumber());
		userAccountHistoryService.save(userAccountHistory);
		//修改订单状态
		orders.setStatus(1033);
		super.update(orders);
		datas.setMessage("支付成功");
		return datas;
	}
	@Override
	public ReturnDatas withdrawDeposit(AppOrders orders) throws Exception {
		ReturnDatas datas=new ReturnDatas(ReturnDatas.SUCCESS);
		// TODO Auto-generated method stub
		AppUser appUser=super.findById(orders.getUserid(), AppUser.class);
		if(orders.getAmount()<=0){
			datas.setMessage("提现金额不能小于等于0");
			datas.setStatus(ReturnDatas.ERROR);
			return datas;
		}
		if(orders.getAmount()>appUser.getBalance()){
			datas.setMessage("账户余额不足");
			datas.setStatus(ReturnDatas.ERROR);
			return datas;
		}
		String tradeNo=DateUtils.getnumber("TX");
		
		
		if(orders.getPayType().equals("wx")){
			
		}else if(orders.getPayType().equals("zfb")){
			AlipayFundTransToaccountTransferResponse ar=AliPayOpenApiUtils.ALiPayZZ(tradeNo, "余额提现", orders.getAmount().toString(), appUser.getZfbAccount(), appUser.getRealName());
			if(ar.getCode().equals("10000")){
				if(ar.getPayDate()!=null){
					appUser.setBalance(CalculationUtil.subtract(appUser.getBalance(), orders.getAmount()));
					super.update(appUser);
					UserAccountHistory userAccountHistory=new UserAccountHistory();
					userAccountHistory.setUserId(appUser.getId());
					userAccountHistory.setType(2);
					userAccountHistory.setMoney(orders.getAmount());
					userAccountHistory.setAfterMoney(appUser.getBalance());
					userAccountHistory.setCreatetime(new Date());
					userAccountHistory.setStatus(2);
					userAccountHistory.setRemarkers("账户提现");
					userAccountHistory.setOrderId(orders.getId());
					userAccountHistory.setTradeNo(tradeNo);
					userAccountHistoryService.save(userAccountHistory);
					datas.setMessage("提现成功，请在您支付宝中查看");
					datas.setStatus(ReturnDatas.ERROR);
					return datas;
				}else{
					datas.setMessage(ar.getSubMsg());
					datas.setStatus(ReturnDatas.ERROR);
					return datas;
				}
			}else{
				datas.setMessage(ar.getSubMsg());
				datas.setStatus(ReturnDatas.ERROR);
				return datas;
			}
		}
		return datas;
	}
	@Override
	public ReturnDatas invoice(Invoice invoice,HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		ReturnDatas returnDatas=new ReturnDatas(ReturnDatas.SUCCESS);
		String orderIds[]=request.getParameter("orderIds").split("-");
		Finder finder=new Finder("select * from t_app_orders where 1=1"); 
		finder.append(" and id in (");
		for (int i = 0; i < orderIds.length; i++) {
			if(i==orderIds.length-1){
				finder.append(":id"+i+"");
				finder.setParam("id"+i, orderIds[i]);
			}else{
				finder.append(":id"+i+",");
				finder.setParam("id"+i, orderIds[i]);
			}
		}
		finder.append(" )");
		List<AppOrders> ilist=  super.queryForList(finder,AppOrders.class);
		for (int i = 0; i < orderIds.length; i++) {
			if(ilist.get(i).getIsInvoice()==1){
				returnDatas.setStatus(ReturnDatas.SUCCESS);
				returnDatas.setMessage("有的订单已经开过发票了");
				return returnDatas;
			}
		}
		
		 finder=new Finder("update t_app_orders  set  isInvoice=1 where 1=1"); 
			finder.append(" and id in (");
			for (int i = 0; i < orderIds.length; i++) {
				if(i==orderIds.length-1){
					finder.append(":id"+i+"");
					finder.setParam("id"+i, orderIds[i]);
				}else{
					finder.append(":id"+i+",");
					finder.setParam("id"+i, orderIds[i]);
				}
			}
			finder.append(" )");
		invoice.setDescr(request.getParameter("orderIds"));
		invoice.setCreateTime(new Date());
		super.save(invoice);
		
		super.update(finder);
		return returnDatas;
	}
	
}
