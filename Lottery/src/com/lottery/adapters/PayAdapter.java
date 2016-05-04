package com.lottery.adapters;

import java.util.List;

import com.example.bean.Pay;
import com.example.bean.Payment;
import com.example.lottery.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PayAdapter extends BaseAdapter
{

	private List<Pay> payments;
	private Context context;


	public PayAdapter()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public PayAdapter(List<Pay> payments, Context context)
	{
		super();
		this.payments = payments;
		this.context = context;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return payments.size();
	}

	@Override
	public Object getItem(int position)
	{
		
		return payments.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}
	public void addMoreData(List<Pay> pays){
		if (payments != null)
		{
			payments.addAll(pays);
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		System.out.println("getView...............");
		ViewHolder holder;
		if (convertView == null)
		{
			convertView = LayoutInflater.from(context).inflate(R.layout.item_record, null,false);
			TextView onum = (TextView) convertView.findViewById(R.id.onum);//单号
			TextView odate = (TextView) convertView.findViewById(R.id.odate);//时间
			TextView omessage = (TextView) convertView.findViewById(R.id.omessage);//消息
			holder = new ViewHolder();
			holder.setOnum(onum);
			holder.setOdate(odate);
			holder.setOmessage(omessage);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.getOnum().setText("单号 :"+payments.get(position).getId());
		holder.getOdate().setText(payments.get(position).getDate());
		holder.getOmessage().setText(payments.get(position).getBody());
		return convertView;
	}
	private class ViewHolder{
		TextView onum;
		TextView odate;
		TextView omessage;
		
		public TextView getOnum()
		{
			return onum;
		}
		public void setOnum(TextView onum)
		{
			this.onum = onum;
		}
		public TextView getOdate()
		{
			return odate;
		}
		public void setOdate(TextView odate)
		{
			this.odate = odate;
		}
		public TextView getOmessage()
		{
			return omessage;
		}
		public void setOmessage(TextView omessage)
		{
			this.omessage = omessage;
		}
		
	}


}
