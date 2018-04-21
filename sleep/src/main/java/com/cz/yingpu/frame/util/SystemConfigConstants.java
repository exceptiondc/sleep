package com.cz.yingpu.frame.util;

import java.util.ResourceBundle;

/**
 * Created by lenovo on 2017/3/23.
 */
public class SystemConfigConstants {

//    //Token过期时间
//    public static final int TOKENTIMEOUT;
//    //用于生成的Token长度
//    public static final int TOKEN_BYTE_LEN;

//    /*
//     * 取值过程
//     */
//    static{
//        //配置文件读取
//        InputStream stream = null;
//        //读取工具
//        Properties properties = null;
//
//        try {
//            stream = SystemConfigConstants.class.getResourceAsStream("SystemComfig.properties");
//            properties = new Properties();
//            properties.load(stream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        TOKENTIMEOUT = Integer.parseInt(properties.getProperty("TOKENTIMEOUT"));
//        TOKEN_BYTE_LEN = Integer.parseInt(properties.getProperty("TOKEN_BYTE_LEN"));
//    }

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("SystemComfig");

    /**
     * @param propName
     * @param key
     */
    public static String getConfig(String key) {

        try {
            System.out.println("key值====" + RESOURCE_BUNDLE.getString(key));
            return RESOURCE_BUNDLE.getString(key);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static int getInt(String key) {

//        System.out.println("key值====" + RESOURCE_BUNDLE.getString(key));

        return Integer.parseInt(RESOURCE_BUNDLE.getString(key));
    }
}
