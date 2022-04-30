package com.computers.dto;

public class PaymentRequest {
	
	private String id;
	private String cname;
	private int cnt;
	private int price;
	private String means;
	
	public PaymentRequest(
			String id, String cname, int cnt, int price, String means) {
		this.id = id;
		this.cname = cname;
		this.cnt = cnt;
		this.price = price;
		this.means = means;
	}

	public String getId() {
		return id;
	}

	public String getCname() {
		return cname;
	}

	public int getCnt() {
		return cnt;
	}

	public int getPrice() {
		return price;
	}

	public String getMeans() {
		return means;
	}
	
}
