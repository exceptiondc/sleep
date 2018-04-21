package com.cz.yingpu.system.service;

import java.util.List;

/**
 * Created by LENOVO on 2017/5/3.
 */
public interface JPushService extends IBaseSpringrainService {

    /**
     * jpush通知
     * @Description
     * @author wxy
     * @param type  跟客户端自定义的推送类型
     * @param id	要
     * @param userId
     * @param appType
     * @param extend
     * @return
     * @throws Exception
     * Integer
     */
    Integer notify(Integer type, Integer id, List userIds, String... extend ) throws Exception;
}
