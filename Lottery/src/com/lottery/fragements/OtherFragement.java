package com.lottery.fragements;

import com.example.lottery.AboutUsActivity;
import com.example.lottery.AboutVersionActivity;
import com.example.lottery.HelpActivity;
import com.example.lottery.IntruduceActivity;
import com.example.lottery.R;
import com.example.lottery.WinHistoryActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class OtherFragement extends BaseFragement
{

	private Button call;
	private Button introduce;
	private Button lijie;
	private Button about;
	private Button us;
	private CheckBox cb;
	private Button help;
	@Override
	public void setContentView(LayoutInflater inflater,ViewGroup container)
	{
		rootView = inflater.inflate(R.layout.fragement_more, null);
	}

	@Override
	public void initView()
	{
		call = (Button) rootView.findViewById(R.id.call);//电话咨询
		call.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent phoneIntent = new Intent("android.intent.action.CALL",
					       Uri.parse("tel:" + "13898494560"));
					     //启动
					     startActivity(phoneIntent); 
				
			}
		});
		help = (Button) rootView.findViewById(R.id.help);
		help.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(),HelpActivity.class));
			}
		});
		introduce = (Button) rootView.findViewById(R.id.introduce);//玩法介绍
		introduce.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(),IntruduceActivity.class));
			}
		});
		lijie = (Button) rootView.findViewById(R.id.lijie);//历届中奖信息
		cb = (CheckBox) rootView.findViewById(R.id.cb);
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
			}
		});
		lijie.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(getActivity(),WinHistoryActivity.class));
			}
		});
		about = (Button) rootView.findViewById(R.id.about);//关于版本
		about.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				startActivity(new Intent(getActivity(),AboutVersionActivity.class));
			}
		});
		us = (Button) rootView.findViewById(R.id.us);//关于我们
		us.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(getActivity(),AboutUsActivity.class));
				
			}
		});
	}

	@Override
	public void initListener()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initData()
	{
		// TODO Auto-generated method stub
		
	}

}
