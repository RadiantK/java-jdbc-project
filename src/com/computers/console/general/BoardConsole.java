package com.computers.console.general;

import java.io.BufferedReader;
import java.io.IOException;

import com.computers.config.Config;
import com.computers.entity.Member;
import com.computers.service.BoardListService;

public class BoardConsole {

	public void boardFunction(BufferedReader br, Member member) {
		Config config = Config.getInstance();
		BoardListService service = config.getBoardListService();
		
		while(true) {
			service.getBoardService();
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
						service.checkDetailBoard(br);
						break;
					case 5:
						service.updateBoard(br, member);
						break;
					case 6:
						service.removeBoard(br, member);
						break;
					case 7:
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
