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
				// ֧��״̬��
				String resultStatus = payResult.getResultStatus();

				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(ChoBuyWayActivity.this, "֧���ɹ�", Toast.LENGTH_SHORT)
							.show();
				} else if (TextUtils.equals(resultStatus, "8000")) {
					Toast.makeText(ChoBuyWayActivity.this, "֧�����ȷ����",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(ChoBuyWayActivity.this, "֧��ʧ��", Toast.LENGTH_SHORT)
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
		desc = "����";
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
			// 1.������������
			String orderInfo = getOrderInfo(name, desc, price);

			// 2.�Զ�������ǩ��
			String sign = sign(orderInfo);
			try {
				// ת��
				sign = URLEncoder.encode(sign, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			// 3.����һ������֧���������淶�Ķ�������
			final String payInfo = orderInfo + "&sign=\"" + sign
					+ "\"&sign_type=\"RSA\"";

			// 4.����֧���ӿ�
			new Thread() {
				public void run() {
					// ֧������
					PayTask task = new PayTask(ChoBuyWayActivity.this);
					String result = task.pay(payInfo);
					// ����֧�����Ĺ������֧������Ľ���

					Message msg = handler.obtainMessage(MESSAGE_PAY);
					msg.obj = result;
					handler.sendMessage(msg);
				}
			}.start();

			// 5.����֧�����

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

	// �̻�ID
	public static final String PARTNER = Constant.PARTNER;
	// �̻��տ��˺�
	public static final String SELLER = Constant.SELLER;
	// �̻�˽Կ
	// ˽Կ���ڶ����ݽ���ǩ������
	// ��Կ���ڶ�ǩ��������֤
	public static final String RSA_PRIVATE = Constant.RSA_PRIVATE;

	/**
	 * �Զ�������ǩ��
	 * 
	 * @param orderInfo
	 * @return
	 */
	private String sign(String orderInfo) {
		return SignUtils.sign(orderInfo, RSA_PRIVATE);
	}

	/**
	 * ����������Ϣ
	 * 
	 * @param subject
	 *            ��Ʒ����
	 * @param body
	 *            ��Ʒ����
	 * @param price
	 *            ��Ʒ���
	 * @return
	 */
	public String getOrderInfo(String subject, String body, String price) {
		// ǩԼ���������ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// ǩԼ����֧�����˺�
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// �̻���վΨһ������
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// ��Ʒ����
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// ��Ʒ����
		orderInfo += "&body=" + "\"" + body + "\"";

		// ��Ʒ���
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// �������첽֪ͨҳ��·��
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
				+ "\"";

		// ����ӿ����ƣ� �̶�ֵ
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// ֧�����ͣ� �̶�ֵ
		orderInfo += "&payment_type=\"1\"";

		// �������룬 �̶�ֵ
		orderInfo += "&_input_charset=\"utf-8\"";

		// ����δ����׵ĳ�ʱʱ��
		// Ĭ��30���ӣ�һ����ʱ���ñʽ��׾ͻ��Զ����رա�
		// ȡֵ��Χ��1m��15d��
		// m-���ӣ�h-Сʱ��d-�죬1c-���죨���۽��׺�ʱ����������0��رգ���
		// �ò�����ֵ������С���㣬��1.5h����ת��Ϊ90m��
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_tokenΪ���������Ȩ��ȡ����alipay_open_id,���ϴ˲����û���ʹ����Ȩ���˻�����֧��
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// ֧��������������󣬵�ǰҳ����ת���̻�ָ��ҳ���·�����ɿ�
		orderInfo += "&return_url=\"m.alipay.com\"";

		// �������п�֧���������ô˲���������ǩ���� �̶�ֵ ����ҪǩԼ���������п����֧��������ʹ�ã�
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * ����һ������ʱ�������Ķ�����
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
