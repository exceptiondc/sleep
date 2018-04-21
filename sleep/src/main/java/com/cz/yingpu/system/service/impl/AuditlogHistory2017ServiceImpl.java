package com.cz.yingpu.system.service.impl;

import java.io.File;
import java.util.List;
import org.springframework.stereotype.Service;
import com.cz.yingpu.system.entity.AuditlogHistory2017;
import com.cz.yingpu.system.service.IAuditlogHistory2017Service;
import com.cz.yingpu.frame.entity.IBaseEntity;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.system.service.BaseSpringrainServiceImpl;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:44
 * @see com.cz.yingpu.system.service.impl.AuditlogHistory2017
 */
@Service("auditlogHistory2017Service")
public class AuditlogHistory2017ServiceImpl extends BaseSpringrainServiceImpl implements IAuditlogHistory2017Service {

   
    @Override
	public String  save(Object entity ) throws Exception{
	      AuditlogHistory2017 auditlogHistory2017=(AuditlogHistory2017) entity;
	       return super.save(auditlogHistory2017).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
	      AuditlogHistory2017 auditlogHistory2017=(AuditlogHistory2017) entity;
		 return super.saveorupdate(auditlogHistory2017).toString();
	}
	
	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
	 AuditlogHistory2017 auditlogHistory2017=(AuditlogHistory2017) entity;
	return super.update(auditlogHistory2017);
    }
    @Override
	public AuditlogHistory2017 findAuditlogHistory2017ById(Object id) throws Exception{
	 return super.findById(id,AuditlogHistory2017.class);
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

}
