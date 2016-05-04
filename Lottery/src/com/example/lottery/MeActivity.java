package com.example.lottery;

import java.util.HashMap;

import org.json.JSONException;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.myviews.ClearEditText;
import com.lottery.common.BaseActivity;
import com.lottery.constant.Constant;
import com.lottery.utils.AndroidTools;
import com.lottery.utils.HttpUtils;

public class MeActivity extends BaseActivity {

	private ClearEditText clearEditTexts[] = new ClearEditText[8];
	private TextView editable;
	private Button change;

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_me);
	}

	@Override
	public void initView()
	{
		HashMap<String, String> data = new HashMap<>();
		data.put("key", prefUtils.getString("key"));
		HttpUtils httpUtils = new HttpUtils(httpCallback, "http://test.nsscn.org/zc/index.php?act=login&op=get_perfect"
				, "POST", data);
		httpUtils.excute();
		handler = new Handler(){
			private String age;
			private String sex;
			private String province;
			private String  city;
			private String professional;//职业
			private String company_school;
			@Override
			public void handleMessage(Message msg) {
				System.out.println(msg.obj);
				if (msg.what == Constant.HANDLE_SUCCESS) {
					//"user_id":"21","gender":"man","age":"18","province":"辽宁"
						//,"city":"大连","professional":"学生狗","company_school":"东软"
					try {
						for (ClearEditText iterable_element : clearEditTexts) {
							iterable_element.setEnabled(true);
						}
						age = (String) datas.get("age");
						sex = (String) datas.get("gender");
						province = (String) datas.get("province");
						city = (String) datas.get("city");
						professional = (String) datas.get("professional");
						company_school = datas.getString("company_school");
						clearEditTexts[0].setText(prefUtils.getString("username"));
						clearEditTexts[1].setText("");
						clearEditTexts[2].setText(sex);
						clearEditTexts[3].setText(age);
						clearEditTexts[4].setText(province);
						clearEditTexts[5].setText(city);
						clearEditTexts[6].setText(professional);
						clearEditTexts[7].setText(company_school);
						for (ClearEditText iterable_element : clearEditTexts) {
							iterable_element.setEnabled(false);
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		clearEditTexts[0] = (ClearEditText) findViewById(R.id.username);
		clearEditTexts[1] = (ClearEditText) findViewById(R.id.email);
		clearEditTexts[2] = (ClearEditText) findViewById(R.id.sex);
		clearEditTexts[3] = (ClearEditText) findViewById(R.id.age);
		clearEditTexts[4] = (ClearEditText) findViewById(R.id.province);
		clearEditTexts[5] = (ClearEditText) findViewById(R.id.city);
		clearEditTexts[6] = (ClearEditText) findViewById(R.id.job);
		clearEditTexts[7] = (ClearEditText) findViewById(R.id.company);
		change = (Button) findViewById(R.id.change);
		editable = (TextView) findViewById(R.id.editable);
		editable.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				for (ClearEditText clearedittext : clearEditTexts) {
					clearedittext.setEnabled(true);
				}
			}
		});
		change.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				HashMap<String, String> data = new HashMap<>();
			//	post:key,,gender,age,province,city,professional,company/school
				
				data.put("key", prefUtils.getString("key"));
				data.put("gender", clearEditTexts[2].getText().toString()==null?""
						:clearEditTexts[2].getText().toString());
				data.put("province",clearEditTexts[4].getText().toString()==null?""
						:clearEditTexts[4].getText().toString());
				data.put("city", clearEditTexts[5].getText().toString()==null?""
						:clearEditTexts[5].getText().toString());
				data.put("professional", clearEditTexts[6].getText().toString()==null?""
						:clearEditTexts[6].getText().toString());
				data.put("company/school", clearEditTexts[7].getText().toString()==null?""
						:clearEditTexts[7].getText().toString());
				HttpUtils httpUtils = new HttpUtils(httpCallback, 
						"http://test.nsscn.org/zc/index.php?act=login&op=perfect", "POST", data);
				httpUtils.excute();
				handler = new Handler(){
					@Override
					public void handleMessage(Message msg) {
						System.out.println(msg.obj);
						String ok = "";
						if (null == datas) {
							try {
								ok = job.getString("datas");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if (msg.what == Constant.HANDLE_SUCCESS) {
							if (ok.equals("ok")) {
								AndroidTools.showToastLong(MeActivity.this, "修改成功");
							}else {
								AndroidTools.showToastLong(MeActivity.this, "修改失败");
							}
						}else {
							System.out.println("FAIL"+msg.obj);
							AndroidTools.showToastLong(MeActivity.this, "修改失败");
						}
					}
				
				};
			}
		});
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

}
