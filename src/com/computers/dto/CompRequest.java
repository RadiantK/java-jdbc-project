package com.computers.dto;

// 부품 구매요청을 위한 클래스
public class CompRequest {

	private int cnum;
	private int cnt;
	private String means;
	private String address;
	
	public CompRequest(int cnum, int cnt, String means, String address) {
		this.cnum = cnum;
		this.cnt = cnt;
		this.means = means;
		this.address = address;
	}
	
	public int getCnum() {
		return cnum;
	}
	public int getCnt() {
		return cnt;
	}

	public String getMeans() {
		return means;
	}

	public String getAddress() {
		return address;
	}
	
}
