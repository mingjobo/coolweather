package com.myweather.app.model;

public interface HttpCallBackListener {
	void onSusses(String resquest);
	void onError(Exception e);
}
