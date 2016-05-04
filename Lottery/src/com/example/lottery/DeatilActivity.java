package com.example.lottery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.data.i;

import com.example.bean.Detail;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lottery.adapters.DetailAdapter;
import com.lottery.common.BaseActivity;
import com.lottery.constant.Constant;
import com.lottery.listeners.HttpCallback;
import com.lottery.utils.AndroidTools;
import com.lottery.utils.HttpUtils;
import com.lottery.utils.PrefUtils;

public class DeatilActivity extends BaseActivity
{

	private ImageView back;
	private ListView detial;
	private Button buy;
	private String id;
	private TextView num,kind;
	private Map<String, String> data;
	private List<Detail> details = new ArrayList<>();
	private Gson gson=new Gson();
	@Override
	public void setContentView()
	{
		setContentView(R.layout.item_detail);
		
		back = (ImageView) findViewById(R.id.back);
		detial = (ListView) findViewById(R.id.detail);
		buy = (Button) findViewById(R.id.buy);
		num  = (TextView) findViewById(R.id.num);
		Intent intent = getIntent();
		num.setText("       众酬"+intent.getIntExtra("position", 0)+1+"组");
		kind = (TextView) findViewById(R.id.kind);
		kind.setText("胜负彩-任选"+intent.getIntExtra("plans_type",0));
		id= intent.getStringExtra("id");

	}
  
	@Override
	public void initView()
	{
		buy.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				System.out.println("key" + prefUtils.getString("key")+(prefUtils.getString("key").equals("null")));
				if (!prefUtils.getString("key").equals("null"))
				{
					Map<String, String> datas = new HashMap<>();
					datas.put("plans_id", getIntent().getStringExtra("id"));
					//datas.put("plans_num", );
					datas.put("key", prefUtils.getString("key"));
					//datas.put(KEYGUARD_SERVICE, value)
					HttpUtils httpUtils = new HttpUtils(new HttpCallback()
					{
						
						@Override
						public void Success(String success)
						{
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void Failed(String failed)
						{
							// TODO Auto-generated method stub
							
						}
					}, "http:/test.nsscn.org/zc/index.php?act=order&op=create_order", "POST", datas);
					Intent intent = new Intent(DeatilActivity.this,DetailBuyActivity.class);
					intent.putExtra("way", getIntent().getStringExtra("way"));//方案几
					intent.putExtra("all", getIntent().getIntExtra("all", 0));//总酬数
					intent.putExtra("price", getIntent().getIntExtra("price", 0));//每酬多少钱
					intent.putExtra("storage", getIntent().getIntExtra("storage", 0));//已经卖了多少酬
					intent.putExtra("id", id);
					startActivity(intent);
				}else {
					startActivity(new Intent(DeatilActivity.this,LoginActivity.class));
				}
			}
		});
	}

	@Override
	public void initListener()
	{
		data = new HashMap<>();
		data.put("plans_id", id);
		if (isNetworkAvailable(this))
		{
			HttpUtils httpUtils = new HttpUtils(httpCallback, "http://test.nsscn.org/zc/index.php?act=plans&op=plans_detail", "POST", data);
			httpUtils.excute();
		}
		
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg)
			{
				if (msg.what == Constant.HANDLE_SUCCESS)
				{
						if (code.equals("200"))
						{
	//						AndroidTools.showToastShort(DeatilActivity.this, datas.toString());
//							JSONObject jsonObject;
//							try
//							{
//								 jsonObject = new JSONObject(datas.getString("data"));
//							} catch (JSONException e)
//							{
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
							System.out.println(datas.toString());
							try
							{
								 details = gson.fromJson(datas.getString("data"),  new TypeToken<ArrayList<Detail>>()
								{
								}.getType());
							} catch (JsonSyntaxException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JSONException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							detial.setAdapter(new DetailAdapter(DeatilActivity.this, details));
							setListViewHeightBasedOnChildren(detial);
						}else {
							AndroidTools.showToastShort(DeatilActivity.this, error);
						}
					
				}else {
					AndroidTools.showToastShort(DeatilActivity.this, msg.obj.toString());
				}
			}
		};
		
	}

	@Override
	public void initData()
	{
		// TODO Auto-generated method stub
		
	}
	 public void setListViewHeightBasedOnChildren(ListView listView){

		 ListAdapter listAdapter = listView.getAdapter();
		 if (listAdapter == null)
		{
			return ;
		}
		 int totalHeight = 0;
		 for (int i = 0, len = listAdapter.getCount() ; i<len ; i++)
		{

			 View listItemView = listAdapter.getView(i, null, listView);
			 listItemView.measure(0, 0);
			 totalHeight += listItemView.getMeasuredHeight();
		}

		 ViewGroup.LayoutParams params = listView.getLayoutParams();
		 params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)); 
		 listView.setLayoutParams(params);
	}

}
