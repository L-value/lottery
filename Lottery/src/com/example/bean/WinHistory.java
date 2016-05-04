package com.example.bean;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonObject;

public class WinHistory implements Serializable
{
//	{"crowdfunding_id":"1",
//	"start_time":"2015-05-19",
//	"end_time":"2015-06-19",
//	"plans_name":"ÖÚ³ïµÚ0ÆÚA×é",
//	"plans_price":"2","plans_salenum":"100",
//	"plans_storage":"0","plans_type":"9",
//	"detail":[{"team":"»ÊÂívs°ÍÈø","res":"Ê¤","time":"3014-11-7"},
//	 {"team":"»ÊÂívs°ÍÈø","res":"Ê¤","time":"3014-11-7"},
//	 {"team":"»ÊÂívs°ÍÈø","res":"Ê¤","time":"3014-11-7"},
//	 {"team":"»ÊÂívs°ÍÈø","res":"Ê¤","time":"3014-11-7"},
//	 {"team":"»ÊÂívs°ÍÈø","res":"Ê¤","time":"3014-11-7"},
//	 {"team":"»ÊÂívs°ÍÈø","res":"Ê¤","time":"3014-11-7"},
//	 {"team":"»ÊÂívs°ÍÈø","res":"Ê¤","time":"3014-11-7"},
//	 {"team":"»ÊÂívs°ÍÈø","res":"Ê¤","time":"3014-11-7"},
//	 {"team":"»ÊÂívs°ÍÈø","res":"Ê¤","time":"3014-11-7"},
//	 {"team":"","res":"¸º","time":false}],"record":"»ñµÃ½±½ğ100000Ôª"}
	private String crowdfunding_id;
	private String start_time;
	private String end_time;
	private String plans_name;
	private String plans_price;
	private String plans_salenum;
	private String plans_storage;
	private String plans_type;
	private String detail;
	private String record;
	public WinHistory()
	{
		// TODO Auto-generated constructor stub
	}

	public WinHistory(String crowdfunding_id, String start_time,
			String end_time, String plans_name, String plans_price,
			String plans_salenum, String plans_storage, String plans_type,
			String detail, String record)
	{
		super();
		this.crowdfunding_id = crowdfunding_id;
		this.start_time = start_time;
		this.end_time = end_time;
		this.plans_name = plans_name;
		this.plans_price = plans_price;
		this.plans_salenum = plans_salenum;
		this.plans_storage = plans_storage;
		this.plans_type = plans_type;
		this.detail = detail;
		this.record = record;
	}

	public String getCrowdfunding_id()
	{
		return crowdfunding_id;
	}
	public void setCrowdfunding_id(String crowdfunding_id)
	{
		this.crowdfunding_id = crowdfunding_id;
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
	public String getPlans_name()
	{
		return plans_name;
	}
	public void setPlans_name(String plans_name)
	{
		this.plans_name = plans_name;
	}
	public String getPlans_price()
	{
		return plans_price;
	}
	public void setPlans_price(String plans_price)
	{
		this.plans_price = plans_price;
	}
	public String getPlans_salenum()
	{
		return plans_salenum;
	}
	public void setPlans_salenum(String plans_salenum)
	{
		this.plans_salenum = plans_salenum;
	}
	public String getPlans_storage()
	{
		return plans_storage;
	}
	public void setPlans_storage(String plans_storage)
	{
		this.plans_storage = plans_storage;
	}
	public String getPlans_type()
	{
		return plans_type;
	}
	public void setPlans_type(String plans_type)
	{
		this.plans_type = plans_type;
	}
	public String getDetail()
	{
		return detail;
	}
	public void setDetail(String detail)
	{
		this.detail = detail;
	}
	public String getRecord()
	{
		return record;
	}
	public void setRecord(String record)
	{
		this.record = record;
	}
}
