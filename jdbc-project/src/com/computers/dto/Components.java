package com.computers.dto;

import java.sql.Date;

public class Components {

	private int cnum;
	private String type;
	private String cname;
	private int price;
	private Date regDate;
	private int cnt;
	
	public Components() {}

	public Components(int cnum, String type, String cname, int price, Date regDate, int cnt) {
		super();
		this.cnum = cnum;
		this.type = type;
		this.cname = cname;
		this.price = price;
		this.regDate = regDate;
		this.cnt = cnt;
	}

	public int getCnum() {
		return cnum;
	}

	public void setCnum(int cnum) {
		this.cnum = cnum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

}
