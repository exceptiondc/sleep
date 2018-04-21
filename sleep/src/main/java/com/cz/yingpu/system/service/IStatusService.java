package com.cz.yingpu.system.service;

import java.util.List;

import com.cz.yingpu.system.entity.Status;

public interface IStatusService extends IBaseSpringrainService {
	List<Status> findStatusByGroup(String Grounp) throws Exception;
}
