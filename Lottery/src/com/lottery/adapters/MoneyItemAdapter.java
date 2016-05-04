package com.lottery.adapters;

import java.util.ArrayList;
import java.util.List;

import com.example.bean.MoneyDetail;
import com.example.lottery.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MoneyItemAdapter extends BaseAdapter
{
	private List<MoneyDetail> moneyDetails;
	private Context context;

	public MoneyItemAdapter(List<MoneyDetail> moneyDetails, Context context)
	{
		super();
		this.moneyDetails = moneyDetails;
		this.context = context;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return moneyDetails.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return moneyDetails.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}
	public void upData(List<MoneyDetail> moneyDetailss){
		if (moneyDetails != null)
		{
			moneyDetails.addAll(moneyDetailss);
		}
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder;
		if (convertView == null)
		{
			convertView = LayoutInflater.from(context).inflate(R.layout.item_money_detail, null);
			viewHolder = new ViewHolder();
			viewHolder.setDetail((TextView)convertView.findViewById(R.id.money_detail));
			viewHolder.setTime((TextView)convertView.findViewById(R.id.money_time));
			viewHolder.setWhaT((TextView)convertView.findViewById(R.id.money_what));
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.getDetail().setText(moneyDetails.get(position).getBody());
		viewHolder.getTime().setText(moneyDetails.get(position).getDate());
		viewHolder.getWhaT().setText(moneyDetails.get(position).getTitle());
		return convertView;
	}
	private class ViewHolder{
		private TextView time;
		private TextView whaT;
		private TextView detail;
		public TextView getTime()
		{
			return time;
		}
		public void setTime(TextView time)
		{
			this.time = time;
		}
		public TextView getWhaT()
		{
			return whaT;
		}
		public void setWhaT(TextView whaT)
		{
			this.whaT = whaT;
		}
		public TextView getDetail()
		{
			return detail;
		}
		public void setDetail(TextView detail)
		{
			this.detail = detail;
		}
		public ViewHolder()
		{
			// TODO Auto-generated constructor stub
		}
	}

}
