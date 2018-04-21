package com.cz.yingpu.system.service.impl;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cz.yingpu.frame.entity.IBaseEntity;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.system.entity.LunboPic;
import com.cz.yingpu.system.service.BaseSpringrainServiceImpl;
import com.cz.yingpu.system.service.ILunboPicService;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-03-21 15:09:46
 * @see com.cz.yingpu.system.service.impl.LunboPic
 */
@Service("lunboPicService")
public class LunboPicServiceImpl extends BaseSpringrainServiceImpl implements ILunboPicService {

   
    @Override
	public String  save(Object entity ) throws Exception{
	      LunboPic lunboPic=(LunboPic) entity;
	       return super.save(lunboPic).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
	      LunboPic lunboPic=(LunboPic) entity;
		 return super.saveorupdate(lunboPic).toString();
	}
	
	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
	 LunboPic lunboPic=(LunboPic) entity;
	return super.update(lunboPic);
    }
    @Override
	public LunboPic findLunboPicById(Object id) throws Exception{
	 return super.findById(id,LunboPic.class);
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
	public List<LunboPic> list(Page page, LunboPic lunboPic) throws Exception {
		List<LunboPic> list = super.queryForListByEntity(lunboPic,page) ;
		if(list !=  null && list.size() != 0){
			Iterator<LunboPic> iter = list.iterator() ;
			LunboPic lunbo = null ;
			/*while (iter.hasNext()){
				lunbo = iter.next() ;
				if(lunbo.getSkipType() != null){
					if(lunbo.getSkipType() == 2){ //投资产品
						Project project = super.findById(Integer.valueOf(lunbo.getItem()),Project.class);
						if(project != null){
							lunbo.setProjectName(project.getName());
						}
					}else  if (lunbo.getSkipType() == 3){  //站内公告
						Announce announce = super.findById(Integer.valueOf(lunbo.getItem()),Announce.class) ;
						if(announce != null){
							lunbo.setAnnounceTitle(announce.getTitle());
						}
					}
				}

			}*/
		}

		return list;
	}
}
