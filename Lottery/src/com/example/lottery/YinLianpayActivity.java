package com.example.lottery;



import org.apache.http.util.EncodingUtils;

import com.example.bean.OrderDetail;
import com.lottery.common.BaseActivity;
import com.lottery.utils.AndroidTools;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * Created by cttol on 2015/11/19.
 */


public class YinLianpayActivity extends BaseActivity {
   
    private WebView we;
   
    boolean sucess;
    String orderId;


	private OrderDetail orderDetail;
	@Override
	public void setContentView()
	{
		setContentView(R.layout.layout_yinlian);
		
	}
	@Override
	public void initView()
	{
		  we = (WebView) findViewById(R.id.we);
		  orderDetail = (OrderDetail) getIntent().getSerializableExtra("order");
		  if (isNetworkAvailable(this)){

	      
	            we.setVerticalScrollbarOverlay(true); //指定的垂直滚动条有叠加样式
	            WebSettings settings = we.getSettings();
	            settings.setJavaScriptEnabled(true);
	            settings.setUseWideViewPort(true);//设定支持viewport

	            settings.setLoadWithOverviewMode(true);

	            settings.setBuiltInZoomControls(true);

	            settings.setSupportZoom(true);//设定支持缩放

	            String postData = "merSign="+orderDetail.getMerSign()+
	                    "&chrCode="+orderDetail.getChrCode()+
	                    "&tranId="+orderDetail.getTransId()+
	                    "&mchantUserCode=1234567890123456"+
	                    "&url=http://www.hao123.com/?tn=newbdie_hao_dg"+
	                    "&bankName=&cardType=";

	            we.postUrl("http://116.228.21.162:9127/umsFrontWebQmjf/umspay", EncodingUtils.getBytes(postData, "utf-8"));
	            we.setWebViewClient(new WebViewClient() {
	                @Override
	                public void onPageFinished(WebView view, String url) {
	                    if (url.equals("http://116.228.21.162:9127/umsFrontWebQmjf/payInfo!backSuccessPage.ac")) {
	                        sucess = true;
	                       // getEvenice();
	                    }
	                    super.onPageFinished(view, url);
	                }

	                @Override
	                public void onPageStarted(WebView view, String url, Bitmap favicon) {
	                    Log.i("info", "start......" + url);
	                }

	                @Override
	                public boolean shouldOverrideUrlLoading(WebView view, String url) {
	                    Log.i("info", "should over" + url);
	                    return super.shouldOverrideUrlLoading(view, url);
	                }
	            });
	        }else{
	            AndroidTools.showToastShort(this,"网络连接失败");
	            this.finish();
	        }		
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
