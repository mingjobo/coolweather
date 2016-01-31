package com.myweather.app.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.myweather.app.model.City;
import com.myweather.app.model.County;
import com.myweather.app.model.Province;

public class MyWeatherDB {
	/**
	 * 数据库名字
	 */
	private static final String DB_NAME = "my_weather";
	
	private static final int VERSION = 1;
	private static MyWeatherDB myweatherBD;
	private SQLiteDatabase db;
	
	private MyWeatherDB(Context context){
		MyWeatherOpenHelper dbHelper = new MyWeatherOpenHelper(context,DB_NAME,null,VERSION);
		db = dbHelper.getWritableDatabase();
	}
	
	/**
	 * 获取MyWeatherDB 实例
	 */
	
	public synchronized static MyWeatherDB getInstance(Context context){
		if(myweatherBD == null)
			myweatherBD = new MyWeatherDB(context);
		return myweatherBD;
	}
	/**
	 * 向数据库中存贮数据
	 * @param province
	 */
	public void saveProvince(Province province){
		if (province != null) {
			ContentValues values = new ContentValues();
			values.put("id", province.getId());
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}
	
	public List<Province> loadProvince(){
		List<Province> list = new ArrayList<Province>();
		 Cursor cursor = db.query("Province",null,null,null,null,null,null,null);
		 while (cursor.moveToNext()) {
			 Province province = new Province();
			 province.setId(cursor.getInt(cursor.getColumnIndex("id")));
			 province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
			 province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
			 list.add(province);
		 }
		return list;
	}
	/**
	 * 向数据库存储city
	 * @param city
	 */
	public void saveCity(City city){
		if (city != null) {
			ContentValues values = new ContentValues();
			values.put("id", city.getId());
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}
	
	public List<City> loadCity(){
		List<City> list = new ArrayList<City>();
		 Cursor cursor = db.query("City",null,null,null,null,null,null,null);
		 while (cursor.moveToNext()) {
			 City city = new City();
			 city.setId(cursor.getInt(cursor.getColumnIndex("id")));
			 city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
			 city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
			 city.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
			 list.add(city);
		 }
		return list;
	}

	
	public void saveCounty(County county){
		if (county != null) {
			ContentValues values = new ContentValues();
			values.put("id", county.getId());
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			db.insert("County", null, values);
		}
	}
	public List<County> loadCounty(){
		List<County> list = new ArrayList<County>();
		 Cursor cursor = db.query("County",null,null,null,null,null,null,null);
		 while (cursor.moveToNext()) {
			 County county = new County();
			 county.setId(cursor.getInt(cursor.getColumnIndex("id")));
			 county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
			 county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
			 county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
			 list.add(county);
		 }
		return list;
	}
}
