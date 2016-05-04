package com.example.lottery;

import java.util.HashMap;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lottery.common.BaseActivity;
import com.lottery.utils.AndroidTools;
import com.lottery.utils.HttpUtils;

public class FindPasswordActivity extends BaseActivity implements OnClickListener {

	private EditText cardtype;
	private Button get,sure;
	private TextView pass;
	
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_findpass);
		cardtype = (EditText) findViewById(R.id.cardtype);
		get = (Button) findViewById(R.id.get);
		get.setOnClickListener(this);
		sure = (Button) findViewById(R.id.sure);
		pass = (TextView) findViewById(R.id.pass);
		sure.setOnClickListener(this);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}
	private HttpUtils httpUtils;
	private HashMap<String, String> data;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sure:
			if (isNetworkAvailable(FindPasswordActivity.this)) {
				data = new HashMap<>();
				data.put("username", prefUtils.getString("username"));
				data.put("email", "");
				httpUtils = new HttpUtils(httpCallback, "http://test.nsscn.org/zc/index.php?act=login&op=change_password4",
						"POST", data);
				httpUtils.excute();
			}else {
				AndroidTools.showToastShort(FindPasswordActivity.this, "网络未连接");
			}
			break;
		case R.id.get:
			if (isNetworkAvailable(FindPasswordActivity.this)) {
				data = new HashMap<>();
				data.put("username", prefUtils.getString("username"));
				if (cardtype.getText().toString() != null && !cardtype.getText().toString().equals("")) {
					data.put("token", cardtype.getText().toString());
					httpUtils = new HttpUtils(httpCallback, "http://test.nsscn.org/zc/index.php?act=login&op=click5",
							"POST", data);
					httpUtils.excute();
				}else {
					AndroidTools.showToastShort(FindPasswordActivity.this, "请填写验证码");
				}
			}else {
				AndroidTools.showToastShort(FindPasswordActivity.this, "网络未连接");
			}
			break;
		default:
			break;
		}
		
	}

}
