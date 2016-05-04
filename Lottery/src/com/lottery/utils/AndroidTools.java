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
		// ����ProgressDialog����
		m_pDialog = new ProgressDialog(context);

		// ���ý�������񣬷��ΪԲ�Σ���ת��
		m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		// ����ProgressDialog ��ʾ��Ϣ
		m_pDialog.setMessage("���Եȡ�����");

		// ����ProgressDialog �Ľ������Ƿ���ȷ
		m_pDialog.setIndeterminate(false);

		// ����ProgressDialog �Ƿ���԰��˻ذ���ȡ��
		m_pDialog.setCancelable(false);

		m_pDialog.show();
	}
	public static void dismissLoadingDialog()
	{
		m_pDialog.hide();
	}
}
