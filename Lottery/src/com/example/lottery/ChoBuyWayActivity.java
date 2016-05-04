package com.example.lottery;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import com.alipay.sdk.app.PayTask;
import com.example.bean.OrderDetail;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lottery.common.BaseActivity;
import com.lottery.constant.Constant;
import com.lottery.listeners.HttpCallback;
import com.lottery.utils.HttpUtils;
import com.lottery.utils.PayResult;
import com.lottery.utils.SignUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChoBuyWayActivity extends BaseActivity implements OnClickListener {
	private TextView payMoney;

	private LinearLayout pay_yinlian, pay_alipay, pay_weixin;

	private HttpUtils httpUtils;
	private HashMap<String, String> data;

	private String name;
	private String price;
	private String desc;

	private String id;
	private final static int MESSAGE_PAY = 1;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MESSAGE_PAY:
				String result = (String) msg.obj;
				PayResult payResult = new PayResult(result);
				// 支付状态码
				String resultStatus = payResult.getResultStatus();

				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(ChoBuyWayActivity.this, "支付成功", Toast.LENGTH_SHORT)
							.show();
				} else if (TextUtils.equals(resultStatus, "8000")) {
					Toast.makeText(ChoBuyWayActivity.this, "支付结果确认中",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(ChoBuyWayActivity.this, "支付失败", Toast.LENGTH_SHORT)
							.show();
				}

				break;

			default:
				break;
			}
		}
	};

	@Override
	public void setContentView() {
		setContentView(R.layout.activity_cho_buy_way);
	}

	@Override
	public void initView() {
		name = getIntent().getStringExtra("way");
		price = getIntent().getStringExtra("money");
		desc = "购彩";
		payMoney = (TextView) findViewById(R.id.pay_money);
		payMoney.setText(getIntent().getStringExtra("money"));
		pay_yinlian = (LinearLayout) findViewById(R.id.pay_yinlian);
		pay_alipay = (LinearLayout) findViewById(R.id.pay_alipay);
		pay_weixin = (LinearLayout) findViewById(R.id.pay_weixin);
	}

	@Override
	public void initListener() {
		pay_yinlian.setOnClickListener(this);
		pay_weixin.setOnClickListener(this);
		pay_alipay.setOnClickListener(this);
	}

	private Gson gson;

	@Override
	public void initData() {
		id = getIntent().getStringExtra("id");
		gson = new Gson();
		handler = new Handler() {

			private OrderDetail orderDetail;

			@Override
			public void handleMessage(Message msg) {
				System.out.println((String) msg.obj);
				if (msg.what == Constant.HANDLE_SUCCESS) {
					System.out.println(datas);

					try {
						orderDetail = gson.fromJson(datas.getString("data"),
								new TypeToken<OrderDetail>() {
								}.getType());
					} catch (JsonSyntaxException | JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// {"data":{"merSign":"e38579d58fcd7bd23e38f5415903df7f0446277f4a60c137f48fd993010e3b26dba18339aa4b14cf5e5c94972ddb05d995c6e1d091a36e6d7982566763a9b1cba1aa3159036a091d398bd03c3b88a25435e8525b4b4685afac45e49e93b6ac0bac17d8c2a1106886126c9183cb4ae1019386b9ae5283363a5bd400520c511b70","TransId":"662016031328532574","ChrCode":"201603131155572EwjyfQwsN","mchantUserCode":"898000093990002"}}
					Intent intent = new Intent(ChoBuyWayActivity.this,
							YinLianpayActivity.class);
					intent.putExtra("order", orderDetail);
					startActivity(intent);
				} else if (msg.what == Constant.HANDLE_FAIL) {
						System.out.println("Fail");
				}
			}
		};
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pay_alipay:
			// 1.构建订单数据
			String orderInfo = getOrderInfo(name, desc, price);

			// 2.对订单进行签名
			String sign = sign(orderInfo);
			try {
				// 转码
				sign = URLEncoder.encode(sign, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			// 3.构建一个符合支付宝参数规范的订单数据
			final String payInfo = orderInfo + "&sign=\"" + sign
					+ "\"&sign_type=\"RSA\"";

			// 4.调用支付接口
			new Thread() {
				public void run() {
					// 支付任务
					PayTask task = new PayTask(ChoBuyWayActivity.this);
					String result = task.pay(payInfo);
					// 按照支付宝的规则进行支付结果的解析

					Message msg = handler.obtainMessage(MESSAGE_PAY);
					msg.obj = result;
					handler.sendMessage(msg);
				}
			}.start();

			// 5.处理支付结果

			break;
		case R.id.pay_weixin:

			break;
		case R.id.pay_yinlian:
			System.out.println("yinlian");
			data = new HashMap<>();
			data.put("key", prefUtils.getString("key"));
			data.put("plans_id", id);
			data.put("plans_num", getIntent().getStringExtra("fenshu"));
			httpUtils = new HttpUtils(new HttpCallback() {

				@Override
				public void Success(String success) {
					try {
						job = new JSONObject(success);
						code = job.getString("code");
						datas = new JSONObject(job.getString("datas"));
						error = job.getString("error");
						count = job.getInt("count");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					Message message = Message.obtain();
					message.what = Constant.HANDLE_SUCCESS;
					message.obj = success;
					handler.sendMessage(message);
				}

				@Override
				public void Failed(String failed) {
					Message message = Message.obtain();
					message.what = Constant.HANDLE_FAIL;
					handler.sendMessage(message);
				}
			}, "http://test.nsscn.org/zc/index.php?act=order&op=chinapay_tt",
					"POST", data);
			httpUtils.excute();
			break;
		default:
			break;
		}
	}

	// 商户ID
	public static final String PARTNER = Constant.PARTNER;
	// 商户收款账号
	public static final String SELLER = Constant.SELLER;
	// 商户私钥
	// 私钥用于对数据进行签名加密
	// 公钥用于对签名进行验证
	public static final String RSA_PRIVATE = Constant.RSA_PRIVATE;

	/**
	 * 对订单进行签名
	 * 
	 * @param orderInfo
	 * @return
	 */
	private String sign(String orderInfo) {
		return SignUtils.sign(orderInfo, RSA_PRIVATE);
	}

	/**
	 * 构建订单信息
	 * 
	 * @param subject
	 *            商品名称
	 * @param body
	 *            商品详情
	 * @param price
	 *            商品金额
	 * @return
	 */
	public String getOrderInfo(String subject, String body, String price) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
				+ "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * 产生一个包含时间的随机的订单号
	 * 
	 * @return
	 */
	private String getOutTradeNo() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String key = sdf.format(date);
		Random random = new Random();
		key = key + random.nextInt();
		key = key.substring(0, 15);
		return key;
	}

}
