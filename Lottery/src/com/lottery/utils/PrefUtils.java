package com.lottery.utils;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtils
{
	private static SharedPreferences sharedPreferences;
	private static SharedPreferences.Editor editor;
	
	private String stringDefValue = "null";
	private int intDefValue = -1;
	private long longDefValue = -1;
	private boolean boolDefValue = false;
	private float floatDefValue = -1;

	public PrefUtils(Context context)
	{
		if (sharedPreferences == null)
		{
			sharedPreferences = context.getSharedPreferences("lottery", Context.MODE_PRIVATE);
			editor = sharedPreferences.edit();
		}
	}
	public void putString(String key,String value){
		editor.putString(key, value);
	}
	public void putInt(String key,int value){
		editor.putInt(key, value);
	}
	public void putLong(String key,long value){
		editor.putLong(key, value);
	}
	public void putBoolean(String key,Boolean value)
	{
		editor.putBoolean(key, value);
	}
	public void putFloat(String key,long value){
		editor.putFloat(key, value);
	}
	public String getString(String key){
		return sharedPreferences.getString(key, stringDefValue);
	}
	public int getInt(String key){
		return sharedPreferences.getInt(key, intDefValue);
	}
	public long getLong(String key){
		return sharedPreferences.getLong(key, longDefValue);
	}
	public boolean getBoolean(String key)
	{
		return sharedPreferences.getBoolean(key, boolDefValue);
	}
	public float getFloat(String key){
		return sharedPreferences.getFloat(key, floatDefValue);
	}
	public void clear(){
		editor.clear();
		editor.commit();
	}
	public boolean contains(String key){
		return sharedPreferences.contains(key);
	}
	public boolean commit(){ 
		return editor.commit();
	}
	public void remove(String key){
		editor.remove(key);
	}
	
}
