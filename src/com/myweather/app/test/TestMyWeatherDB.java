package com.myweather.app.test;

import java.util.ArrayList;

import android.test.AndroidTestCase;

import com.myweather.app.db.MyWeatherDB;
import com.myweather.app.model.Province;

public class TestMyWeatherDB extends AndroidTestCase {
	
	public void TestCreateDB() throws Exception {
		MyWeatherDB myweather = MyWeatherDB.getInstance(getContext());
	}
	
	public void TestAddProvince() throws Exception{
		MyWeatherDB myweather = MyWeatherDB.getInstance(getContext());
		Province province = new Province();
		province.setId(1);
		province.setProvinceName("¹ã¶«");
		province.setProvinceCode("12");
		myweather.saveProvince(province);
	}
	
	public void TestLoadProvince() throws Exception{
		MyWeatherDB myweather = MyWeatherDB.getInstance(getContext());
		ArrayList<Province> list =(ArrayList<Province>)myweather.loadProvince();
		for (Province province : list) 
			System.out.println(province.toString());		
	}
}
