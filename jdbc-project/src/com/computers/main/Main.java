package com.computers.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.computers.config.Config;
import com.computers.console.JoinConsole;
import com.computers.console.LoginConsole;

public class Main {
	
	public static BufferedReader br =
			new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) {
		Config config = Config.getInstance();
		LoginConsole loginConsole = config.getLoginColsole();
		JoinConsole joinConsole = config.getJoinConsole();
		try {
			while(true) {
				switch(mainConsole()) {
					case 1:
						loginConsole.login(br);
						break;
					case 2:
						joinConsole.join(br);
						break;
					case 3:
						System.out.println("페이지를 닫습니다.");
						br.close();
						System.exit(0);
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static int mainConsole() {
		System.out.println("===================================");
		System.out.println("컴퓨터 부품 구매 사이트 컴퓨터스입니다.");
		System.out.println("모든 메뉴는 로그인 후 이용가능합니다.");
		System.out.println("메뉴를 선택해주세요.");
		System.out.println("1.로그인   2.회원가입   3.사이트 나가기");
		System.out.println("===================================");
		
		int menu = 0;
		while(true) {
			try {	
				menu = Integer.parseInt(br.readLine());
				if(menu >= 1 && menu <= 3) break;

			}catch (NumberFormatException e) {
				System.out.println("잘못된 입력입니다 다시 입력하세요.");
			}catch (IOException e) {
				System.out.println("잘못된 입력입니다 다시 입력하세요.");
			}
		}
		return menu;
	}
}
