package com.computers.dto;

public class ShippingInfoCommand {

	private int pnum;
	private String endDate;
	private String status;
	
	public ShippingInfoCommand(int pnum, String endDate, String status) {
		this.pnum = pnum;
		this.endDate = endDate;
		this.status = status;
	}

	public int getPnum() {
		return pnum;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getStatus() {
		return status;
	}
	
}
