package com.cz.yingpu.system.app;

import com.cz.yingpu.frame.annotation.UserCheck;
import com.cz.yingpu.frame.controller.BaseController;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.ImgCompress;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.servlet.AdminFileUpload;
import com.cz.yingpu.system.entity.Comment;
import com.cz.yingpu.system.entity.CommentTag;
import com.cz.yingpu.system.entity.CommentThumbup;
import com.cz.yingpu.system.service.IAppOrdersService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="/app/comment")
public class CommentAppController extends BaseController {

	@Resource
	private IAppOrdersService appOrdersService;
	
	// 加载用户评论列表
	@RequestMapping("/list/json")
	@ResponseBody
	public ReturnDatas commentList(HttpServletRequest request, Model model, Integer hotelId,
								   String tags, Integer userId, String type){
		ReturnDatas returnDatas=new ReturnDatas();
		try {
			Finder finder = new Finder("SELECT c.*, au.phone, au.name, c.thumbNum thumbupCount,")
					.append(" (SELECT COUNT(id) FROM t_comment_thumbup WHERE commentId = c.id AND userId = :uid) isThumbup")
					.append(" FROM t_comment c, t_hotelHouse h, t_app_user au")
					.append(" WHERE h.id = :hotelId AND c.userId = au.id AND hh.id = c.hotelId")
					.setParam("hotelId", hotelId == null ? 0 : hotelId)
					.setParam("uid", userId);
			Page page = newPage(request);

			page.setOrder("c.createTime");
			page.setSort("DESC");
			if (StringUtils.isNotBlank(tags)) {
				String tagArr[] = tags.contains(",") ? tags.split(",") : new String[] { tags };
				finder.append(" AND (");
				for (int i = 0; i < tagArr.length; i++) {
					String tag = tagArr[i];
					finder.append("FIND_IN_SET(:tag_" + tag + ", c.tags)")
						  .setParam("tag_" + tag, tag);
					if (i != tagArr.length - 1) {
						finder.append(" AND ");
					}
				}

				finder.append(")");
			}

			if (StringUtils.isNotBlank(type)) {
				switch (type) {
					case "all":
						break;
					case "has-image":
						finder.setEscapeSql(false);
						finder.append(" AND c.images <> '' AND c.images IS NOT NULL");
						break;
					case "need-improve":
						finder.append(" AND (c.impressionScore + c.environmentScore + c.satisficingScore) / 3 < 3");
						break;
				}
			}

			returnDatas.setData(appOrdersService.queryForList(finder, page));
			returnDatas.setPage(page);
		} catch (Exception e) {
			e.printStackTrace();
			returnDatas.setStatus(ReturnDatas.ERROR);
		}
		
		return returnDatas;
	}

	/** 添加新评论 */
	@UserCheck
	@RequestMapping("/add/json")
	@ResponseBody
	public ReturnDatas addComment(Comment comment) {
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();

		try {
			if (comment == null || StringUtils.isBlank(comment.getContent())) {
				throw new RuntimeErrorException(null, "评论添加失败！");
			}

			if (StringUtils.isNotBlank(comment.getImages())) {
				String imgStrDatas = comment.getImages();
				String[] imgStrDataArr = imgStrDatas.split(";");
				String finalImgUrls = "";
				for (String imgStrData : imgStrDataArr) {
					String savePath = AdminFileUpload.realfilepath;
					String imgName = UUID.randomUUID().toString() + ".png";
					String urlPath = AdminFileUpload.httpfilepath + "/" + comment.getUserId() + "/" + imgName;
					ImgCompress.Base64ToImage(imgStrData, savePath + "/" + comment.getUserId() + "/" + imgName);
					finalImgUrls += urlPath + ";";
				}

				finalImgUrls = finalImgUrls.substring(0, finalImgUrls.length() - 1);
				comment.setImages(finalImgUrls);
			}

			comment.setThumbNum(0);
			comment.setType(1);
			comment.setCreateTime(new Date());
			appOrdersService.save(comment);

			Finder finder = new Finder("UPDATE t_hotel SET commentNum = IF(commentNum IS NULL, 0, commentNum) + 1")
					.append(" WHERE id = :hid")
					.setParam("hid", comment.getHotelId());
			appOrdersService.update(finder);

		}
		catch(Exception e) {
			if (e instanceof RuntimeErrorException) {
				System.err.println(e.getMessage());
				rt.setMessage(e.getMessage());
			}
			else {
				e.printStackTrace();
			}
			rt.setStatus(ReturnDatas.ERROR);
		}

		return rt;
	}


	/** 评论加载标签 */
	@UserCheck
	@RequestMapping("/tag/add/json")
	@ResponseBody
	public ReturnDatas addCommentTag(CommentTag tag) {
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();

		try {
			if (tag == null || StringUtils.isBlank(tag.getName()) || tag.getHotelRoomId() == null) {
				throw new RuntimeErrorException(null, "添加标签失败！");
			}

			appOrdersService.save(tag);
		}
		catch(Exception e) {
			if (e instanceof RuntimeErrorException) {
				System.err.println(e.getMessage());
				rt.setMessage(e.getMessage());
			}
			else {
				e.printStackTrace();
			}
			rt.setStatus(ReturnDatas.ERROR);
		}

		return rt;
	}


	/** 加载评论标签 */
	@RequestMapping("/tags/json")
	@ResponseBody
	public ReturnDatas commentTagList(Integer hotelRoomId) {
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();

		try {
			if (hotelRoomId == null) {
				throw new RuntimeErrorException(null, "无法加载自定义标签！");
			}

			CommentTag tag = new CommentTag();
			tag.setHotelRoomId(-1);
			List<CommentTag> globalTags = appOrdersService.queryForListByEntity(tag, null);
			tag.setHotelRoomId(hotelRoomId);
			List<CommentTag> roomTags = appOrdersService.queryForListByEntity(tag, null);
			if (roomTags != null && !roomTags.isEmpty()) {
				globalTags.addAll(roomTags);
			}

			rt.setData(globalTags);
		}
		catch(Exception e) {
			if (e instanceof RuntimeErrorException) {
				System.err.println(e.getMessage());
				rt.setMessage(e.getMessage());
			}
			else {
				e.printStackTrace();
			}
			rt.setStatus(ReturnDatas.ERROR);
		}

		return rt;
	}

	/** 评论点赞/取消赞  */
	@RequestMapping("/thumbup/json")
	@ResponseBody
	public ReturnDatas thumbup(CommentThumbup ct) {
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();

		try {
			if (ct.getCommentId() == null || ct.getUserId() == null) {
				throw new RuntimeErrorException(null, "好评失败！");
			}

			boolean isCancel = false;
			CommentThumbup commentThumbup = appOrdersService.queryForObject(ct);
			Finder finder = new Finder("UPDATE t_comment SET thumbNum = ");
			String sqlExp = "";

			if (commentThumbup == null) {
				ct.setCreateTime(new Date());
				rt.setData(appOrdersService.save(ct));
				sqlExp = " thumbNum + 1";
			}
			else {
				appOrdersService.deleteByEntity(commentThumbup);
				sqlExp = " thumbNum - 1";
			}

			finder.append(sqlExp)
					.append(" WHERE id = :id")
					.setParam("id", ct.getCommentId());
			appOrdersService.update(finder);
		}
		catch(Exception e) {
			if (e instanceof RuntimeErrorException) {
				System.err.println(e.getMessage());
				rt.setMessage(e.getMessage());
			}
			else {
				e.printStackTrace();
			}
			rt.setStatus(ReturnDatas.ERROR);
		}

		return rt;
	}

}
