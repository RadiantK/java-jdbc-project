package com.computers.dto;

public class CompRequest {

	private int cnum;
	private int cnt;
	private String menas;
	private String address;
	
	public CompRequest(int cnum, int cnt, String menas, String address) {
		this.cnum = cnum;
		this.cnt = cnt;
		this.menas = menas;
		this.address = address;
	}
	
	public int getCnum() {
		return cnum;
	}
	public int getCnt() {
		return cnt;
	}

	public String getMenas() {
		return menas;
	}

	public String getAddress() {
		return address;
	}
	
}
