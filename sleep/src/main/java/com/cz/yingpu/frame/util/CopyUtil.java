package com.cz.yingpu.frame.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*********************************************
 * 	<p>
 *  进行对象复制的类       
 *  </p>
 *  <p>                                                                   
 *  对象复制不仅仅局限于同一类型，不同类型之间也可以复制     
 *  </p>   
 *  <p>
 *  eg.1<br/>
 *  A aClass = new A();<br/>
 *  B bClass = new B();<br/>
 *  CopyUtil.copyClass(bClass,aClass);
 *  </p>
 *  <p>
 *  eg.2<br/>
 *  A aClass = new A();<br/>
 *  B bClass = (B) CopyUtil.copyAndCreate(B.class, aClass);
 *  </p>
 *  
 *********************************************/

public class CopyUtil {
	String a = new String();
	/*********************************************
	 * 复制classA的属性值给classB，两个对象可以是不同的       
	 * 类型，但是对应的字段要一样.    
	 * @param   classB     复制到的对象.  
	 * @param   classA     被复制的对象.    
	 * @param   extend     附加参数
	 * 
	 * <p>
	 * 对象值的对拷方法的附加参数：extend
	 * <br />
	 * 形式1: ["field1"[,"field2"[,field3...]]] :和lefeJoin联合使用的时候，只拷贝附加参数内的值，如果没有leftJoin,则排除附加参数内的值
	 * <br />
	 * 形式2: ["|leftJoin|"]
	 * <br />
	 * eg1: CopyUtil.copyClass(a,b,"username","password");
	 * <br />
	 * result: a.username = null , a.password = null;
	 * <br />
	 * eg2: CopyUtil.copyClass(a,b,"username","password","|leftJoin|");
	 * <br />
	 * result: a.username != null, a.password != null;
	 * <br />
	 * 形式3: ["|enableLike|"] 
	 * <br />
	 * eg3:CopyUtil.copyClass(a,b,"|enableLike|");
	 * <br />
	 * result: a.username == "%tommy%"
	 * <br />
	 * 形式4: ["star=precision2"]:对某一个数据进行经度的格式化 
	 * <br />
	 * eg4:CopyUtil.copyClass(a,b,"star=precision2");
	 * <br />
	 * result: a.star = 3.23
	 * <br />
	 * 形式5: "name->username" :在拷贝的过程中进行域名的转化
	 * <br />
	 * eg5:CopyUtil.copyClass(a,b,"name->username");
	 * <br />
	 * result: a.username == b.name
	 * </p>
	 * @throws ParseException
	 * class A name , phone 
	 * class B name , phone 
	 * 
	 *
	 * 
	 * 
	 * 
	*********************************************/
	public static Object copyClass(Object classB , Object classA , String ... extend) throws IllegalAccessException, ParseException {
		if(null == classA) {classB = null;return null;}
		Field[] fields = classA.getClass().getDeclaredFields();
		Field field = null;
		Boolean enableLike = false;
		Boolean leftJoin = false;
		Boolean forced = false;
		Boolean noNull = false;
		for(String e:extend){
			if("|enableLike|".equals(e)){
				enableLike = true;
			}
			if("|leftJoin|".equals(e)){
				leftJoin = true;
			}
			
			if("|forced|".equals(e)){
				forced = true;
			}
			
			if("|noNull|".equals(e)){
				noNull = true;
			}
			
		}
		for (int i = 0; i < fields.length; i++) {
			String name = fields[i].getName();
			Boolean pass = false;
			Integer precision = -1; //默认精度-1
			String precisionName = null;
			for (String e : extend) {
				pass = false;
				if(e.contains("->")){
					String[] kv = e.split("->");
					if(name.equals(kv[0])){
						name = kv[1];
						break;
					}
				}
				if(leftJoin){
					pass = true;
					if(name.equals(e)){
						pass = false;
						break;
					}
				}else{
					pass = false;
					if(name.equals(e)){
						pass = true;
						break;
					}
				}
				//形式：star=precision2:对star做精确度为2的格式化，四舍五入
				if(e.contains("precision")){
					String subStr = e.substring(e.indexOf("=precision") + 10, e.length());
					precision = Integer.parseInt(subStr);
					precisionName = e.substring(0,e.indexOf("=precision"));
					if(name.equals(precisionName)) break;
				}
			}
			if(pass) continue;
			try{
				field = classB.getClass().getDeclaredField(name);
			}catch(NoSuchFieldException e){
				continue;
			}
			field.setAccessible(true);
			fields[i].setAccessible(true);
			
			
			Object value = fields[i].get(classA);
			
			if(noNull && null == value) continue; //如果禁止null出现，进入下次循环
			
			if(forced){ //如果进行强制类型转化
				if(fields[i].getType().getName().equals("java.lang.String")){ 
					if(field.getType().getName().equals("java.lang.Integer")){//如果是string转化为int
						if(value==null || "".equals(value))	continue;
						value = Integer.parseInt((String)value);
					}
					if(field.getType().getName().equals("java.lang.Double")){
						if(value==null || "".equals(value)) continue;
						value = Double.parseDouble((String)value);
					}
					if(field.getType().getName().equals("java.lang.Boolean")){
						if(value==null) continue;
						if(value.equals("0") || "".equals(value) || "false".equalsIgnoreCase((String)value)) value = false;
						else value = true;
					}
					if(field.getType().getName().equals("java.util.Date")){  //如果是string转化为Date
						if(value==null) continue;
						try{
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							value = sdf.parse((String)value);
						}catch(Exception e){
							try{
								SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
								value = sdf.parse((String)value);
							}catch(Exception ex){
								try{
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
									value = sdf.parse((String)value);
								}catch(Exception exp){
									SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
									Date date = sdf.parse((String)value);
									value = new Date(date.getTime() + 60 * 1000 * 60 * 24);  //为了防止出现 1970-01-01 07:30 以及以前时间的状况，在原有时间基础上加一天
								}
							} 
						}
					}
				}
			}
			
			if(enableLike && (value instanceof String)){
				value = "%" + (String)value + "%";
			}
			//形式：star=precision2:对star做精确度为2的格式化，四舍五入
			if(precision > -1 && name.equals(precisionName) && (value instanceof Double)){
				Double f =  (Double) value;
				BigDecimal bigDecimal = new BigDecimal(f); 
				value = bigDecimal.setScale(precision,BigDecimal.ROUND_HALF_UP).doubleValue(); 
			}
		
			field.set(classB,value);
		}
		return classB;
	}
	
