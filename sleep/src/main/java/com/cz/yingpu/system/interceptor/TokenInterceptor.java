package com.cz.yingpu.system.interceptor;

import com.cz.yingpu.frame.util.GlobalStatic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lenovo on 2017/3/23.
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {

    private final Logger log = LoggerFactory.getLogger(TokenInterceptor.class);
    @Autowired(required = true)
    private CacheManager cacheManager;

    @Override
	public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            log.info("==============执行顺序: 1、preHandle================");
        }
        String token = request.getParameter("token");
        Cache cache = cacheManager.getCache(GlobalStatic.tokenCacheKey);
//        String userId = request.getParameter("id") ;
//        Object to = cache.get("token_"+userId) ;

//        log.info("==============执行顺序: 1、preHandle================"+token);
//        log.info("==============执行顺序: 1、preHandle================"+cache);
        return true;
    }
}
