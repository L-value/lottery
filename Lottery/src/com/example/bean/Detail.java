package com.example.bean;

public class Detail
{
	private String team;
	private String one;
	private String two;
	private String three;
	private String res;
	private String time;
	public Detail()
	{
		
	}
	
	public Detail(String team, String one, String two, String three,
			String res, String time)
	{
		super();
		this.team = team;
		this.one = one;
		this.two = two;
		this.three = three;
		this.res = res;
		this.time = time;
	}

	public String getTeam()
	{
		
		return team;
		
	}
	public void setTeam(String team)
	{
		this.team = team;
	}
	public String getOne()
	{
		return one;
	}
	public void setOne(String one)
	{
		this.one = one;
	}
	public String getTwo()
	{
		return two;
	}
	public void setTwo(String two)
	{
		this.two = two;
	}
	public String getThree()
	{
		return three;
	}
	public void setThree(String three)
	{
		this.three = three;
	}
	public String getTime()
	{
		return time;
	}
	public void setTime(String time)
	{
		this.time = time;
	}

	public String getRes()
	{
		return res;
	}

	public void setRes(String res)
	{
		this.res = res;
	}
	
	
	
}
