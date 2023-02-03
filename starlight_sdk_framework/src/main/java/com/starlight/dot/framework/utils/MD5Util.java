package com.starlight.dot.framework.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {



	public static void main(String[] args) {
		System.out.println(encryptDate("123456"));
	}


	/**
	 * 对数据进行MD5加密处理
	 * @author hux
	 * update at 2018年6月6日 下午4:11:23 by admin
	 *		version:0.0.1
	 *      create the method
	 * @param data
	 * 		需要加密的数据
	 * @return
	 * 		MD5加密后的数据
	 */
	public static String encryptDate(String data){
		if(data.isEmpty()){
			return "";
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] inputByteArray = data.getBytes("utf-8");
			messageDigest.update(inputByteArray);
			byte[] resultByteArray = messageDigest.digest();
			return byteArrayToHex(resultByteArray);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}




	 private static String byteArrayToHex(byte[] byteArray) {
		 char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };
		 char[] resultCharArray =new char[byteArray.length * 2];
		 int index = 0;
		 for (byte b : byteArray) {
			 resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
			 resultCharArray[index++] = hexDigits[b& 0xf];
		 }
		 return new String(resultCharArray);
	 }
}
