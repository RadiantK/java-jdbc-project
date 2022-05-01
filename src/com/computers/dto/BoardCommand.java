package com.computers.dto;

public class BoardCommand {
	
	private int bnum;
	private String title;
	private String content;
	
	public BoardCommand(int bnum, String title, String content) {
		this.bnum = bnum;
		this.title = title;
		this.content = content;
	}

	public int getBnum() {
		return bnum;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

}
