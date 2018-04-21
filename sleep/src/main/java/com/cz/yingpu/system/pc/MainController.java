package com.cz.yingpu.system.pc;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.GlobalStatic;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.system.entity.Announce;
import com.cz.yingpu.system.entity.CompanyState;
import com.cz.yingpu.system.entity.Contact;
import com.cz.yingpu.system.entity.ContractSample;
import com.cz.yingpu.system.entity.LunboPic;
import com.cz.yingpu.system.entity.News;
import com.cz.yingpu.system.entity.ServiceIntroduce;
import com.cz.yingpu.system.service.IAnnounceService;
import com.cz.yingpu.system.service.IContactService;
import com.cz.yingpu.system.service.ILunboPicService;

@Controller
@RequestMapping(value="/pc/main")
public class MainController {
	@Resource
	private IContactService contactService;
	
	@Resource
	private ILunboPicService lunboPicService;	
	
	@Autowired
	private IAnnounceService announceService;
	
	@Autowired
	private IAnnounceService companyStateService;
	
	@Autowired
	private IAnnounceService newsService;
	
	@Autowired
	private IAnnounceService serviceIntroduceService;
	
	@Autowired
	private IAnnounceService contractSampleService;
	
	public Page newPage(HttpServletRequest request) {
		// ==获取分页信息
	 return 	newPage(request, "id", "desc");
	}
	
