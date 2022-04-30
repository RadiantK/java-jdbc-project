package com.computers.console.general;

import java.io.BufferedReader;
import java.io.IOException;

import com.computers.config.Config;
import com.computers.entity.Member;
import com.computers.exception.RemoveDataException;
import com.computers.exception.WrongIdPasswordException;
import com.computers.service.MemberService;

public class GeneralConsole {

	public void generalFunction(BufferedReader br, Member member) {
		Config config = Config.getInstance();
		ComponentsConsole compConsole = config.getComponentsConsole();
		BuyComponentsConsole buyConsole = config.getBuyComponentsConsole();
		
		generalMenu();
		while(true) {
			try {
				System.out.println("원하시는 메뉴를 선택해주세요.(도움말:help)");
				String temp = br.readLine();
				if(temp.equalsIgnoreCase("help")) {
					help();
					continue;
				}
				int menu = Integer.parseInt(temp);
				
				switch(menu) {
					case 1:
						memberInfo(br, member);
						break;
					case 2:
						ChangePwd(br, member);
						break;
					case 3:
						int n = withdrawMember(br, member);
						if(n > 0) return;
						break;
					case 4:
						compConsole.ComponentsList(br);
						break;
					case 5:
						buyConsole.buyComponents(br, member);
						break;
					case 8:
						break;
				}
			}catch (NumberFormatException e) {
				System.out.println("문자를 입력하실 수 없습니다.");
			}catch (IOException e) {
				System.out.println("잘못된 입력입니다.");
			}
		}
	}
	
	private void generalMenu() {
		System.out.println("==============================================");
		System.out.println("원하시는 메뉴를 선택해주세요.");
		System.out.println("회원님 환영합니다. 컴퓨터 부품 판매사이트 컴퓨터스입니다.");
		System.out.println("1.내 정보확인   2.비밀번호 변경   3. 회원 탈퇴");
		System.out.println("4.부품목록   5.부품 구매 및 결재,배송 정보 확인");
		System.out.println("6.게시판 확인   7.게시글 올리기   8.로그아웃");
		System.out.println("==============================================");
	}
	
	private void help() {
		System.out.println("1.내 정보확인   2.비밀번호 변경   3. 회원 탈퇴");
		System.out.println("4.부품목록   5.부품 구매 및 결재,배송 정보 확인");
		System.out.println("6.게시판 확인   7.게시글 올리기   8.로그아웃");
	}
	
	// 회원 정보
	private void memberInfo(BufferedReader br, Member member) {
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
	private void ChangePwd(BufferedReader br, Member member) {
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
	private int withdrawMember(BufferedReader br, Member member) {
		Config config = Config.getInstance();
		MemberService service = config.getMemberService();
		try {
			System.out.println("비밀번호를 입력하세요");
			String pwd = br.readLine();
			service.memberConfirmPassword(member, pwd);
			
			System.out.println("탈퇴 후 복구는 불가능 합니다. 회원탈퇴 하시겠습니까?(y/n선택)");
			String choice = br.readLine();
			if(choice.equalsIgnoreCase("y")) {
				int n = service.withdraw(member);
				if(n > 0) {
					System.out.println("회원탈퇴 되었습니다.");
					return n;
				}
				System.out.println("회원탈퇴에 실패했습니다.");
				return 0;
			}
			System.out.println("회원탈퇴를 취소하셨습니다.");
		}catch(WrongIdPasswordException e) {
			System.out.println("비밀번호가 일치하지 않습니다.");
		}catch (RemoveDataException e) {
			System.out.println("데이터 제거중 오류가 발생했습니다."+e.getMessage());
		}catch (IOException e) {
			System.out.println("잘못된 입력입니다.");
		}
		return 0;
	}
}
