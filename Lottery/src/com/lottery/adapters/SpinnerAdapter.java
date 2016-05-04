package com.lottery.adapters;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.bean.SpinnerCell;
import com.example.lottery.R;


/**
 * Created by apple on 16/3/6.
 */
public class SpinnerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SpinnerCell> list = new ArrayList<SpinnerCell>();


    public SpinnerAdapter() {
        super();
    }

    public SpinnerAdapter(Context context,ArrayList<SpinnerCell> list) {
        super();
        this.context = context;
        this.list = list;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public SpinnerCell getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout ll = null;
        ViewHolder vh = null;
        SpinnerCell data = list.get(position);
        if(convertView != null){
            ll = (LinearLayout) convertView;
            vh = (ViewHolder) ll.getTag();
        }else{
            ll = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.spinner_cell, null);
            vh = new ViewHolder();

            vh.name = (TextView) ll.findViewById(R.id.spinner_cell_text);

            ll.setTag(vh);
        }

        vh.name.setText(data.getName());


        return ll;
    }

    private static class ViewHolder{
        TextView name;
    }

}