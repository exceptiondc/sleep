package  com.cz.yingpu.system.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cz.yingpu.frame.controller.BaseController;
import com.cz.yingpu.frame.dao.IBaseJdbcDao;
import com.cz.yingpu.frame.service.IBaseService;
import com.cz.yingpu.frame.util.CalculationUtil;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.GlobalStatic;
import com.cz.yingpu.frame.util.MessageUtils;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.frame.util.StringUtil;
import com.cz.yingpu.system.entity.Facility;
import com.cz.yingpu.system.entity.Favorite;
import com.cz.yingpu.system.entity.Footprint;
import com.cz.yingpu.system.entity.Hotel;
import com.cz.yingpu.system.entity.HotelBrand;
import com.cz.yingpu.system.entity.HotelFacility;
import com.cz.yingpu.system.entity.HotelHouse;
import com.cz.yingpu.system.entity.SearchLocation;
import com.cz.yingpu.system.entity.SearchLocationCategory;
import com.cz.yingpu.system.entity.User;
import com.cz.yingpu.system.service.IHotelHouseService;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:43
 * @see com.cz.yingpu.system.web.Hotel
 */
@Controller
@RequestMapping(value="/system/hotel")
public class HotelController  extends BaseController {
	@Resource
	protected IBaseService hotelService;
	
