package com.computers.dto;

// 게시판 수정을 위한 클래스
public class BoardCommand {
	
	private int bnum;
	private String id;
	private String title;
	private String content;
	
	
	public BoardCommand(int bnum, String id, String title, String content) {
		super();
		this.bnum = bnum;
		this.id = id;
		this.title = title;
		this.content = content;
	}

	public int getBnum() {
		return bnum;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

}
