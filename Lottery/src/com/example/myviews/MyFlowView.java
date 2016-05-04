package com.example.myviews;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 
 * @author cttol
 * this view is used to show the adlists
 */
public class MyFlowView extends TextView
{
	//监听服务器的接口，如果服务器发来推送消息，就以动画的形式展示。
	public MyFlowView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	}
}