	private String listurl="/hotel/list";
	@Resource
	private IBaseJdbcDao baseSpringrainDao;
	
	
	@Resource
	private IHotelHouseService hotelHouseService;  
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
	public String list(HttpServletRequest request, Model model,Hotel LawRule, Date startTime, Date endTime) 
			throws Exception {
		ReturnDatas returnObject = listjson(request, LawRule, startTime, endTime);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	
	
	
	/**
	 * json数据,为APP提供数据
	 * 
	 * @param request
	 * @param model
	 * @param hotel
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	public @ResponseBody
	ReturnDatas listjson(HttpServletRequest request, Hotel hotel, Date startTime, Date endTime)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		// ==构造分页请求
		Page page = newPage(request);
		Map<String, Object> map = new HashMap<>();
		// ==执行分页查询
		Finder finder = new Finder("SELECT h.*,hb.name brandName FROM t_hotel h left join t_hotelBrand  hb  on h.brandId=hb.id  "
				+ "left JOIN `t_hotelHouse` hh ON hh.hotelid=h.id   WHERE 1=1");
		if(request.getAttribute("hotelHouseStatus")!=null){
			finder.append(" and hh.`status`=:hotelHouseStatus");
			finder.setParam("hotelHouseStatus", request.getAttribute("hotelHouseStatus"));
		}
		if (StringUtils.isNotBlank(hotel.getName())) {
			finder.append(" AND h.name LIKE :name");
			finder.setParam("name", "%" + hotel.getName() + "%");
		}

		if (StringUtils.isNotBlank(hotel.getPhone())) {
			finder.append(" AND h.phone = :phone");
			finder.setParam("phone", hotel.getPhone());
		}
		if (startTime != null && endTime != null) {
			finder.append(" AND h.createTime BETWEEN :start AND :end");
			finder.setParam("start", startTime).setParam("end", endTime);
		}
		if(request.getParameter("starLevel")!=null&&!request.getParameter("starLevel").equals("2,6")){
			if(request.getParameter("starLevel").indexOf(",")>0){
				String str[]=request.getParameter("starLevel").split(",");
				finder.append(" AND (");
				for (int i = 0; i < str.length; i++) {
					if(i==str.length-1){
						if(str[i].equals("2")){
							finder.append(" h.level >= :level"+i);
							finder.setParam("level"+i, str[i]);
						}else{
							finder.append(" h.level = :level"+i);
							finder.setParam("level"+i, str[i]);
						}
					}else{
						if(str[i].equals("2")){
							finder.append(" h.level >= :level"+i+" or");
							finder.setParam("level"+i, str[i]);
						}else{
							finder.append(" h.level = :level"+i+" or");
							finder.setParam("level"+i, str[i]);
						}
					}
				}
				finder.append(" )");
			}else{
				if(request.getParameter("starLevel").equals("2")){
					finder.append(" and h.level >= :level");
					finder.setParam("level", request.getParameter("starLevel"));
				}else{
					finder.append(" and h.level = :level");
					finder.setParam("level", request.getParameter("starLevel"));
				}
			}
		}

		if(request.getParameter("priceRange")!=null){
			String str[]=request.getParameter("priceRange").split(",");
			finder.append(" and h.avgPrice > :sprice and h.avgPrice < :eprice");
			finder.setParam("sprice",str[0]);
			finder.setParam("eprice", str[1]);
		}
		if(request.getParameter("hotelType")!=null&&!request.getParameter("hotelType").equals("")){
			if(request.getParameter("hotelType").indexOf(",")>0){
				String  str[]=request.getParameter("hotelType").split(",");
				finder.append(" AND (");
				for (int i = 0; i < str.length; i++) {
					if(i==str.length-1){
							finder.append(" h.hotelType = :hotelType"+i);
							finder.setParam("hotelType"+i, str[i]);

					}else{
							finder.append(" h.hotelType = :hotelType"+i+" or");
							finder.setParam("hotelType"+i, str[i]);
					}
				}
				finder.append(" )");
			}else{
				finder.append(" and h.hotelType = :hotelType");
				finder.setParam("hotelType", request.getParameter("hotelType"));
			}
		}
		if(request.getParameter("bedType")!=null&&!request.getParameter("bedType").equals("")){
			finder.append(" and hh.bedType=:bedType ");
			finder.setParam("bedType",request.getParameter("bedType") );
		}

		if(StringUtil.isValid(request.getParameter("hotelFacility"))){
			/*if(request.getParameter("hotelFacility").indexOf(",")>0){
				String  str[]=request.getParameter("hotelFacility").split(",");
				finder.append(" AND (SELECT COUNT(1)  FROM `t_hotel_facility` hf WHERE hf.`hotelId`=h.`id` AND ( ");
				for (int i = 0; i < str.length; i++) {
					if(i==str.length-1){
							finder.append(" hf.`facilityId` = :facilityId"+i);
							finder.setParam("facilityId"+i, str[i]);
					}else{
							finder.append(" hf.facilityId = :facilityId"+i+" or");
							finder.setParam("facilityId"+i, str[i]);
					}
				}
				finder.append(" )) =:hoCount");
				finder.setParam("hoCount", str.length);
			}else{
				finder.append(" AND (SELECT COUNT(1)  FROM `t_hotel_facility` hf WHERE hf.`hotelId`=h.`id` AND ( hf.`facilityId`=:facilityId)=1");
			}*/
			
			if(request.getParameter("hotelFacility").indexOf(",")>0){
				String  str[]=request.getParameter("hotelFacility").split(",");
				finder.append(" AND (SELECT COUNT(1)  FROM `t_hotel_facility` hf WHERE hf.`hotelId`=h.`id` in ( ");
				finder.append(request.getParameter("hotelFacility"));
				finder.append(" )) =:hoCount");
				finder.setParam("hoCount", str.length);
			}else{
				finder.append(" AND (SELECT COUNT(1)  FROM `t_hotel_facility` hf WHERE hf.`hotelId`=h.`id` AND ( hf.`facilityId`=:facilityId)=1");
			}
		}
		//根据经纬度筛选周围的酒店
		if(StringUtils.isNotBlank(hotel.getLongitude())){
			//先计算查询点的经纬度范围
	        double r = 6371;//地球半径千米
	        double dis = CalculationUtil.divide(  Double.parseDouble(hotel.getRange()), 1000.00);//距离
	        double dlng =  2*Math.asin(Math.sin(dis/(2*r))/Math.cos(Double.parseDouble(hotel.getLatitude())*Math.PI/180));
	        dlng = dlng*180/Math.PI;//角度转为弧度
	        double dlat = dis/r;
	        dlat = dlat*180/Math.PI;
	        double minlat =Double.parseDouble(hotel.getLatitude())-dlat;
	        double maxlat = Double.parseDouble(hotel.getLatitude())+dlat;
	        double minlng = Double.parseDouble(hotel.getLongitude()) -dlng;
	        double maxlng = Double.parseDouble(hotel.getLongitude()) + dlng;
			finder.append(" AND h.longitude >= :minlng and h.longitude <=:maxlng and h.latitude >= :minlat and latitude< :maxlat");
			finder.setParam("minlng", minlng);
			finder.setParam("maxlng", maxlng);
			finder.setParam("minlat", minlat);
			finder.setParam("maxlat", maxlat);
		}

