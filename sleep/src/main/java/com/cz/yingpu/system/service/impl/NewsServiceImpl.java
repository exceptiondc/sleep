package com.cz.yingpu.system.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;
import org.zefer.d.c.n;

import com.cz.yingpu.frame.entity.IBaseEntity;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.system.entity.Announce;
import com.cz.yingpu.system.entity.CompanyState;
import com.cz.yingpu.system.entity.ContractSample;
import com.cz.yingpu.system.entity.ServiceIntroduce;
import com.cz.yingpu.system.entity.News;
import com.cz.yingpu.system.entity.News;
import com.cz.yingpu.system.service.BaseSpringrainServiceImpl;
import com.cz.yingpu.system.service.IAnnounceService;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:43
 * @see com.cz.yingpu.system.service.impl.News
 */
@Service("newsService")
public class NewsServiceImpl extends BaseSpringrainServiceImpl implements IAnnounceService {

   
    @Override
	public String  save(Object entity ) throws Exception{
	      News News=(News) entity;
	       return super.save(News).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
	      News News=(News) entity;
		 return super.saveorupdate(News).toString();
	}
	
	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
	 News News=(News) entity;
	return super.update(News);
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
	public Announce findAnnounceById(Object id) throws Exception {
		 return super.findById(id,Announce.class);
	}

	
	public News findNewsById(Object id) throws Exception {
		 return super.findById(id, News.class);
	}

	@Override
	public CompanyState findCompanyStateById(Object id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContractSample findContractSampleById(Object id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceIntroduce findServiceIntroduceById(Object id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
