package com.cz.yingpu.frame.util;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * php与java通用AES加密解密算法
 * AES指高级加密标准（Advanced Encryption Standard）,是当前最流行的一种密码算法，在web应用开发，特别是对外提供接口时经常会用到，
 * 下面是整理的一套php与java通用的AES加密解密算法。
 * @author Administrator
 *
 */
public class CryptAES {
	
	private static final String AESTYPE ="AES/ECB/PKCS5Padding";
	private static final  String keyStr = "UITN25LMUQC436IM";
	 
	/**
	 * 加密方法
	 * @param plainText
	 * @return
	 */
    public static String AES_Encrypt(String plainText) {
        byte[] encrypt = null;
        try{
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance(AESTYPE);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypt = cipher.doFinal(plainText.getBytes());    
        }catch(Exception e){
            e.printStackTrace();
        }
        return new String(Base64.encodeBase64(encrypt));
    }
 
    /**
     * 解密方法
     * @param encryptData
     * @return
     */
    public static String AES_Decrypt(String encryptData) {
        byte[] decrypt = null;
        try{
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance(AESTYPE);
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypt = cipher.doFinal(Base64.decodeBase64(encryptData));
        }catch(Exception e){
            e.printStackTrace();
        }
        return new String(decrypt).trim();
    }
 
    private static Key generateKey(String key)throws Exception{
        try{           
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            return keySpec;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
 
    }

    public static void main(String[] args) {
 

        String plainText = "OISyIDTWG1UVSSm0s4db8A==";

        //String encText = AES_Encrypt(plainText);
        String decString = AES_Decrypt(plainText);
//        
     //   System.out.println(encText);
        System.out.println(decString);
    	 
        
    } 

}
