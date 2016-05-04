package com.example.myviews;



import com.example.lottery.R;
import com.lottery.adapters.SpinnerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;



/**
 * Created by apple on 16/3/6.
 */
public class SpinnerPopWindow extends PopupWindow {

    private Context context;
    private ListView listView;
    private SpinnerAdapter adapter;
    private AdapterView.OnItemClickListener mOnItemClickListener;
    public SpinnerPopWindow(Context context,
                                SpinnerAdapter adapter,AdapterView.OnItemClickListener mOnItemClickListener) {
        super();


        this.context = context;
        this.adapter = adapter;
        this.mOnItemClickListener = mOnItemClickListener;
        init();
    }



    public Context getContext() {
        return context;
    }



    private void init(){
        LinearLayout ll = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.spinner_content, null);
        setContentView(ll);
        setWidth(AbsListView.LayoutParams.WRAP_CONTENT);
        setHeight(AbsListView.LayoutParams.WRAP_CONTENT);

        setFocusable(true);

        listView = (ListView) ll.findViewById(R.id.spinner_listivew);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(mOnItemClickListener);
    }



}