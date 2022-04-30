package com.computers.entity;

import java.sql.Date;

public class Board {
	private int bnum;
	private String id;
	private String title;
	private Date regDate;
	private String content;
	
	public Board() {}

	public Board(int bnum, String id, String title, Date regDate, String content) {
		this.bnum = bnum;
		this.id = id;
		this.title = title;
		this.regDate = regDate;
		this.content = content;
	}

	public int getBnum() {
		return bnum;
	}

	public void setBnum(int bnum) {
		this.bnum = bnum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
