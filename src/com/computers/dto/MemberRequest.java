package com.computers.dto;

public class MemberRequest {
	
	private String id;
	private String pwd;
	private String mname;
	private String email;
	private String phone;
	
	public MemberRequest(String id, String pwd, String mname, String email, String phone) {
		this.id = id;
		this.pwd = pwd;
		this.mname = mname;
		this.email = email;
		this.phone = phone;
	}

	public String getId() {
		return id;
	}

	public String getPwd() {
		return pwd;
	}

	public String getMname() {
		return mname;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}
	
}
