package com.lottery.adapters;

import java.util.ArrayList;
import java.util.List;

import com.example.bean.Message;

import com.example.lottery.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter
{
	private Context context;
	private List<Message> messages;

	public MessageAdapter(Context context, List<Message> messages)
	{
		super();
		this.context = context;
		this.messages = messages;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return messages.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return messages.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		System.out.println(messages.get(position).getTitle());
		ViewHolder viewHolder;

		if (convertView == null)
		{
			convertView = LayoutInflater.from(context).inflate(R.layout.item_message,
					null);
			TextView message_name = (TextView) convertView
					.findViewById(R.id.message_name);
			TextView message_content = (TextView) convertView
					.findViewById(R.id.message_content);
			viewHolder = new ViewHolder();
			viewHolder.setMessage_content(message_content);
			viewHolder.setMessage_name(message_name);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.getMessage_name().setText(messages.get(position).getTitle());
		viewHolder.getMessage_content().setText(messages.get(position).getBody());
		return convertView;
	}
	
	public void UpData(List<Message> messages0){
		if (messages != null)
		{
			messages.addAll(messages0);
		}
	}

	private class ViewHolder
	{
		private TextView message_name;
		private TextView message_content;

		public ViewHolder()
		{
		}

		public TextView getMessage_name()
		{
			return message_name;
		}

		public void setMessage_name(TextView message_name)
		{
			this.message_name = message_name;
		}

		public TextView getMessage_content()
		{
			return message_content;
		}

		public void setMessage_content(TextView message_content)
		{
			this.message_content = message_content;
		}

	

	}

}
