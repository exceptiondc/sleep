package com.cz.yingpu.frame.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.stereotype.Component;


/**
 * 前台用户的 过滤器，主要设置登陆界面
 * @author dongchuang
 *
 */


@Component("home")
public class HomeFilter extends AccessControlFilter {
	
	public HomeFilter(){
		
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		
		HttpServletRequest httpServletRequest=(HttpServletRequest)request;
		HttpServletResponse  httpServletResponse =(HttpServletResponse)response;
		System.out.println(httpServletRequest.getRequestURI());
		
		if (httpServletRequest.getRequestURI().equals("/")) {
			httpServletRequest.getRequestDispatcher("/yingpu/pc/index").forward(httpServletRequest, httpServletResponse);
		}
		
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest httpServletRequest=(HttpServletRequest)request;
		HttpServletResponse  httpServletResponse =(HttpServletResponse)response;
		Subject subject = SecurityUtils.getSubject(); 
		Session session = subject.getSession();
		System.out.println(httpServletRequest.getRequestURI());
		
		
		if (httpServletRequest.getRequestURI().equals("/")) {
			httpServletRequest.getRequestDispatcher("/yingpu/pc/index").forward(httpServletRequest, httpServletResponse);
		}
		
		return true;
	}
}
