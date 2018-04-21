package com.cz.yingpu.system.web;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.CacheManager;
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
import com.cz.yingpu.system.entity.SysParamBean;
import com.cz.yingpu.system.entity.SysSysparam;
import com.cz.yingpu.system.service.ISysSysparamService;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:49
 * @see com.cz.yingpu.system.web.SysSysparam
 */
@Controller
@RequestMapping(value="/system/syssysparam")
public class SysSysparamController  extends BaseController {
	@Resource
	private ISysSysparamService sysSysparamService;
	
	private String listurl="/system/syssysparam/syssysparamList";
	
	
	@Resource
	private CacheManager cacheManager ;
	
	   
	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param sysSysparam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model,SysSysparam sysSysparam) 
			throws Exception {
		ReturnDatas returnObject = listjson(request, model, sysSysparam);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param sysSysparam
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	public @ResponseBody
	ReturnDatas listjson(HttpServletRequest request, Model model,SysSysparam sysSysparam) throws Exception{
		SysParamBean datas = sysSysparamService.findParamBean();
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setData(datas);
		return returnObject;
	}
	
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request,HttpServletResponse response, Model model,SysSysparam sysSysparam) throws Exception{
		// ==构造分页请求
		Page page = newPage(request);
	
		File file = sysSysparamService.findDataExportExcel(null,listurl, page,SysSysparam.class,sysSysparam);
		String fileName="sysSysparam"+GlobalStatic.excelext;
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
		return "/system/syssysparam/syssysparamLook";
	}

	
	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/json")
	public @ResponseBody
	ReturnDatas lookjson(Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String id=request.getParameter("code");
		if(StringUtils.isNotBlank(id)){
		  SysSysparam sysSysparam = sysSysparamService.findSysSysparamById(id);
		   returnObject.setData(sysSysparam);
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
	ReturnDatas saveorupdate(Model model,SysSysparam sysSysparam,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {

			String code=sysSysparam.getCode();

			Finder finder=Finder.getSelectFinder(SysSysparam.class).append(" where 1=1 ");

			Page page=new Page();

			page.setPageSize(1);

			SysSysparam sysparamR = new SysSysparam();

			sysparamR.setCode(code);

			List<SysSysparam> sysSysparams=sysSysparamService.findListDataByFinder(finder, page, SysSysparam.class,sysparamR );

			if(sysSysparams.size()>0){

				sysSysparams.get(0).setValue(sysSysparam.getValue());

//				sysSysparamService.update(sysSysparams.get(0), true);

				sysSysparamService.saveOrUpdate(sysSysparams.get(0)) ;
			}


		} catch (Exception e) {
			e.printStackTrace();
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
		return "/system/syssysparam/syssysparamCru";
	}
	
	/**
	 * 删除操作
	 */
	@RequestMapping(value="/delete")
	public @ResponseBody ReturnDatas delete(HttpServletRequest request) throws Exception {

			// 执行删除
		try {
		String id=request.getParameter("code");
		if(StringUtils.isNotBlank(id)){
				sysSysparamService.deleteById(id,SysSysparam.class);
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
			sysSysparamService.deleteByIds(ids,SysSysparam.class);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);
		
		
	}


	/**
	 * 修改注册协议
	 * @author wxy
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/register")
	public String updateRegister(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","userRegisterProtocol");
		List sysparams = sysSysparamService.queryForList(finder);
		returnObject.setData(sysparams.get(0));
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/register";
	}

	/**
	 * 修改客服热线
	 * @author wxy
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/kefuphone")
	public String updateKefuPhone(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","kefuphone");
		List sysparams = sysSysparamService.queryForList(finder);
		returnObject.setData(sysparams.get(0));
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/kefu";
	}
	/**
	 * 修改分销比例
	 * @author wxy
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/fenxiaoPercent")
	public String updateFenxiaoPerchant(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","fenxiaoPercent");
		List sysparams = sysSysparamService.queryForList(finder,SysSysparam.class) ;
		SysSysparam sysparam = (SysSysparam) sysparams.get(0);
		if(sysparam != null && StringUtils.isNotBlank(sysparam.getValue())){
			sysparam.setValue(String.valueOf(Float.valueOf(sysparam.getValue()) * 100));
		}
		returnObject.setData(sysparam);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/fenxiaoPercent";
	}
	/**
	 * 修改关于我们
	 * @author wxy
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/aboutUs")
	public String updateAboutUs(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","aboutUs");
		List sysparams = sysSysparamService.queryForList(finder);
		returnObject.setData(sysparams.get(0));
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/aboutUs";
	}
	/**
	 * 修改项目管理机制
	 * @author wxy
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/updateInst")
	public String updateInst(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","manageInst");
		List sysparams = sysSysparamService.queryForList(finder);
		returnObject.setData(sysparams.get(0));
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/inst";
	}

	/**
	 * 修改理财计划
	 * @author wxy
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/updateFinancialPlan")
	public String updateFinancialPlan(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","financialPlan");
		List sysparams = sysSysparamService.queryForList(finder);
		returnObject.setData(sysparams.get(0));
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/financialPlan";
	}

	/**
	 * 修改安全保障
	 * @author wxy
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/updateSafeguard")
	public String updateSafeguard(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","safeguard");
		List sysparams = sysSysparamService.queryForList(finder);
		returnObject.setData(sysparams.get(0));
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/safeguard";
	}
	/**
	 * 余额宝理财说明
	 * @author wxy
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/financialExplain")
	public String financialExplain(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","financialExplain");
		List sysparams = sysSysparamService.queryForList(finder);
		returnObject.setData(sysparams.get(0));
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/financialExplain";
	}
	/**
	 * 签到最低投资金额
	 * @author wxy
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/signLimitAmount")
	public String signLimitAmount(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","signLimitAmount");
		List sysparams = sysSysparamService.queryForList(finder);
		returnObject.setData(sysparams.get(0));
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/signLimitAmount";
	}
	/**
	 * 签到最低投资金额
	 * @author wxy
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/experienceMoney")
	public String experienceMoney(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","experienceMoney");
		List sysparams = sysSysparamService.queryForList(finder);
		returnObject.setData(sysparams.get(0));
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/experienceMoney";
	}

	/**
	 * 分享内容
	 * @author wxy
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/shareContent")
	public String apkContent(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","sharecontent");
		List sysparams = sysSysparamService.queryForList(finder);
		returnObject.setData(sysparams.get(0));
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/sharecontent";
	}
	/**
	 * 邀请好友规则
	 * @author wxy
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/inviteRule")
	public String inviteRule(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","inviteRule");
		List sysparams = sysSysparamService.queryForList(finder);
		returnObject.setData(sysparams.get(0));
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/inviteRule";
	}
	/**
	 * 邀请好友奖励上级金额
	 * @author wxy
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/investAward")
	public String investAward(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","investAward");
		List sysparams = sysSysparamService.queryForList(finder);
		returnObject.setData(sysparams.get(0));
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/investAward";
	}
	/**
	 * 关于贾克斯
	 * @author wxy
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/aboutYingpu")
	public String aboutYingpu(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","aboutYingpu");
		List sysparams = sysSysparamService.queryForList(finder);
		returnObject.setData(sysparams.get(0));
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/aboutYingpu";
	}

	/**
	 * 关于贾克斯
	 * @author jky
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/protocolTemplate")
	public String protocolTemplate(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","protocolTemplate");
		List sysparams = sysSysparamService.queryForList(finder);
		returnObject.setData(sysparams.get(0));
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/protocolTemplate";
	}

	/**
	 * 合伙人分成比例
	 * @author jky
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/partnerPercent")
	public String partnerPercent(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","partnerPercent");
		List sysparams = sysSysparamService.queryForList(finder,SysSysparam.class);
		SysSysparam sysparam = (SysSysparam) sysparams.get(0);
		if(sysparam != null && StringUtils.isNotBlank(sysparam.getValue())){
			sysparam.setValue(String.valueOf(Float.valueOf(sysparam.getValue()) * 100));
		}
		returnObject.setData(sysparam);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/partnerPercent";
	}

	/**
	 * 合伙人申请页面
	 * @author jky
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/partnerHtml")
	public String partnerHtml(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","partnerHtml");
		List sysparams = sysSysparamService.queryForList(finder,SysSysparam.class);
		SysSysparam sysparam = (SysSysparam) sysparams.get(0);
//		if(sysparam != null && StringUtils.isNotBlank(sysparam.getValue())){
//			sysparam.setValue(String.valueOf(Float.valueOf(sysparam.getValue()) * 100));
//		}
		returnObject.setData(sysparam);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/partnerHtml";
	}

	/**
	 * 合伙人申请页面
	 * @author jky
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/partnerInstruction")
	public String partnerInstruction(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","partnerInstruction");
		List sysparams = sysSysparamService.queryForList(finder,SysSysparam.class);
		SysSysparam sysparam = (SysSysparam) sysparams.get(0);
//		if(sysparam != null && StringUtils.isNotBlank(sysparam.getValue())){
//			sysparam.setValue(String.valueOf(Float.valueOf(sysparam.getValue()) * 100));
//		}
		returnObject.setData(sysparam);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/partnerInstruction";
	}

	/**
	 * 天天存吧协议
	 * @author jky
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/yebTemplant")
	public String yebTemplant(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","yebTemplant");
		List sysparams = sysSysparamService.queryForList(finder,SysSysparam.class);
		SysSysparam sysparam = (SysSysparam) sysparams.get(0);
//		if(sysparam != null && StringUtils.isNotBlank(sysparam.getValue())){
//			sysparam.setValue(String.valueOf(Float.valueOf(sysparam.getValue()) * 100));
//		}
		returnObject.setData(sysparam);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/yebTemplant";
	}

	/**
	 * 网络借贷风险和禁止性行为提示书
	 * @author jky
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/tipsBook")
	public String tipsBook(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","tipsBook");
		List sysparams = sysSysparamService.queryForList(finder,SysSysparam.class);
		SysSysparam sysparam = (SysSysparam) sysparams.get(0);
//		if(sysparam != null && StringUtils.isNotBlank(sysparam.getValue())){
//			sysparam.setValue(String.valueOf(Float.valueOf(sysparam.getValue()) * 100));
//		}
		returnObject.setData(sysparam);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/tipsBook";
	}

	/**
	 * 资金来源合法承诺书
	 * @author jky
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/commitmentBook")
	public String commitmentBook(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","commitmentBook");
		List sysparams = sysSysparamService.queryForList(finder,SysSysparam.class);
		SysSysparam sysparam = (SysSysparam) sysparams.get(0);
//		if(sysparam != null && StringUtils.isNotBlank(sysparam.getValue())){
//			sysparam.setValue(String.valueOf(Float.valueOf(sysparam.getValue()) * 100));
//		}
		returnObject.setData(sysparam);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/commitmentBook";
	}

	/**
	 * 风控信息
	 * @author jky
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/windControlInformation")
	public String windControlInformation(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","windControlInformation");
		List sysparams = sysSysparamService.queryForList(finder,SysSysparam.class);
		SysSysparam sysparam = (SysSysparam) sysparams.get(0);
//		if(sysparam != null && StringUtils.isNotBlank(sysparam.getValue())){
//			sysparam.setValue(String.valueOf(Float.valueOf(sysparam.getValue()) * 100));
//		}
		returnObject.setData(sysparam);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/windControlInformation";
	}

	/**
	 * 注册送卡券开关
	 * @author jky
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/isOpenRegisterGift")
	public String isOpenRegisterGift(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","isOpenRegisterGift");
		List sysparams = sysSysparamService.queryForList(finder,SysSysparam.class);
		SysSysparam sysparam = (SysSysparam) sysparams.get(0);
//		if(sysparam != null && StringUtils.isNotBlank(sysparam.getValue())){
//			sysparam.setValue(String.valueOf(Float.valueOf(sysparam.getValue()) * 100));
//		}
		returnObject.setData(sysparam);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/isOpenRegisterGift";
	}

	/**
	 * 风控信息
	 * @author jky
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/operationalReport")
	public String operationalReport(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","operationalReport");
		List sysparams = sysSysparamService.queryForList(finder,SysSysparam.class);
		SysSysparam sysparam = (SysSysparam) sysparams.get(0);
//		if(sysparam != null && StringUtils.isNotBlank(sysparam.getValue())){
//			sysparam.setValue(String.valueOf(Float.valueOf(sysparam.getValue()) * 100));
//		}
		returnObject.setData(sysparam);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/operationalReport";
	}
	
	/**
	 * 风控信息
	 * @author jky
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update/operationalReport2017")
	public String operationalReport2017(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder = new Finder("SELECT code,value FROM t_sys_sysparam WHERE `code`=:code");
		finder.setParam("code","operationalReport2017");
		List sysparams = sysSysparamService.queryForList(finder,SysSysparam.class);
		SysSysparam sysparam = (SysSysparam) sysparams.get(0);
//		if(sysparam != null && StringUtils.isNotBlank(sysparam.getValue())){
//			sysparam.setValue(String.valueOf(Float.valueOf(sysparam.getValue()) * 100));
//		}
		returnObject.setData(sysparam);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/operationalReport2017";
	}
	
	
	/**
	 * 分拥.
	 * @author jky
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/commission/save/json")
	@ResponseBody
	public Object saveCommission(Model model,HttpServletRequest request,HttpServletResponse response, 
			Double c1, Double c2, Double c3, Double c4)  throws Exception {

		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		Finder finder1 = new Finder("UPDATE t_sys_sysparam SET value = :c1 WHERE code = \"commission1\"");
		Finder finder2 = new Finder("UPDATE t_sys_sysparam SET value = :c2 WHERE code = \"commission2\"");
		Finder finder3 = new Finder("UPDATE t_sys_sysparam SET value = :c3 WHERE code = \"commission3\"");
		Finder finder4 = new Finder("UPDATE t_sys_sysparam SET value = :c4 WHERE code = \"commission4\"");
		
		if (c1 != null) {
			finder1.setParam("c1", c1);
		}
		if (c2 != null) {
			finder2.setParam("c2", c2);
		}
		if (c3 != null) {
			finder3.setParam("c3", c3);
		}
		if (c4 != null) {
			finder4.setParam("c4", c4);
		}
		
		try {
			sysSysparamService.update(finder1);
			sysSysparamService.update(finder2);
			sysSysparamService.update(finder3);
			sysSysparamService.update(finder4);
			cacheManager.getCache(GlobalStatic.cacheKey).clear();
		}
		catch(Exception e) {
			e.printStackTrace();
			returnObject = ReturnDatas.getErrorReturnDatas();
		}
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return returnObject;
	}


	/** 分拥. */
	@RequestMapping(value = "/commission/show")
	public String showCommission(Model model, HttpServletRequest request) {
		List<Map<String, Object>> list = null;
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		try {
			list = sysSysparamService.queryForList(new Finder("SELECT * FROM t_sys_sysparam WHERE instr(code, \"commission\") ORDER BY code"));
			returnObject.setData(list);
		}
		catch (Exception e) {
			returnObject = ReturnDatas.getErrorReturnDatas();
			e.printStackTrace();
		}

		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/syssysparam/commission-edit";
	}
}
