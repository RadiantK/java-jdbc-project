package com.computers.dto;

public class ShippingInfoRequest {

	private String id;
	private String cname;
	private String sname;
	private String address;
	
	public ShippingInfoRequest(String id, String cname, String sname, String address) {
		this.id = id;
		this.cname = cname;
		this.sname = sname;
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public String getCname() {
		return cname;
	}

	public String getSname() {
		return sname;
	}

	public String getAddress() {
		return address;
	}
	
}
