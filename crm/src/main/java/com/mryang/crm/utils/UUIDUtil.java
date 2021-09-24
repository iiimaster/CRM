package com.mryang.crm.utils;

import java.util.UUID;

/**
 * 唯一标识 工具类
 */
public class UUIDUtil {
	
	public static String getUUID(){
		
		return UUID.randomUUID().toString().replaceAll("-","");
		
	}
	
}
