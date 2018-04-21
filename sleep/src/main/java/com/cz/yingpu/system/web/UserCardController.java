package com.cz.yingpu.system.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cz.yingpu.system.exception.CardTimeException;
import com.cz.yingpu.system.exception.NoUserException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cz.yingpu.system.entity.Hotel;
import com.cz.yingpu.system.entity.HotelBrand;
import com.cz.yingpu.system.entity.UserCard;
import com.cz.yingpu.system.service.IBaseSpringrainService;
import com.cz.yingpu.system.service.IUserCardService;
import com.cz.yingpu.frame.controller.BaseController;
import com.cz.yingpu.frame.dao.IBaseJdbcDao;
import com.cz.yingpu.frame.service.IBaseService;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.GlobalStatic;
import com.cz.yingpu.frame.util.MessageUtils;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.frame.util.ReturnDatas;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-06-21 09:28:19
 * @see com.cz.yingpu.system.web.UserCard
 */
@Controller
@RequestMapping(value="/system/usercard")
public class UserCardController  extends BaseController {
	@Resource
	private IUserCardService userCardService;
	
	private String listurl="/usercard/usercardList";
	
	@Resource
	private IBaseJdbcDao  baseSpringrainDao;
	
	   
	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param userCard
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model,UserCard userCard) 
			throws Exception {
		ReturnDatas returnObject = listjson(request, model, userCard);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	/**
	 * json数据,为APP提供数据
	 *
	 * @param request
	 * @param model
	 * @param userCard
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	public @ResponseBody
	ReturnDatas listjson(HttpServletRequest request, Model model,UserCard userCard) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		Page page = newPage(request);
		// ==执行分页查询
		List<UserCard> datas=new ArrayList<>();
		if(request.getParameter("hotel")==null){
			datas=userCardService.findListDataByFinder(null,page,UserCard.class,userCard);
			returnObject.setData(datas);
		}else{
			Finder finder=new Finder("select uc.*,h.img from t_user_card uc left join t_hotel h on uc.hotelId=h.id  where 1=1");
			returnObject.setData( baseSpringrainDao.findListDataByFinder(finder, page, userCard));
		}
		returnObject.setQueryBean(userCard);
		returnObject.setPage(page);
		
		return returnObject;
	}
	
	@RequestMapping("/list/export/json")
	public void listexport(HttpServletRequest request,HttpServletResponse response, Model model,UserCard userCard) throws Exception{
		// ==构造分页请求
		Page page = newPage(request);
		page.setPageSize(100000);
		
		File file = userCardService.findDataExportExcel(userCardService.findListDataByFinder(null,page,UserCard.class,userCard),
				listurl, page, null);
		String fileName="优惠券列表"+GlobalStatic.excelext;
		downFile(response, file, fileName,true);
		return;
	}

	/**
	 * json数据,为APP提供数据
	 *
	 * @param request
	 * @param model
	 * @param userCard
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findUserCard/json")
	public @ResponseBody
	ReturnDatas findUserCard(HttpServletRequest request, Model model,UserCard userCard) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		Page page = newPage(request);
		// ==执行分页查询
		List<UserCard> datas=userCardService.findUserCard(page,userCard);
		returnObject.setQueryBean(userCard);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}

		/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/usercard/usercardLook";
	}

	
	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/json")
	public @ResponseBody
	ReturnDatas lookjson(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		  String  strId=request.getParameter("id");
		  Integer id=null;
		  UserCard userCard=new UserCard();
		if(StringUtils.isNotBlank(strId)){
			 id= Integer.valueOf(strId.trim());
			 userCard = userCardService.findUserCardById(id);
		   
		}else{
			returnObject.setStatus(ReturnDatas.ERROR);
		}
		  List<Hotel> hlist=   userCardService.queryForList(new Finder("select id,name from t_hotel"), Hotel.class);
		  userCard.setHotels(hlist);
		  List<HotelBrand> hblist= userCardService.queryForList(new Finder("select id,name from t_hotelBrand"), HotelBrand.class);
		  userCard.setHotelBrands(hblist);
		  returnObject.setData(userCard);
		return returnObject;
	}
	/**
	 * 新增/修改 操作吗,返回json格式数据
	 * 
	 */
	@RequestMapping("/update")
	public @ResponseBody
	ReturnDatas saveorupdate(Model model,UserCard userCard,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
		

			userCardService.sendUserCard(userCard);
			
		} catch (NoUserException e){
			returnObject.setStatus(ReturnDatas.ERROR);
			if("1".equals(userCard.getInputType())){
				returnObject.setMessage("用户不存在");
			}else if("3".equals(userCard.getInputType())){
				returnObject.setMessage("有用户不存在，请核实");
			}
		}catch (CardTimeException e){
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage("开始时间必须大于当前时间");
		}catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			logger.error(errorMessage);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	
	}
	
	/**
	 * 进入修改页面,APP端可以调用 lookjson 获取json格式数据
	 */
	@RequestMapping(value = "/update/pre")
	public String updatepre(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception{
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/usercard/usercardCru";
	}
	
	/**
	 * 删除操作
	 */
	@RequestMapping(value="/delete")
	public @ResponseBody ReturnDatas delete(HttpServletRequest request) throws Exception {

			// 执行删除
		try {
		  String  strId=request.getParameter("id");
		  Integer id=null;
		  if(StringUtils.isNotBlank(strId)){
			 id= Integer.valueOf(strId.trim());
				userCardService.deleteById(id,UserCard.class);
				return new ReturnDatas(ReturnDatas.SUCCESS,
						MessageUtils.DELETE_SUCCESS);
			} else {
				return new ReturnDatas(ReturnDatas.WARNING,
						MessageUtils.DELETE_WARNING);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ReturnDatas(ReturnDatas.WARNING, MessageUtils.DELETE_WARNING);
	}
	
	/**
	 * 删除多条记录
	 * 
	 */
	@RequestMapping("/delete/more")
	public @ResponseBody
	ReturnDatas deleteMore(HttpServletRequest request, Model model) {
		String records = request.getParameter("records");
		if(StringUtils.isBlank(records)){
			 return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		String[] rs = records.split(",");
		if (rs == null || rs.length < 1) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_NULL_FAIL);
		}
		try {
			List<String> ids = Arrays.asList(rs);
			userCardService.deleteByIds(ids,UserCard.class);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);
	}




}
