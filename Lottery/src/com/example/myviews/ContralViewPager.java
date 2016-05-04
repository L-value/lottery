package com.example.myviews;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ContralViewPager extends ViewPager
{

	public ContralViewPager(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	 private boolean scrollble = true;

	  @Override
	  public boolean onTouchEvent(MotionEvent ev) {
	    if (!scrollble) {
	      return false;
	    }
	    return super.onTouchEvent(ev);
	  }


	  public boolean isScrollble() {
	    return scrollble;
	  }

	  public void setScrollble(boolean scrollble) {
	    this.scrollble = scrollble;
	  }
	  @Override
		public boolean onInterceptTouchEvent(MotionEvent arg0) {
			// TODO Auto-generated method stub
			if (scrollble) {
				return super.onInterceptTouchEvent(arg0);
			} else {
				return false;
			}

		}
}
