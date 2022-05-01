package com.computers.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.computers.dao.BoardDao;
import com.computers.dto.BoardCommand;
import com.computers.entity.Board;
import com.computers.entity.Member;

public class BoardListService {

	private BoardDao boardDao;
	private int page;
	private String type;
	private String word;
	
	public BoardListService(BoardDao boardDao) {
		this.boardDao = boardDao;
		page = 1;
		type = "TITLE";
		word = "";
	}
	
	public void getBoardService() {
		List<Board> list = boardDao.boardList(page, type, word);
		int count = boardDao.getCount(type, word);
		int lastPage = count / 10;
		lastPage = count%10==0 ? lastPage : lastPage + 1;
		
		System.out.println("====================================================================================================================");
		System.out.printf("<< 전체 게시글 목록 (총 게시글 수 : %d) >>%n", count);
		System.out.println("====================================================================================================================");
		for(Board b : list) {
			System.out.printf("게시물번호:%d | 작성자ID:%s | 제목:%s | 등록일:%tF%n",
					b.getBnum(), b.getId(), b.getTitle(), b.getRegDate());
		}
		System.out.println("====================================================================================================================");
		System.out.printf("                                                  %d / %d%n", page, lastPage);
		System.out.println("              1.이전페이지   2.다음페이지   3.검색하기   4.상세보기   5.게시물 수정   6.게시물 삭제   7.돌아가기");
	}

	public void prevPage() {
		if(page == 1) {
			System.out.println("[ 이전 페이지가 없습니다. ]");
			return;
		}
		
		page--;
	}

	public void nextPage() {
		int count = boardDao.getCount(type, word);
		int lastPage = count / 10;
		lastPage = count%10==0 ? lastPage : lastPage + 1;
		
		if(page == lastPage) {
			System.out.println("[ 다음 페이지가 없습니다. ]");
			return;
		}
		
		page++;
	}

	public void search(BufferedReader br) {
		try {
			System.out.println("검색할 범주를 선택하세요.(id/title)");
			String temp = br.readLine();
			if(!temp.equals("title") && !temp.equals("id")) {
				System.out.println("잘못된 입력입니다.");
				type = "title";
				word = "";
				return;
			}
			
			type = temp;
			System.out.println("검색 내용을 입력하세요.");
			word = br.readLine();
		} catch (NumberFormatException | IOException e) {
			System.out.println("잘못된 양식으로 입력했습니다.");
		}
	}
	
	public void checkDetailBoard(BufferedReader br) {
		try {
			System.out.println("게시물 번호를 입력하세요.");
			int num = Integer.parseInt(br.readLine());

			Board b = boardDao.detailBoard(num);
			if(b == null) {
				System.out.println("해당 번호의 게시물은 존재하지 않습니다.");
				return;
			}
			
			System.out.println("====================================================================================================================");
			System.out.printf("게시물번호:%d | 작성자ID:%s | 제목:%s | 등록일:%tF%n 내용:%s%n",
				b.getBnum(), b.getId(), b.getTitle(), b.getRegDate(), b.getContent());
			System.out.println("====================================================================================================================");
			System.out.println("돌아가려면 아무키나 입력하세요.");
			br.readLine();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	public void updateBoard(BufferedReader br, Member member) {
		try {
			System.out.println("수정하실 게시물 번호를 입력하세요.");
			int num = Integer.parseInt(br.readLine());

			Board b = boardDao.detailBoard(num);
			if(b == null) {
				System.out.println("해당 번호의 게시물은 존재하지 않습니다.");
				return;
			}
			if(!b.getId().equals(member.getId())) {
				System.out.println("자신의 게시물이 아닙니다.");
				return;
			}
			
			System.out.println("수정하실 게시물의 제목을 입력하세요.(엔터를 누르면 넘어감)");
			String reqTitle = br.readLine();
			if(reqTitle.equals("")) reqTitle = b.getTitle();
			
			System.out.println("수정하실 게시물의 내용을 입력하세요.");
			String content = br.readLine();
			BoardCommand reqBoard = new BoardCommand(num, reqTitle, content);
			
			int n = boardDao.editBoard(reqBoard);
			if(n < 1) {
				System.out.println("[ 수정실패 ]");
				return;
			}
			System.out.println("[ 게시글이 수정되었습니다. ]");
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	public void removeBoard(BufferedReader br, Member member) {
		try {
			System.out.println("제거하실 게시물 번호를 입력하세요.");
			int num = Integer.parseInt(br.readLine());

			Board b = boardDao.detailBoard(num);
			if(b == null) {
				System.out.println("해당 번호의 게시물은 존재하지 않습니다.");
				return;
			}
			if(!b.getId().equals(member.getId())) {
				System.out.println("자신의 게시물이 아닙니다.");
				return;
			}
			
			int n = boardDao.removeNum(num);
			if(n < 1) {
				System.out.println("[ 제거실패 ]");
				return;
			}
			System.out.println("[ 게시글이 제거되었습니다. ]");
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void adminRemoveBoard(BufferedReader br) {
		try {
			System.out.println("제거하실 게시물 번호를 입력하세요.");
			int num = Integer.parseInt(br.readLine());

			int n = boardDao.removeNum(num);
			if(n < 1) {
				System.out.println("[ 제거실패 ]");
				return;
			}
			System.out.println("[ 게시글이 제거되었습니다. ]");
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	// 검색값 초기화
	public void init() {
		page = 1;
		type = "TITLE";
		word = "";
	}

}
