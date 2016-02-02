package com.myweather.app.utils;

import android.text.TextUtils;

import com.myweather.app.db.MyWeatherDB;
import com.myweather.app.model.City;
import com.myweather.app.model.County;
import com.myweather.app.model.Province;

public class Utility {
	/**
	 * 解析和处理市返回的所有数据
	 */
	public synchronized static boolean handleProvinceresponse(MyWeatherDB db, String response){
		if (!TextUtils.isEmpty(response)) {
			String[] allProvince = response.split(",");
			if (allProvince != null && allProvince.length > 0) {
				for (String p : allProvince) {
					String[] array = p.split("\\|");
					Province province = new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					db.saveProvince(province);
				}
				return true;
			}
		}
		return false;		
	}
	
	/**
	 * 解析和处理区返回的所有数据
	 */
	public synchronized static boolean handleCityresponse(MyWeatherDB db,String response,int provinceId){
		if (!TextUtils.isEmpty(response)) {
			String[] allCitys = response.split(",");
			if (allCitys != null && allCitys.length > 0) {
				for (String p : allCitys) {
					String[] array = p.split("\\|");
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceId(provinceId);
					db.saveCity(city);
				}
				return true;
			}
		}
		
		return false;		
	}
	
	/**
	 * 解析和处理县返回的所有数据
	 */
	public synchronized static boolean handleCountyresponse(MyWeatherDB db, String response,int cityId){
		if (!TextUtils.isEmpty(response)) {
			String[] allCounty = response.split(",");
			if (allCounty != null && allCounty.length > 0) {
				for (String p : allCounty) {
					String[] array = p.split("\\|");
					County county = new County();
					county.setCountyCode(array[0]);
					county.setCountyName(array[1]);
					county.setCityId(cityId);
					db.saveCounty(county);
				}
				return true;
			}
		}
		
		return false;		
	}
}
