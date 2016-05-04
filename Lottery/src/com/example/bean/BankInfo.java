package com.example.bean;

public class BankInfo {
	//{"id":"1","user_id":"21","card_number":"*************4567",
	//"type":"¥¢–Óø®","name":"’‘≥…À≥"}]}
	private String id;
	private String user_id;
	private String card_number;
	private String type;
	private String name;
	public BankInfo(String id, String user_id, String card_number, String type,
			String name) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.card_number = card_number;
		this.type = type;
		this.name = name;
	}
	public BankInfo() {
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getCard_number() {
		return card_number;
	}
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
