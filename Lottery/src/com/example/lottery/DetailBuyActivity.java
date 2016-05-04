package com.example.lottery;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lottery.common.BaseActivity;

public class DetailBuyActivity extends BaseActivity
{
	private TextView buyway,getMoney,all,every,jindu,spend;//方案，奖金，总酬数，每酬，进度，总花费
	private EditText fenshu;//份数
	
	private Intent intent;
	private String way;
	private Button buy;
	
	@Override
	public void setContentView()
	{
		setContentView(R.layout.activity_detail_buy);
	}

	@Override
	public void initView()
	{
//		intent.putExtra("way", getIntent().getStringExtra("way"));//方案几
//		intent.putExtra("all", getIntent().getIntExtra("all", 0));//总酬数
//		intent.putExtra("price", getIntent().getIntExtra("price", 0));//每酬多少钱
//		intent.putExtra("storage", getIntent().getIntExtra("storage", 0));//已经卖了多少酬
		buyway = (TextView) findViewById(R.id.buyway);
		getMoney = (TextView) findViewById(R.id.getMoney);
		all = (TextView) findViewById(R.id.all);
		every = (TextView) findViewById(R.id.every);
		jindu = (TextView) findViewById(R.id.jindu);
		spend = (TextView) findViewById(R.id.spend);
		fenshu = (EditText) findViewById(R.id.fenshu);
		buy = (Button) findViewById(R.id.buy);
		intent = getIntent();
		way = intent.getStringExtra("way");
		all.setText(""+intent.getIntExtra("all", 0));//总酬数
		every.setText(""+intent.getIntExtra("price", 0));//每酬多少钱
		spend.setText("0");
	}

	@Override
	public void initListener()
	{
			buy.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					if (spend.getText() != null && !"".equals(spend.getText()))
					{
						Intent intent = new Intent(DetailBuyActivity.this,ChoBuyWayActivity.class);
						intent.putExtra("id",
								getIntent().getStringExtra("id"));
						intent.putExtra("money", spend.getText().toString());
						intent.putExtra("fenshu", fenshu.getText().toString());
						intent.putExtra("way", way);
						startActivity(intent);
					}
				}
			});
			fenshu.addTextChangedListener(new TextWatcher()
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
					if (s.toString() != null && !s.toString().equals("")&& isNumber(s.toString()))
					{
						spend.setText(Integer.valueOf(s.toString())*intent.getIntExtra("price", 0)+"");
					}else {
						spend.setText("0");
					}
				}
			});
	}
	public boolean isNumber(String str) 
    { 
        java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("[0-9]*"); 
        java.util.regex.Matcher match=pattern.matcher(str); 
        if(match.matches()==false) 
        { 
             return false; 
        } 
        else 
        { 
             return true; 
        } 
    }

	@Override
	public void initData()
	{
		all.setText(getIntent().getIntExtra("all",0)+"");
		every.setText(intent.getIntExtra("price", 0)+"");
		jindu.setText(intent.getIntExtra("storage",1)/intent.getIntExtra("all",0)+"%");
		buyway.setText(way);
		
	}

}
