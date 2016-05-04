package com.example.lottery;

import android.widget.LinearLayout;

import com.lottery.common.BaseActivity;

public class IntruduceActivity extends BaseActivity {
	
	private LinearLayout bg_introduce;
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_introduce);
		bg_introduce = (LinearLayout) findViewById(R.id.bg_introduce);
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

}
