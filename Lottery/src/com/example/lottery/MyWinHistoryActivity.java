package com.example.lottery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;

import com.example.bean.MoneyDetail;
import com.example.bean.SpinnerCell;
import com.example.myviews.PullToRefreshLayout;
import com.example.myviews.PullToRefreshLayout.OnRefreshListener;
import com.example.myviews.PullableListView;
import com.example.myviews.SpinnerPopWindow;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.lottery.adapters.MoneyItemAdapter;
import com.lottery.adapters.SpinnerAdapter;
import com.lottery.common.BaseActivity;
import com.lottery.constant.Constant;
import com.lottery.listeners.HttpCallback;
import com.lottery.utils.AndroidTools;
import com.lottery.utils.HttpUtils;

public class MyWinHistoryActivity extends BaseActivity implements
		OnClickListener, OnRefreshListener
{
	private PullToRefreshLayout historylist;
	private PullableListView pullableListView;

	private HttpUtils httpUtils;

	private LinearLayout spinner_surgery, spinner_title;
	protected ArrayList<SpinnerCell> timelist = new ArrayList<SpinnerCell>();
	protected ArrayList<SpinnerCell> waylist = new ArrayList<SpinnerCell>();

	private ImageView spinner_surgery_arrow, spinner_title_arrow;// 左和右
	private TextView spinner_surgery_text, spinner_title_text;// 时间和玩法

	@Override
	public void setContentView()
	{
		setContentView(R.layout.activity_my_win_history);
	}

	private SpinnerPopWindow timePopWindow;
	private SpinnerPopWindow wayPopWindow;

	@Override
	public void initView()
	{
		historylist = (PullToRefreshLayout) findViewById(R.id.list_win);
		pullableListView = (PullableListView) findViewById(R.id.content_view);
		spinner_surgery = (LinearLayout) findViewById(R.id.spinner_surgery);
		spinner_surgery.setOnClickListener(this);
		spinner_title = (LinearLayout) findViewById(R.id.spinner_title);
		spinner_title.setOnClickListener(this);
		historylist.setOnRefreshListener(this);
		timelist.add(new SpinnerCell("最一周", "aaaa"));
		timelist.add(new SpinnerCell("最一个月", "aaaa"));
		timelist.add(new SpinnerCell("最近一年", "aaaa"));
		waylist.add(new SpinnerCell("购彩", "aaaa"));
		waylist.add(new SpinnerCell("充值", "aaaa"));
		waylist.add(new SpinnerCell("提现", "aaaa"));
		waylist.add(new SpinnerCell("派奖", "aaaa"));
		waylist.add(new SpinnerCell("红包", "aaaa"));
		waylist.add(new SpinnerCell("其他", "aaaa"));
		timeAdapter = new SpinnerAdapter(this, timelist);
		playWayAdapter = new SpinnerAdapter(this, waylist);
		spinner_surgery_arrow = (ImageView) findViewById(R.id.spinner_surgery_arrow);
		spinner_title_arrow = (ImageView) findViewById(R.id.spinner_title_arrow);// wanfa
		spinner_surgery_text = (TextView) findViewById(R.id.spinner_surgery_text);
		spinner_title_text = (TextView) findViewById(R.id.spinner_title_text);// wanfa
		// httpUtils = new HttpUtils(httpCallback, path, method, data)
	}

	protected SpinnerAdapter timeAdapter;
	protected SpinnerAdapter playWayAdapter;
	int type = 1;
	@Override
	public void initListener()
	{
		historylist.setOnRefreshListener(this);
		timePopWindow = new SpinnerPopWindow(this, timeAdapter,
				new OnItemClickListener()
				{

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id)
					{
						timePopWindow.dismiss();
						spinner_surgery_text.setText(timelist.get(position)
								.getName());
						spinner_surgery_arrow
								.setImageResource(R.drawable.arrow_up_black);
						spinner_surgery_text.setTextColor(Color
								.parseColor("#000000"));
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"yyyy-MM-dd");
						String time = dateFormat.format(new Date());
						
						int	day = 10*Integer.valueOf(time.substring(time.length() - 2, time.length()-1))
								+ Integer.valueOf(time.substring(time.length() - 1, time.length()));
						int month = Integer.valueOf(time.substring(time.length() - 5, time.length() - 3));
						int year = Integer.valueOf(time.substring(0,4));
						System.out.println("position"+position);
						switch (position) {
						case 0:
						if (day >= 7) {
							day -= 7;
							time = time.substring(0, time.length() - 2) + String.valueOf(day);
						}else {
							if (month == 1) {
								year -= 1;
								month = 12;
								day = 30 - 7 + day;
								time = String.valueOf(year)+ "-" + String.valueOf(month)+ "-" + String.valueOf(day);
							}else {
								month -= 1;
								if (month == 1 || month == 3|| month == 5|| month == 7 || month == 8||
										month == 10 || month == 11) {
									day = 31 - 7 + day;
								}else if(month == 2 && (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)){
									day = 29 - 7 + day;
								}else if (month == 2) {
									day = 28 - 7 + day;
								}else {
									day = 30 - 7 + day;
								}
							}
							time = String.valueOf(year)+ "-" + String.valueOf(month)+ "-" + String.valueOf(day);
						}	
						break;
						case 1:
						if (month == 1) {
							year = year - 1;
							month = 12;
							time = String.valueOf(year)+ "-" + String.valueOf(month)+ "-" + String.valueOf(day);
						}else {
							month -= 1;
							time = String.valueOf(year)+ "-" + String.valueOf(month)+ "-" + String.valueOf(day);
						}		
							break;
						case 2:
							year -= 1;
							time = String.valueOf(year)+ "-" + String.valueOf(month)+ "-" + String.valueOf(day);
							break;
						default:
							break;
						}
						HashMap<String, String> data = new HashMap<>();
						data.put("time", time);
						data.put("key", prefUtils.getString("key"));
						data.put("type", "" + type);
						data.put("page", ""+pageSize);
						data.put("curpage", "0");
						pageIndex = 0;
						HttpUtils httpUtils = new HttpUtils(
								httpCallback,
								"http://test.nsscn.org/zc/index.php?act=message&op=get_capital_subsidiary",
								"POST", data);
						httpUtils.excute();
					}
				});
		wayPopWindow = new SpinnerPopWindow(this, playWayAdapter,
				new OnItemClickListener()
				{

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id)
					{
						wayPopWindow.dismiss();
						spinner_title_arrow
								.setBackgroundResource(R.drawable.arrow_up_black);
						spinner_title_text.setText(waylist.get(position)
								.getName());
						spinner_title_text.setTextColor(Color
								.parseColor("#000000"));
						HashMap<String, String> data = new HashMap<>();
						data.put("time", "2000-1-1");
						data.put("key", prefUtils.getString("key"));
						if (position == 0) {
							data.put("type", "" + 9);
							type = 9;
						}else {
							data.put("type", ""+16);
							type = 16;
						}
						data.put("page", ""+pageSize);
						data.put("curpage", "0");
						pageIndex = 0;
						HttpUtils httpUtils = new HttpUtils(
								httpCallback,
								"http://test.nsscn.org/zc/index.php?act=message&op=get_capital_subsidiary",
								"POST", data);
						httpUtils.excute();
					}
				});
		BitmapDrawable bd = new BitmapDrawable();
		wayPopWindow.setBackgroundDrawable(bd);
		timePopWindow.setBackgroundDrawable(bd);
		wayPopWindow.setOutsideTouchable(true);
		timePopWindow.setOutsideTouchable(true);

		wayPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener()
		{
			@Override
			public void onDismiss()
			{
				spinner_title_arrow.setImageResource(R.drawable.arrow_up_black);
				spinner_title_text.setTextColor(Color.parseColor("#000000"));
			}
		});
		timePopWindow.setOnDismissListener(new OnDismissListener()
		{

			@Override
			public void onDismiss()
			{
				spinner_surgery_arrow
						.setImageResource(R.drawable.arrow_up_black);
				spinner_surgery_text.setTextColor(Color.parseColor("#000000"));
			}
		});

		HashMap<String, String> data = new HashMap<>();
		data.put("type", "1");
		data.put("time", "2000-10-10");
		data.put("key", prefUtils.getString("key"));
		data.put("page", "10");
		data.put("curpage", "0");
		httpUtils = new HttpUtils(
				httpCallback,
				"http://test.nsscn.org/zc/index.php?act=message&op=get_capital_subsidiary",
				"POST", data);
		httpUtils.excute();
		// http://test.nsscn.org/zc/index.php?act=allocation&op=get_historic_records
		// type,time
	}

	private List<MoneyDetail> moneyDetails;
	private Gson gson;

	@Override
	public void initData()
	{
		gson = new Gson();
		handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				try
				{
					moneyDetails = gson.fromJson(datas.getString("data"),
							new TypeToken<List<MoneyDetail>>()
							{
							}.getType());
				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (msg.what == Constant.HANDLE_SUCCESS)
				{
					moneyItemAdapter = new MoneyItemAdapter(moneyDetails, MyWinHistoryActivity.this);
					pullableListView.setAdapter(moneyItemAdapter);
				}else if (msg.what == Constant.HANDLE_REFRESH_SUCCESS) {
					moneyItemAdapter = new MoneyItemAdapter(moneyDetails, MyWinHistoryActivity.this);
					pullableListView.setAdapter(moneyItemAdapter);
					historylist.refreshFinish(PullToRefreshLayout.SUCCEED);
				}else if (msg.what == Constant.HANDLE_LOAD_SUCCESS) {
					moneyItemAdapter.upData(moneyDetails);
					moneyItemAdapter.notifyDataSetChanged();
					historylist.loadmoreFinish(PullToRefreshLayout.SUCCEED);
				}else if (msg.what == Constant.HANDLE_REFRESH_FAIL) {
					historylist.refreshFinish(PullToRefreshLayout.FAIL);
				}else if (msg.what == Constant.HANDLE_LOAD_FAIL) {
					historylist.loadmoreFinish(PullToRefreshLayout.FAIL);
				}
			}
		};
	}

	private int pageSize = 10;
	private int pageIndex = 1;

	private MoneyItemAdapter moneyItemAdapter;

	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout)
	{
		if (isNetworkAvailable(this))
		{
			HashMap<String, String> data = new HashMap<>();
			data.put("type", "1");
			data.put("time", "2000-10-10");
			data.put("key", prefUtils.getString("key"));
			data.put("page", "10");
			data.put("curpage", "0");
			httpUtils = new HttpUtils(
					new HttpCallback()
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
								count = job.getInt("count");
							} catch (JSONException e)
							{
								e.printStackTrace();
							}
							Message message = Message.obtain();
							message.what = Constant.HANDLE_REFRESH_SUCCESS;
							handler.sendMessage(message);
						}
						
						@Override
						public void Failed(String failed)
						{
							Message message = Message.obtain();
							message.what = Constant.HANDLE_FAIL;
							handler.sendMessage(message);
						}
					},
					"http://test.nsscn.org/zc/index.php?act=message&op=get_capital_subsidiary",
					"POST", data);
			httpUtils.excute();
		}else  {
			pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
		}
	}

	@Override
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout)
	{
		if (isNetworkAvailable(this))
		{
			if (moneyItemAdapter.getCount() < count)
			{
				pageIndex++;
				HashMap<String, String> data = new HashMap<>();
				data.put("type", "1");
				data.put("time", "2000-10-10");
				data.put("key", prefUtils.getString("key"));
				data.put("page", "10");
				data.put("curpage", "0"+pageIndex);
				httpUtils = new HttpUtils(
						new HttpCallback()
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
								message.what = Constant.HANDLE_LOAD_SUCCESS;
								handler.sendMessage(message);
							}
							
							@Override
							public void Failed(String failed)
							{
								Message message = Message.obtain();
								message.what = Constant.HANDLE_LOAD_FAIL;
								handler.sendMessage(message);
							}
						},
						"http://test.nsscn.org/zc/index.php?act=message&op=get_capital_subsidiary",
						"POST", data);
				httpUtils.excute();
			}else {
				AndroidTools.showToastShort(this, "没有更多数据了");
				pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
			}
		}else {
			pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
		}
	}

	@Override
	public void onClick(View v)
	{
		System.out.println(v.getId());
		switch (v.getId())
		{
		case R.id.spinner_surgery:// 按时间
			spinner_surgery_arrow.setImageResource(R.drawable.arrow_down_blue);
			timePopWindow.setWidth(spinner_surgery.getWidth());
			timePopWindow.showAsDropDown(spinner_surgery);
			spinner_surgery_text.setTextColor(Color.parseColor("#56abe4"));
			break;
		case R.id.spinner_title:// 按玩法
			spinner_title_arrow.setImageResource(R.drawable.arrow_down_blue);
			wayPopWindow.setWidth(spinner_title.getWidth());
			wayPopWindow.showAsDropDown(spinner_title);
			spinner_title_text.setTextColor(Color.parseColor("#56abe4"));
			break;
		default:
			break;
		}
	}

}
