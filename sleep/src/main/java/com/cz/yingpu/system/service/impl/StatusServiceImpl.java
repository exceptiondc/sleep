package com.cz.yingpu.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cz.yingpu.frame.util.Finder;
import com.cz.yingpu.system.entity.Status;
import com.cz.yingpu.system.service.BaseSpringrainServiceImpl;
import com.cz.yingpu.system.service.IStatusService;

@Service("statusService")
public class StatusServiceImpl extends BaseSpringrainServiceImpl implements IStatusService{

	@Override
	public List<Status> findStatusByGroup(String Group) throws Exception {
		Finder finder=new Finder();
		finder=new Finder("select * from t_status  where 1=1 and t_status.group=:group order by statusCode asc");
		finder.setParam("group", Group);
		return super.queryForList(finder,Status.class);
	}

}
