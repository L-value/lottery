package com.example.lottery;

import java.util.HashMap;
import java.util.Map;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import com.example.myviews.ClearEditText;
import com.lottery.common.BaseActivity;
import com.lottery.constant.Constant;
import com.lottery.utils.HttpUtils;

public class GetMoneyActivity extends BaseActivity {
	
	private TextView yue;
	private ClearEditText tixian;
	private Button sure;
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_getmoney);
		yue = (TextView) findViewById(R.id.yue);
		tixian = (ClearEditText) findViewById(R.id.tixian);
		sure = (Button) findViewById(R.id.sure);
		
	}
	private HttpUtils httpUtils;
	
	@Override
	public void initView() {
		Map<String, String> data = new HashMap<>();
		data.put("key", prefUtils.getString("key"));
		httpUtils = new HttpUtils(httpCallback, "http://test.nsscn.org/zc/index.php?act=message&op=get_money", "POST", data);
		httpUtils.excute();
		System.out.println("====");
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg)
			{
				System.out.println("what"+msg.what);
				if (msg.what == Constant.HANDLE_SUCCESS)
				{
					yue.setText(""+(String)msg.obj);
				}else {
					yue.setText("==");
				}
				//super.handleMessage(msg);
			}
		};
	}

	@Override
	public void initListener() {
		sure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

}
