package com.lottery.fragements;


import java.lang.ref.WeakReference;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.lottery.R;
import com.lottery.constant.Constant;
import com.lottery.listeners.HttpCallback;
import com.lottery.utils.AndroidTools;
import com.lottery.utils.HttpUtils;
import com.lottery.utils.PrefUtils;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FeedFragement extends BaseFragement
{

	private EditText feedback;
	private Button submit;
	@Override
	public void setContentView(LayoutInflater inflater, ViewGroup container)
	{
		rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragement_feed, null);
		feedback = (EditText) rootView.findViewById(R.id.feedback);
		submit = (Button) rootView.findViewById(R.id.submit);
	}

	@Override
	public void initView()
	{
		PrefUtils prefUtils = new PrefUtils(getActivity());
		final String key = prefUtils.getString("key");
		
		
		submit.setOnClickListener(new OnClickListener() {
			
			private WeakReference<Handler> weekHandler;

			@Override
			public void onClick(View v) {
				HashMap<String, String> data = new HashMap<>();
				// key,title,body
				data.put("key", key);
				if (feedback.getText().toString() != null && !feedback.getText().toString().equals("")) {
					data.put("title", "反馈");
					data.put("body", feedback.getText().toString());
				}
				//http://test.nsscn.org/zc/index.php?act=message&op=feedback				
			HttpUtils httpUtils = new HttpUtils(new HttpCallback() {
				
				@Override
				public void Success(String success) {
					JSONObject jsonObject = null;
					try {
						jsonObject = new JSONObject(success);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String datas = "";
					try {
						datas = jsonObject.getString("datas");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message message = Message.obtain();
					message.obj = datas;
					message.what = Constant.HANDLE_SUCCESS;
					weekHandler.get().handleMessage(message);
					
				}
				
				@Override
				public void Failed(String failed) {
					Message message = Message.obtain();
					message.obj = failed;
					message.what = Constant.HANDLE_FAIL;
					weekHandler.get().handleMessage(message);
				}
			}, "http://test.nsscn.org/zc/index.php?act=message&op=feedback",
			"POST", data);
			if (isNetworkAvailable(getActivity())) {
				httpUtils.excute();
				
				weekHandler = new WeakReference<Handler>(new Handler(){
					public void handleMessage(android.os.Message msg) {
						if (msg.what == Constant.HANDLE_SUCCESS) {
							String sucess = (String) msg.obj;
							if (sucess.equals("ok")) {
								AndroidTools.showToastShort(getActivity(), "感谢您的反馈");
							}else {
								AndroidTools.showToastShort(getActivity(), "反馈失败，请稍后再试");
							}
						}else {
							AndroidTools.showToastShort(getActivity(), "网络连接异常");
						}
					};
				});
			}else {
				AndroidTools.showToastShort(getActivity(), "网络连接异常");
			}
			}
		});
	}

	@Override
	public void initListener()
	{
		
	}

	@Override
	public void initData()
	{
		
	}

	

}
