package  com.cz.yingpu.system.web;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cz.yingpu.frame.common.SessionUser;
import com.cz.yingpu.frame.controller.BaseController;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.GlobalStatic;
import com.cz.yingpu.frame.util.MessageUtils;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.system.entity.Announce;
import com.cz.yingpu.system.entity.HotelHouse;
import com.cz.yingpu.system.entity.User;
import com.cz.yingpu.system.service.IAnnounceService;
import com.cz.yingpu.system.service.IHotelHouseService;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:43
 * @see com.cz.yingpu.system.web.Announce
 */
@Controller
@RequestMapping(value="/system/hotelHouse")
public class HotelHouseController  extends BaseController {
	

	@Resource
	private IHotelHouseService hotelHouseService;

	
	private String listurl="/hotelHouse/list";
	
	
	   
	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param HotelHouse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model,HotelHouse HotelHouse) 
			throws Exception {
		
		
		// ==构造分页请求
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		Page page = newPage(request);
	
		User user=new User(SessionUser.getUserId());
		user=hotelHouseService.queryForObject(user);
		if(user.getUserType()==1){
			HotelHouse.setHotelid(user.getHotelId());
		}
		
		// ==执行分页查询
		List<Map<String, Object>> datas=hotelHouseService.list(page,HotelHouse);
		returnObject.setQueryBean(HotelHouse);
		returnObject.setPage(page);
		returnObject.setData(datas);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param HotelHouse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	public @ResponseBody
	ReturnDatas listjson(HttpServletRequest request, Model model,HotelHouse HotelHouse) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		Page page = newPage(request);
		// ==执行分页查询
		
		
		List<HotelHouse> datas=hotelHouseService.findListDataByFinder(null,page,HotelHouse.class,HotelHouse);
			returnObject.setQueryBean(HotelHouse);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}
	
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request,HttpServletResponse response, Model model,HotelHouse HotelHouse) throws Exception{
		// ==构造分页请求
		Page page = newPage(request);
		User user=new User(SessionUser.getUserId());
		user=hotelHouseService.queryForObject(user);
		if(user.getUserType()==1){
			HotelHouse.setId(user.getHotelId());
		}
		List<Map<String, Object>> datas=hotelHouseService.list(page,HotelHouse);
		File file = hotelHouseService.findDataExportExcel(datas, listurl, page, HotelHouse);
		String fileName="HotelHouse"+GlobalStatic.excelext;
		downFile(response, file, fileName,true);
		return ;
	}
	
		/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/HotelHouse/HotelHouseLook";
	}

	
	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/json")
	public @ResponseBody
	ReturnDatas lookjson(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		  String  strId=request.getParameter("id");
		  java.lang.Integer id=null;
		  HotelHouse HotelHouse=new HotelHouse();
		  if(StringUtils.isNotBlank(strId)){
			 id= java.lang.Integer.valueOf(strId.trim());
		   HotelHouse = hotelHouseService.findHotelHouseById(id);
		}
		  Finder finder=new Finder("select id,name from t_hotel where 1=1");
		  	User user=new User(SessionUser.getUserId());
			user=hotelHouseService.queryForObject(user);
			if(user.getUserType()==1){
				finder.append(" and id=:id");
				finder.setParam("id", user.getHotelId());
			}
			List<Map<String, Object>> datas=hotelHouseService.queryForList(finder); 
			HotelHouse.setDatas(datas);
			finder=new Finder("select * from t_status  where 1=1 and t_status.group=:group");
			finder.setParam("group", "hotelHouse");
			datas=hotelHouseService.queryForList(finder);
			HotelHouse.setStatusdata(datas);

			finder=new Finder("select * from t_status where 1=1 and t_status.group=:group");
			finder.setParam("group", "hotelType");
			datas=hotelHouseService.queryForList(finder);
			HotelHouse.setTypes(datas);
			returnObject.setData(HotelHouse);
		return returnObject;
		
	}
	
	//app加载房间详情
	public @ResponseBody
	ReturnDatas lookjson(HttpServletRequest request) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		  String  strId=request.getParameter("id");
		  java.lang.Integer id=null;
			id= java.lang.Integer.valueOf(strId.trim());
			Finder finder=new Finder("select hh.*,h.name hotelName,h.longitude,h.latitude from t_hotelHouse hh inner join t_hotel h on hh.hotelid=h.id where hh.id=:id");
			finder.setParam("id", id);
			returnObject.setData(hotelHouseService.queryForList(finder));
		return returnObject;
		
	}
	/**
	 * 新增/修改 操作吗,返回json格式数据
	 * 
	 */
	@RequestMapping("/update")
	public @ResponseBody
	ReturnDatas saveorupdate(Model model,HotelHouse HotelHouse,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {

			/*if(HotelHouse.getSkipType() == 1)
				HotelHouse.setItem(HotelHouse.getUrl());*/
			hotelHouseService.saveorupdate(HotelHouse);
			
		} catch (Exception e) {
			String errorMessage = e.getLocalizedMessage();
			e.printStackTrace();
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
		try {
			ReturnDatas returnObject = lookjson(model, request, response);
			model.addAttribute(GlobalStatic.returnDatas, returnObject);
		} catch (Exception e) {
			// TODO: handle exceptio
			e.printStackTrace();
		}
		return "/hotelHouse/edit";
	}
	
	/**
	 * 删除操作
	 */
	@RequestMapping(value="/delete/json")
	public @ResponseBody ReturnDatas delete(HttpServletRequest request) throws Exception {
			// 执行删除
		try {
		  String  strId=request.getParameter("id");
		  java.lang.Integer id=null;
		  if(StringUtils.isNotBlank(strId)){
			 id= java.lang.Integer.valueOf(strId.trim());
				hotelHouseService.deleteById(id,HotelHouse.class);
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
			hotelHouseService.deleteByIds(ids,HotelHouse.class);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);
	}

}
