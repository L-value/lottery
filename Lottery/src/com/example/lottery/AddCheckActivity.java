package com.example.lottery;

import java.util.HashMap;

import org.json.JSONException;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.myviews.ClearEditText;
import com.lottery.common.BaseActivity;
import com.lottery.constant.Constant;
import com.lottery.utils.AndroidTools;
import com.lottery.utils.HttpUtils;

public class AddCheckActivity extends BaseActivity {

	private ClearEditText cardtype;
	private Button next;
	@Override
	public void setContentView() {
		userId = Constant.USERID;
		setContentView(R.layout.activity_addcheck);
	}

	@Override
	public void initView() {
		cardtype = (ClearEditText) findViewById(R.id.cardtype);
		next = (Button) findViewById(R.id.next);
	}

	@Override
	public void initListener() {
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			//	http://test.nsscn.org/zc/index.php?act=bank&op=click
				//	参数:key,token(验证码)
				if (!cardtype.getText().toString().equals("")) {
					HashMap<String, String> data  = new HashMap<>();
					data.put("key", prefUtils.getString("key"));
					data.put("token", cardtype.getText().toString());
					if (isNetworkAvailable(AddCheckActivity.this)) {
						AndroidTools.showLoadDialog(AddCheckActivity.this);
						HttpUtils httpUtils = new HttpUtils(httpCallback, "http://test.nsscn.org/zc/index.php?act=bank&op=click",
								"POST", data);
						httpUtils.excute();
						handler = new Handler(){
							public void handleMessage(android.os.Message msg) {
								String content = (String)msg.obj;
								
								try {
									if (job.getString("datas").equals("ok")) {
										AndroidTools.dismissLoadingDialog();
										startActivity(new Intent(AddCheckActivity.this,BankActivity.class));
										for (BaseActivity iterable_element : baseApplication.getUserActivity()) {
											iterable_element.finish();
										}
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	
							}
						};
				}else {
					AndroidTools.showToastShort(AddCheckActivity.this, "请输入验证码");
				}
			}
			}});
		
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

}