	/*********************************************
	 * 首先创建目标对象，然后将classA的属性值复制给目标对象    
	 * 可以是不同类型，但是对应的字段要一样.     
	 * @param   target     要复制的对象的类型.         
	 * @param   classA     被复制的对象.        
	 * @throws ParseException 
	 * 
	 *********************************************/
	public static Object copyAndCreate(Class target, Object classA ,String ... extend) throws IllegalAccessException,InstantiationException,NoSuchFieldException, ParseException{
		if(classA == null) return null;
		Object classB = target.newInstance();
		copyClass(classB,classA,extend);
		return classB;
	}
	
	/*********************************************
	 * 首先创建目标对象，然后将classA的属性值复制给目标对象    
	 * 可以是不同类型，但是对应的字段要一样.     
	 * @param   target     要复制的对象的类型.         
	 * @param   classA     被复制的对象.        
	 * @throws ParseException 
	 *********************************************/
	public static List copyAndCreateList(Class target,List listA,String ... extend) throws  IllegalAccessException,InstantiationException,NoSuchFieldException, ParseException{
		List listB = new ArrayList();
		if(listA == null || listA.size() == 0) return listB;
		
		for (Object classA : listA) {
			Object classB = target.newInstance();
			copyClass(classB,classA, extend);
			listB.add(classB);
		}
		return listB;
	}
	
	/*********************************************
	 * 首先创建目标对象，然后将classA的属性值复制给目标对象    
	 * 可以是不同类型，但是对应的字段要一样.     
	 * @param   target     要复制的对象的类型.         
	 * @param   classA     被复制的对象.        
	 * @throws ParseException 
	 *********************************************/
	public static Set copyAndCreateSet(Class target,Set listA) throws  IllegalAccessException,InstantiationException,NoSuchFieldException, ParseException{
		Set setB = new HashSet();
		if(listA == null || listA.size() == 0) return setB;
		
		for (Object classA : listA) {
			Object classB = target.newInstance();
			copyClass(classB,classA);
			setB.add(classB);
		}
		return setB;
	}
	
	/**
	 * 清空一个对象内部的数据
	 * @param o
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void clearObj(Object o) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = o.getClass().getDeclaredFields();
		for (Field field : fields) {
			//如果是静态变量，则跳过
			if(Modifier.isStatic(field.getModifiers())) continue;
			field.setAccessible(true);
			field.set(o,null);
		}
	}

	/**
	 * 为一个对象的某个field赋值
	 * @param target
	 * @param o
	 * @param field
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void putValue(Object target,Object o,String fieldName) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Field field = target.getClass().getDeclaredField(fieldName);
		if(field!=null){
			field.setAccessible(true);
			field.set(target,o);
		}
	}
	
	
	public static void main(String[] args) throws ParseException {
		String aa = "07:30";
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date value = sdf.parse(aa);
		System.out.println(value);
	}
	
}
