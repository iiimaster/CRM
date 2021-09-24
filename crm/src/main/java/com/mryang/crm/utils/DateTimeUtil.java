package com.mryang.crm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期时间 工具类
 */
public class DateTimeUtil {
	
	public static String getSysTime(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date date = new Date();
		String dateStr = sdf.format(date);
		
		return dateStr;
		
	}
	public static String getSysTimeForUpload(){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

		Date date = new Date();
		String dateStr = sdf.format(date);

		return dateStr;

	}
}
