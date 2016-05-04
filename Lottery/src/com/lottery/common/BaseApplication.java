package com.lottery.common;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;
import android.app.Application;

public class BaseApplication extends Application
{
	public static BaseApplication application = null;
	private static ArrayList<BaseActivity> activities = new ArrayList<>();
	private static ArrayList<BaseActivity> userActivity = new ArrayList<>();
	@Override
	public void onCreate()
	{
		JPushInterface.setDebugMode(true); // ���ÿ�����־,����ʱ��ر���־
		JPushInterface.init(this); // ��ʼ�� JPush
		super.onCreate();
	}
	private BaseApplication()
	{
		
	}
	public static BaseApplication getInstance()
	{
		synchronized (Application.class)
		{
			if (application == null)
			{
				application = new BaseApplication();
			}	
		}
		return application;
	}
	public  ArrayList<BaseActivity> getActivities()
	{
		return activities;
	}
	public void addActivity(BaseActivity activity)
	{
		activities.add(activity);
	}
	public void exit(){
		for (BaseActivity activity : activities)
		{
			activity.finish();
		}
	}
	public  ArrayList<BaseActivity> getUserActivity() {
		return userActivity;
	}
	public  void addUserActivity(BaseActivity userActivity) {
		BaseApplication.userActivity.add(userActivity);
	}

}
