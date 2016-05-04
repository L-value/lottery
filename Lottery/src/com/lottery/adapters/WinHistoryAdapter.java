package com.lottery.adapters;


import java.util.List;

import com.example.bean.WinHistory;
import com.example.lottery.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WinHistoryAdapter extends BaseAdapter
{
	private Context context;
	private List<WinHistory> winHistories;
	
	public WinHistoryAdapter(Context context, List<WinHistory> winHistories)
	{
		super();
		this.context = context;
		this.winHistories = winHistories;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return winHistories.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return winHistories.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}
	public void upData(List<WinHistory> histories){
		if (winHistories != null)
		{
			winHistories.addAll(histories);
		}
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder;
		if (convertView == null)
		{
			convertView = LayoutInflater.from(context).inflate(R.layout.item_all_history, null);
			viewHolder = new ViewHolder();
			viewHolder.setAll_ji((TextView)convertView.findViewById(R.id.all__ji));
			viewHolder.setAll_time((TextView)convertView.findViewById(R.id.all_time));
			viewHolder.setAll_way((TextView)convertView.findViewById(R.id.all_way));
			viewHolder.setAll_what((TextView)convertView.findViewById(R.id.all_waht));
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.getAll_ji().setText("×ã²Ê"+winHistories.get(position).getPlans_type());
		viewHolder.getAll_way().setText("·½°¸"+winHistories.get(position).getPlans_name());
		viewHolder.getAll_time().setText(winHistories.get(position).getEnd_time());
		viewHolder.getAll_what().setText(winHistories.get(position).getRecord());
		return convertView;
	}
	private class ViewHolder{
		private TextView all_ji;
		private TextView all_way;
		private TextView all_time;
		private TextView all_what;
		public TextView getAll_ji()
		{
			return all_ji;
		}
		public void setAll_ji(TextView all_ji)
		{
			this.all_ji = all_ji;
		}
		public TextView getAll_way()
		{
			return all_way;
		}
		public void setAll_way(TextView all_way)
		{
			this.all_way = all_way;
		}
		public TextView getAll_time()
		{
			return all_time;
		}
		public void setAll_time(TextView all_time)
		{
			this.all_time = all_time;
		}
		public TextView getAll_what()
		{
			return all_what;
		}
		public void setAll_what(TextView all_what)
		{
			this.all_what = all_what;
		}
		
	}

}
