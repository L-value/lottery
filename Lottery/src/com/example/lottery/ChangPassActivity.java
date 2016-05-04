package com.example.lottery;

import java.util.HashMap;

import org.json.JSONException;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.myviews.ClearEditText;
import com.lottery.common.BaseActivity;
import com.lottery.constant.Constant;
import com.lottery.utils.AndroidTools;
import com.lottery.utils.HttpUtils;

public class ChangPassActivity extends BaseActivity implements OnClickListener{
	
	private Button change;
	private ClearEditText orpass,chapass,repass;
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_changepass);
	}

	@Override
	public void initView() {
		orpass = (ClearEditText) findViewById(R.id.orpass);
		chapass = (ClearEditText) findViewById(R.id.chapass);
		repass = (ClearEditText) findViewById(R.id.repass);
		change = (Button) findViewById(R.id.change);
		change.setOnClickListener(this);
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
	@Override
	public void onClick(View v) {
//		key<input type="text" name="key" />
//		老密码<input type="text" name="password" />
//		新密码<input type="text" name="new_password" />
		System.out.println("orpass"+orpass.getText().toString().equals(""));
		if (orpass.getText() != null && chapass.getText() != null && repass.getText() != null
				&& !orpass.getText().toString().equals("") && !chapass.getText().toString().equals("") && !repass.getText().toString().equals("")) {
			if (chapass.getText().toString().equals(repass.getText().toString())) {
				System.out.println("???????????????");
				HashMap<String, String> datas = new HashMap<>();
				datas.put("key", prefUtils.getString("key"));
				datas.put("password", orpass.getText().toString());
				datas.put("new_password", repass.getText().toString());
				httpUtils = new HttpUtils(httpCallback, "http://test.nsscn.org/zc/index.php?act=login&op=change_password3", "POST", datas);
				httpUtils.excute();
				handler = new Handler(){
					@Override
					public void handleMessage(Message msg) {
						System.out.println(ChangPassActivity.this.datas);
						String ok = "";
						if (null == ChangPassActivity.this.datas) {
							try {
								ok = job.getString("datas");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (msg.what == Constant.HANDLE_SUCCESS) {
							if (ok.equals("ok")) {
								AndroidTools.showToastLong(ChangPassActivity.this, "修改成功");
							}else {
								AndroidTools.showToastLong(ChangPassActivity.this, "修改失败");
							}
						}else {
							System.out.println("FAIL"+msg.obj);
							AndroidTools.showToastLong(ChangPassActivity.this, "修改失败");
						}
					}
				};
			}else {
				AndroidTools.showToastShort(this, "两次填写的密码不一至");
			}
		}else {
			AndroidTools.showToastShort(this, "请确认填写完整");
		}
		
	}

}
