package com.lottery.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class CustomerPagerAdapter extends FragmentPagerAdapter
{

	
	Context context;
	ArrayList<Fragment> fragments;
	public CustomerPagerAdapter(FragmentManager fm,Context context,ArrayList<Fragment> fragments)
	{
		super(fm);
		this.context = context;
		this.fragments = fragments;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		Fragment fragment = (Fragment) object;
		container.removeView(fragment.getView());
		super.destroyItem(container, position, object);
	}
	
	@Override
	public Fragment getItem(int arg0)
	{
		return fragments.get(arg0);
	}

	  @Override
      public int getCount() {
          return fragments == null ? 0 : fragments.size();
      }
     
	 @Override
     public boolean isViewFromObject(View view, Object obj) {
         return view == ((Fragment) obj).getView();
     }
}
