package com.cz.yingpu.system.service.impl;

import java.io.File;
import java.util.List;
import org.springframework.stereotype.Service;
import com.cz.yingpu.system.entity.Card;
import com.cz.yingpu.system.service.ICardService;
import com.cz.yingpu.frame.entity.IBaseEntity;
import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.frame.util.Page;
import com.cz.yingpu.system.service.BaseSpringrainServiceImpl;


/**
 * TODO 在此加入类描述
 * @copyright {@link 9iu.org}
 * @author springrain<Auto generate>
 * @version  2017-07-12 10:28:03
 * @see com.cz.yingpu.system.service.impl.Card
 */
@Service("cardService")
public class CardServiceImpl extends BaseSpringrainServiceImpl implements ICardService {

   
    @Override
	public String  save(Object entity ) throws Exception{
	      Card card=(Card) entity;
	       return super.save(card).toString();
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
	      Card card=(Card) entity;
		 return super.saveorupdate(card).toString();
	}
	
	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
	 Card card=(Card) entity;
	return super.update(card);
    }
    @Override
	public Card findCardById(Object id) throws Exception{
	 return super.findById(id,Card.class);
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
