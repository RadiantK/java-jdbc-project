package com.computers.dto;

import java.sql.Date;

// 관리자용 결재정보와 배송정보를 조인할 때 사용할 클래스
public class PayInfoCommand {
	
	private int pnum;
	private String id;
	private String cname;
	private int cnt;
	private int amount;
	private String means;
	private String pstatus;
	private int snum;
	private String sname;
	private String address;
	private Date startDate;
	private Date endDate;
	private String sstatus;
	
	public PayInfoCommand(int pnum, String id, String cname, int cnt,
			int amount, String means, String pstatus, int snum, String sname,
			String address, Date startDate, Date endDate, String sstatus) {
		this.pnum = pnum;
		this.id = id;
		this.cname = cname;
		this.cnt = cnt;
		this.amount = amount;
		this.means = means;
		this.pstatus = pstatus;
		this.snum = snum;
		this.sname = sname;
		this.address = address;
		this.startDate = startDate;
		this.endDate = endDate;
		this.sstatus = sstatus;
	}

	public int getPnum() {
		return pnum;
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

	public int getAmount() {
		return amount;
	}

	public String getMeans() {
		return means;
	}

	public String getPstatus() {
		return pstatus;
	}

	public int getSnum() {
		return snum;
	}

	public String getSname() {
		return sname;
	}

	public String getAddress() {
		return address;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getSstatus() {
		return sstatus;
	}
	
}
