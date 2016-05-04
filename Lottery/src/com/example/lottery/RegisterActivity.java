package com.example.lottery;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.myviews.ClearEditText;
import com.google.gson.jpush.annotations.c;
import com.lottery.common.BaseActivity;
import com.lottery.constant.Constant;
import com.lottery.listeners.HttpCallback;
import com.lottery.utils.AndroidTools;
import com.lottery.utils.HttpUtils;

public class RegisterActivity extends BaseActivity
{
	private ClearEditText email;
	private ClearEditText username;
	private ClearEditText password;
	private ClearEditText rePassword;
	private Button register_btn;
	
	private boolean isusername;
	private boolean isemail;
	private boolean pass;
	private boolean repass;
	
	private CheckBox policy;
	private TextView look;
	
	@Override
	public void setContentView()
	{
		setContentView(R.layout.activity_register);		
	}
	public boolean isEmail(String email) {		
	        boolean isExist = false;
	        Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");
	        Matcher m = p.matcher(email);
	        boolean b = m.matches();
	        if(b) {
	            isExist=true;
	        } else {
	        }
	        return isExist;
	}

	@Override
	public void initView()
	{
		email = (ClearEditText) findViewById(R.id.re_email);
		username = (ClearEditText) findViewById(R.id.re_username);
		password = (ClearEditText) findViewById(R.id.re_pass);
		rePassword = (ClearEditText) findViewById(R.id.re_passs);
		register_btn = (Button) findViewById(R.id.register_btn);
	}

	@Override
	public void initListener()
	{
		email.addTextChangedListener(new TextWatcher()
		{
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after)
			{
				
			}
			
			@Override
			public void afterTextChanged(Editable s)
			{
				if (null != s && !"".equals(s.toString()) && isEmail(s.toString())) 
				{
					isemail = true;
				}else {
					isemail = false;
				}
			}
		});
		email.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				if (!hasFocus)
				{
					if (!isemail && !"".equals(email.getText().toString()) && !hasFocus)
					{
						AndroidTools.showToastShort(RegisterActivity.this, "邮箱格式不正确");
					}
				}
				
			}
		});
		username.addTextChangedListener(new TextWatcher()
		{
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after)
			{
				
			}
			
			@Override
			public void afterTextChanged(Editable s)
			{
				if (null != s && !"".equals(s.toString()) && s.toString().length() > 3) 
				{
					isusername = true;
				}else {
					isusername = false;
				}
			}
		});
		username.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				if (!isusername && !"".equals(username.getText().toString()) && !hasFocus)
				{
					AndroidTools.showToastShort(RegisterActivity.this, "用户名须大于三位");	
				}
				
			}
		});
		password.addTextChangedListener(new TextWatcher()
		{
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after)
			{
				
			}
			
			@Override
			public void afterTextChanged(Editable s)
			{
				if (null != s && !"".equals(s.toString())) 
				{
					if (s.toString().length() > 3)
					{
						if (( 'z' >= s.toString().charAt(0) && s.toString().charAt(0) >= 'a')||
								( 'Z' >= s.toString().charAt(0) && s.toString().charAt(0) >= 'A')
								&& !password.hasFocus())
						{
							pass = true;
						}else {
							pass = false;
						}
					}else {
						pass = false;
					}
				}else {
					pass = false;
				}
			}
		});
		password.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				Editable  s = password.getText();
				if (!hasFocus)
				{
					if (null != s && !"".equals(s.toString())) 
					{
						if (s.toString().length() > 3)
						{
							if (( 'z' >= s.toString().charAt(0) && s.toString().charAt(0) >= 'a')||
									( 'Z' >= s.toString().charAt(0) && s.toString().charAt(0) >= 'A'))
							{
								System.out.println(s.toString().charAt(0));
								pass = true;
							}else {
								pass = false;
								AndroidTools.showToastShort(RegisterActivity.this, "密码首位需是字母");
							}
						}else {
							pass = false;
							AndroidTools.showToastShort(RegisterActivity.this, "密码须大于三位");
						}
					}else {
						pass = false;
					}
				}
				
			}
		});
		rePassword.addTextChangedListener(new TextWatcher()
		{
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after)
			{
				
			}
			
			@Override
			public void afterTextChanged(Editable s)
			{
				if (null != s)
				{
					if (null != password.getText().toString() && s.toString().equals(password.getText().toString()))
					{
						repass = true;
					}else {
						repass = false;
					}
				}
			}
		});
		rePassword.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				if (!repass && !hasFocus && !"".equals(rePassword.getText().toString()))
				{
					AndroidTools.showToastShort(RegisterActivity.this, "两次填写的密码不一致");
				}
				
			}
		});
		register_btn.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				rePassword.clearFocus();
				if (isemail && isusername && pass && repass)
				{
					AndroidTools.showLoadDialog(RegisterActivity.this);
					handler = new Handler(){
						public void handleMessage(android.os.Message msg) {
							AndroidTools.dismissLoadingDialog();
							try
							{
								job = new JSONObject((String)msg.obj);
								error = job.getString("error");
								if ("200".equals(code))
								{
									AndroidTools.showToastShort(RegisterActivity.this, "注册成功");									
								}else {
									AndroidTools.showToastShort(RegisterActivity.this, error);		
								}
							} catch (JSONException e)
							{
								e.printStackTrace();
							}
							
							
						};
					};
					datas.put("username",username.getText().toString());
					datas.put("password",password.getText().toString());
					datas.put("password_confirm", rePassword.getText().toString());
					datas.put("email", email.getText().toString());
					datas.put("client", "wap");	
					datas.put("member_phone_number", "13354202222");
					httpUtils = new HttpUtils(httpCallback, Constant.PATH+"index.php?act=login&op=register", "POST", datas);
					if (isNetworkAvailable(RegisterActivity.this))
					{
						httpUtils.excute();
					}else {
						AndroidTools.showToastLong(RegisterActivity.this, "网络连接失败，请稍后再试");
					}
				}else {
					AndroidTools.showToastLong(RegisterActivity.this, "请确定注册信息填写正确");
				}
			}
		});
	}
	/**
	 * 注册提交信息
	 */
	
	    
	private HashMap<String, String> datas = new HashMap<>();
	private HttpUtils httpUtils;
	@Override
	public void initData()
	{
		
	}

}
