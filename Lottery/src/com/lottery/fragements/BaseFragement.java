package com.lottery.fragements;


import com.example.lottery.R;
import com.lottery.utils.ImmersedStatusbarUtils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragement extends Fragment{
	public View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		setContentView(inflater,container);
		initView();
		initListener();
		initData();
		View topview  = rootView.findViewById(R.id.title);
		ImmersedStatusbarUtils.initAfterSetContentView(getActivity(), topview);
		return rootView;
	}
	public abstract void setContentView(LayoutInflater inflater,ViewGroup container);
	public abstract void initView();
	public abstract void initListener();
	public abstract void initData();	  /**
     * ��鵱ǰ�����Ƿ����
     */
    public boolean isNetworkAvailable(Activity activity)
    {
        Context context = activity.getApplicationContext();
        // ��ȡ�ֻ��������ӹ�����󣨰�����wi-fi,net�����ӵĹ���
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null)
        {
            return false;
        }
        else
        {
            // ��ȡNetworkInfo����
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int i = 0; i < networkInfo.length; i++)
                {
                    System.out.println(i + "===״̬===" + networkInfo[i].getState());
                    System.out.println(i + "===����===" + networkInfo[i].getTypeName());
                    // �жϵ�ǰ����״̬�Ƿ�Ϊ����״̬
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}