		finder.append(" GROUP BY h.id");
		//hotelService.findListDataByFinder(finder,page,Hotel.class,Hotel.class.newInstance());
		//new Finder("select * from t_hotel")
		List<Hotel> datas= hotelService.queryForList(finder, Hotel.class, page);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("name", hotel.getName());
		map.put("phone", hotel.getPhone());
		returnObject.setQueryBean(hotel);
		returnObject.setPage(page);
		returnObject.setData(datas);
		returnObject.setMap(map);
		return returnObject;
	}
	
	@RequestMapping("/list/export")
	public void listexport(HttpServletRequest request,HttpServletResponse response, Model model,Hotel h, Date startTime, Date endTime)
			throws Exception{
		// ==构造分页请求
		Page page = newPage(request);
		ReturnDatas rt = listjson(request, h, startTime, endTime);
		File file = hotelService.findDataExportExcel((List<?>) rt.getData(), listurl, page, Hotel.class.newInstance());
		String fileName="酒店导出"+GlobalStatic.excelext;
		downFile(response, file, fileName,true);
	}
	
		/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/LawRule/LawRuleLook";
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
		  Hotel LawRule =new Hotel();
		  if(StringUtils.isNotBlank(strId)){
			 id= java.lang.Integer.valueOf(strId.trim());
		   LawRule = hotelService.findById(id, Hotel.class);
		   Finder finder=new Finder("SELECT sf.*,f.`name`,f.`img`,f.`showSort`,pf.`img` parentImg,pf.`name` parentName,pf.`showSort` parentShowSort,pf.id parentId   FROM `t_hotel_facility` sf INNER JOIN `t_facility` f ON sf.facilityId=f.id  INNER JOIN t_facility pf  ON f.`parentId`=pf.`id` WHERE sf.hotelId=:hotelId");
		   finder.setParam("hotelId", id);
		   List<HotelFacility> hotelFacilities=  hotelService.queryForList(finder, HotelFacility.class);
		   LawRule.setHotelFacilities(hotelFacilities);

		   List<Facility> flist=hotelService.queryForList(new Finder("select * from t_facility "), Facility.class);
		   
		   LawRule.setFacilityLists(flist);
		   if(request.getAttribute("app")!=null&&request.getAttribute("app").equals("true")){
			   	HotelHouse hotelHouse=new HotelHouse();
				hotelHouse.setHotelid(Integer.parseInt(request.getParameter("id")));
				hotelHouse.setStatus(1001);
				LawRule.setHotelHouses(hotelHouseService.list(null, hotelHouse));
		   }
		   
		}else{
			returnObject.setStatus(ReturnDatas.ERROR);
		}
		  List<HotelBrand> hblist= hotelService.queryForList(new Finder("select id,name from t_hotelBrand"), HotelBrand.class);
		  LawRule.setHotelBrands(hblist);
		
		  //我的足迹
		  if(StringUtil.isValid(request.getParameter("userId"))){
			  Footprint footprint=new Footprint();
			  footprint.setHotelId(id);
			  footprint.setUserId(Integer.parseInt(request.getParameter("userId")));
			  footprint.setIsDelete(0);
			  List<Footprint> flist=baseSpringrainDao.findListDataByFinder(null, null, Footprint.class, footprint);
			  if(flist.size()<=0){
				  footprint.setCreateTime(new Date());
				  baseSpringrainDao.save(footprint);
			  }else{
				  footprint.setId(flist.get(0).getId());
				  footprint.setCreateTime(new Date());
				  baseSpringrainDao.update(footprint);
			  }
			  //我的收藏
			  
			  Favorite favorite=new Favorite();
			  favorite.setHotelId(id);
			  favorite.setIsCancel(0);
			  favorite.setUserId(Integer.parseInt(request.getParameter("userId")));
			  List<Favorite> favoriteList=baseSpringrainDao.findListDataByFinder(null, null, Favorite.class, favorite);
			  if(favoriteList.size()>0){
				  LawRule.setCollect(1);
			  }else{
				  LawRule.setCollect(0);
			  }
		  }
		 
		  returnObject.setData(LawRule);
		return returnObject;
	}
	
	
	/**
	 * 新增/修改 操作吗,返回json格式数据
	 * 
	 */
	@RequestMapping("/update")
	public @ResponseBody
	ReturnDatas update(Hotel hotel, String[] images) throws Exception{
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		if (images != null) {
			String imgStr = "";
			for (String img : images) {
				imgStr += img + ";";
			}
			if (imgStr.length() != 0) {
				imgStr = imgStr.substring(0, imgStr.length() - 1);
			}
			hotel.setImg(imgStr);
		}
		
		try {
			if (hotel.getId() != null) {
				hotelService.update(hotel, true);
				if(StringUtils.isNotEmpty(hotel.getFacilitys())){
					HotelFacility hotelFacility=new HotelFacility();
					hotelFacility.setHotelId(hotel.getId());
					hotelService.deleteByEntity(hotelFacility);
					String strs[]=hotel.getFacilitys().split("-");
					 hotelFacility=new HotelFacility();
					for (int i = 0; i < strs.length; i++) {
						hotelFacility.setFacilityId(Integer.parseInt(strs[i]));
						hotelFacility.setHotelId(hotel.getId());
						hotelService.save(hotelFacility);
					}
				}
 			}
			else {
				Object id = hotelService.save(hotel);
				if(StringUtils.isNotEmpty(hotel.getFacilitys())){
					HotelFacility hotelFacility=new HotelFacility();
					hotelFacility.setHotelId(hotel.getId());
					hotelService.deleteByEntity(hotelFacility);
					String strs[]=hotel.getFacilitys().split("-");
					 hotelFacility=new HotelFacility();
					for (int i = 0; i < strs.length; i++) {
						hotelFacility.setFacilityId(Integer.parseInt(strs[i]));
						hotelFacility.setHotelId(hotel.getId());
						hotelService.save(hotelFacility);
					}
				}
				if (id != null) {
					User au = new User();
					au.setHotelId(Integer.parseInt(id.toString()));
					au.setMobile(hotel.getPhone());
					au.setUserType(1);
					if (hotelService.queryForObject(au) != null) {
						hotelService.save(au);
					}
				}
			}
		}
		catch (Exception e) {
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	
	}
	
	/**
	 * 进入修改页面,APP端可以调用 lookjson 获取json格式数据
	 */
	@RequestMapping(value = "/edit")
	public String updatepre(Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception{
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/hotel/edit";
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
				hotelService.deleteById(id,Hotel.class);
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
			hotelService.deleteByIds(ids,Hotel.class);
		} catch (Exception e) {
			return new ReturnDatas(ReturnDatas.ERROR,
					MessageUtils.DELETE_ALL_FAIL);
		}
		return new ReturnDatas(ReturnDatas.SUCCESS,
				MessageUtils.DELETE_ALL_SUCCESS);
		
		
	}

	/**
	 * 添加用于搜索的位置
	 */
	@RequestMapping(value="/search_location/add")
	public @ResponseBody ReturnDatas addSearchLocation(SearchLocation sl) throws Exception {
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();

		try {

			if (sl == null) {
				throw new RuntimeErrorException(null, "新增搜索位置出现异常！");
			}

			sl.setCreateTime(new Date());
			hotelService.save(sl);

		}
		catch(Exception e) {
			rt.setStatus(ReturnDatas.ERROR);
			e.printStackTrace();
		}

		return rt;
	}

	/**
	 * 修改位置信息
	 */
	@RequestMapping(value="/search_location/modify")
	public @ResponseBody ReturnDatas modifySearchLocation(SearchLocation sl) throws Exception {
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();

		try {

			if (sl == null || sl.getId() == null) {
				throw new RuntimeErrorException(null, "无法修改搜索位置！");
			}

			hotelService.update(sl, true);

		}
		catch(Exception e) {
			rt.setStatus(ReturnDatas.ERROR);
			e.printStackTrace();
		}

		return rt;
	}


	/**
	 * 修改位置信息
	 */
	@RequestMapping(value="/search_location/delete")
	public @ResponseBody ReturnDatas deleteSearchLocation(SearchLocation sl) throws Exception {
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();

		try {

			if (sl == null || sl.getId() == null) {
				throw new RuntimeErrorException(null, "无法修改搜索位置！");
			}

			hotelService.deleteByEntity(sl);

		}
		catch(Exception e) {
			rt.setStatus(ReturnDatas.ERROR);
			e.printStackTrace();
		}

		return rt;
	}


	/**
	 * 获取位置列表
	 */
	@RequestMapping(value="/search_location/list")
	public String getSearchLocationList(SearchLocation sl) throws Exception {
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		page.setSort("DESC");
		page.setOrder("createTime");

		try {

			Finder finder = new Finder("SELECT sl.*, slc.name categoryName ")
					.append(" FROM t_search_location sl, t_search_location_cate slc")
					.append(" WHERE slc.id = sl.categoryId");
			rt.setData(hotelService.queryForList(finder, page));

		}
		catch(Exception e) {
			rt.setStatus(ReturnDatas.ERROR);
			e.printStackTrace();
		}

		request.setAttribute(GlobalStatic.returnDatas, rt);
		return "/hotel/list_search_location";
	}

	/**
	 * 修改位置信息
	 */
	@RequestMapping(value="/search_location/edit")
	public String editLocation(SearchLocation sl) throws Exception {
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();

		try {

			if (sl.getId() != null) {
				rt.setData(hotelService.queryForObject(sl));
			}

			rt.setQueryBean(hotelService.findListDataByFinder(
					null, null, SearchLocationCategory.class, SearchLocationCategory.class.newInstance()));

		}
		catch(Exception e) {
			rt.setStatus(ReturnDatas.ERROR);
			e.printStackTrace();
		}

		request.setAttribute(GlobalStatic.returnDatas, rt);

		return "/hotel/edit_search_location";
	}


	/**
	 * 添加用于搜索的位置
	 */
	@RequestMapping(value="/search_location_cate/add")
	public @ResponseBody ReturnDatas addSearchLocationCate(SearchLocationCategory sl) throws Exception {
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();

		try {

			if (sl == null) {
				throw new RuntimeErrorException(null, "新增搜索位置出现异常！");
			}

			sl.setCreateTime(new Date());
			hotelService.save(sl);

		}
		catch(Exception e) {
			rt.setStatus(ReturnDatas.ERROR);
			e.printStackTrace();
		}

		return rt;
	}

	/**
	 * 修改位置信息
	 */
	@RequestMapping(value="/search_location_cate/modify")
	public @ResponseBody ReturnDatas modifySearchLocationCate(SearchLocationCategory sl) throws Exception {
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();

		try {

			if (sl == null || sl.getId() == null) {
				throw new RuntimeErrorException(null, "无法修改搜索位置！");
			}

			hotelService.update(sl, true);

		}
		catch(Exception e) {
			rt.setStatus(ReturnDatas.ERROR);
			e.printStackTrace();
		}

		return rt;
	}


	/**
	 * 修改位置信息
	 */
	@RequestMapping(value="/search_location_cate/delete")
	public @ResponseBody ReturnDatas deleteSearchLocationCaate(SearchLocationCategory sl) throws Exception {
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();

		try {

			if (sl == null || sl.getId() == null) {
				throw new RuntimeErrorException(null, "无法修改搜索位置！");
			}

			hotelService.deleteByEntity(sl);

		}
		catch(Exception e) {
			rt.setStatus(ReturnDatas.ERROR);
			e.printStackTrace();
		}

		return rt;
	}


	/**
	 * 获取位置列表
	 */
	@RequestMapping(value="/search_location_cate/list")
	public String getSearchLocationCateList(SearchLocationCategory sl) throws Exception {
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();
		Page page = newPage(request);
		page.setSort("DESC");
		page.setOrder("createTime");

		try {

			rt.setData(hotelService.findListDataByFinder(
					null, page, SearchLocationCategory.class, SearchLocationCategory.class.newInstance()));

		}
		catch(Exception e) {
			rt.setStatus(ReturnDatas.ERROR);
			e.printStackTrace();
		}

		request.setAttribute(GlobalStatic.returnDatas, rt);
		return "/hotel/list_search_location_cate";
	}

	/**
	 * 修改位置信息
	 */
	@RequestMapping(value="/search_location_cate/edit")
	public String editLocationCate(SearchLocationCategory sl) throws Exception {
		ReturnDatas rt = ReturnDatas.getSuccessReturnDatas();

		try {

			if (sl.getId() != null) {
				rt.setData(hotelService.queryForObject(sl));
			}

		}
		catch(Exception e) {
			rt.setStatus(ReturnDatas.ERROR);
			e.printStackTrace();
		}

		request.setAttribute(GlobalStatic.returnDatas, rt);

		return "/hotel/edit_search_location_cate";
	}
}
