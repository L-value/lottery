package com.lottery.adapters;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.bean.Detail;
import com.example.lottery.R;
public class DetailAdapter extends BaseAdapter
{
	private Context context;
	private List<Detail> details;
	
	
	public DetailAdapter(Context context, List<Detail> details)
	{			
		super();
		this.context = context;
		this.details = details;
	}

	@Override
	public int getCount()
	{
		return details.size() - 1;
	}

	@Override 
	public Object getItem(int position)
	{
		return details.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
		}
		TextView team = (TextView) convertView.findViewById(R.id.team);
		team.setText(details.get(position).getTeam());
		Button one = (Button) convertView.findViewById(R.id.one);
		Button two = (Button) convertView.findViewById(R.id.two);
		Button three = (Button) convertView.findViewById(R.id.three);
		if (details.get(position).getRes().equals("Ê¤"))
		{
			//one.setTextColor(Color.RED);
			one.setBackground(context.getResources().getDrawable(R.drawable.cyb_shape_red));
		}else if(details.get(position).getRes().equals("¸º")){
			//two.setTextColor(Color.RED);
			two.setBackground(context.getResources().getDrawable(R.drawable.cyb_shape_red));
		}else {
		//	three.setTextColor(Color.RED);
			three.setBackground(context.getResources().getDrawable(R.drawable.cyb_shape_red));
		}
		one.setText("Ê¤");
		two.setText("¸º");
		three.setText("Æ½");
		TextView time = (TextView) convertView.findViewById(R.id.time);	
		time.setText(details.get(position).getTime());
		return convertView;
	}

}
