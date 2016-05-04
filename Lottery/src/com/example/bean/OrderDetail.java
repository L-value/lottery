package com.example.bean;

import java.io.Serializable;

public class OrderDetail implements Serializable
{
	
	public String getMerSign()
	{
		return merSign;
	}
	public void setMerSign(String merSign)
	{
		this.merSign = merSign;
	}
	public String getTransId()
	{
		return TransId;
	}
	public void setTransId(String transId)
	{
		TransId = transId;
	}
	public String getChrCode()
	{
		return ChrCode;
	}
	public void setChrCode(String chrCode)
	{
		ChrCode = chrCode;
	}
	public String getMchantUserCode()
	{
		return mchantUserCode;
	}
	public void setMchantUserCode(String mchantUserCode)
	{
		this.mchantUserCode = mchantUserCode;
	}
	private String merSign;
	private String TransId;
	private String ChrCode;
	private String mchantUserCode;
	

}
