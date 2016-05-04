package com.example.lottery;

import java.util.HashMap;
import java.util.Map.Entry;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.myviews.ClearEditText;
import com.lottery.common.BaseActivity;
import com.lottery.constant.Constant;
import com.lottery.utils.AndroidTools;
import com.lottery.utils.HttpUtils;

public class BankMessageActivity extends BaseActivity {

	private ClearEditText username,telnum;
	private Spinner cardtype;
	private Button change;
	//(0:���;1:���ÿ�)
	private HashMap<String, String> daHashMap = new HashMap<>();
	private String text ="";
	
	
	@Override
	public void setContentView() {
		daHashMap.put("0","���");
		daHashMap.put("1", "���ÿ�");
		userId = Constant.USERID;
		setContentView(R.layout.activity_bankmessage);
		cardtype = (Spinner) findViewById(R.id.cardtype);
		cardtype.setAdapter(new SpinnerAdapter(this));
		cardtype.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
					text = "���";
				}else {
					text = "��ǿ�";
				}
				
			}
		});
		username = (ClearEditText) findViewById(R.id.username);
		telnum = (ClearEditText) findViewById(R.id.telnum);
		change = (Button) findViewById(R.id.change);
		change.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//��Ӹ������п���Ϣ��http://test.nsscn.org/zc/index.php?act=bank&op=get_info_list
				//������key,type(0:���;1:���ÿ�),name(��ʵ����),card_number�����ţ�
				//���أ���ϲ���ʼ����ͳɹ�����
				if (!text.equals("")
						&& ! username.getText().toString().equals("")
						&& !telnum.getText().toString().equals("")) {
					HashMap<String, String> data  = new HashMap<>();
					data.put("key", prefUtils.getString("key"));
					data.put("type", "0");
					data.put("name", username.getText().toString());
					data.put("card_number", getIntent().getStringExtra("number"));
					if (isNetworkAvailable(BankMessageActivity.this)) {
						AndroidTools.showLoadDialog(BankMessageActivity.this);
						HttpUtils httpUtils = new HttpUtils(httpCallback, "http://test.nsscn.org/zc/index.php?act=bank&op=add_bank_info",
								"POST", data);
						httpUtils.excute();
						handler = new Handler(){
							public void handleMessage(android.os.Message msg) {
								String content = (String)msg.obj;
								if (content.equals("��ϲ���ʼ����ͳɹ�����")) {
									AndroidTools.dismissLoadingDialog();
									startActivity(new Intent(BankMessageActivity.this,AddCheckActivity.class));
								}else {
									AndroidTools.dismissLoadingDialog();
									AndroidTools.showToastShort(BankMessageActivity.this, (String)msg.obj);
								}	
							};
						};
					}else {
						AndroidTools.showToastShort(BankMessageActivity.this, "�������Ӳ��ȶ�");
					}
				}
				//	startActivity(new Intent(BankMessageActivity.this,AddCheckActivity.class));
			}
		});
	}

	@Override
	public void initView() {
		
	}

	@Override
	public void initListener() {

	}

	@Override
	public void initData() {

	}
	private class SpinnerAdapter extends BaseAdapter{
		private Context context;
		public SpinnerAdapter(Context context) {
			this.context = context;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 2;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return daHashMap.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		TextView textView = null;

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (textView == null) {
				textView = new TextView(context);
			}
			if (convertView == null) {
				convertView = textView;
			}
			if (position == 0) {
				textView.setText("���");
			}else {
				textView.setText("�����");
			}
			return convertView;
		} 
		
	}

}
