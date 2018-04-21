package com.cz.yingpu.system.task;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cz.yingpu.frame.common.BaseLogger;
import com.cz.yingpu.frame.util.CalculationUtil;
import com.cz.yingpu.frame.util.DateUtils;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.system.entity.AppOrders;
import com.cz.yingpu.system.entity.Hotel;
import com.cz.yingpu.system.entity.HotelHouse;
import com.cz.yingpu.system.entity.HotelUser;
import com.cz.yingpu.system.service.IAppOrdersService;

@Component
public class AppOrdersTask extends BaseLogger{
	
	@Resource
	private IAppOrdersService appOrdersService;
	
	 // @Scheduled(cron = "00 */1 * * * ?")
	 /* public void cancelAppOrders() {
		  logger.info("**************订单取消任务***************");
				 try {
					 Finder finder=new Finder("UPDATE `t_app_orders` ao  INNER JOIN `t_hotelHouse` hh  ON ao.`hotelHouseId`=hh.id SET ao.STATUS=1036,ao.cancelTime=NOW() WHERE ao.createTime  < DATE_SUB(NOW(), INTERVAL 30 MINUTE) AND ao.STATUS = 1031");
					 appOrdersService.update(finder);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
	    }*/
	  @Scheduled(cron = "00 */1 * * * ?")
	  public void CalAppOrders() {
		  logger.info("**************订单价格每分钟计算一次***************");
				 try {
					 Finder finder=new Finder("select ao.*,hh.price,hh.hourPrice from t_app_orders ao inner join t_hotelHouse hh on ao.hotelHouseId=hh.id  where ao.status=1030");
					 List<AppOrders> aolist= appOrdersService.queryForList(finder, AppOrders.class);
					 for (AppOrders appOrder : aolist) {
						 //全日房
						 if(appOrder.getOrderType()==1){
						 }else if(appOrder.getOrderType()==4){
							 HotelUser hotelUser=new HotelUser();
							 hotelUser.setOrderid(appOrder.getId());
							 hotelUser=appOrdersService.queryForObject(hotelUser);
							 Date checkTime=null;
							//如果用户已经入住则以入住时间开始计费，如果没有入住。创建订单时间超过30分钟开始计费，以创建时间加上三十分钟开始计费
							 if(hotelUser.getCheckTime()!=null){
								 checkTime=hotelUser.getCheckTime();
								 if(checkTime.compareTo(DateUtils.addMinute(30, appOrder.getCreateTime()))>0){
									 checkTime=	DateUtils.addMinute(30, appOrder.getCreateTime());
								 }
							 }else{
								 long min=	 DateUtils.TimeDifference(new Date(), appOrder.getCreateTime(), "min");	
								 if(min>=30){
									 checkTime=	DateUtils.addMinute(30, appOrder.getCreateTime());
								 }
							 }
							 if(checkTime!=null){
								 //分时房
									long hour=   DateUtils.TimeDifference(new Date(), checkTime, "hour");	
									hour=hour+1;
									Double price=CalculationUtil.multiply(new BigDecimal(hour).doubleValue(), appOrder.getHourPrice());
									if(price>appOrder.getAmount()){
										appOrder.setAmount(price);
										appOrdersService.update(appOrder);
									}
							 }
						 }
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
	    }
}
