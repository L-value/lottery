package com.lottery.fragements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.taptwo.android.widget.CircleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.example.bean.Way;
import com.example.imageloader.ImageAdapter;
import com.example.lottery.DeatilActivity;
import com.example.lottery.R;
import com.example.myviews.PullToRefreshLayout;
import com.example.myviews.PullToRefreshLayout.OnRefreshListener;
import com.example.myviews.PullableListView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.lottery.adapters.ViewFlowAdapter;
import com.lottery.constant.Constant;
import com.lottery.listeners.HttpCallback;
import com.lottery.utils.AndroidTools;
import com.lottery.utils.HttpUtils;
import com.lottery.utils.ImmersedStatusbarUtils;

public class HomeFragement extends BaseFragement implements OnRefreshListener
{
	private int current = 0;
	private String pagesize = "10";
	private ImageAdapter imageAdapter;
	private Button choose9,choose16;
	
	//{"code":200,"datas":{"plans_list":[
//	{"crowdfunding_id":"6","start_time":"2016-01-13 00:00:00",
//		"end_time":"2016-02-15 00:00:00","plans_name":"\u4f17\u7b79F\u7ec4","plans_price"
//		:"1","plans_salenum":"0","plans_storage":"100","plans_image":"rgb.bmp","plans_type":"9"}
//	,{"crowdfunding_id":"5","start_time":"2016-01-13 00:00:00","end_time":"2016-02-15 00:00:00",
//		"plans_name":"\u4f17\u7b79E\u7ec4","plans_price":"1","plans_salenum":"0","plans_storage":
//			"100","plans_image":"rgb.bmp","plans_type":"9"},{"crowdfunding_id":"4","start_time":""
//					+ "2016-01-13 00:00:00","end_time":"2016-02-15 00:00:00","plans_name":
 //							"100","plans_image":"rgb.bmp","plans_type":"9"},{"crowdfunding_id":"3"
//								,"start_time":"2016-01-13 00:00:00","end_time":"2016-02-15 00:00:00"
//								,"plans_name":"\u4f17\u7b79C\u7ec4","plans_price":"1","plans_salenum":"0",
//								"plans_storage":"100","plans_image":"rgb.bmp","plans_type":"9"}]}}
//	<input type="text" name="page" value="10"/>
//	<input type="text" name="curpage" value="0"/>
	private boolean click9 = true,click16;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			System.out.println(msg.what);
			if (msg.what == Constant.HANDLE_SUCCESS)
			{
				System.out.println(msg.obj.toString());
				JSONObject jsonObject = null;
				try
				{
					jsonObject = new JSONObject(msg.obj.toString());
				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try
				{
					String code = jsonObject.getString("code");
					JSONObject datas = new JSONObject(jsonObject.getString("datas"));
					String plans_list = datas.getString("plans_list");
				//	count = datas.getInt("count");
					JSONArray jsonArrays = new JSONArray(plans_list);
					ways9 = new ArrayList<>();
					ways16 = new ArrayList<>();
					for (int i = 0; i < jsonArrays.length(); i++)
					{
					
						Way way = new Way();
						JSONObject way1 = (JSONObject) jsonArrays.get(i);
						way.setCrowdfunding_id(way1.getString("crowdfunding_id"));
						JSONArray detail = new JSONArray(way1.getString("detail"));
						String detail1 = new String();
						for (int j = 0; j < detail.length(); j++)
						{
							JSONObject jsonObject2 = (JSONObject) detail.get(j);
							detail1 += jsonObject2.getString("team") + ":" +jsonObject2.getString("res");							
						}
						way.setDetail(detail1);
						way.setEnd_time(way1.getString("end_time"));
						way.setPlans_image(way1.getString("plans_image"));
						way.setPlans_name(way1.getString("plans_name"));
						way.setPlans_price(way1.getInt("plans_price"));
						way.setPlans_salenum(way1.getInt("plans_salenum"));
						way.setPlans_storage(way1.getInt("plans_storage"));
						way.setPlans_type(way1.getInt("plans_type"));
						way.setStart_time(way1.getString("start_time"));
						if (way.getPlans_type() == 9) {
							ways9.add(way);
						}else {
							ways16.add(way);
						}
						System.out.println(way.toString());
					}
					//ways = gson.fromJson(plans_list, new TypeToken<List<Way>>()
					//{
					//}.getType());
//					for (Way way : ways)
//					{
//						System.out.println(way.toString());
//					}
					//List<Way> ways = jsonArray.

				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (click16) {
					imageAdapter = new ImageAdapter(getActivity(), pullablelistview, new String[]{"","","","",""},ways16);
				}else{
					imageAdapter = new ImageAdapter(getActivity(), pullablelistview, new String[]{"","","","",""},ways9);
				}
				pullablelistview.setAdapter(imageAdapter);
				pullablelistview.setOnItemClickListener(new OnItemClickListener()
				{

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id)
					{
						Intent intent = new Intent();
						if (click16) {
							intent.setClass(getActivity(), DeatilActivity.class);
							intent.putExtra("id", ways16.get(position).getCrowdfunding_id());
							intent.putExtra("plans_type", ways16.get(position).getPlans_type());
							intent.putExtra("position", position);
							intent.putExtra("way", ways16.get(position).getPlans_name());//方案几
							intent.putExtra("all", ways16.get(position).getPlans_salenum());//总酬数
							intent.putExtra("price", ways16.get(position).getPlans_price());//每酬多少钱
							intent.putExtra("storage", ways16.get(position).getPlans_storage());//已经卖了多少酬
						}else {
							intent.setClass(getActivity(), DeatilActivity.class);
							intent.putExtra("id", ways9.get(position).getCrowdfunding_id());
							intent.putExtra("plans_type", ways9.get(position).getPlans_type());
							intent.putExtra("position", position);
							intent.putExtra("way", ways9.get(position).getPlans_name());//方案几
							intent.putExtra("all", ways9.get(position).getPlans_salenum());//总酬数
							intent.putExtra("price", ways9.get(position).getPlans_price());//每酬多少钱
							intent.putExtra("storage", ways9.get(position).getPlans_storage());//已经卖了多少酬
						}
						
						startActivity(intent);
					}
				});
			}else if (msg.what == Constant.HANDLE_REFRESH_SUCCESS) {
				System.out.println(msg.obj.toString());
				JSONObject jsonObject = null;
				try
				{
					jsonObject = new JSONObject(msg.obj.toString());
				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try
				{
					String code = jsonObject.getString("code");
					JSONObject datas = new JSONObject(jsonObject.getString("datas"));
					String plans_list = datas.getString("plans_list");
					JSONArray jsonArrays = new JSONArray(plans_list);
					if (click16) {
						ways16 = new ArrayList<>();
					}else {
						ways9 = new ArrayList<>();
					}
					for (int i = 0; i < jsonArrays.length(); i++)
					{
					
						Way way = new Way();
						JSONObject way1 = (JSONObject) jsonArrays.get(i);
						way.setCrowdfunding_id(way1.getString("crowdfunding_id"));
						JSONArray detail = new JSONArray(way1.getString("detail"));
						String detail1 = new String();
						for (int j = 0; j < detail.length(); j++)
						{
							JSONObject jsonObject2 = (JSONObject) detail.get(j);
							detail1 += jsonObject2.getString("team") + ":" +jsonObject2.getString("res");							
						}
						way.setDetail(detail1);
						way.setEnd_time(way1.getString("end_time"));
						way.setPlans_image(way1.getString("plans_image"));
						way.setPlans_name(way1.getString("plans_name"));
						way.setPlans_price(way1.getInt("plans_price"));
						way.setPlans_salenum(way1.getInt("plans_salenum"));
						way.setPlans_storage(way1.getInt("plans_storage"));
						way.setPlans_type(way1.getInt("plans_type"));
						way.setStart_time(way1.getString("start_time"));
						if (way.getPlans_type() == 9) {
							ways9.add(way);
						}else {
							ways16.add(way);
						}
						System.out.println(way.toString());
					}
					//ways = gson.fromJson(plans_list, new TypeToken<List<Way>>()
					//{
					//}.getType());
//					for (Way way : ways)
//					{
//						System.out.println(way.toString());
//					}
					//List<Way> ways = jsonArray.

				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (click16) {
					imageAdapter = new ImageAdapter(getActivity(), pullablelistview, new String[]{"","","","",""},ways16);
				}else{
					imageAdapter = new ImageAdapter(getActivity(), pullablelistview, new String[]{"","","","",""},ways9);
				}
				pullablelistview.setAdapter(imageAdapter);
				refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
				
			}else if (msg.what == Constant.HANDLE_LOAD_SUCCESS) {
				JSONObject jsonObject = null;
				try
				{
					jsonObject = new JSONObject(msg.obj.toString());
				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try
				{
					String code = jsonObject.getString("code");
					JSONObject datas = new JSONObject(jsonObject.getString("datas"));
					String plans_list = datas.getString("plans_list");
					JSONArray jsonArrays = new JSONArray(plans_list);
					ways9 = new ArrayList<>();
					ways16 = new ArrayList<>();
					for (int i = 0; i < jsonArrays.length(); i++)
					{
					
						Way way = new Way();
						JSONObject way1 = (JSONObject) jsonArrays.get(i);
						way.setCrowdfunding_id(way1.getString("crowdfunding_id"));
						JSONArray detail = new JSONArray(way1.getString("detail"));
						String detail1 = new String();
						for (int j = 0; j < detail.length(); j++)
						{
							JSONObject jsonObject2 = (JSONObject) detail.get(j);
							detail1 += jsonObject2.getString("team") + ":" +jsonObject2.getString("res");							
						}
						way.setDetail(detail1);
						way.setEnd_time(way1.getString("end_time"));
						way.setPlans_image(way1.getString("plans_image"));
						way.setPlans_name(way1.getString("plans_name"));
						way.setPlans_price(way1.getInt("plans_price"));
						way.setPlans_salenum(way1.getInt("plans_salenum"));
						way.setPlans_storage(way1.getInt("plans_storage"));
						way.setPlans_type(way1.getInt("plans_type"));
						way.setStart_time(way1.getString("start_time"));
						if (way.getPlans_type() == 9) {
							ways9.add(way);
						}else {
							ways16.add(way);
						}
						System.out.println(way.toString());
					}
					//ways = gson.fromJson(plans_list, new TypeToken<List<Way>>()
					//{
					//}.getType());
//					for (Way way : ways)
//					{
//						System.out.println(way.toString());
//					}
					//List<Way> ways = jsonArray.

				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (click16) {
					imageAdapter.addMoreData(ways16);
				}else {
					imageAdapter.addMoreData(ways9);
				}
				imageAdapter.notifyDataSetChanged();
			}else if (msg.what == Constant.HANDLE_LOAD_FAIL) {
				refreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
			}else if (msg.what == Constant.HANDLE_REFRESH_FAIL) {
				refreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
			}else {
				AndroidTools.showToastLong(getActivity(), "请求失败");
			}
		};
	};
	private List<Way> ways9;
	private List<Way> ways16;
	private PullableListView pullablelistview;
	private PullToRefreshLayout refreshLayout;
	private Gson gson = new Gson();
	
	@Override
	public void setContentView(LayoutInflater inflater, ViewGroup container)
	{ 
		rootView = inflater.inflate(R.layout.fragement_main, null);

		//refresh =  (ListView) rootView.findViewById(R.id.refresh);
		//refresh.setAdapter(new MainListAdapter(this.getActivity(),(ViewPager)getActivity().findViewById(R.id.vp_main)));
		ViewFlow viewFlow = (ViewFlow) rootView.findViewById(R.id.viewflow);
		viewFlow.setAdapter(new ViewFlowAdapter(this.getActivity()));
		viewFlow.setViewPager((ViewPager)getActivity().findViewById(R.id.vp_main));
		viewFlow.setmSideBuffer(3); // 实际图片张数， 我的ImageAdapter实际图片张数为3
		CircleFlowIndicator indic = (CircleFlowIndicator) rootView.findViewById(R.id.viewflowindic);
		viewFlow.setFlowIndicator(indic);
		viewFlow.setSelection(3 * 1000); // 设置初始位置
		viewFlow.startAutoFlowTimer(); // 启动自动播放
		pullablelistview = (PullableListView) rootView.findViewById(R.id.content_view);
		refreshLayout = (PullToRefreshLayout) rootView.findViewById(R.id.list_recommend);
		choose9 = (Button) rootView.findViewById(R.id.choose9);
		choose16 = (Button) rootView.findViewById(R.id.choose14);
		if (click16) {
			choose16.setBackgroundResource(R.drawable.after_choose14);
		}
		if (click9) {
			choose9.setBackgroundResource(R.drawable.after_choose9);
		}
		choose9.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				click9 = true;
				click16 = false;
				choose9.setBackgroundResource(R.drawable.after_choose9);
				choose16.setBackgroundResource(R.drawable.before_14);
				if (ways9 != null) {
					imageAdapter = new ImageAdapter(getActivity(), pullablelistview, null, ways9);
					pullablelistview.setAdapter(imageAdapter);
				}
			}
		});
		choose16.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				click9 = false;
				click16 = true;
				choose9.setBackgroundResource(R.drawable.before9);
				choose16.setBackgroundResource(R.drawable.after_choose14);
				if (ways16 != null) {
					imageAdapter = new ImageAdapter(getActivity(), pullablelistview, null, ways16);
					pullablelistview.setAdapter(imageAdapter);
				}
			}
		});
		refreshLayout.setOnRefreshListener(this);
		Map<String, String> datas = new HashMap<>();
		datas.put("all", "0");
		datas.put("order", "0");
		datas.put("page", "20");
		datas.put("curpage", "0");
	
