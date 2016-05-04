package com.example.lottery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.myviews.PullToRefreshLayout;
import com.example.myviews.PullToRefreshLayout.OnRefreshListener;
import com.example.myviews.PullableListView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lottery.adapters.MessageAdapter;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MessageActivity extends BaseActivity implements OnRefreshListener
{
	private PullToRefreshLayout message_list;
	private PullableListView content_View;

	private Gson gson;
	private List<com.example.bean.Message> messages;

	private String pageSize = "10";
	private int pageIndex = 0;

	private HashMap<String, String> data;

	@Override
	public void setContentView()
	{
		setContentView(R.layout.activity_message);

	}

	@Override
	public void initView()
	{
		
		content_View = (PullableListView) findViewById(R.id.content_view);
		message_list = (PullToRefreshLayout) findViewById(R.id.list_message);
	}

	@Override
	public void initListener()
	{
		data = new HashMap<>();
		gson = new Gson();
		data.put("key", prefUtils.getString("key"));
		System.out.println(prefUtils.getString("key"));
		data.put("page", "12");
		data.put("curpage", "0");
		message_list.setOnRefreshListener(this);
		HttpUtils httpUtils = new HttpUtils(
				httpCallback,
				"http://test.nsscn.org/zc/index.php?act=message&op=get_message",
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
	public void initData()
	{
		handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				System.out.println(datas.toString()+msg.what);
				if (msg.what == Constant.HANDLE_SUCCESS)
				{
					try
					{
						messages = gson
								.fromJson(
										datas.getString("data"),
										new TypeToken<ArrayList<com.example.bean.Message>>()
										{
										}.getType());
					} catch (JsonSyntaxException | JSONException e)
					{
						e.printStackTrace();
					}
					if (messages != null)
					{
						messageAdapter = new  MessageAdapter(
								MessageActivity.this, messages);
						content_View
								.setAdapter(messageAdapter);
					}
				} else if (msg.what == Constant.HANDLE_REFRESH_SUCCESS)
				{

					try
					{
						messages = gson
								.fromJson(
										datas.getString("data"),
										new TypeToken<ArrayList<com.example.bean.Message>>()
										{
										}.getType());
					} catch (JsonSyntaxException | JSONException e)
					{
						e.printStackTrace();
					}
					if (messages != null)
					{
						content_View
								.setAdapter(messageAdapter = new MessageAdapter(
										MessageActivity.this, messages));
					}
					message_list.refreshFinish(PullToRefreshLayout.SUCCEED);
				} else if (msg.what == Constant.HANDLE_LOAD_SUCCESS)
				{

					try
					{
						messages = gson
								.fromJson(
										datas.getString("data"),
										new TypeToken<ArrayList<com.example.bean.Message>>()
										{
										}.getType());
					} catch (JsonSyntaxException | JSONException e)
					{
						e.printStackTrace();
					}
					if (messages != null)
					{
						messageAdapter.UpData(messages);
						messageAdapter.notifyDataSetChanged();
						message_list.loadmoreFinish(PullToRefreshLayout.SUCCEED);
					}
				} else if (msg.what == Constant.HANDLE_REFRESH_FAIL)
				{
					message_list.loadmoreFinish(PullToRefreshLayout.FAIL);
				} else if (msg.what == Constant.HANDLE_LOAD_FAIL)
				{
					message_list.refreshFinish(PullToRefreshLayout.FAIL);
				}

			}
		};

	}

	private MessageAdapter messageAdapter;

	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout)
	{
		data = new HashMap<>();
		gson = new Gson();
		data.put("key", prefUtils.getString("key"));
		System.out.println(prefUtils.getString("key"));
		data.put("page", "12");
		data.put("curpage", "0");
		HttpUtils httpUtils = new HttpUtils(new HttpCallback()
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
				message.obj = failed;
				handler.sendMessage(message);
			}
		}, "http://test.nsscn.org/zc/index.php?act=message&op=get_message",
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
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout)
	{
		if (isNetworkAvailable(this))
		{
			pageIndex += 1;
			data = new HashMap<>();
			gson = new Gson();
			data.put("key", prefUtils.getString("key"));
			System.out.println(prefUtils.getString("key"));
			data.put("page", "12");
			data.put("curpage", "" + pageIndex);
			HttpUtils httpUtils = new HttpUtils(new HttpCallback()
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
			}, "http://test.nsscn.org/zc/index.php?act=message&op=get_message",
					"POST", data);
			httpUtils.excute();
		} else
		{
			pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
			AndroidTools.showToastShort(this, "Õ¯¬Á¡¨Ω” ß∞‹");
		}
	}

}
