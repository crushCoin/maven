package com.runningfish.spmk.common;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;



/**
 * MD5加密工具
 * @author issuser
 *
 */
public class EncryptUtil {

	/**
	 * MD5加密
	 * @param source 源字符串
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String getMD5(String source) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		byte[] sourceOfBytes = source.getBytes("utf-8");
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		java.security.MessageDigest md5 = java.security.MessageDigest.getInstance("MD5");
		md5.update(sourceOfBytes);
		byte tmp[] = md5.digest(); 
		char str[] = new char[16 * 2];
										
		int k = 0; 
		for (int i = 0; i < 16; i++) {
			byte byte0 = tmp[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf]; 
														
			str[k++] = hexDigits[byte0 & 0xf];
		}
		s = new String(str);
		return s;
	}
	/**
	 * MD5加密后用Base64加密
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String base64MD5Encrypt(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		String strMD5 = getMD5(str);
    	String encodeStr = Base64.byteArrayToBase64(strMD5.getBytes("utf-8"));
		return encodeStr;
	}
	/**
	 * 将字符串用Base64解密
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String Base64ToStr(String str) throws Exception{
		String newStr = new String(Base64.base64ToByteArray(str));
		return newStr;
	}
	/**
	 * 将字符串用Base64加密
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String StrToBase64(String str) throws Exception{
		String newStr = Base64.byteArrayToBase64(str.getBytes());
		return newStr;
	}
	
	
	
	public static void main(String[] args){
		try {
			System.out.println(getMD5(""));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
}