//		•	order 排序方式 1-降序 0-升序
//		•	page 每页数量
//		•	curpage 当前页码
		HttpUtils httpUtils = new HttpUtils(new HttpCallback()
		{ 
			
			@Override
			public void Success(String success)
			{
				Message message = Message.obtain();
				message.what = Constant.HANDLE_SUCCESS;
				message.obj = success;
				handler.sendMessage(message);
			}
			
			@Override
			public void Failed(String failed)
			{
				handler.sendEmptyMessage(Constant.HANDLE_FAIL);
				
			}
		}, "http://test.nsscn.org/zc/index.php?act=plans&op=plans_list2", "POST",datas);
		httpUtils.excute();
	}

	@Override
	public void initView()
	{
	
	}

	@Override
	public void initListener()
	{

	}

	@Override
	public void initData()
	{

	}

	@Override
	public void onRefresh(PullToRefreshLayout pullToRefreshLayout)
	{
		
		if (isNetworkAvailable(getActivity()))
		{
			Map<String, String> datas = new HashMap<>();
			datas.put("all", "0");
			datas.put("order", "0");
			datas.put("page", pagesize);
			datas.put("curpage", "0");
			if (click16) {
				datas.put("type", "16");
			}else {
				datas.put("type", "9");
			}
//			•	order 排序方式 1-降序 0-升序
//			•	page 每页数量
//			•	curpage 当前页码
			HttpUtils httpUtils = new HttpUtils(new HttpCallback()
			{
				
				@Override
				public void Success(String success)
				{
					Message message = Message.obtain();
					message.what = Constant.HANDLE_REFRESH_SUCCESS;
					message.obj = success;
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
			}, "http://test.nsscn.org/zc/index.php?act=plans&op=plans_list2", "POST",datas);
			httpUtils.excute();
		}else {
			pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
		}
		
	}
	private int count = 0;
	@Override
	public void onLoadMore(PullToRefreshLayout pullToRefreshLayout)
	{
		if (imageAdapter != null && imageAdapter.getCount() < count)
		{
			if (isNetworkAvailable(getActivity()))
			{
				current++;
				Map<String, String> datas = new HashMap<>();
				datas.put("all", "0");
				datas.put("order", "0");
				datas.put("page", pagesize);
				datas.put("curpage", ""+current);
				if (click16) {
					datas.put("type", "16");
				}else {
					datas.put("type", "9");
				}
//				•	order 排序方式 1-降序 0-升序
//				•	page 每页数量
//				•	curpage 当前页码
				HttpUtils httpUtils = new HttpUtils(new HttpCallback()
				{
					
					@Override
					public void Success(String success)
					{
						Message message = Message.obtain();
						message.what = Constant.HANDLE_LOAD_SUCCESS;
						message.obj = success;
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
				}, "http://test.nsscn.org/zc/index.php?act=plans&op=plans_list2", "POST",datas);
				httpUtils.excute();
			}else {
				pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
			}
		}else {
			pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
			AndroidTools.showToastShort(getActivity(), "没有更多数据了");
		}
		
	}

	

}
