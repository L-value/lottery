package com.example.bean;

public class Payment
{
//	{"order_id":"1","order_sn":"1","pay_sn":"1","buyer_id":"1","plans_id":"1",
//		"add_time":"2016-01-16 14:45:40","payment_code":"zfb","payment_time":"2016-01-16 14:45:40",
//		"finished_time":"2016-01-16 14:45:40","plans_num":"1","order_amount":"1","order_state":"30"
//	}
	private String order_id;//¶©µ¥ºÅ
	private String order_sn;
	private String pay_sn;
	private String buyer_id;
	private String plans_id;
	private String add_time;
	private String payment_code;
	private String payment_time;//Ê±¼ä
	private String finished_time;
	private String plans_num;
	private String order_amount;
	private String order_state;
	private String info;
	public Payment()
	{
		// TODO Auto-generated constructor stub
	}
	public Payment(String order_id, String order_sn, String pay_sn,
			String buyer_id, String plans_id, String add_time,
			String payment_code, String payment_time, String finished_time,
			String plans_num, String order_amount, String order_state,String info)
	{
		super();
		this.order_id = order_id;
		this.order_sn = order_sn;
		this.pay_sn = pay_sn;
		this.buyer_id = buyer_id;
		this.plans_id = plans_id;
		this.add_time = add_time;
		this.payment_code = payment_code;
		this.payment_time = payment_time;
		this.finished_time = finished_time;
		this.plans_num = plans_num;
		this.order_amount = order_amount;
		this.order_state = order_state;
		this.info = info;
	}
	public String getOrder_id()
	{
		return order_id;
	}
	public void setOrder_id(String order_id)
	{
		this.order_id = order_id;
	}
	public String getOrder_sn()
	{
		return order_sn;
	}
	public void setOrder_sn(String order_sn)
	{
		this.order_sn = order_sn;
	}
	public String getPay_sn()
	{
		return pay_sn;
	}
	public void setPay_sn(String pay_sn)
	{
		this.pay_sn = pay_sn;
	}
	public String getBuyer_id()
	{
		return buyer_id;
	}
	public void setBuyer_id(String buyer_id)
	{
		this.buyer_id = buyer_id;
	}
	public String getPlans_id()
	{
		return plans_id;
	}
	public void setPlans_id(String plans_id)
	{
		this.plans_id = plans_id;
	}
	public String getAdd_time()
	{
		return add_time;
	}
	public void setAdd_time(String add_time)
	{
		this.add_time = add_time;
	}
	public String getPayment_code()
	{
		return payment_code;
	}
	public void setPayment_code(String payment_code)
	{
		this.payment_code = payment_code;
	}
	public String getPayment_time()
	{
		return payment_time;
	}
	public void setPayment_time(String payment_time)
	{
		this.payment_time = payment_time;
	}
	public String getFinished_time()
	{
		return finished_time;
	}
	public void setFinished_time(String finished_time)
	{
		this.finished_time = finished_time;
	}
	public String getPlans_num()
	{
		return plans_num;
	}
	public void setPlans_num(String plans_num)
	{
		this.plans_num = plans_num;
	}
	public String getOrder_amount()
	{
		return order_amount;
	}
	public void setOrder_amount(String order_amount)
	{
		this.order_amount = order_amount;
	}
	public String getOrder_state()
	{
		return order_state;
	}
	public void setOrder_state(String order_state)
	{
		this.order_state = order_state;
	}
	public String getInfo()
	{
		return info;
	}
	public void setInfo(String info)
	{
		this.info = info;
	}
	
	
}
