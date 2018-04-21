package  com.cz.yingpu.system.web;

import com.cz.yingpu.frame.common.SessionUser;
import com.cz.yingpu.frame.controller.BaseController;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.GlobalStatic;
import com.cz.yingpu.frame.util.MessageUtils;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.system.entity.AppOrders;
import com.cz.yingpu.system.entity.Invoice;
import com.cz.yingpu.system.entity.SearchLocationCategory;
import com.cz.yingpu.system.entity.Status;
import com.cz.yingpu.system.entity.User;
import com.cz.yingpu.system.service.IAppOrdersService;
import com.cz.yingpu.system.service.IStatusService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:43
 * @see com.cz.yingpu.system.web.Announce
 */
@Controller
@RequestMapping(value="/system/appOrders")
public class AppOrdersController  extends BaseController {
	

	@Resource
	private IAppOrdersService appOrdersService;
	
	@Resource
	private IStatusService statusService;
	private String listurl="/appOrders/list";
	
	
	   
	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param appOrders
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model,AppOrders appOrders) 
			throws Exception {
		
		
		// ==构造分页请求
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		Page page = newPage(request);
	
		User user=new User(SessionUser.getUserId());
		user=appOrdersService.queryForObject(user);
		if(user.getUserType()==1){
			appOrders.setId(user.getHotelId());
		}
		// ==执行分页查询
		List<Map<String, Object>> datas=appOrdersService.list(page,appOrders);
		List<Status> datass=statusService.findStatusByGroup("appOrder");
		appOrders.setDatas(datass);
		returnObject.setQueryBean(appOrders);
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
	 * @param appOrders
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	public @ResponseBody
	ReturnDatas listjson(HttpServletRequest request, Model model,AppOrders appOrders) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		Page page = newPage(request);
		// ==执行分页查询
		
		
		List<AppOrders> datas=appOrdersService.findListDataByFinder(null,page,AppOrders.class,appOrders);
			returnObject.setQueryBean(appOrders);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}
	
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request,HttpServletResponse response, Model model,AppOrders appOrders) throws Exception{
		// ==构造分页请求
		Page page = newPage(request);
		User user=new User(SessionUser.getUserId());
		user=appOrdersService.queryForObject(user);
		if(user.getUserType()==1){
			appOrders.setId(user.getHotelId());
		}
		List<Map<String, Object>> datas=appOrdersService.list(page,appOrders);
		File file = appOrdersService.findDataExportExcel(datas, listurl, page, appOrders);
		String fileName="appOrders"+GlobalStatic.excelext;
		downFile(response, file, fileName,true);
		return;
	}
	
		/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/appOrders/appOrdersLook";
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
		  AppOrders appOrders=new AppOrders();
		  if(StringUtils.isNotBlank(strId)){
			 id= java.lang.Integer.valueOf(strId.trim());
		   appOrders = appOrdersService.findAppOrdersById(id);
		}
		  Finder finder=new Finder("select id,name from t_hotel where 1=1");
		  	User user=new User(SessionUser.getUserId());
			user=appOrdersService.queryForObject(user);
			if(user.getUserType()==1){
				finder.append(" and id=:id");
				finder.setParam("id", user.getHotelId());
			}
			List<Map<String, Object>> datas=appOrdersService.queryForList(finder); 
			finder=new Finder("select * from t_status  where 1=1 and t_status.group=:group");
			finder.setParam("group", "appOrders");
			List<Status> slist=appOrdersService.queryForList(finder,Status.class);
			appOrders.setDatas(slist);

		
			returnObject.setData(appOrders);
		return returnObject;
		
	}
	
	
	/**
	 * 新增/修改 操作吗,返回json格式数据
	 * 
	 */
	@RequestMapping("/update")
	public @ResponseBody
	ReturnDatas saveorupdate(Model 	model,AppOrders appOrders,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {

			/*if(appOrders.getSkipType() == 1)
				appOrders.setItem(appOrders.getUrl());*/
			appOrdersService.saveorupdate(appOrders);
			
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
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/appOrders/edit";
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
				appOrdersService.deleteById(id,AppOrders.class);
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
			appOrdersService.deleteByIds(ids,AppOrders.class);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);
		
		
	}

	/** 发票列表 */
	@RequestMapping("/invoice/list")
	public String invoiceList(String name) {
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();

		try {
			Page page = newPage(request);
			page.setOrder("i.createTime");
			page.setSort("DESC");

			Finder finder = new Finder("SELECT i.*, au.name userName FROM t_invoice i, t_app_user au")
					.append(" WHERE au.id = i.userId");

			if (StringUtils.isNotBlank(name)) {
				finder.append(" AND au.name LIKE :name")
						.setParam("name", '%' + name + '%');
			}

			rt.setQueryBean(appOrdersService.queryForList(finder, page));

		}
		catch(Exception e) {
			rt.setStatus(ReturnDatas.ERROR);
			e.printStackTrace();
		}

		request.setAttribute(GlobalStatic.returnDatas, rt);

		return "/order/invoice_list";
	}
}
