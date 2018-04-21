package com.cz.yingpu.frame.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  标记可以用于api接口返回数据加密，引用此注解的方法都会配合SecurityAspect进行加密
 * @author Michael
 * @version  2017-03-08 11:08:15
 * @see com.cz.yingpu.frame.annotation.SecurityApi
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SecurityApi {
//	String description()  default "";
	String value() default "" ;
}
