package com.myweather.app.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.myweather.app.R;
import com.myweather.app.db.MyWeatherDB;
import com.myweather.app.model.City;
import com.myweather.app.model.County;
import com.myweather.app.model.HttpCallBackListener;
import com.myweather.app.model.Province;
import com.myweather.app.utils.HttpUtil;
import com.myweather.app.utils.Utility;

public class ChooseAreaActivity extends Activity {
	
	public static final int LEVEL_PROVINCE = 0;
	public static final int LEVEL_CITY = 1;
	public static final int LEVEL_COUNTY = 2;
	
	private TextView title_text;
	private ListView list;
	private ProgressDialog progressdialog;
	//获取省得数据
	private List<Province> dataProvince;
	//获取市的数据
	private List<City> dataCity;
	//获取区的数据
	private List<County> dataCounty;
	
	//选中的省
	private Province selectedProvince;
	//选中的市
	private City selectedCity;
	//选中的区
	private County selectedCounty;
	
	private ArrayAdapter adapter;
	private List<String> datalist = new ArrayList<String>();
	
	private int courrentLevel;
	private MyWeatherDB myweather;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//设置没有标题栏
		setContentView(R.layout.choose_area);
		
		list = (ListView) findViewById(R.id.list_view);
		title_text = (TextView) findViewById(R.id.title_tv);
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,datalist);
		
		list.setAdapter(adapter);
		
		myweather = MyWeatherDB.getInstance(this);
		list.setOnItemClickListener(new OnItemClickListener() {
			
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (courrentLevel == LEVEL_PROVINCE) {
				selectedProvince = dataProvince.get(position);
				queryCities();
				}else if(courrentLevel == LEVEL_CITY){
					selectedCity = dataCity.get(position);
					queryCounties();
				}				
			}
		});		
		queryProvinces();
	}

	/**
	 * 查询所有省得数据先从数据库里面查询，让后再从服务器查询
	 */
	private void queryProvinces() {
		dataProvince = myweather.loadProvince();
		if (dataProvince.size() > 0) {
			datalist.clear();
			for (Province p : dataProvince) {
				datalist.add(p.getProvinceName());
			}
			adapter.notifyDataSetChanged();
			list.setSelection(0);
			title_text.setText("中国");
			courrentLevel = LEVEL_PROVINCE;
		}else {
			queryFromService(null,"province");
		}
		
	}

	protected void queryCounties() {
		dataCounty = myweather.loadCounty(selectedCity.getId());
		if (dataCounty.size() > 0) {
			datalist.clear();
			for (County c : dataCounty) {
				datalist.add(c.getCountyName());
			}
			adapter.notifyDataSetChanged();
			title_text.setText(selectedCity.getCityName());
			list.setSelection(0);
			courrentLevel = LEVEL_COUNTY;
		}else {
			queryFromService(selectedCity.getCityCode(),"county");
		}			
	}

	protected void queryCities() {
		dataCity = myweather.loadCity(selectedProvince.getId());
		if (dataCity.size() > 0) {
			datalist.clear();
			for (City c : dataCity) {
				datalist.add(c.getCityName());
			}
			adapter.notifyDataSetChanged();
			title_text.setText(selectedProvince.getProvinceName());
			list.setSelection(0);
			courrentLevel = LEVEL_CITY;
		}else {
			queryFromService(selectedProvince.getProvinceCode(),"city");
		}		
	}
	
	private void queryFromService(final String code,final String type) {
		String address;
		if (!TextUtils.isEmpty(code)) {
			address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
		}else {
			address = "http://www.weather.com.cn/data/list3/city.xml";
		}
		showProgressDialog();
		//开始从服务器下载资源
		HttpUtil.sendHttpRequest(address, new HttpCallBackListener(){

			public void onSusses(String resquest) {
				Boolean result = false;
				if ("province".equals(type)) {
					result = Utility.handleProvinceresponse(myweather, resquest);
				}else if("city".equals(type)){
					result = Utility.handleCityresponse(myweather, resquest,selectedProvince.getId());
				}else if("county".equals(type)){
					result = Utility.handleCountyresponse(myweather, resquest, selectedCity.getId());
				}	
				if(result){
					runOnUiThread(new Runnable(){
						public void run() {
							closeProgressDialog();
							if ("province".equals(type)) {
								queryProvinces();
							}else if("city".equals(type)){
								queryCities();
							}else if("county".equals(type)){
								queryCounties();
							}
						}
					});
				}
			}
			public void onError(Exception e) {
					runOnUiThread(new Runnable(){
						public void run() {
							closeProgressDialog();
							Toast.makeText(getApplicationContext(), "加载失败....",Toast.LENGTH_SHORT).show();
						}
					});			
			}
			
		});
		
	}

	private void showProgressDialog() {
		if (progressdialog == null) {
			progressdialog = new ProgressDialog(this);
			progressdialog.setMessage("正在加载....");
			progressdialog.setCanceledOnTouchOutside(false);
		}
		progressdialog.show();
	}
	
	private void closeProgressDialog() {
		if (progressdialog != null) {
			progressdialog.dismiss();
		}
		
	}
	
	public void onBackPressed(){
		if (courrentLevel == LEVEL_COUNTY) {
			queryCities();
		}else if (courrentLevel == LEVEL_CITY) {
			queryProvinces();
		}else {
			finish();
		}
	}
	
}
