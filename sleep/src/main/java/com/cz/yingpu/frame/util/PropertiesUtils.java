package com.cz.yingpu.frame.util;

/**
 * @filename       : PropertiesUtil.java
 * @description    : TODO
 * @author         : penglong
 * @create         : 2015-6-13 下午03:01:21
 * @copyright      : mall Corporation 2015
 *
 * Modification History:
 * Date             Author       Version
 * --------------------------------------
 * 2015-6-13 下午03:01:21  long
 */
 


/**
 * @filename       : PropertiesUtil.java
 * @description    : TODO
 * @author         : penglong
 * @create         : 2015-6-13 下午03:01:21
 * @copyright      : mall Corporation 2015
 *
 * Modification History:
 * Date             Author       Version
 * --------------------------------------
 * 2015-6-13 下午03:01:21  long
 */


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
* 读取Properties综合类
* @author
*/
public class PropertiesUtils {
  
   /**
    * 配置文件对象
    */
   private Properties props=null;
  
   /**
    * 构造函数
    * @param fileName 配置文件名称
    */
   public PropertiesUtils(String fileName){
       String filePath=getPath(PropertiesUtils.class) + fileName;
       props = new Properties();
       try {
           InputStream in = new BufferedInputStream(new FileInputStream(filePath));
           props.load(in);
           //关闭资源
           in.close();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
  
   /**
    * 根据key值读取配置的值
    * Jun 26, 2010 9:15:43 PM
    * @author 
    * @param key key值
    * @return key 键对应的值
    * @throws IOException
    */
   public String readValue(String key) throws IOException {
       return  props.getProperty(key);
   }
  
   /**
    * 读取properties的全部信息
    * Jun 26, 2010 9:21:01 PM
    * @author 
    * @throws FileNotFoundException 配置文件没有找到
    * @throws IOException 关闭资源文件，或者加载配置文件错误
    *
    */
   public Map<String,String> readAllProperties() throws FileNotFoundException,IOException  {
       //保存所有的键值
       Map<String,String> map=new HashMap<String,String>();
       Enumeration en = props.propertyNames();
       while (en.hasMoreElements()) {
           String key = (String) en.nextElement();
           String Property = props.getProperty(key);
           map.put(key, Property);
       }
       return map;
   }


   /**
    * 得到某一个类的路径
    * @param name
    * @return
    */
   private String getPath(Class name) {
       String strResult = null;
       if (System.getProperty("os.name").toLowerCase().indexOf("window") > -1) {
           strResult = name.getResource("/").toString().replace("file:/", "")
                   .replace("%20", " ");
       } else {
           strResult = name.getResource("/").toString().replace("file:", "")
                   .replace("%20", " ");
       }
       return strResult;
   }

}