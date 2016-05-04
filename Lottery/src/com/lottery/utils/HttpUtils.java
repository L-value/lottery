package com.lottery.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;












import com.lottery.constant.Constant;
import com.lottery.listeners.HttpCallback;

import android.os.AsyncTask;

public class HttpUtils
{
	private HttpCallback httpCallback; // 访问接口后的回调
	private String path; // 接口路径
	private String method; // 访问的方法
	private Map<String, String> data;
	private String content = "";
	private MyTask myTask;

	public HttpUtils(HttpCallback httpCallback, String path, String method,
			Map<String, String> data)
	{
		super();
		this.httpCallback = httpCallback;
		this.path = path;
		this.method = method;
		this.data = data;
		myTask = new MyTask();
	}
	public void excute(){
		myTask.execute(method);
	}

	private class MyTask extends AsyncTask<String, Integer, String>
	{

		@Override
		protected String doInBackground(String... params)
		{
			if (method.equals("POST"))
			{
				return doPostByUrlConnection();
			} else if (method.equals("GET"))
			{
				return doGetByUrlConnection();
			}
			return null;
		}
		

	}

	private String doPostByUrlConnection()
	{
		URL url = null;
		StringBuffer parms = new StringBuffer();
		for(Map.Entry<String, String> entry: data.entrySet()){
				parms.append(entry.getKey()+"="+entry.getValue() +"&");
		}
		if (data.size() != 0)
		{
			parms.deleteCharAt(parms.length() - 1);
		}
		byte datas[] = parms.toString().getBytes();
		try
		{
			url = new URL(path);
			System.out.println("path :"+path);
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		HttpURLConnection urlConnection = null;
		try
		{
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setRequestMethod("POST");
			OutputStream outputStream;
			outputStream = urlConnection.getOutputStream();
			outputStream.write(datas);
			AndroidTools.logS("PostCODE: " + urlConnection.getResponseCode());
			if (urlConnection.getResponseCode() == 200)
			{
				InputStream inputStream = urlConnection.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				content = reader.readLine();
				httpCallback.Success(content);
			}else {
				httpCallback.Failed("服务器连接不稳定，请稍后再试");
			}
		} catch (IOException e)
		{
				httpCallback.Failed("网络连接失败，请稍后再试");
		}
		return content;
	}

	private String doGetByUrlConnection()
	{
		URL url = null;
		StringBuffer parms = new StringBuffer();
		parms.append(path);
		parms.append("?");
		for (Map.Entry<String, String> entry : data.entrySet())
		{
			parms.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		if (data.size() != 0)
		{
			parms.deleteCharAt(parms.length() - 1);
		}
		System.out.println(path);
		try
		{
			url = new URL(parms.toString());
		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		HttpURLConnection urlConnection;
		try
		{
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			AndroidTools.logS("GETCODE: " + urlConnection.getResponseCode());
			if (urlConnection.getResponseCode() == 200)
			{
				InputStream inputStream = urlConnection.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				content = reader.readLine();
				httpCallback.Success(content);
			}
		} catch (IOException e)
		{
			httpCallback.Failed("网络连接失败，请稍后再试");
			e.printStackTrace();
		}
		return null;
	}
}
