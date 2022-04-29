package com.computers.console.general;

import java.io.BufferedReader;
import java.io.IOException;

import com.computers.config.Config;
import com.computers.dto.Member;
import com.computers.exception.RemoveDataException;
import com.computers.exception.WrongIdPasswordException;
import com.computers.service.MemberService;

public class GeneralConsole {

	public void generalFunction(BufferedReader br, Member member) {
		generalMenu();
		while(true) {
			try {
				System.out.println("원하시는 메뉴를 선택해주세요.");
				int menu = Integer.parseInt(br.readLine());
				
				switch(menu) {
					case 1:
						memberInfo(br, member);
						break;
					case 2:
						ChangePwd(br, member);
						break;
					case 3:
						withdrawMember(br, member);
						break;
					case 11:
						break;
					default :
						help();
						break;
				}
			}catch (NumberFormatException e) {
				System.out.println("문자를 입력하실 수 없습니다.");
			}catch (IOException e) {
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
	public void generalMenu() {
		System.out.println("==============================================");
		System.out.println("원하시는 메뉴를 선택해주세요.");
		System.out.println("회원님 환영합니다. 컴퓨터 부품 판매사이트 컴퓨터스입니다.");
		System.out.println("1.내 정보확인   2.비밀번호 변경   3. 회원 탈퇴");
		System.out.println("4.부품목록   5.부품 구매");
		System.out.println("6.결재정보 확인   7.배송정보 확인   8.구매취소");
		System.out.println("9.게시판 확인   10.게시글 올리기   11.로그아웃");
		System.out.println("==============================================");
	}
	
	public void help() {
		System.out.println("메뉴입력이 잘못됐습니다.");
		System.out.println("1.내 정보확인   2.비밀번호 변경   3. 회원 탈퇴");
		System.out.println("4.부품목록   5.부품 구매");
		System.out.println("6.결재정보 확인   7.배송정보 확인   8.구매취소");
		System.out.println("9.게시판 확인   10.게시글 올리기   11.로그아웃");
	}
	
	// 회원 정보
	public void memberInfo(BufferedReader br, Member member) {
		Config config = Config.getInstance();
		MemberService service = config.getMemberService();
		
		try {
			System.out.println("비밀번호를 입력하세요.");
			String pwd = br.readLine();
			service.memberInfo(member, pwd);
		}catch (WrongIdPasswordException e) {
			System.out.println("비밀번호를 잘못 입력하셨습니다.");
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 비밀번호 변경
	public void ChangePwd(BufferedReader br, Member member) {
		Config config = Config.getInstance();
		MemberService service = config.getMemberService();
		try {
			System.out.println("비밀번호를 입력하세요.");
			String pwd = br.readLine();
			service.memberConfirmPassword(member, pwd);
			
			System.out.print("새비밀번호 입력 > ");
			String newPwd = br.readLine();
			System.out.print("비밀번호 확인 > ");
			String newConfirmPwd = br.readLine();		
			service.memberChangePassword(member, newPwd, newConfirmPwd);
			
		}catch (WrongIdPasswordException e) {
			System.out.println("비밀번호가 일치하지 않습니다.");
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	// 회원 탈퇴
	public void withdrawMember(BufferedReader br, Member member) {
		Config config = Config.getInstance();
		MemberService service = config.getMemberService();
		try {
			System.out.println("비밀번호를 입력하세요");
			String pwd = br.readLine();
			service.memberConfirmPassword(member, pwd);
			
			System.out.println("회원탈퇴 하시겠습니까?(y/n선택)");
			String choice = br.readLine();
			if(choice.equalsIgnoreCase("y")) {
				int n = service.withdraw(member);
				if(n > 0) {
					System.out.println("회원탈퇴 되었습니다.");
					return;
				}
				System.out.println("회원탈퇴에 실패했습니다.");
				return;
			}
			System.out.println("회원탈퇴를 취소하셨습니다.");
		}catch(WrongIdPasswordException e) {
			System.out.println("비밀번호가 일치하지 않습니다.");
		}catch (RemoveDataException e) {
			System.out.println("데이터 제거중 오류가 발생했습니다."+e.getMessage());
		}catch (IOException e) {
			System.out.println("잘못된 입력입니다.");
		}
	}
}
