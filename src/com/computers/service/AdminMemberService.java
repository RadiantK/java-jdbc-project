package com.computers.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.computers.dao.MemberDao;
import com.computers.entity.Member;

/*
 * 관리자용 서비스 
 */
public class AdminMemberService {
	
	private MemberDao memberDao;
	private int page;
	private String type;
	private String word;
	
	public AdminMemberService(MemberDao memberDao) {
		this.memberDao = memberDao;
		page = 1;
		type = "id";
		word = "";
	}
	
	//모든 회원 정보(권한, 회원탈퇴 여부 포함)
	public void getMemberService() {
		List<Member> list = memberDao.memberList(page, type, word);
		int count =  memberDao.getCount(type, word);
		int lastPage = count / 10;
		lastPage = count%10==0 ? lastPage : lastPage + 1;
		
		System.out.println("====================================================================================================================");
		System.out.printf("<< 전체 회원 목록 (총원 : %d) >>%n", count);
		System.out.println("====================================================================================================================");
		for(Member m : list) {
			System.out.printf("회원아이디:%s | 비밀번호:%s | 이름:%s | 이메일:%s | "
					+ "전화번호:%s | 가입일:%tF | 회원탈퇴여부:%d | 권한:%s%n",
					m.getId(), m.getPwd(), m.getMname(), m.getEmail(),
					m.getPhone(), m.getRegDate(), m.getAvailable(), m.getAuthority());
		}
		System.out.println("====================================================================================================================");
		System.out.printf("                                                                %d / %d%n", page, lastPage);
		System.out.println("                               1.이전페이지   2.다음페이지   3.검색하기   4.회원제거   5.돌아가기");
	}
	
	public void prevPage() {
		if(page == 1) {
			System.out.println("[ 이전 페이지가 없습니다. ]");
			return;
		}
		
		page--;
	}

	public void nextPage() {
		int count = memberDao.getCount(type, word);
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
			System.out.println("검색할 범주를 선택하세요.(id/mname/available)");
			String temp = br.readLine();
			if(!temp.equals("mname") && !temp.equals("id") && !temp.equals("available")) {
				System.out.println("잘못된 입력입니다.");
				type = "id";
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
	
	public void removeMember(BufferedReader br) {
		try {
			System.out.println("제거하실 회원 아이디를 입력하세요.");
			String id = br.readLine();
			
			int n = memberDao.remove(id);
			if(n < 1) {
				System.out.println("회원 제거에 실패했습니다.");
				return;
			}
			System.out.println("회원이 제거되었습니다.");
		} catch (IOException e) {
			System.out.println("잘못된 양식입니다.");
		}
	}
	
	// 검색값 초기화
	public void init() {
		page = 1;
		type = "id";
		word = "";
	}
}
