package com.example.lottery;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.myviews.ClearEditText;
import com.lottery.common.BaseActivity;
import com.lottery.constant.Constant;
import com.lottery.listeners.HttpCallback;
import com.lottery.utils.AndroidTools;
import com.lottery.utils.HttpUtils;
import com.lottery.utils.PrefUtils;

public class LoginActivity extends BaseActivity
{
	private Button loginButton;
	private Button registerButton;
	private ClearEditText username;
	private ClearEditText password;
	
	private CheckBox rempass;
	private TextView forget;
	private boolean pay = false;
	
	private HttpUtils httpUtils;
	private HashMap<String, String> data;
	
	
	
	private boolean autoLogin;


	@Override
	public void setContentView()
	{
		setContentView(R.layout.activity_login);		
	}

	@Override
	public void initView()
	{
		loginButton = (Button) findViewById(R.id.btlogin);
		registerButton = (Button) findViewById(R.id.btregister);
		username = (ClearEditText) findViewById(R.id.username);
		password = (ClearEditText) findViewById(R.id.password);
		rempass = (CheckBox) findViewById(R.id.rempass);
		forget = (TextView) findViewById(R.id.forget);
		pay = getIntent().getBooleanExtra("pay", false);
		autoLogin = prefUtils.getBoolean("autologin");
		if (autoLogin)
		{
			data = new HashMap<>();
			data.put("username", username.getText().toString());
			data.put("password", password.getText().toString());
			data.put("client", "wap");
			
			handler = new Handler(){
				public void handleMessage(android.os.Message msg) {
					AndroidTools.dismissLoadingDialog();
					Log.i("info", (String)msg.obj);
					String obj = (String)msg.obj;
					if (msg.what == Constant.HANDLE_SUCCESS)
					{
						try
						{
							if (code.equals("200"))
							{
								key = datas.getString("key");
								prefUtils.putString("key", key);
								prefUtils.putString("username", username.getText().toString());
								prefUtils.putString("password", password.getText().toString());
								prefUtils.putString("money", datas.getString("money"));
								prefUtils.putBoolean(Constant.LOGIN_KEY, true);
								prefUtils.putBoolean("autologin", autoLogin);
								prefUtils.commit();
								if (key == null || key.equals("")) {
									AndroidTools.showToastShort(LoginActivity.this, "用户名或密码错误");
								}
								if (pay)
								{
									startActivity(new Intent(LoginActivity.this,RechargeActivity.class));
									finish();
								}else {
									startActivity(new Intent(LoginActivity.this,MainActivity.class).
											putExtra("fromLogin", true).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
								}
							}else {
								AndroidTools.showToastShort(LoginActivity.this, error);
							}
						} catch (JSONException e)
						{
							e.printStackTrace();
						}
					}else {
						AndroidTools.showToastShort(LoginActivity.this, obj);
					}
				};
			};
			if (isNetworkAvailable(LoginActivity.this))
			{
				AndroidTools.showLoadDialog(LoginActivity.this);
				httpUtils = new HttpUtils(httpCallback, Constant.PATH+"index.php?act=login", "POST", data);
				httpUtils.excute();
			}else {
				AndroidTools.showToastShort(LoginActivity.this, "网络连接失败，请稍后再试");
			}
		}
	}

	@Override
	public void initListener()
	{
		rempass.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				autoLogin = isChecked;
				
			}
		});
		registerButton.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
			}
		});
		
		loginButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				data = new HashMap<>();
				data.put("username", username.getText().toString());
				data.put("password", password.getText().toString());
				data.put("client", "wap");
				
				handler = new Handler(){
					public void handleMessage(android.os.Message msg) {
						AndroidTools.dismissLoadingDialog();
						Log.i("info", (String)msg.obj);
						String obj = (String)msg.obj;
						if (msg.what == Constant.HANDLE_SUCCESS)
						{
							try
							{
								if (code.equals("200"))
								{
									key = datas.getString("key");
									prefUtils.putString("key", key);
									prefUtils.putString("username", username.getText().toString());
									prefUtils.putString("password", password.getText().toString());
									prefUtils.putString("money", datas.getString("money"));
									prefUtils.putBoolean(Constant.LOGIN_KEY, true);
									prefUtils.putBoolean("autologin", autoLogin);
									prefUtils.commit();
									if (pay)
									{
										startActivity(new Intent(LoginActivity.this,RechargeActivity.class));
										finish();
									}else {
										startActivity(new Intent(LoginActivity.this,MainActivity.class).
												putExtra("fromLogin", true).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
									}
								}else {
									AndroidTools.showToastShort(LoginActivity.this, error);
								}
							} catch (JSONException e)
							{
								e.printStackTrace();
							}
						}else {
							AndroidTools.showToastShort(LoginActivity.this, obj);
						}
					};
				};
				if (isNetworkAvailable(LoginActivity.this))
				{
					AndroidTools.showLoadDialog(LoginActivity.this);
					httpUtils = new HttpUtils(httpCallback, Constant.PATH+"index.php?act=login", "POST", data);
					httpUtils.excute();
				}else {
					AndroidTools.showToastShort(LoginActivity.this, "网络连接失败，请稍后再试");
				}
					
				
			}
		});
	}

	@Override
	public void initData()
	{
		
	}

	
}
