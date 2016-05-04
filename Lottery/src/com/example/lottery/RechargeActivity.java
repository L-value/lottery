package com.example.lottery;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lottery.common.BaseActivity;
import com.lottery.constant.Constant;
import com.lottery.utils.HttpUtils;

public class RechargeActivity extends BaseActivity
{
	
	private  TextView balance;
	private EditText input;
	private LinearLayout pay_yinlian,pay_alipay,pay_weixin;
	private Button pay;
	
	private HttpUtils httpUtils;
	
	@Override
	public void setContentView()
	{
		setContentView(R.layout.activity_recharge);
		balance = (TextView) findViewById(R.id.balance);
		input = (EditText) findViewById(R.id.input);
		pay_yinlian = (LinearLayout) findViewById(R.id.pay_yinlian);
		pay_alipay = (LinearLayout) findViewById(R.id.pay_alipay);
		pay_weixin = (LinearLayout) findViewById(R.id.pay_weixin);
	
	}
	

	@Override
	public void initView()
	{
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
					balance.setText(""+(String)msg.obj);
				}else {
					balance.setText("==");
				}
				//super.handleMessage(msg);
			}
		};
	}

	@Override
	public void initListener()
	{
		
	}

	@Override
	public void initData()
	{
		
	}

}
