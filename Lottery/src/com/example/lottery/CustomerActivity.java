package com.example.lottery;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.lottery.adapters.CustomerPagerAdapter;
import com.lottery.common.BaseActivity;
import com.lottery.fragements.FeedFragement;
import com.lottery.fragements.HelpFragement;

public class CustomerActivity extends BaseActivity implements OnClickListener
{

	private Button usehelp;
	private Button feed;
	private ViewPager vp;
	private CustomerPagerAdapter customerPagerAdapter;
	private FragmentManager fm;
	private ArrayList<Fragment> fragments;
	@Override
	public void setContentView()
	{
		setContentView(R.layout.activity_customer);
		
	}

	@Override
	public void initView()
	{
		usehelp = (Button) findViewById(R.id.usehelp);
		feed = (Button) findViewById(R.id.feed);
		usehelp.setOnClickListener(this);
		feed.setOnClickListener(this);
		vp = (ViewPager) findViewById(R.id.vp);
		fm = getSupportFragmentManager();
		fragments = new ArrayList<>();
		fragments.add(new HelpFragement());
		fragments.add(new FeedFragement());
		customerPagerAdapter = new CustomerPagerAdapter(fm,this,fragments);
		vp.setAdapter(customerPagerAdapter);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.usehelp:
			vp.setCurrentItem(0);
			break;
		case R.id.feed:
			vp.setCurrentItem(1);
			break;
		default:
			break;
		}
	}

}
