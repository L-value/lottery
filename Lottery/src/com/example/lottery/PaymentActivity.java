package com.example.lottery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.bean.Payment;
import com.example.myviews.PullToRefreshLayout;
import com.example.myviews.PullToRefreshLayout.OnRefreshListener;
import com.example.myviews.PullableListView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lottery.adapters.PaymentAdapter;
import com.lottery.common.BaseActivity;
import com.lottery.constant.Constant;
import com.lottery.listeners.HttpCallback;
import com.lottery.utils.AndroidTools;
import com.lottery.utils.HttpUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;

public class PaymentActivity extends BaseActivity  //Õ∂◊¢º«¬º
 implements OnRefreshListener
{
	private PullToRefreshLayout list_payment;
	private PullableListView content_view;
	private List<Payment> payments;
	@Override
	public void setContentView()
	{
		setContentView(R.layout.activity_payment);
		
	}

	@Override
	public void initView()
	{
		list_payment = (PullToRefreshLayout) findViewById(R.id.list_payment);
		content_view = (PullableListView) findViewById(R.id.content_view);
	}
	private HttpUtils httpUtils;
	private HashMap<String, String> data;
	private Gson gson;
	@Override
	public void initListener()
	{
		list_payment.setOnRefreshListener(this);
		gson = new Gson();
		data = new HashMap<>();
		data.put("key", prefUtils.getString("key"));
		data.put("page", "12");
		data.put("curpage", "0");
		httpUtils = new HttpUtils(httpCallback, "http://test.nsscn.org/zc/index.php?act=message&op=get_bay_info_message", "POST", data);
		if (isNetworkAvailable(this))
		{
			httpUtils.excute();
		}else {
			AndroidTools.showToastShort(this, "Õ¯¬Á¡¨Ω” ß∞‹");
		}
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg)
			{
				if (msg.what == Constant.HANDLE_SUCCESS)
				{
					System.out.println(msg.obj);
					try
					{
						payments = gson.fromJson(datas.getString("data"), new TypeToken<ArrayList<Payment>>()
						{
						}.getType());
					} catch (JsonSyntaxException | JSONException e)
					{
						e.printStackTrace();
					}
					if (payments != null)
					{
						content_view.setAdapter(paymentAdapter = new PaymentAdapter(payments, PaymentActivity.this));
					}
				}else if (msg.what == Constant.HANDLE_REFRESH_SUCCESS) {
					System.out.println(msg.obj);
					try
					{
						payments = gson.fromJson(datas.getString("data"), new TypeToken<ArrayList<Payment>>()
						{
						}.getType());
					} catch (JsonSyntaxException | JSONException e)
					{
						e.printStackTrace();
					}
					if (payments != null)
					{
						content_view.setAdapter(paymentAdapter = new PaymentAdapter(payments, PaymentActivity.this));
					}
					list_payment.refreshFinish(PullToRefreshLayout.SUCCEED);
				}else if (msg.what == Constant.HANDLE_LOAD_SUCCESS) {
					System.out.println(msg.obj);
					try
					{
						payments = gson.fromJson(datas.getString("data"), new TypeToken<ArrayList<Payment>>()
						{
						}.getType());
					} catch (JsonSyntaxException | JSONException e)
					{
						e.printStackTrace();
					}
					if (payments != null)
					{
						paymentAdapter.addMoreData(payments);
						paymentAdapter.notifyDataSetChanged();
					}
				}else if (msg.what == Constant.HANDLE_REFRESH_FAIL) {
					list_payment.refreshFinish(PullToRefreshLayout.FAIL);
				}else if (msg.what == Constant.HANDLE_LOAD_FAIL) {
					list_payment.loadmoreFinish(PullToRefreshLayout.FAIL);
				}
			}
		};
	}
	private PaymentAdapter paymentAdapter;
	@Override
	public void initData()
	{
		
	}

	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout)
	{
		if (isNetworkAvailable(this))
		{
			data = new HashMap<>();
			data.put("key", prefUtils.getString("key"));
			data.put("page", "12");
			data.put("curpage", "0");
			httpUtils = new HttpUtils(new HttpCallback()
			{
				
				@Override
				public void Success(String success)
				{
					try
					{
						job = new JSONObject(success);
						code = job.getString("code");
						datas = new JSONObject(job.getString("datas"));
						error = job.getString("error");
					} catch (JSONException e)
					{
						e.printStackTrace();
					}
					Message message = Message.obtain();
					message.obj = success;
					message.what = Constant.HANDLE_REFRESH_SUCCESS;
					handler.sendMessage(message);
					
				}
				
				@Override
				public void Failed(String failed)
				{
					
					Message message = Message.obtain();
					message.obj = failed;
					message.what = Constant.HANDLE_REFRESH_FAIL;
					handler.sendMessage(message);
					
				}
			}, "http://test.nsscn.org/zc/index.php?act=message&op=get_bay_info_message", "POST", data);
			if (isNetworkAvailable(this))
			{
				httpUtils.excute();
			}else {
				AndroidTools.showToastShort(this, "Õ¯¬Á¡¨Ω” ß∞‹");
			}
		}else {
			AndroidTools.showToastShort(this, "Õ¯¬Á¡¨Ω” ß∞‹");
			pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
		}
		
	}

	private String pageSize = "10";
	private int pageIndex = 0;
	@Override
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout)
	{
		if (isNetworkAvailable(this))
		{
			pageIndex++;
			data = new HashMap<>();
			data.put("key", prefUtils.getString("key"));
			data.put("page", "12");
			data.put("curpage", pageIndex+"");
			httpUtils = new HttpUtils(new HttpCallback()
			{
				@Override
				public void Success(String success)
				{
					try
					{
						job = new JSONObject(success);
						code = job.getString("code");
						datas = new JSONObject(job.getString("datas"));
						error = job.getString("error");
					} catch (JSONException e)
					{
						e.printStackTrace();
					}
					Message message = Message.obtain();
					message.obj = success;
					message.what = Constant.HANDLE_LOAD_SUCCESS;
					handler.sendMessage(message);
					
				}
				
				@Override
				public void Failed(String failed)
				{
					
					Message message = Message.obtain();
					message.obj = failed;
					message.what = Constant.HANDLE_LOAD_FAIL;
					handler.sendMessage(message);
					
				}
			}, "http://test.nsscn.org/zc/index.php?act=message&op=get_bay_info_message", "POST", data);
			if (isNetworkAvailable(this))
			{
				httpUtils.excute();
			}else {
				AndroidTools.showToastShort(this, "Õ¯¬Á¡¨Ω” ß∞‹");
			}
		}else {
			AndroidTools.showToastShort(this, "Õ¯¬Á¡¨Ω” ß∞‹");
			pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
		}
	}

	
}
