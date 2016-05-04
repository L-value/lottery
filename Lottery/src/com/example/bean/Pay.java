package com.example.bean;

public class Pay
{
//	id":"133","
//	+ ""target_id":"1","title":"中奖信息","
//	+ ""body":"第0期 众筹A组id:1中奖获利:65.7934元","
//	+ ""date":"2016-03-02 16:13:00","flag":"0","source":"系统","type":"22"
	private String id;
	private String target_id;
	private String title;
	private String body;
	private String date;
	private String flag;
	private String suorce;
	private String type;
	public Pay()
	{
		// TODO Auto-generated constructor stub
	}
	public Pay(String id, String target_id, String title, String body,
			String date, String flag, String suorce, String type)
	{
		super();
		this.id = id;
		this.target_id = target_id;
		this.title = title;
		this.body = body;
		this.date = date;
		this.flag = flag;
		this.suorce = suorce;
		this.type = type;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getTarget_id()
	{
		return target_id;
	}
	public void setTarget_id(String target_id)
	{
		this.target_id = target_id;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getBody()
	{
		return body;
	}
	public void setBody(String body)
	{
		this.body = body;
	}
	public String getDate()
	{
		return date;
	}
	public void setDate(String date)
	{
		this.date = date;
	}
	public String getFlag()
	{
		return flag;
	}
	public void setFlag(String flag)
	{
		this.flag = flag;
	}
	public String getSuorce()
	{
		return suorce;
	}
	public void setSuorce(String suorce)
	{
		this.suorce = suorce;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	
}
