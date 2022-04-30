package com.computers.entity;

import java.sql.Date;

public class ShippingInfo {

	private int snum;
	private String id;
	private String cname;
	private int pnum;
	private String sname;
	private String address;
	private Date startDate;
	private Date endDate;
	private String status;
	
	public ShippingInfo() {}

	public ShippingInfo(int snum, String id, String cname, int pnum, String sname,
			String address, Date startDate, Date endDate, String status) {
		this.snum = snum;
		this.id = id;
		this.cname = cname;
		this.pnum = pnum;
		this.sname = sname;
		this.address = address;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
	}

	public int getSnum() {
		return snum;
	}

	public void setSnum(int snum) {
		this.snum = snum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}
	
	public int getPnum() {
		return pnum;
	}

	public void setPnum(int pnum) {
		this.pnum = pnum;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
