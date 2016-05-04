package com.example.lottery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.example.bean.SpinnerCell;
import com.example.bean.WinHistory;
import com.example.myviews.PullToRefreshLayout;
import com.example.myviews.PullToRefreshLayout.OnRefreshListener;
import com.example.myviews.PullableListView;
import com.example.myviews.SpinnerPopWindow;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lottery.adapters.SpinnerAdapter;
import com.lottery.adapters.WinHistoryAdapter;
import com.lottery.common.BaseActivity;
import com.lottery.constant.Constant;
import com.lottery.listeners.HttpCallback;
import com.lottery.utils.AndroidTools;
import com.lottery.utils.HttpUtils;

public class WinHistoryActivity extends BaseActivity implements
		OnRefreshListener, OnClickListener {
	private PullToRefreshLayout historylist;
	private PullableListView pullableListView;

	private HttpUtils httpUtils;

	private LinearLayout spinner_surgery, spinner_title;
	protected ArrayList<SpinnerCell> timelist = new ArrayList<SpinnerCell>();
	protected ArrayList<SpinnerCell> waylist = new ArrayList<SpinnerCell>();

	private ImageView spinner_surgery_arrow, spinner_title_arrow;// 左和右
	private TextView spinner_surgery_text, spinner_title_text;// 时间和玩法

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_win_history);

	}

	private SpinnerPopWindow timePopWindow;
	private SpinnerPopWindow wayPopWindow;


	@Override
	public void initView() {
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
		waylist.add(new SpinnerCell("任选9", "aaaa"));
		waylist.add(new SpinnerCell("任选16", "aaaa"));
		timeAdapter = new SpinnerAdapter(this, timelist);
		playWayAdapter = new SpinnerAdapter(this, waylist);
		spinner_surgery_arrow = (ImageView) findViewById(R.id.spinner_surgery_arrow);
		spinner_title_arrow = (ImageView) findViewById(R.id.spinner_title_arrow);// wanfa
		spinner_surgery_text = (TextView) findViewById(R.id.spinner_surgery_text);
		spinner_title_text = (TextView) findViewById(R.id.spinner_title_text);// wanfa
	}

	protected SpinnerAdapter timeAdapter;
	protected SpinnerAdapter playWayAdapter;
	int type = 0;
	@Override
	public void initListener() {
		historylist.setOnRefreshListener(this);
		timePopWindow = new SpinnerPopWindow(this, timeAdapter,
				new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
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
						data.put("type", "" + type);
						data.put("page", ""+pageSize);
						data.put("curpage", "0");
						pageIndex = 0;
						HttpUtils httpUtils = new HttpUtils(
								httpCallback,
								"http://test.nsscn.org/zc/index.php?act=allocation&op=get_historic_records",
								"POST", data);
						httpUtils.excute();
						System.out.println(time);
					}
				});
		wayPopWindow = new SpinnerPopWindow(this, playWayAdapter,
				new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						HashMap<String, String> data = new HashMap<>();
						data.put("time", "2000-1-1");
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
								"http://test.nsscn.org/zc/index.php?act=allocation&op=get_historic_records",
								"POST", data);
						httpUtils.excute();
						wayPopWindow.dismiss();
						spinner_title_arrow
								.setBackgroundResource(R.drawable.arrow_up_black);
						spinner_title_text.setText(waylist.get(position)
								.getName());
						spinner_title_text.setTextColor(Color
								.parseColor("#000000"));
					}
				});
		BitmapDrawable bd = new BitmapDrawable();
		wayPopWindow.setBackgroundDrawable(bd);
		timePopWindow.setBackgroundDrawable(bd);
		wayPopWindow.setOutsideTouchable(true);
		timePopWindow.setOutsideTouchable(true);

		wayPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				spinner_title_arrow.setImageResource(R.drawable.arrow_up_black);
				spinner_title_text.setTextColor(Color.parseColor("#000000"));
			}
		});
		timePopWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				spinner_surgery_arrow
						.setImageResource(R.drawable.arrow_up_black);
				spinner_surgery_text.setTextColor(Color.parseColor("#000000"));
			}
		});
		HashMap<String, String> data = new HashMap<>();
		data.put("time", "2000-1-1");
		data.put("type", "0");
		data.put("page", ""+pageSize);
		data.put("curpage", "0");
		HttpUtils httpUtils = new HttpUtils(
				httpCallback,
				"http://test.nsscn.org/zc/index.php?act=allocation&op=get_historic_records",
				"POST", data);
		httpUtils.excute();
		// http://test.nsscn.org/zc/index.php?act=allocation&op=get_historic_records
		// type,time
	}

	private WinHistoryAdapter winHistoryAdapter;
	private List<WinHistory> winHistories;
	private Gson gson;

	@Override
	public void initData() {
		gson = new Gson();
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				System.out.println("what" + datas.length());
				if (isNetworkAvailable(WinHistoryActivity.this)) {
					winHistories = new LinkedList<>();
					try {
						// winHistories = gson.fromJson(datas.getString("data"),
						// new TypeToken<List<WinHistory>>()
						// {
						// }.getType());
						JSONArray jsonArray = new JSONArray(
								datas.getString("data"));
						
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							WinHistory winHistory = new WinHistory();
							winHistory.setCrowdfunding_id(jsonObject
									.getString("crowdfunding_id"));
							JSONArray detail = new JSONArray(
									jsonObject.getString("detail"));
							String detail1 = new String();
							for (int j = 0; j < detail.length(); j++) {
								JSONObject jsonObject2 = (JSONObject) detail
										.get(j);
								detail1 += jsonObject2.getString("team") + ":"
										+ jsonObject2.getString("res");
							}
							winHistory.setDetail(detail1);
							winHistory.setEnd_time(jsonObject
									.getString("end_time"));
							winHistory.setPlans_name(jsonObject
									.getString("plans_name"));
							winHistory.setPlans_price(jsonObject
									.getString("plans_price"));
							winHistory.setPlans_salenum(jsonObject
									.getString("plans_salenum"));
							winHistory.setPlans_storage(jsonObject
									.getString("plans_storage"));
							winHistory.setPlans_type(jsonObject
									.getString("plans_type"));
							winHistory
									.setRecord(jsonObject.getString("record"));
							winHistory.setStart_time(jsonObject
									.getString("start_time"));
							winHistories.add(winHistory);
						}
					} catch (JsonSyntaxException | JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println(winHistories.size()+"size");
				if (msg.what == Constant.HANDLE_SUCCESS) {

					winHistoryAdapter = new WinHistoryAdapter(
							WinHistoryActivity.this, winHistories);
					pullableListView.setAdapter(winHistoryAdapter);
				} else if (msg.what == Constant.HANDLE_REFRESH_SUCCESS) {

					winHistoryAdapter = new WinHistoryAdapter(
							WinHistoryActivity.this, winHistories);
					pullableListView.setAdapter(winHistoryAdapter);
					historylist.refreshFinish(PullToRefreshLayout.SUCCEED);
				} else if (msg.what == Constant.HANDLE_LOAD_SUCCESS) {

					winHistoryAdapter.upData(winHistories);
					winHistoryAdapter.notifyDataSetChanged();
					historylist.loadmoreFinish(PullToRefreshLayout.SUCCEED);
				} else if (msg.what == Constant.HANDLE_LOAD_FAIL) {
					historylist.refreshFinish(PullToRefreshLayout.FAIL);
				} else if (msg.what == Constant.HANDLE_REFRESH_FAIL) {
					historylist.loadmoreFinish(PullToRefreshLayout.FAIL);
				}
			}
		};
	}

	private int pageSize = 15;
	private int pageIndex = 0;

	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
		if (isNetworkAvailable(this)) {
			if (winHistoryAdapter.getCount() < count) {
				HashMap<String, String> data = new HashMap<>();
				data.put("time", "2000-1-1");
				data.put("type", ""+type);
				data.put("page", ""+pageSize);
				data.put("curpage", "0");
				httpUtils = new HttpUtils(
						new HttpCallback() {

							@Override
							public void Success(String success) {
								try {
									job = new JSONObject(success);
									code = job.getString("code");
									datas = new JSONObject(job
											.getString("datas"));
									error = job.getString("error");
									count = job.getInt("count");
								} catch (JSONException e) {
									e.printStackTrace();
								}
								Message message = Message.obtain();
								message.what = Constant.HANDLE_REFRESH_SUCCESS;
								handler.sendMessage(message);
							}

							@Override
							public void Failed(String failed) {
								Message message = Message.obtain();
								message.what = Constant.HANDLE_FAIL;
								handler.sendMessage(message);
							}
						},
						"http://test.nsscn.org/zc/index.php?act=allocation&op=get_historic_records",
						"POST", data);
				httpUtils.excute();
			} else {
				AndroidTools.showToastShort(this, "没有更多数据了");
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
			}
		} else {
			pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
		}
	}

	@Override
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
		if (isNetworkAvailable(this)) {
			pageIndex++;
			HashMap<String, String> data = new HashMap<>();
			data.put("time", "2000-1-1");
			data.put("type", ""+type);
			data.put("page", "10");
			data.put("curpage", "0" + pageIndex);
			httpUtils = new HttpUtils(
					new HttpCallback() {

						@Override
						public void Success(String success) {
							try {
								job = new JSONObject(success);
								code = job.getString("code");
								datas = new JSONObject(job.getString("datas"));
								error = job.getString("error");
								count = job.getInt("count");
							} catch (JSONException e) {
								e.printStackTrace();
							}
							Message message = Message.obtain();
							message.what = Constant.HANDLE_LOAD_SUCCESS;
							handler.sendMessage(message);
						}

						@Override
						public void Failed(String failed) {
							Message message = Message.obtain();
							message.what = Constant.HANDLE_LOAD_FAIL;
							handler.sendMessage(message);
						}
					},
					"http://test.nsscn.org/zc/index.php?act=allocation&op=get_historic_records",
					"POST", data);
			httpUtils.excute();
		} else {
			pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
		}
	}

	@Override
	public void onClick(View v) {
		System.out.println(v.getId());
		switch (v.getId()) {
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
