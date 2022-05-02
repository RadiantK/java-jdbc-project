package com.computers.dto;

// 관리자용 배송 정보를 수정할 때 사용할 클래스
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
