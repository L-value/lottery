package com.example.lottery;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.myviews.ClearEditText;
import com.lottery.common.BaseActivity;
import com.lottery.constant.Constant;
import com.lottery.utils.AndroidTools;
import com.lottery.utils.HttpUtils;

public class AddBankActivity extends BaseActivity {
	
	private Button next;
	private ClearEditText bankcard;
	@Override
	public void setContentView() {
		userId = Constant.USERID;
		setContentView(R.layout.activity_addbank);
		next = (Button) findViewById(R.id.next);
		bankcard = (ClearEditText) findViewById(R.id.bankcard);
		next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!bankcard.getText().toString().equals("") && bankcard.getText().toString() != null) {
					if (mathBankNumber(bankcard.getText().toString())) {
						startActivity(new Intent(AddBankActivity.this,BankMessageActivity.class).putExtra("number", bankcard.getText().toString()));
					}else {
						AndroidTools.showToastShort(AddBankActivity.this, "银行卡格式不正确");
					}
				}else {
					AndroidTools.showToastShort(AddBankActivity.this, "请填写银行卡号");
				}
			}
		});
	}

	@Override
	public void initView() {
		
		
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}
	public static boolean mathBankNumber(String number){
		Pattern pattern = Pattern.compile("/^(\\d{16}|\\d{19})$/");
		Matcher matcher = pattern.matcher(number);
		return matcher.matches();
		///^(\d{16}|\d{19})$/
	}

}
