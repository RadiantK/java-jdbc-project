package com.computers.console.admin;

import java.io.BufferedReader;
import java.io.IOException;

import com.computers.config.ConsoleConfig;
import com.computers.entity.Member;

public class AdminConsole {

	public void adminFunction(BufferedReader br, Member member) {
		ConsoleConfig config = ConsoleConfig.getInstance();
		AdminMemberListConsole memberListConsole = config.getMemberListConsole();
		AdminPayInfoConsole payConsole = config.getPayInfoConsole();
		AdminComponentsConsole compConsole = config.getComponentsConsole();
		AdminBoardConsole boardConsole = config.getAdminBoardConsole();
		
		adminMenu();
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
						memberListConsole.memberFunction(br);
						break;
					case 2:
						payConsole.payInfoFunction(br);
						break;
					case 3:
						compConsole.ComponentsFunction(br);
						break;
					case 4:
						boardConsole.boardFunction(br, member);
						break;
					case 5:
						return;
				}
				
			} catch (NumberFormatException | IOException e) {
				System.out.println("잘못된 형식 입니다.");
			}
			
		}
	}
	
	private void adminMenu() {
		System.out.println("*************************************************");
		System.out.println("[ 관리자 계정으로 로그인 되었습니다. ]");
		System.out.println("1.회원정보 확인    2.회원 결제,배송정보 확인");
		System.out.println("3.부품관리        4.건의사항 게시판 확인");
		System.out.println("5.로그아웃");
		System.out.println("*************************************************");
	}
	
	private void help() {
		System.out.println("1.회원정보 확인    2.회원 결제,배송정보 확인");
		System.out.println("3.부품관리        4.건의사항 게시판 확인");
		System.out.println("5.로그아웃");
	}
}
