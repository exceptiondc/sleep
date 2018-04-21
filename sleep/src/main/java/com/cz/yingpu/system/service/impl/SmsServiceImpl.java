package com.cz.yingpu.system.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import jxl.Cell;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cz.yingpu.frame.entity.IBaseEntity;
import com.cz.yingpu.frame.util.ClassUtils;
import com.cz.yingpu.frame.util.ExcelUtils;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.frame.util.ReturnDatas;
import com.cz.yingpu.frame.util.SMSUtil;
import com.cz.yingpu.system.entity.AppUser;
import com.cz.yingpu.system.entity.Sms;
import com.cz.yingpu.system.exception.ParameterErrorException;
import com.cz.yingpu.system.service.BaseSpringrainServiceImpl;
import com.cz.yingpu.system.service.IAppUserService;
import com.cz.yingpu.system.service.ISmsService;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-22 11:49:23
 * @see com.cz.yingpu.system.service.impl.Sms
 */
@Service("smsService")
public class SmsServiceImpl extends BaseSpringrainServiceImpl implements ISmsService {

	@Resource
	private IAppUserService appUserService ;
   
    @Override
	public String  save(Object entity ) throws Exception{
	      Sms sms=(Sms) entity;
	       return super.save(sms).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
	      Sms sms=(Sms) entity;
		 return super.saveorupdate(sms).toString();
	}
	
	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
	 Sms sms=(Sms) entity;
	return super.update(sms);
    }
    @Override
	public Sms findSmsById(Object id) throws Exception{
	 return super.findById(id,Sms.class);
	}
    
    
    public <T> T queryForObject(T entity) throws Exception {
    	String tableName = ClassUtils.getTableName(entity);
		Finder finder = new Finder("SELECT * FROM ");
		finder.append(tableName).append("  WHERE 1=1 ");
		finder.append(" AND NOW() - createTime < 600");
		getFinderWhereByQueryBean(finder, entity);
		// 打印sql
		return (T) queryForObject(finder, entity.getClass());
    	
    }
	
