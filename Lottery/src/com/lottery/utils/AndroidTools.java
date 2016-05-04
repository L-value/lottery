package com.lottery.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class AndroidTools
{
	private static ProgressDialog m_pDialog;

	public static void showToastLong(Context context, String message)
	{
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	public static void showToastShort(Context context, String message)
	{
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	public static void logS(String msg)
	{
		Log.i("info", msg);
	}

	public static void logI(int num)
	{
		Log.i("info", Integer.toString(num));
	}

	public static void logD(double num)
	{
		Log.i("info", Double.toString(num));
	}

	public static void showLoadDialog(Context context)
	{
		// 创建ProgressDialog对象
		m_pDialog = new ProgressDialog(context);

		// 设置进度条风格，风格为圆形，旋转的
		m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		// 设置ProgressDialog 提示信息
		m_pDialog.setMessage("请稍等。。。");

		// 设置ProgressDialog 的进度条是否不明确
		m_pDialog.setIndeterminate(false);

		// 设置ProgressDialog 是否可以按退回按键取消
		m_pDialog.setCancelable(false);

		m_pDialog.show();
	}
	public static void dismissLoadingDialog()
	{
		m_pDialog.hide();
	}
}
