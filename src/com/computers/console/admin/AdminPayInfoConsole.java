package com.computers.console.admin;

import java.io.BufferedReader;
import java.io.IOException;

import com.computers.config.Config;
import com.computers.service.AdminPayInfoService;

public class AdminPayInfoConsole {

	public void payInfoFunction(BufferedReader br) {
		Config config = Config.getInstance();
		AdminPayInfoService service = config.getAdminPayInfoService();
		
		while(true) {
			service.getMemberService();
			try {
				int menu = Integer.parseInt(br.readLine());
				switch(menu) {
					case 1:
						service.prevPage();
						break;
					case 2:
						service.nextPage();
						break;
					case 3:
						service.search(br);
						break;
					case 4:
						service.editShippingInfo(br);
						break;
					case 5:
						service.init();
						System.out.println("이전으로 돌아갑니다.");
						return;
				}
				
			} catch (NumberFormatException | IOException e) {
				System.out.println("잘못된 형식 입니다.");
			}
		}
	}
}
