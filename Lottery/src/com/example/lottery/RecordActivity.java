package com.example.lottery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.ListView;

import com.example.bean.Pay;
import com.example.bean.Payment;
import com.example.myviews.PullToRefreshLayout;
import com.example.myviews.PullToRefreshLayout.OnRefreshListener;
import com.example.myviews.PullableListView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lottery.adapters.PayAdapter;
import com.lottery.adapters.PaymentAdapter;
import com.lottery.common.BaseActivity;
import com.lottery.constant.Constant;
import com.lottery.listeners.HttpCallback;
import com.lottery.utils.AndroidTools;
import com.lottery.utils.HttpUtils;

public class RecordActivity extends BaseActivity // ÷–Ω±ºÕ¬º
		implements OnRefreshListener
{
	private Button all, draw;
	private PullToRefreshLayout list_record;
	private PullableListView content_view;

	private List<Pay> payments;

	@Override
	public void setContentView()
	{
		setContentView(R.layout.activity_record);
		// all = (Button) findViewById(R.id.all);
		// draw = (Button) findViewById(R.id.draw);
		list_record = (PullToRefreshLayout) findViewById(R.id.list_record);
		content_view = (PullableListView) findViewById(R.id.content_view);
	}

	private Gson gson;
	private HashMap<String, String> data;

	@Override
	public void initView()
	{
		data = new HashMap<>();
		data.put("key", prefUtils.getString("key"));
		data.put("page", "10");
		data.put("curpage", "0");
		HttpUtils httpUtils = new HttpUtils(
				httpCallback,
				"http://test.nsscn.org/zc/index.php?act=message&op=get_the_winning_record_message",
				"POST", data);
		if (isNetworkAvailable(this))
		{
			httpUtils.excute();
		} else
		{
			AndroidTools.showToastShort(this, "Õ¯¬Á¡¨Ω” ß∞‹");
		}
	}

	@Override
	public void initListener()
	{
		gson = new Gson();
		handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				if (msg.what == Constant.HANDLE_SUCCESS)
				{
					try
					{
						payments = gson.fromJson(datas.getString("data"),
								new TypeToken<ArrayList<Pay>>()
								{
								}.getType());
					} catch (JsonSyntaxException | JSONException e)
					{
						e.printStackTrace();
					}
					if (payments != null)
					{
						content_view.setAdapter(new PayAdapter(payments,
								RecordActivity.this));
					}
				}else if (msg.what == Constant.HANDLE_REFRESH_SUCCESS) {
					try
					{
						payments = gson.fromJson(datas.getString("data"),
								new TypeToken<ArrayList<Pay>>()
								{
								}.getType());
					} catch (JsonSyntaxException | JSONException e)
					{
						e.printStackTrace();
					}
					if (payments != null)
					{
						content_view.setAdapter(payAdapter = new PayAdapter(payments,
								RecordActivity.this));
					}
					list_record.refreshFinish(PullToRefreshLayout.SUCCEED);
				}else if (msg.what == Constant.HANDLE_LOAD_SUCCESS) {
					try
					{
						payments = gson.fromJson(datas.getString("data"),
								new TypeToken<ArrayList<Pay>>()
								{
								}.getType());
					} catch (JsonSyntaxException | JSONException e)
					{
						e.printStackTrace();
					}
					if (payments != null)
					{
						content_view.setAdapter(new PayAdapter(payments,
								RecordActivity.this));
					}
				}else if (msg.what == Constant.HANDLE_LOAD_SUCCESS) {
					try
					{
						payments = gson.fromJson(datas.getString("data"),
								new TypeToken<ArrayList<Pay>>()
								{
								}.getType());
					} catch (JsonSyntaxException | JSONException e)
					{
						e.printStackTrace();
					}
					if (payments != null)
					{
						content_view.setAdapter(new PayAdapter(payments,
								RecordActivity.this));
					}
				}else if (msg.what == Constant.HANDLE_REFRESH_SUCCESS) {
					try
					{
						payments = gson.fromJson(datas.getString("data"),
								new TypeToken<ArrayList<Pay>>()
								{
								}.getType());
					} catch (JsonSyntaxException | JSONException e)
					{
						e.printStackTrace();
					}
					if (payments != null)
					{
						payAdapter.addMoreData(payments);
						payAdapter.notifyDataSetChanged();
					}
					list_record.loadmoreFinish(PullToRefreshLayout.SUCCEED);
				}else if (msg.what == Constant.HANDLE_REFRESH_FAIL) {
					list_record.loadmoreFinish(PullToRefreshLayout.FAIL);
				}else if (msg.what == Constant.HANDLE_LOAD_FAIL) {
					list_record.refreshFinish(PullToRefreshLayout.FAIL);
				}
			}
		};
		list_record.setOnRefreshListener(this);
	}

	@Override
	public void initData()
	{

	}
	private int pageIndex = 0;
	private String pageSize = "10";
	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout)
	{
		if (isNetworkAvailable(this))
		{
			data = new HashMap<>();
			data.put("key", prefUtils.getString("key"));
			data.put("page", "10");
			data.put("curpage", "0");
			HttpUtils httpUtils = new HttpUtils(
					new HttpCallback()
					{
						
						@Override
						public void Success(String success)
						{
							Message message = Message.obtain();
							message.what = Constant.HANDLE_REFRESH_SUCCESS;
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
							message.obj = datas;
							handler.sendMessage(message);
							
						}
						
						@Override
						public void Failed(String failed)
						{
							Message message = Message.obtain();
							message.what = Constant.HANDLE_REFRESH_FAIL;
							message.obj = datas;
							handler.sendMessage(message);
							
						}
					},
					"http://test.nsscn.org/zc/index.php?act=message&op=get_the_winning_record_message",
					"POST", data);
			if (isNetworkAvailable(this))
			{
				httpUtils.excute();
			} else
			{
				AndroidTools.showToastShort(this, "Õ¯¬Á¡¨Ω” ß∞‹");
			}
		} else
		{
			AndroidTools.showToastShort(this, "Õ¯¬Á¡¨Ω” ß∞‹");
			pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
		}
	}
	private PayAdapter payAdapter;

	@Override
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout)
	{
		if (isNetworkAvailable(this))
		{
			pageIndex++;
			data = new HashMap<>();
			data.put("key", prefUtils.getString("key"));
			data.put("page", "10");
			data.put("curpage", ""+pageIndex);
			HttpUtils httpUtils = new HttpUtils(
					new HttpCallback()
					{
						
						@Override
						public void Success(String success)
						{
							Message message = Message.obtain();
							message.what = Constant.HANDLE_LOAD_SUCCESS;
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
							message.obj = datas;
							handler.sendMessage(message);
						}
						
						@Override
						public void Failed(String failed)
						{
							Message message = Message.obtain();
							message.what = Constant.HANDLE_LOAD_FAIL;
							message.obj = failed;
							handler.sendMessage(message);
						}
					},
					"http://test.nsscn.org/zc/index.php?act=message&op=get_the_winning_record_message",
					"POST", data);
			if (isNetworkAvailable(this))
			{
				httpUtils.excute();
			} else
			{
				AndroidTools.showToastShort(this, "Õ¯¬Á¡¨Ω” ß∞‹");
			}
			
		} else
		{
			AndroidTools.showToastShort(this, "Õ¯¬Á¡¨Ω” ß∞‹");
			pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
		}

	}

}
