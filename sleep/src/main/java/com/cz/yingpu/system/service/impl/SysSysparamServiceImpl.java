package com.cz.yingpu.system.service.impl;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import com.cz.yingpu.frame.util.GlobalStatic;
import com.cz.yingpu.system.entity.SysParamBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.cz.yingpu.system.entity.SysSysparam;
import com.cz.yingpu.system.service.ISysSysparamService;
import com.cz.yingpu.frame.entity.IBaseEntity;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.system.service.BaseSpringrainServiceImpl;

import javax.annotation.Resource;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:49
 * @see com.cz.yingpu.system.service.impl.SysSysparam
 */
@Service("sysSysparamService")
public class SysSysparamServiceImpl extends BaseSpringrainServiceImpl implements ISysSysparamService {

	@Resource
	private CacheManager cacheManager ;
   
    @Override
	public String  save(Object entity ) throws Exception{
	      SysSysparam sysSysparam=(SysSysparam) entity;
	       return super.save(sysSysparam).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
	      SysSysparam sysSysparam=(SysSysparam) entity;
		 return super.saveorupdate(sysSysparam).toString();
	}
	
	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
	 SysSysparam sysSysparam=(SysSysparam) entity;
	return super.update(sysSysparam);
    }
    @Override
	public SysSysparam findSysSysparamById(Object id) throws Exception{
	 return super.findById(id,SysSysparam.class);
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


	@Override
	@Cacheable(value = GlobalStatic.cacheKey, key = "'sysParamData'")
	public SysParamBean findParamBean() throws Exception {
		Finder finder=Finder.getSelectFinder(SysSysparam.class);
		List<SysSysparam> list = super.queryForList(finder, SysSysparam.class);
		//利用反射将list对象转化为Bean
		SysParamBean param = new SysParamBean() ;
		//获取类
		Class clazz = param.getClass() ;
		Iterator<SysSysparam> iter = list.iterator() ;
		while(iter.hasNext()){

			SysSysparam sysSysparam = iter.next() ;
			String code = sysSysparam.getCode() ;
			String val = sysSysparam.getValue() ;
			//获取属性
			Field field = clazz.getDeclaredField(code) ;
			//打破封装性，但是会导致java对象的属性不安全
			field.setAccessible(true);
			//给configBean对象的属性赋值
			field.set(param, val);
		}
		return param ;
	}

	@CachePut(value = GlobalStatic.cacheKey, key = "'sysParamData'")
	@Override
	public SysParamBean saveOrUpdate(SysSysparam param) throws Exception {
		SysParamBean sys = null ;
		if(param != null){
				//更新数据库
				update(param, true);
				sys = findParamBean() ;
			}
		return sys;
	}
}
