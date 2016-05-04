package com.lottery.common;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.proto.ba;

import com.example.lottery.R;
import com.lottery.constant.Constant;
import com.lottery.listeners.HttpCallback;
import com.lottery.utils.ImmersedStatusbarUtils;
import com.lottery.utils.PrefUtils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Space;

public abstract class BaseActivity extends FragmentActivity
{
	public BaseApplication baseApplication;
	protected JSONObject job;
	protected String code;
	protected String error;
	protected String key;
	protected int  count;
	protected JSONObject datas;
	protected PrefUtils prefUtils; 
	
	protected int userId = 0;
	protected ImageView back;
	
	protected static Handler handler; 
	protected HttpCallback httpCallback = new HttpCallback(){

		@Override
		public void Success(String success)
		{
			try
			{
				job = new JSONObject(success);
				code = job.getString("code");
				datas = new JSONObject(job.getString("datas"));
				System.out.println("datas"+datas);
				error = job.getString("error");
				count = job.getInt("count");
			} catch (JSONException e)
			{
				e.printStackTrace();
			}
			Message message = Message.obtain();
			message.obj = success;
			message.what = Constant.HANDLE_SUCCESS;
			handler.sendMessage(message);
		}

		@Override
		public void Failed(String failed)
		{
			Message message = Message.obtain();
			message.obj = failed;
			message.what = Constant.HANDLE_FAIL;
			handler.sendMessage(message);
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		baseApplication = BaseApplication.getInstance();
		baseApplication.addActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView();
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
//		{
//			 Window window = getWindow();
//			 window.setFlags(
//					 WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//					 WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//		}
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		View topview  = findViewById(R.id.title);
		ImmersedStatusbarUtils.initAfterSetContentView(this, topview);

		prefUtils = new PrefUtils(this);
		back = (ImageView) findViewById(R.id.back);
		if (null != back)
		{
			back.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					finish();
				}
			});
		}
		initView();
		initListener();
		initData();
		if (userId == Constant.USERID) {
			baseApplication.addUserActivity(this);
		}
	}
	public abstract void setContentView();
	public abstract void initView();
	public abstract void initListener();
	public abstract void initData();
	  /**
     * 检查当前网络是否可用
     */
    public boolean isNetworkAvailable(Activity activity)
    {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null)
        {
            return false;
        }
        else
        {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int i = 0; i < networkInfo.length; i++)
                {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //隐藏软键盘
    void closeInputMethod(Activity activity){
        /*隐藏软键盘*/
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isActive()){
            if(activity.getCurrentFocus()!=null){
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }
    @Override
    protected void onResume()
    {
    	super.onResume();
    }

    
}
