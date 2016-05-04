package com.lottery.utils;

import android.R.integer;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class ImmersedStatusbarUtils
{

    @TargetApi(Build.VERSION_CODES.KITKAT)
	public static void initAfterSetContentView(Activity activity,
						View titleViewGroup) {
		if (activity == null)
		{
			System.out.println(1);
			return;
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
		{
			Window window = activity.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		if (titleViewGroup == null)
		{
			System.out.println(2);
			return;
		}
		int statusBarHeight = getStatusBarHeight(activity);
		System.out.println(3);
		titleViewGroup.setPadding(0, statusBarHeight, 0, 0);
		
	}

	private static int getStatusBarHeight(Context context)
	{
//		  int result = 0;
//		        int resourceId = context.getResources().getIdentifier(
//		                "status_bar_height", "dimen", "android");
//		        if (resourceId > 0) {
//		            result = context.getResources().getDimensionPixelSize(resourceId);
//		        }
//		        return result;
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0)
		{
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		System.out.println("resource"+result);
		return result;
	}
}