/**
 * 列表查询,每个service都会重载,要把sql语句封装到service中,Finder只是最后的方案
 * @param finder
 * @param page
 * @param clazz
 * @param o
 * @return
 * @throws Exception
 */
        @Override
    public <T> List<T> findListDataByFinder(Finder finder, Page page, Class<T> clazz,
			Object o) throws Exception{
			 return super.findListDataByFinder(finder,page,clazz,o);
			}
	/**
	 * 根据查询列表的宏,导出Excel
	 * @param finder 为空则只查询 clazz表
	 * @param ftlurl 类表的模版宏
	 * @param page 分页对象
	 * @param clazz 要查询的对象
	 * @param o  querybean
	 * @return
	 * @throws Exception
	 */
		@Override
	public <T> File findDataExportExcel(Finder finder,String ftlurl, Page page,
			Class<T> clazz, Object o)
			throws Exception {
			 return super.findDataExportExcel(finder,ftlurl,page,clazz,o);
		}
	public String getRand(){
		String x = "";
		Random r = new Random();
		x = r.nextInt(9999) + "";
		if (x.length() < 5) {
			for (int i = 0; i <= 5 - x.length(); i++) {
				x += "0";
			}
		}
		return x;
	}
	@Override
	public Sms sendCode(Sms sms) throws Exception {
		AppUser appuser = null ;
		if(sms.getPhone() != null){
			AppUser user = new AppUser() ;
			user.setPhone(sms.getPhone());
			appuser = super.queryForObject(user) ;
		}else{
			throw  new ParameterErrorException();
		}
		String code=getRand();
		String smscode = SMSUtil.SendSMS(code,sms.getPhone(), sms.getType());
		sms.setContent(code);
		sms.setCreateTime(new Date());
		save(sms);
		return sms;
	}
	
	public Sms saveSMS(String phone, String Content, Integer type) throws Exception {
		AppUser appuser = null ;
		Sms sms = new Sms();
		sms.setCreateTime(new Date());
		sms.setType(type);
		sms.setPhone(phone);
		sms.setContent(Content);
		if(sms.getPhone() != null){
			AppUser user = new AppUser() ;
			user.setPhone(sms.getPhone());
			appuser = super.queryForObject(user) ;
		}else{
			throw  new ParameterErrorException();
		}
		save(sms);
		return sms;
	}

	
	@Override
	public ReturnDatas sendsms(final Map<String, String> map, final String[] variable) throws Exception {
		ReturnDatas returnDatas=new ReturnDatas(ReturnDatas.SUCCESS);
		int type= Integer.parseInt(map.get("inputType") == null ? "-1" : map.get("inputType"));
		
		//用来发短信的用户List
		List<String> phonelist=new ArrayList<String>();
		//单个用户
		if(type==1){
			if(map.get("phone")==null){
				returnDatas.setStatus(ReturnDatas.ERROR);
				returnDatas.setMessage("手机号不能为空");
				return returnDatas;
			}
			
			phonelist.add(map.get("phone").toString());
		}else if(type==3){
			//读取excel文件，将文件里的手机号读取出来
			if(map.get("fileUrl")==null){
				returnDatas.setStatus(ReturnDatas.ERROR);
				returnDatas.setMessage("请先上传用户文件");
				return returnDatas;
			}
			
			File file = new File(map.get("fileUrl").toString().replace("http://114.215.130.18:22222","/webdata/app"));
			List<Cell[]> cells = ExcelUtils.getExcle(file);
			System.out.println(cells.size());
			if(null!=cells){
				int phoneColumn = 1;
				if (cells.size() != 0) {
					Cell[] firstRowCells = cells.get(0);
					for (Cell cell : firstRowCells) {
						if (cell.getContents() != null && cell.getContents().indexOf("手机号") != -1) {
							phoneColumn = cell.getColumn();
						}
					}
				}
				for (int i=1;i<cells.size();i++){
					String phone = cells.get(i)[phoneColumn].getContents();
					if (phone != null && phone.trim().length() != 0) {
						phonelist.add(phone.trim());
					}
				}
			}
		}
		else if (type == 2) {	//全部
			Finder finder = new Finder("SELECT * FROM t_app_user au WHERE phone IS NOT NULL AND phone <> ''");
			finder.setEscapeSql(false);
			List<AppUser> users = appUserService.queryForList(finder, AppUser.class);
			Iterator<AppUser> iterator = users.iterator();
			while(iterator.hasNext()) {
				AppUser au = iterator.next();
				if (StringUtils.isNotBlank(au.getPhone())) {
					phonelist.add(au.getPhone());
				}
			}
		}
		else if (type == -1) {
			phonelist.clear();
		}
		
		if (phonelist.size() != 0) {
			final List<String> phones = phonelist;
			final ExecutorService executor = Executors.newFixedThreadPool(1000);
			new Thread(new Runnable() {
				public void run() {
					int limit = 100;
					for (int i = 0; i < phones.size(); i += limit) {
						try {
							limit = phones.size() < i + limit ? phones.size() - i : limit;
							final List<String> apartPhones = phones.subList(i, i + limit);
							for (final String phone : apartPhones) {
								executor.execute(new Runnable() {
									public void run() {
										try { saveSMS(phone, map.get("templateID"), 6); } catch (Exception e) {
											e.printStackTrace();
										}
									}
								});
							}
							
							executor.execute(new Runnable() {
								public void run() {
									String code = null;
									try {
//										code = SMSUtil.SendSMSMulituser(apartPhones.toArray(new String[0]), map.get("templateID"), variable);
									} catch (Exception e) {
										e.printStackTrace();
									}
									System.out.println("- Sent SMS to " + apartPhones.toString() + " => " + ("200".equals(code) ? "Success" : "Failure"));
								}
							});
							
							Thread.sleep(1);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					executor.shutdown();
				}
			}).start();
		}
		
		return null;
	}

}
