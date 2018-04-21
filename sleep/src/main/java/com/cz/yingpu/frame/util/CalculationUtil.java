package com.cz.yingpu.frame.util;

import java.math.BigDecimal;

import com.sun.star.lang.NullPointerException;

public class CalculationUtil {
	//加法
	public static Double addition(Double d1,Double d2) throws NullPointerException{
		if(d1==null||d2==null) throw new NullPointerException("用来计算的值不能为null");
	 return  	new BigDecimal(d1).add(new BigDecimal(d2)).doubleValue();
	}
	//减法
	public static Double subtract(Double d1,Double d2) throws NullPointerException{
		if(d1==null||d2==null) throw new NullPointerException("用来计算的值不能为null");
	 return  	new BigDecimal(d1).subtract(new BigDecimal(d2)).doubleValue();
	}
	//乘法
		public static Double multiply(Double d1,Double d2) throws NullPointerException{
			if(d1==null||d2==null) throw new NullPointerException("用来计算的值不能为null");
		 return  	new BigDecimal(d1).multiply(new BigDecimal(d2)).doubleValue();
		}
		//除法
		public static Double divide(Double d1,Double d2) throws NullPointerException{
			if(d1==null||d2==null) throw new NullPointerException("用来计算的值不能为null");
		 return  	new BigDecimal(d1).divide(new BigDecimal(d2),2,BigDecimal.ROUND_HALF_UP).doubleValue();
		}
}
