package com.lottery.adapters;



import com.example.lottery.R;
import com.lottery.utils.AndroidTools;

import android.R.integer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/*
 * 数据装载类
 */
public class ViewFlowAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private static final int[] ids = {R.drawable.fliper_1, R.drawable.test2, R.drawable.test3 };
	private Context context;

	public ViewFlowAdapter(Context context) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;   //返回很大的值使得getView中的position不断增大来实现循环。
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.image_item, null);
		}
		 convertView.findViewById(R.id.imgView).setBackgroundResource(ids[position%ids.length]);
		System.out.println(ids[position%ids.length]+"position"+position%ids.length);
		final int pp =position;
		convertView.findViewById(R.id.imgView).setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
					AndroidTools.showToastShort(context, pp%ids.length+"");
			}
		});
		return convertView;
	}

}

