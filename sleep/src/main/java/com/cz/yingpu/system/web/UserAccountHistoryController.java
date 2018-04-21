package  com.cz.yingpu.system.web;

import java.io.File;
import java.util.Arrays;
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

import com.cz.yingpu.frame.controller.BaseController;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.GlobalStatic;
import com.cz.yingpu.frame.util.MessageUtils;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.frame.util.StringUtil;
import com.cz.yingpu.system.entity.UserAccountHistory;
import com.cz.yingpu.system.service.IUserAccountHistoryService;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:49
 * @see com.cz.yingpu.system.web.UserAccountHistory
 */
@Controller
@RequestMapping(value="/system/useraccounthistory")
public class UserAccountHistoryController  extends BaseController {
	@Resource
	private IUserAccountHistoryService userAccountHistoryService;
	
	private String listurl="/useraccounthistory/useraccounthistoryList";
	
	

	
	
	   
	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param userAccountHistory
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model,UserAccountHistory userAccountHistory) 
			throws Exception {
		ReturnDatas returnObject = listjson(request, model, userAccountHistory);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param userAccountHistory
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	public @ResponseBody
	ReturnDatas listjson(HttpServletRequest request, Model model,UserAccountHistory userAccountHistory) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		Page page = newPage(request);
		// ==执行分页查询
		Finder finder = new Finder("SELECT  ua.*,au.`realName` FROM t_user_account_history ua INNER JOIN `t_app_user` au  ON ua.`userId`=au.`id` where 1=1");
		
		if(StringUtil.isValid(userAccountHistory.getUserPhone())){
			finder.append(" and au.phone=:phone");
			finder.setParam("phone", userAccountHistory.getUserPhone());
		}
		if(StringUtil.isValid(userAccountHistory.getUserName())){
			finder.append(" and au.realName=:realName");
			finder.setParam("realName", userAccountHistory.getUserName());
		}
		if(StringUtil.isValid(userAccountHistory.getTradeNo())){
			finder.append(" and ua.tradeNo=:tradeNo");
			finder.setParam("realName", userAccountHistory.getTradeNo());
		}
		if(userAccountHistory.getStartTime()!=null){
			finder.append(" and ua.createtime >= :startTime");
			finder.setParam("startTime", userAccountHistory.getStartTime());
		}
		if(userAccountHistory.getEndTime()!=null){
			finder.append(" and ua.createtime <= :endTime");
			finder.setParam("endTime", userAccountHistory.getEndTime());
		}
		if(userAccountHistory.getUserId()!=null){
			finder.append(" and ua.userId = :userId");
			finder.setParam("userId", userAccountHistory.getUserId());
		}
		page.setOrder("ua.id");
		page.setSort("desc");
		List<Map<String, Object>> datas=userAccountHistoryService.queryForList(finder, page);
		returnObject.setQueryBean(userAccountHistory);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}
	
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request,HttpServletResponse response, Model model,UserAccountHistory userAccountHistory) throws Exception{
		// ==构造分页请求
		Page page = newPage(request);
	
		File file = userAccountHistoryService.findDataExportExcel(null,listurl, page,UserAccountHistory.class,userAccountHistory);
		String fileName="userAccountHistory"+GlobalStatic.excelext;
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
		return "/system/useraccounthistory/useraccounthistoryLook";
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
		  if(StringUtils.isNotBlank(strId)){
			 id= java.lang.Integer.valueOf(strId.trim());
		  UserAccountHistory userAccountHistory = userAccountHistoryService.findUserAccountHistoryById(id);
		   returnObject.setData(userAccountHistory);
		}else{
		returnObject.setStatus(ReturnDatas.ERROR);
		}
		return returnObject;
		
	}
	
	
	/**
	 * 新增/修改 操作吗,返回json格式数据
	 * 
	 */
	@RequestMapping("/update")
	public @ResponseBody
	ReturnDatas saveorupdate(Model model,UserAccountHistory userAccountHistory,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
		
		
			userAccountHistoryService.saveorupdate(userAccountHistory);
			
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
		return "/system/useraccounthistory/useraccounthistoryCru";
	}
	
	/**
	 * 删除操作
	 */
	@RequestMapping(value="/delete")
	public @ResponseBody ReturnDatas delete(HttpServletRequest request) throws Exception {

			// 执行删除
		try {
		  String  strId=request.getParameter("id");
		  java.lang.Integer id=null;
		  if(StringUtils.isNotBlank(strId)){
			 id= java.lang.Integer.valueOf(strId.trim());
				userAccountHistoryService.deleteById(id,UserAccountHistory.class);
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
			userAccountHistoryService.deleteByIds(ids,UserAccountHistory.class);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);


	}

	@RequestMapping("/fenxiaolist/json")
	public  @ResponseBody
	ReturnDatas fenxiaoUser(HttpServletRequest request,UserAccountHistory userAccountHistory) {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		Page page = newPage(request);
		// ==执行分页查询
		List datas= null;
		try {
			if(userAccountHistory.getUserId() == null){
				returnObject.setMessage("参数缺失");
				returnObject.setStatus(ReturnDatas.ERROR) ;
			}else {
				datas = userAccountHistoryService.fenxiaoList(page,userAccountHistory);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnObject.setMessage("系统内部原因");
			returnObject.setStatus(ReturnDatas.ERROR) ;
		}
		returnObject.setQueryBean(userAccountHistory);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}

	@RequestMapping("/fenxiaodetaillist/json")
	public  @ResponseBody
	ReturnDatas fenxiaoDetailUser(HttpServletRequest request,UserAccountHistory userAccountHistory) {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		Page page = newPage(request);
		// ==执行分页查询
		List datas= null;
		try {
			if(userAccountHistory.getUserId() == null || userAccountHistory.getFenxiaoUserId() == null){
				returnObject.setMessage("参数缺失");
				returnObject.setStatus(ReturnDatas.ERROR) ;
			}else {
				datas = userAccountHistoryService.fenxiaoDetailList(page,userAccountHistory) ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnObject.setMessage("系统内部原因");
			returnObject.setStatus(ReturnDatas.ERROR) ;
		}
		returnObject.setQueryBean(userAccountHistory);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}

	@RequestMapping("/investIncome/json")
	public  @ResponseBody
	ReturnDatas investIncome(HttpServletRequest request,UserAccountHistory userAccountHistory) {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		Page page = newPage(request);
		// ==执行分页查询
		List datas= null;
		try {
			datas = userAccountHistoryService.investIncome(page,userAccountHistory) ;
		
		} catch (Exception e) {
			e.printStackTrace();
			returnObject.setMessage("系统内部原因");
			returnObject.setStatus(ReturnDatas.ERROR) ;
		}
		returnObject.setQueryBean(userAccountHistory);
		returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}


	/**
	 * json数据,为APP提供数据
	 *
	 * @param request
	 * @param model
	 * @param appUser
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/repaymentDetailList")
	public String repaymentDetailList(HttpServletRequest request, Model model,UserAccountHistory userAccountHistory) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		Page page = newPage(request);
		// ==执行分页查询
		List<java.util.Map<String, Object>> userAccountHistories = userAccountHistoryService.repaymentDetailList(page,userAccountHistory);
		returnObject.setQueryBean(userAccountHistory);
		returnObject.setPage(page);
		returnObject.setData(userAccountHistories);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/repayment/repaymentDetailList";
	}
	
	
	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param userProject
	 * @return
	 * @throws Exception
	 */
	public
	ReturnDatas listjson(HttpServletRequest request) throws Exception{
 		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();

		return returnObject;
	}
	
	

}
