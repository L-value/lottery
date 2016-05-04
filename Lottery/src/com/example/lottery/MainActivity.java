package com.example.lottery;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.example.myviews.ContralViewPager;
import com.lottery.adapters.HomePagerAdapter;
import com.lottery.common.BaseActivity;
import com.lottery.constant.Constant;
import com.lottery.fragements.FragementFactory;
import com.lottery.fragements.HomeFragement;
import com.lottery.fragements.MeFragement;
import com.lottery.fragements.OtherFragement;
import com.lottery.utils.PrefUtils;

public class MainActivity extends BaseActivity
{

	private ContralViewPager vp;

	private RadioGroup radioGroup;
	private RadioButton radioButton1;
	private RadioButton radioButton2;
	private RadioButton radioButton3;
	private RadioButton radiobutton4;

	private HomeFragement homeFragement;     //��ҳ
	private MeFragement meFragement;		 //�ҵ�
	private OtherFragement otherFragement;   //����
	private ArrayList<Fragment> fragments;

	private PrefUtils prefUtils;
	
	private boolean fromLogin;
	@Override
	public void setContentView()
	{
		setContentView(R.layout.activity_main);
	}

	@Override
	public void initView()
	{
		prefUtils = new PrefUtils(this);
		homeFragement = (HomeFragement) FragementFactory
				.newInstance(Constant.FRAGEMENT_KEY_HOME);
		meFragement = (MeFragement) FragementFactory
				.newInstance(Constant.FRAGEMENT_KEY_ME);
		otherFragement = (OtherFragement) FragementFactory
				.newInstance(Constant.FRAGEMENT_KEY_OTHER);
		fragments = new ArrayList<>();
		fragments.add(homeFragement);
		fragments.add(meFragement);
		fragments.add(otherFragement);

		vp = (ContralViewPager) findViewById(R.id.vp_main);
		radioGroup = (RadioGroup) findViewById(R.id.radiogroup_main);
		radioButton1 = (RadioButton) findViewById(R.id.radio_home);
		radioButton2 = (RadioButton) findViewById(R.id.radio_me);
		radioButton3 = (RadioButton) findViewById(R.id.radio_other);
		radiobutton4 = (RadioButton) findViewById(R.id.radio_recharge);
	//	radioButton1.setTextColor(Color.GREEN);
	}

	@Override
	public void initListener()
	{
		fromLogin = getIntent().getBooleanExtra("fromLogin", false);
		System.out.println(fromLogin);
		vp.setScrollble(false);
		vp.setAdapter(new HomePagerAdapter(getSupportFragmentManager(), this,
				fragments));
		vp.setOnPageChangeListener(new OnPageChangeListener()
		{

			@Override
			public void onPageSelected(int arg0)
			{

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{

			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{

			}
		});

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				switch (checkedId)
				{
				case R.id.radio_home:
					result = 1;
					vp.setCurrentItem(0);
//					radioButton1.setTextColor(Color.GREEN);
//					radioButton2.setTextColor(Color.WHITE);
//					radioButton3.setTextColor(Color.WHITE);
					break;
				case R.id.radio_me:
					if (prefUtils.getBoolean(Constant.LOGIN_KEY))// utilsĬ����false,����õ�����true������Ѿ���½
					{
						vp.setCurrentItem(1);
//						radioButton1.setTextColor(Color.WHITE);
//						radioButton2.setTextColor(Color.GREEN);
//						radioButton3.setTextColor(Color.WHITE);
					} else
					{
						startActivityForResult(new Intent(MainActivity.this,
								LoginActivity.class),result);
					}
					break;
				case R.id.radio_other:
					result = 2;
					vp.setCurrentItem(2);
//					radioButton1.setTextColor(Color.WHITE);
//					radioButton2.setTextColor(Color.WHITE);
//					radioButton3.setTextColor(Color.GREEN);
					break;
				case R.id.radio_recharge:
					result = 1;
					if (prefUtils.getBoolean(Constant.LOGIN_KEY))// utilsĬ����false,����õ�����true������Ѿ���½
					{
//						radioButton1.setTextColor(Color.WHITE);
//						radioButton2.setTextColor(Color.GREEN);
//						radioButton3.setTextColor(Color.WHITE);
						startActivity(new Intent(MainActivity.this,RechargeActivity.class));
						radioButton1.performClick();
					} else
					{
						startActivityForResult(new Intent(MainActivity.this,
								LoginActivity.class).putExtra("pay", true),result);
					}
					break;
				default:
					break;
				}

			}
		});
		if (fromLogin)//����ӵ�½�ɹ�ҳ�淵�أ�����Ҫת���ҵ�ҳ��
		{
			radioButton2.performClick();
		}
	}
	
	private int result = 1;
	@Override
	public void initData()
	{

	}

	// ���ó�ʼʱ��
	private long firstTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			long secondTime = System.currentTimeMillis();
			if (secondTime - firstTime > 2000)
			{
				final Toast toast = Toast.makeText(this, "�ٰ�һ���˳�����",
						Toast.LENGTH_SHORT);
				toast.show();
				firstTime = secondTime;
			} else{
				if (!prefUtils.getBoolean(Constant.SAVE) && !prefUtils.getBoolean("autologin"))//�������Ҫ����,�ͽ���Ϣ���
				{
					prefUtils.clear();
					baseApplication.exit();
				}
			}
			return true;
		}else {
			return super.onKeyDown(keyCode, event);
		}
		
	}
	@Override
	protected void onResume()
	{
    	JPushInterface.onResume(this);
		super.onResume();
	}
	@Override
	protected void onPause()
	{
		JPushInterface.onPause(this);
		super.onPause();
	}
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2)
	{	
			if (arg0 == 1)
			{
				radioButton1.performClick();
			}else if (arg0 == 2) {
				radioButton3.performClick();
			}
		super.onActivityResult(arg0, arg1, arg2);
	}
	
}
