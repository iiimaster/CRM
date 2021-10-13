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
	// 将时间格式化为字符串
	public static String getSysTimeForUpload(String date){

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

		String dateStr = sdf.format(date);

		return dateStr;

	}
}
