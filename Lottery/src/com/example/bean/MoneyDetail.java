package com.example.bean;

public class MoneyDetail
{
	private String title;
	private String body;
	private String date;
	
	public MoneyDetail()
	{
		super();
	}
	public MoneyDetail(String title, String body, String date)
	{
		super();
		this.title = title;
		this.body = body;
		this.date = date;
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
	
}
