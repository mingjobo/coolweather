package com.myweather.app.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.myweather.app.model.HttpCallBackListener;

/**
 * 访问服务器的工具类
 * @author minjobo
 *
 */
public class HttpUtil {
	public static void sendHttpRequest(final String address
			,final HttpCallBackListener listener){
		//开启一个线程去访问服务器的数据
		new Thread(new Runnable() {
			public void run() {
				HttpURLConnection connection = null;				
				try {
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					if (listener != null) {
						listener.onSusses(response.toString());
					}
				} catch (Exception e) {
					if (listener != null) {
						listener.onError(e);
					}
				}	
				finally{
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}).start();		
	}
}
