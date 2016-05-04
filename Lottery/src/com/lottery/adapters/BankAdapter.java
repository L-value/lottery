package com.lottery.adapters;

import java.util.List;

import com.example.bean.BankInfo;
import com.example.lottery.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BankAdapter extends BaseAdapter{

	private List<BankInfo> bankInfos;
	private Context context;
	
	
	public BankAdapter(List<BankInfo> bankInfos, Context context) {
		super();
		this.bankInfos = bankInfos;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bankInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return bankInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_bank, null);
			viewHolder = new ViewHolder();
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.im);
			viewHolder.bankname = (TextView) convertView.findViewById(R.id.bankname);
			viewHolder.banknum = (TextView) convertView.findViewById(R.id.banknum);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.bankname.setText(bankInfos.get(position).getName());
		viewHolder.banknum.setText(bankInfos.get(position).getCard_number());
		return convertView;
	}
	private class ViewHolder{
		 ImageView imageView;
		 TextView bankname;
		 TextView banknum;
	}

}
