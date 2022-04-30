package com.computers.console;

import java.io.BufferedReader;
import java.io.IOException;

import com.computers.config.Config;
import com.computers.dto.MemberRequest;
import com.computers.exception.DuplicateMemberException;
import com.computers.service.JoinService;

public class JoinConsole {

	
	public void join(BufferedReader br) {
		Config config = Config.getInstance();
		joinMenu();
		try {
			String[] info = joinInput(br);
			if(info[0].equalsIgnoreCase("exit")) return;
			
			MemberRequest member = new MemberRequest(
					info[0], info[1], info[3], info[4], info[5]);
			
			JoinService service = config.getJoinService(); // 회원가입 서비스
			int n = service.memberJoin(member);
			if(n < 1) {
				System.out.println("회원가입 실패");
				return;
			}
			System.out.println("회원가입 완료");
		}catch (DuplicateMemberException e) {
			System.out.println("아이디가 이미 존재합니다.");
			System.out.println("회원정보를 다시 입력해주세요.");
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void joinMenu() {
		System.out.println("===============================================================");
		System.out.println("회원가입을 위한 페이지 입니다.");
		System.out.println("아이디 비밀번호 비밀번호확인 이름 이메일 전화번호를 공백을 구분으로 입력해주세요.");
		System.out.println("메인페이지로 돌아가시려면 exit를 입력해주세요.");
		System.out.println("===============================================================");
	}
	
	private String[] joinInput(BufferedReader br) throws IOException {
		while(true) {
			String temp = br.readLine().trim();
			if(temp.equalsIgnoreCase("exit")) return new String[] {"exit"};
			
			String[] info = temp.split(" ");
			if(info.length != 6) {
				System.out.println("입력이 잘못되었습니다 다시 입력해주세요.");
				continue;
			}
			if(!isEqualsConfirmPassword(info[1], info[2])) { // 비밀번호 확인
				System.out.println("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
				continue;
			}
			return info;
		}
	}
	
	private boolean isEqualsConfirmPassword(String pwd, String confirmPwd) {
		return pwd.equals(confirmPwd);
	}
}
