package com.example.bean;

import java.io.Serializable;

public class Way implements Serializable
{
	//"crowdfunding_id":"1","no":"1",
	//"start_time":"2016-01-13 00:00:00","end_time":"2016-01-15 00:00:00",
	//"plans_name":"�ڳ�A��",
	//"":"1","plans_salenum":"100",
	//"plans_storage":"0","plans_image":"rgb.bmp",
	//"plans_type":"9",
	//"detail":"����:������3|3014-11-7;����:������3|3014-11-7;����:������3|3014-11-7;����:������3|3014-11-7;����:������3|3014-11-7;����:������3|30"
	private String plans_name;// ����A��B
	private int plans_type;//9��14
	private String start_time;//��ʼʱ��
	private String end_time;//����ʱ��
	private int plans_salenum;// һ�����ٳ�
	private int plans_storage;// �Ѿ����˶��ٳ�
	private int plans_price;// ����
	private String detail;//��ϸ
	private String plans_image;
	private String crowdfunding_id;//id
	public Way()
	{
		// TODO Auto-generated constructor stub
	}
	public Way(String plans_name, int plans_type, String start_time,
			String end_time, int plans_salenum, int plans_storage,
			int plans_price, String detail, String crowdfunding_id,String plans_image)
	{
		super();
		this.plans_name = plans_name;
		this.plans_type = plans_type;
		this.start_time = start_time;
		this.end_time = end_time;
		this.plans_salenum = plans_salenum;
		this.plans_storage = plans_storage;
		this.plans_price = plans_price;
		this.detail = detail;
		this.plans_image = plans_image;
		this.crowdfunding_id = crowdfunding_id;
	}
	public String getPlans_name()
	{
		return plans_name;
	}
	public void setPlans_name(String plans_name)
	{
		this.plans_name = plans_name;
	}
	public int getPlans_type()
	{
		return plans_type;
	}
	public void setPlans_type(int plans_type)
	{
		this.plans_type = plans_type;
	}
	public String getStart_time()
	{
		return start_time;
	}
	public void setStart_time(String start_time)
	{
		this.start_time = start_time;
	}
	public String getEnd_time()
	{
		return end_time;
	}
	public void setEnd_time(String end_time)
	{
		this.end_time = end_time;
	}
	public int getPlans_salenum()
	{
		return plans_salenum;
	}
	public void setPlans_salenum(int plans_salenum)
	{
		this.plans_salenum = plans_salenum;
	}
	public int getPlans_storage()
	{
		return plans_storage;
	}
	public void setPlans_storage(int plans_storage)
	{
		this.plans_storage = plans_storage;
	}
	public int getPlans_price()
	{
		return plans_price;
	}
	public void setPlans_price(int plans_price)
	{
		this.plans_price = plans_price;
	}
	public String getDetail()
	{
		return detail;
	}
	public void setDetail(String detial)
	{
		this.detail = detial;
	}
	public String getCrowdfunding_id()
	{
		return crowdfunding_id;
	}
	public void setCrowdfunding_id(String crowdfunding_id)
	{
		this.crowdfunding_id = crowdfunding_id;
	}
	public String getPlans_image()
	{
		return plans_image;
	}
	public void setPlans_image(String plans_image)
	{
		this.plans_image = plans_image;
	}
	@Override
	public String toString()
	{
		return "Way [plans_name=" + plans_name + ", plans_type=" + plans_type
				+ ", start_time=" + start_time + ", end_time=" + end_time
				+ ", plans_salenum=" + plans_salenum + ", plans_storage="
				+ plans_storage + ", plans_price=" + plans_price + ", detail="
				+ detail + ", plans_image=" + plans_image
				+ ", crowdfunding_id=" + crowdfunding_id + "]";
	}
	
}
