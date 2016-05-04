package com.example.lottery;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.bean.BankInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.lottery.adapters.BankAdapter;
import com.lottery.common.BaseActivity;
import com.lottery.constant.Constant;
import com.lottery.utils.HttpUtils;

public class BankActivity extends BaseActivity {

	private ImageView add;
	private PopupWindow popupWindow;
	private LinearLayout title;

	@Override
	public void setContentView() {
		userId = Constant.USERID;
		setContentView(R.layout.activity_bank);
	}
	private Gson gson;
	private ListView bank;
	
	@Override
	public void initView() {
		userId = Constant.USERID;
		add = (ImageView) findViewById(R.id.add);
		bank = (ListView) findViewById(R.id.bank);
		title = (LinearLayout) findViewById(R.id.title);
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		final int width = display.getWidth();
		final int height = display.getHeight();
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(BankActivity.this,AddBankActivity.class));
			}
		});
		HashMap<String, String> data = new HashMap<>();
		data.put("key", prefUtils.getString("key"));
		HttpUtils httpUtils = 
				new HttpUtils(httpCallback, "http://test.nsscn.org/zc/index.php?act=bank&op=get_info_list", "POST", data);
		httpUtils.excute();
		gson = new Gson();
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				System.out.println(msg.obj);
				String msgData = (String) msg.obj;
				if (msg.what == Constant.HANDLE_SUCCESS) {
					String datass = null;
					try {
						datass = job.getString("datas");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					infos = gson.fromJson(datass, new TypeToken<List<BankInfo>>() {
					}.getType());
					BankAdapter bankAdapter = new BankAdapter(infos, BankActivity.this);
					bank.setAdapter(bankAdapter);
					bank.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							if (popupWindow == null) {
								View rootView = LayoutInflater.from(BankActivity.this).inflate(R.layout.pop_view, null);
								popupWindow = new PopupWindow(rootView, width, height/5);
								popupWindow.showAsDropDown(title, 0, height - height/5 - title.getMeasuredHeight());
								popupWindow.setTouchable(false);
								popupWindow.setOutsideTouchable(false);
								popupWindow.setBackgroundDrawable(new BitmapDrawable());
								Button delete = (Button) rootView.findViewById(R.id.delete);
								Button cancle = (Button) rootView.findViewById(R.id.cancal);
								delete.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										System.out.println("delete");
									}
								});
								cancle.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										popupWindow.dismiss();
										popupWindow = null;
									}
								});			
							}				
						}
					});
					System.out.println(infos.size());
				}
				
			}
		};
	}
	List<BankInfo> infos;
	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}
	
}
