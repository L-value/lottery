package com.lottery.fragements;


import com.example.lottery.R;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

public class HelpFragement extends BaseFragement
{

	private TextView textView1;//��β�ѯ�н�
	private TextView textView2;//֧����ȫ����
	private TextView textView3;//���½������
	private TextView textView4;//����Ͷע��ʽ
	private TextView textView5;//�����淨����
	
	@Override
	public void setContentView(LayoutInflater inflater, ViewGroup container)
	{
		rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragement_help, null);
		textView1 = (TextView) rootView.findViewById(R.id.textView1);
		textView2 = (TextView) rootView.findViewById(R.id.textView2);
		textView3 = (TextView) rootView.findViewById(R.id.textView3);
		textView4 = (TextView) rootView.findViewById(R.id.textView4);
		textView5 = (TextView) rootView.findViewById(R.id.textView5);
		
	}

	@Override
	public void initView()
	{
		
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
