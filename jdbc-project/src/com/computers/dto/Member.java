package com.computers.dto;

import java.sql.Date;

public class Member {
	
	private String id;
	private String pwd;
	private String mname;
	private String email;
	private String phone;
	private Date regDate;
	private int available;
	private String authority;
	
	public Member() {}

	public Member(String id, String pwd, String mname, String email, 
			String phone, Date regDate, int available, String authority) {
		this.id = id;
		this.pwd = pwd;
		this.mname = mname;
		this.email = email;
		this.phone = phone;
		this.regDate = regDate;
		this.available = available;
		this.authority = authority;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public int getAvailable() {
		return available;
	}

	public void setAvailable(int available) {
		this.available = available;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
}
