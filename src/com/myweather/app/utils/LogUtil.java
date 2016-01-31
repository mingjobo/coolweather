package com.myweather.app.utils;

import android.util.Log;

/**
 * 自己定制的日志的打印
 * 这个类的作用是可以控制日志打印的显示
 * 在开发的时候可以让LEVEL为VERBOSE 就能让所有的日志打印出来
 * 如果把LEVEL设置为NOTHING，那么上线的时候就不会把相应的日志打印出来
 * @author minjobo
 *
 */
public class LogUtil {
	public static final int VERBOSE = 1;
	
	public static final int DEBUG = 2;
	
	public static final int INFO = 3;
	
	public static final int WARN = 4;
	
	public static final int  ERROR = 5;
	
	public static final int NOTHING = 6;
	
	public static final int LEVEL = VERBOSE;
	
	public static void v(String tag, String msg){
		if (LEVEL <= VERBOSE) {
			Log.v(tag,msg);
		}
	}
	
	public static void d(String tag, String msg){
		if (LEVEL <= DEBUG) {
			Log.d(tag,msg);
		}
	}
	
	public static void i(String tag, String msg){
		if (LEVEL <= INFO) {
			Log.i(tag,msg);
		}
	}
	
	public static void w(String tag, String msg){
		if (LEVEL <= WARN) {
			Log.w(tag,msg);
		}
	}
	
	public static void e(String tag, String msg){
		if (LEVEL <= ERROR) {
			Log.e(tag,msg);
		}
	}
	
}