	public Page newPage(HttpServletRequest request,String defaultOrder,String defaultSort) {
		// ==获取分页信息

		String str_pageIndex = request.getParameter("pageIndex");
		int pageIndex = str_pageIndex == null ? 1 : Integer.parseInt(str_pageIndex);
		String order = request.getParameter("order");
		String sort = request.getParameter("sort");
		String pageSize=request.getParameter("pageSize");
		if (StringUtils.isBlank(order)) {
			order = defaultOrder;
		}
		if (StringUtils.isBlank(sort)) {
			sort = defaultSort;
		}
		
		if (StringUtils.isBlank(order)) {
			order = "id";
		}
		if (StringUtils.isBlank(sort)) {
			sort = "desc";
		}
		Page page = new Page(pageIndex);
		if(StringUtils.isNotBlank(pageSize)){
			page.setPageSize(Integer.parseInt(pageSize));
		}
		page.setOrder(order);
		page.setSort(sort);
		return page;
	}

	
	@RequestMapping("contact/json")
	@ResponseBody
	public ReturnDatas contact(Contact contact){
		ReturnDatas returnDatas=new ReturnDatas(ReturnDatas.SUCCESS);
		try {
			contact.setCreateTime(new Date());
			contactService.save(contact);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnDatas.setStatus(ReturnDatas.ERROR);
		}
		return returnDatas;
	}
	
	
	@RequestMapping("lunBoList/json")
	@ResponseBody
	public ReturnDatas lunBoList(){
		ReturnDatas returnDatas=new ReturnDatas(ReturnDatas.SUCCESS);
		try {
			LunboPic lunboPic=new LunboPic();
			lunboPic.setPosition(3);
			List<LunboPic> lplist= lunboPicService.queryForListByEntity(lunboPic, new Page(1, 1000));
			returnDatas.setData(lplist);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnDatas.setStatus(ReturnDatas.ERROR);
		}
		return returnDatas;
	}
	
	
	//公告列表
		@RequestMapping("announces")
		public String announceLists(HttpServletRequest request,Model model ){
			// ==构造分页请求
			HashMap<String, List>  map=new HashMap<String, List>();
			Page page = newPage(request);
			// ==执行分页查询
			try {
				LunboPic l=new LunboPic();
				Page p = new Page(1);
				Page p1 = new Page(1);
				Page p2 = new Page(1);
				p.setPageSize(7);
				p1.setPageSize(7);
				p2.setPageSize(7);

				p.setOrder("`weight`DESC,`postTime`DESC");
				p.setSort("`postTime`DESC");

				p1.setOrder("`weight`DESC,`postTime`DESC");
				p1.setSort("`postTime`DESC");

				p2.setOrder("`weight`DESC,`postTime`DESC");
				p2.setSort("`postTime`DESC");
				
				List<Announce> adata=announceService.findListDataByFinder(null, p1, Announce.class, new Announce());
				request.setAttribute("announces", adata);
				request.setAttribute("companystate", companyStateService.findListDataByFinder(null, p2, CompanyState.class, new CompanyState()));
				request.setAttribute("news", companyStateService.findListDataByFinder(null, p, News.class, new News()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return "pc/infomation/announcement";
		}
		
		
		// 服务简介
		@RequestMapping("service-introduce")
		public String serviceIntroduce(HttpServletRequest request, Model model) {
			request.setAttribute("data", announceList(request, model, "serviceintroduce").getData());
			return "pc/service";
		}
		
		
		
		// 新闻动态
				@RequestMapping("news")
				public String news(HttpServletRequest request, Model model) {
					ReturnDatas rt = announceList(request, model, "news");
					List<?> list = (List<?>) rt.getData();
					HttpSession session = request.getSession();
					if (session.getAttribute("TOP2_NEWS_KEY") == null) {
						session.setAttribute("TOP2_NEWS_KEY", list.subList(0, 2));
					}
					
					List<?> top2News = (List<?>) session.getAttribute("TOP2_NEWS_KEY");
					if (top2News == null) {
						top2News = new ArrayList<>();
					}
					
					request.setAttribute("first", top2News.size() > 0 ? top2News.get(0) : new News());
					request.setAttribute("second", top2News.size() > 1 ? top2News.get(1) : new News());
					request.setAttribute("data", list);
					request.setAttribute("page", rt.getPage());
					return "pc/news";
				}
		
		
		// 新闻动态
		@RequestMapping("news/json")
		@ResponseBody
		public ReturnDatas newsjson(HttpServletRequest request, Model model) {
			ReturnDatas rt = announceList(request, model, "news");
			return rt;
		}
		
		// 新闻动态详情
		@RequestMapping("news-detail/json")
		@ResponseBody
		public ReturnDatas newsDetailjson(HttpServletRequest request,Model model, Integer id, String type) {
			ReturnDatas data=new ReturnDatas(ReturnDatas.SUCCESS);
			try {
				data.setData(newsService.findById(id, News.class));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return data;
		}
		
		// 新闻动态详情
		@RequestMapping("news-detail")
		public String newsDetail(HttpServletRequest request,Model model, Integer id, String type) {
			announceDetials(request, model, id, "news");
			return "pc/news-details";
		}
		
		
		//公告列表
		@RequestMapping("announce/list")
		@ResponseBody
		public ReturnDatas announceList(HttpServletRequest request,Model model, String type){
			Page p = newPage(request);
			ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();
			if (request.getParameter("pageSize") == null) {
				p.setPageSize(8);
			}
			p.setOrder("`weight`DESC,`postTime`DESC");
			p.setSort("`postTime`DESC");
			try {
				Class<?> clazz = getEntityClass(type);
				List<?> adata=announceService.findListDataByFinder(null, p, clazz, clazz.newInstance());
				rt.setData(adata);
				rt.setPage(p);
			}
			catch (Exception e) {
				e.printStackTrace();
				rt.setStatus(ReturnDatas.ERROR);
			}

			request.setAttribute(GlobalStatic.returnDatas, rt);
			
			return rt;
		}
		
		
		private Class<?> getEntityClass(String cate) {
			Class<?> clazz = Announce.class;
			switch(cate) {
				case "announce":
					break;
				case "companystate":
					clazz = CompanyState.class;
					break;
				case "news":
					clazz = News.class;
					break;
				case "contractsample":
					clazz = ContractSample.class;
					break;
					
				case "serviceintroduce":
					clazz = ServiceIntroduce.class;
					break;
			}
			return clazz;
		}
		
		
	//公告详情
		@RequestMapping("announce/detail")
		public void announceDetials(HttpServletRequest request,Model model, Integer id, String type){
			// ==构造分页请求
			HashMap<String, Object>  map=new HashMap<>();
			Page page = newPage(request);
			// ==执行分页查询
			try {
				
				if (id == null || id < 1 || !StringUtils.isNotBlank(type)) {
					throw new IllegalArgumentException();
				}
				
				Page p = new Page(1);
				Page p1 = new Page(1);
				p.setPageSize(50);
				p1.setPageSize(1);
				p.setOrder("`weight`DESC,`postTime`DESC");
				p.setSort("`postTime`DESC");
				
				p1.setOrder("`weight`DESC,`postTime`DESC");
				p1.setSort("`postTime`DESC");
			

				IAnnounceService service = announceService;
				Class<?> clazz = getEntityClass(type);
				
				Object adata=service.findById(id, clazz);
				Map<String, Object> prev = new HashMap<>();
				Map<String, Object> next = new HashMap<>();
				if (adata == null) {
					throw new RuntimeException();
				}
				
				Method getWeight = clazz.getMethod("getWeight"),
					   getPostTime = clazz.getMethod("getPostTime");
				Finder finder = new Finder("SELECT * FROM " + clazz.getAnnotation(Table.class).name());
				Finder finder2 = new Finder("SELECT * FROM " + clazz.getAnnotation(Table.class).name());
				
				System.out.println(getPostTime.invoke(adata));
				finder.append(" WHERE IF(weight > :weight, 1 = 1, postTime > :postTime) AND id <> :id")
						.setParam("weight", getWeight.invoke(adata))
						.setParam("postTime", getPostTime.invoke(adata))
						.setParam("id", id);
				
				finder2.append(" WHERE IF(weight < :weight, 1 = 1, postTime < :postTime) AND id <> :id")
						.setParam("weight", getWeight.invoke(adata))
						.setParam("postTime", getPostTime.invoke(adata))
						.setParam("id", id);

				List<?> list = service.findListDataByFinder(finder, p, clazz, clazz.newInstance());
				List<?> list2 = service.findListDataByFinder(finder2, p1, clazz, clazz.newInstance());
				prev.put("announce", list != null && list.size() != 0 ? list.get(list.size() - 1) : null);
				next.put("announce", list2 != null && list2.size() != 0 ? list2.get(0) : null);
				
				request.setAttribute("announce", adata);
				request.setAttribute("prev", prev);
				request.setAttribute("next", next);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
