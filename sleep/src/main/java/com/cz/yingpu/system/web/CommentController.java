package com.cz.yingpu.system.web;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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
import com.cz.yingpu.frame.service.IBaseService;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.GlobalStatic;
import com.cz.yingpu.frame.util.MessageUtils;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.system.entity.Comment;

/**
 * TODO 在此加入类描述
 * 
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version 2017-03-21 15:09:43
 * @see com.cz.yingpu.system.web.Comment
 */
@Controller
@RequestMapping(value = "/system/comment")
public class CommentController extends BaseController {
	@Resource
	private IBaseService commentService;

	private String listurl = "/comment/list";

	/**
	 * 列表数据,调用listjson方法,保证和app端数据统一
	 * 
	 * @param request
	 * @param model
	 * @param LawRule
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model,
			Comment LawRule, Date startTime, Date endTime, String hotelName) throws Exception {
		ReturnDatas returnObject = listjson(request, LawRule, hotelName, startTime, 
				endTime);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}

	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param comment
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	public @ResponseBody
	ReturnDatas listjson(HttpServletRequest request, Comment comment, String hotelName,
			Date startTime, Date endTime) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		Page page = newPage(request);
		page.setOrder("tc.`createTime`");
		page.setSort("DESC");

		Map<String, Object> map = new HashMap<>();
		// ==执行分页查询
		Finder finder = new Finder(
				"SELECT tc.*, tau.name userName, th.houseName hotelName, tao.orderNumber")
				.append(" FROM t_comment tc LEFT JOIN t_hotelHouse th ON th.id = tc.roomId")
				.append(" LEFT JOIN t_app_orders tao ON tao.id = tc.orderId")
				.append(" LEFT JOIN t_app_user tau ON tau.id = tc.userId");
		
		if (comment.getParentid() == null) {
			finder.append(" LEFT JOIN t_user tu ON tu.id = '"+ SessionUser.getUserId() + "'");
		}
		
		finder.append(" WHERE 1 = 1 " );
		finder.setEscapeSql(false);
		
		if (comment.getParentid() == null) {
			finder.append(" AND tc.type = 1");
		} else {
			finder.append(" AND tc.parentid = :pid");
			finder.setParam("pid", comment.getParentid());
		}

		if (StringUtils.isNotBlank(hotelName)) {
			 finder.append(" AND th.name LIKE :name");
			 finder.setParam("name", "%" + hotelName + "%");
		}

		if (startTime != null && endTime != null) {
			finder.append(" AND tc.createTime BETWEEN :start AND :end");
			finder.setParam("start", startTime).setParam("end", endTime);
		}

		List<Comment> datas = commentService.findListDataByFinder(finder, page,
				Comment.class, Comment.class.newInstance());

		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("hotelName", hotelName);
		// map.put("phone", comment.getPhone());

		returnObject.setQueryBean(comment);
		returnObject.setPage(page);
		returnObject.setData(datas);
		returnObject.setMap(map);

		return returnObject;
	}

	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request,
			HttpServletResponse response, Model model, Comment h, String hotelName,
			Date startTime, Date endTime) throws Exception {
		// ==构造分页请求
		Page page = newPage(request);
		ReturnDatas rt = listjson(request, h, hotelName, startTime, endTime);
		File file = commentService.findDataExportExcel((List<?>) rt.getData(),
				listurl, page, Comment.class.newInstance());
		String fileName = "评论导出" + GlobalStatic.excelext;
		downFile(response, file, fileName, true);
	}

	/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/LawRule/LawRuleLook";
	}

	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/json")
	public @ResponseBody
	ReturnDatas lookjson(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String strId = request.getParameter("id");
		String cId = request.getParameter("cid");
		String parentId = request.getParameter("parentid");
		java.lang.Integer id = null;
		boolean hasId = false;
		if (StringUtils.isNotBlank(strId) || StringUtils.isNotBlank(cId)) {
			id = cId != null ? java.lang.Integer.valueOf(cId.trim()) : java.lang.Integer.valueOf(strId.trim());
			Comment LawRule = commentService.findById(id, Comment.class);
			returnObject.setData(LawRule);
			hasId = true;
		}

		if (StringUtils.isNotBlank(parentId)) {
			id = java.lang.Integer.valueOf(parentId.trim());
			Comment c = new Comment();
			c.setParentid(id);
			Comment LawRule = commentService.queryForObject(new Finder(
					"SELECT * FROM t_comment WHERE id = :pid").setParam("pid",
					id), Comment.class);
			if (hasId) {
				returnObject.setQueryBean(LawRule);
			} else {
				returnObject.setData(LawRule);
			}
		} else {
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
	ReturnDatas update(Comment comment) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
			if (comment.getParentid() != null) {
				if (comment.getId() != null) {
					commentService.update(comment, true);
				} else {
					comment.setType(2);
					Object id = commentService.save(comment);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;

	}

	/**
	 * 进入修改页面,APP端可以调用 lookjson 获取json格式数据
	 */
	@RequestMapping(value = "/edit")
	public String updatepre(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/comment/edit";
	}

	/**
	 * 进入修改页面,APP端可以调用 lookjson 获取json格式数据
	 */
	@RequestMapping(value = "/reply/view")
	public String reply(Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/comment/reply";
	}

	/**
	 * 删除操作
	 */
	@RequestMapping(value = "/delete")
	public @ResponseBody
	ReturnDatas delete(HttpServletRequest request) throws Exception {

		// 执行删除
		try {
			String strId = request.getParameter("id");
			java.lang.Integer id = null;
			if (StringUtils.isNotBlank(strId)) {
				id = java.lang.Integer.valueOf(strId.trim());
				Comment c = commentService.findById(id, Comment.class);
				commentService.deleteById(id, Comment.class);
				
				Finder finder = new Finder("UPDATE t_hotel SET commentNum = IF(commentNum IS NULL, 0, IF (commentNum - 1 < 0, 0, commentNum - 1))")
						.append(" WHERE id = :hid")
						.setParam("hid", c.getHotelId());
				commentService.update(finder);

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
		if (StringUtils.isBlank(records)) {
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
			commentService.deleteByIds(ids, Comment.class);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);

	}

}
