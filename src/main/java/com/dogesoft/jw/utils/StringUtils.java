package com.dogesoft.jw.utils;

public class StringUtils {
	
	public static boolean isNotEmpty(String str){
		
		if(str!=null && !"".equals(str) && str.length() > 0 && !str.isEmpty() ){
			return true;
		}
		return false;
	}

}
