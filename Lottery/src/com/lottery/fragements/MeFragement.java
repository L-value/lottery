package com.lottery.fragements;

import java.util.HashMap;

import javax.security.auth.PrivateCredentialPermission;

import com.example.lottery.AddBankActivity;
import com.example.lottery.BankActivity;
import com.example.lottery.ChangPassActivity;
import com.example.lottery.CustomerActivity;
import com.example.lottery.LoginActivity;
import com.example.lottery.MeActivity;
import com.example.lottery.MessageActivity;
import com.example.lottery.MyWinHistoryActivity;
import com.example.lottery.PaymentActivity;
import com.example.lottery.R;
import com.example.lottery.RechargeActivity;
import com.example.lottery.RecordActivity;
import com.lottery.listeners.HttpCallback;
import com.lottery.utils.HttpUtils;
import com.lottery.utils.ImmersedStatusbarUtils;
import com.lottery.utils.PrefUtils;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MeFragement extends BaseFragement
{

	private TextView me_name,sum;//���ֺ����
	private Button me_btn_get,m_bnt_recharge;//
	private TextView message_center,win_record,buy_rec,
	                 mon_detail,me,bank,phoneTextView,
	                 password,quit;
	private ImageView help;
	
	private PrefUtils prefUtils;
	private HttpUtils httpUtils;
	
	@Override
	public void setContentView(LayoutInflater inflater, ViewGroup container)
	{
		rootView = inflater.inflate(R.layout.fragement_me, null);
		prefUtils = new PrefUtils(this.getActivity());
	}

	@Override
	public void initView()
	{
		me_name = (TextView) rootView.findViewById(R.id.me_name);//����
		me_name.setText("   ��ӭ�� : "+prefUtils.getString("username"));
		sum = (TextView) rootView.findViewById(R.id.sum);//�˻����
		sum.setText("   �˻����:"+prefUtils.getString("money"));
		message_center = (TextView) rootView.findViewById(R.id.message_center);
		help = (ImageView) rootView.findViewById(R.id.help);
		help.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(getActivity(),CustomerActivity.class));
			}
		});
		message_center.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent();
				intent.setClass(getActivity(), MessageActivity.class);
				startActivity(intent);
			}
		});
		win_record = (TextView) rootView.findViewById(R.id.win_record);//�н���¼
		win_record.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(getActivity(),RecordActivity.class));
				
			}
		});
		buy_rec = (TextView) rootView.findViewById(R.id.buy_rec);// Ͷע��¼
		buy_rec.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(getActivity(),PaymentActivity.class));
				
			}
		});
		mon_detail = (TextView) rootView.findViewById(R.id.mon_detail);//�ʽ���ϸ
		mon_detail.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(getActivity(),MyWinHistoryActivity.class));
			}
		});
		me = (TextView) rootView.findViewById(R.id.me);//������Ϣ
		me.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(getActivity(),MeActivity.class));
				
			}
		});
		bank = (TextView) rootView.findViewById(R.id.bank);//������Ϣ
		bank.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(),BankActivity.class));
				
			}
		});
		phoneTextView = (TextView) rootView.findViewById(R.id.phone);//�ֻ���
		password = (TextView) rootView.findViewById(R.id.password);//�޸�����
		password.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(),ChangPassActivity.class));
			}
		});
		quit = (TextView) rootView.findViewById(R.id.quit);//�˳���¼
		quit.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(getActivity(),LoginActivity.class));
				prefUtils.clear();
				getActivity().finish();
			}
		});
		m_bnt_recharge = (Button) rootView.findViewById(R.id.m_bnt_recharge);//��ֵ
		m_bnt_recharge.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(getActivity(),RechargeActivity.class));	
			}
		});
		me_btn_get = (Button) rootView.findViewById(R.id.me_btn_get);//����

	}
	
	
	@Override
	public void initListener()
	{
		
		
	}
	
	@Override
	public void initData()
	{
	
	}


}
