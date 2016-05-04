package com.example.lottery;

import java.lang.reflect.Method;

import android.view.View;
import android.widget.ImageView;

import com.lottery.common.BaseActivity;

public class AboutVersionActivity extends BaseActivity{

	private ImageView imageview;
	
	@Override
	public void setContentView() {
		setContentView(R.layout.activity_version);
	}
	@Override
	public void initView() {
		imageview = (ImageView) findViewById(R.id.version);
		imageview.setBackgroundResource(R.drawable.version);
	}

	@Override
	public void initListener() {
		
	}

	@Override
	public void initData() {
		
	}

}
