package com.computers.console;

import java.io.BufferedReader;
import java.io.IOException;

import com.computers.config.Config;
import com.computers.console.admin.AdminConsole;
import com.computers.console.general.GeneralConsole;
import com.computers.dto.Member;
import com.computers.exception.MemberNotFoundException;
import com.computers.exception.WrongIdPasswordException;
import com.computers.service.LoginService;

public class LoginConsole {

	public void login(BufferedReader br) throws IOException {
		Config config = Config.getInstance();
		LoginService service = config.getLoginService();
		AdminConsole adminConsole = config.getAdminConsole();
		GeneralConsole generalConsole = config.getGeneralConsole();
		loginMenu();
		
		while(true) {
			try {
				String temp = br.readLine();
				if(temp.equalsIgnoreCase("exit")) break;
				
				String[] info = temp.split(" ");
				if(info.length != 2) {
					System.out.println("잘못된 양식의 입력입니다.");
					continue;
				}
				
				Member member = service.userLogin(info[0], info[1]);
				
				switch(member.getAuthority()) {
					case "admin":
						System.out.println("관리자계정으로 로그인합니다.");
						adminConsole.adminFunction(br, member);
						return;
					case"general":
						System.out.println("일반회원 입니다.");
						generalConsole.generalFunction(br, member);
						return;
				}
			}catch(MemberNotFoundException e) {
				System.out.println("아이디가 존재하지 않습니다.");
				System.out.println("아이디와 비밀번호를 입력하세요.(공백으로 구분) ");
			}catch(WrongIdPasswordException e) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				System.out.println("아이디와 비밀번호를 입력하세요.(공백으로 구분) ");
			}
		}
		
	}
	
	public void loginMenu() {
		System.out.println("====================================================");
		System.out.println("로그인 페이지 입니다.");
		System.out.println("아이디와 비밀번호를 입력하세요.(공백으로 구분) ");
		System.out.println("exit를 입력 시 페이지에서 벗어납니다.");
		System.out.println("====================================================");
	}
}
