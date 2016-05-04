package com.example.bean;

public class Message
{
//	id":"111","target_id":"1","
//	+ ""title":"客服","body":"充值500元已到账","
//	+ ""date":"2016-03-02 16:43:25","flag":"0","source":"系统","type":"0"
	private String	id;
	private String target_id;
	private String title;
	private String body;
	private String date;
	private String flag;
	private String source;
	private String type;
	public Message()
	{
		// TODO Auto-generated constructor stub
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
	public String getSource()
	{
		return source;
	}
	public void setSource(String source)
	{
		this.source = source;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public Message(String id, String target_id, String title, String body,
			String date, String flag, String source, String type)
	{
		super();
		this.id = id;
		this.target_id = target_id;
		this.title = title;
		this.body = body;
		this.date = date;
		this.flag = flag;
		this.source = source;
		this.type = type;
	}
	
}
