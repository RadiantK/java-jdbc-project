package com.computers.dto;

public class Payment {
	
	private int pnum;
	private String id;
	private String cname;
	private int cnt;
	private int amount;
	private String means;
	private String status;
	
	public Payment() {}

	public Payment(int pnum, String id, String cname, int cnt,
			int amount, String means,String status) {
		this.pnum = pnum;
		this.id = id;
		this.cname = cname;
		this.cnt = cnt;
		this.amount = amount;
		this.means = means;
		this.status = status;
	}

	public int getPnum() {
		return pnum;
	}

	public void setPnum(int pnum) {
		this.pnum = pnum;
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

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getMeans() {
		return means;
	}

	public void setMeans(String means) {
		this.means = means;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
