package com.computers.console.general;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.computers.config.Config;
import com.computers.dto.CompRequest;
import com.computers.entity.Member;
import com.computers.service.ComponentsService;

public class BuyComponentsConsole {

	public void buyComponents(BufferedReader br, Member member) {
		
		while(true) {
			try {
				System.out.println("1.부품구매 2.결재정보 확인 3.배송정보 확인 4.구매취소 5.돌아가기");
				int menu = Integer.parseInt(br.readLine());
				
				switch(menu) {
					case 1:
						buyComp(br, member);
						break;
					case 2:
						confirmPayment(br, member);
						break;
					case 3:
						confirmShippingInfo(br, member);
						break;
					case 4:
						cancelItem(br, member);
						break;
					case 5:
						return;
				}
			}catch (NumberFormatException e) {
				System.out.println("잘못된 형식 입니다.");
			}catch (IOException e) {
				System.out.println("입력에 오류가 있습니다.");
			}
			
		}
	}

	// 부품 구매
	private void buyComp(BufferedReader br, Member member) {
		Config config = Config.getInstance();
		ComponentsService service = config.getBuyComponentsService();
		List<CompRequest> list = new ArrayList<>();
		while(true) {
			try {
				System.out.println("부품을 구입하시겠습니까 ?(y / n)");
				String temp = br.readLine();
				if(temp.equalsIgnoreCase("n")) break;
				if(!temp.equalsIgnoreCase("y")) {
					System.out.println("잘못된 형식 입니다.");
					continue;
				}
				
				System.out.println("구매하실 부품번호를 입력하세요");
				int cnum = Integer.parseInt(br.readLine());
				System.out.println("구매하실 부품의 수량을 입력하세요");
				int cnt = Integer.parseInt(br.readLine());
				System.out.println("배송받을 주소를 입력해주세요.");
				String address = br.readLine();
				System.out.println("결재수단을 선택해주세요.");
				String means = br.readLine();
				list.add(new CompRequest(cnum, cnt, address, means));
				
			}catch (NumberFormatException e) {
				System.out.println("잘못된 형식 입니다.");
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		int result = service.buyComponents(member, list);
		if(result < 1) {
			System.out.println("제품 구매에 실패했습니다.");
			return;
		}
		System.out.println("부품을 구입했습니다.");
	}
	
	// 결재정보 확인
	private void confirmPayment(BufferedReader br, Member member) {
		Config config = Config.getInstance();
		ComponentsService service = config.getBuyComponentsService();
		
		try {
			System.out.println("확인할 범위를 개월수로 입력하세요.(1개월 = 1)");
			int month = Integer.parseInt(br.readLine());
			service.PaymentList(member, month);
		} catch (NumberFormatException | IOException e) {
			System.out.println("잘못된 입력입니다.");
		}
	}
	
	// 배송정보 확인
	private void confirmShippingInfo(BufferedReader br, Member member) {
		Config config = Config.getInstance();
		ComponentsService service = config.getBuyComponentsService();
		
		try {
			System.out.println("확인할 범위를 개월수로 입력하세요.(1개월 = 1)");
			int month = Integer.parseInt(br.readLine());
			service.ShippingInfoList(member, month);
		} catch (NumberFormatException | IOException e) {
			System.out.println("잘못된 입력입니다.");
		}
	}
	
	private void cancelItem(BufferedReader br, Member member) {
		Config config = Config.getInstance();
		ComponentsService service = config.getBuyComponentsService();
		
		try {
			System.out.println("결제를 취소할 목록의 결제번호를 입력하세요.");
			int pnum = Integer.parseInt(br.readLine());
			
			boolean n = service.cancelComponents(pnum);
			if(n) System.out.println("결제를 취소했습니다.");
		} catch (NumberFormatException | IOException e) {
			System.out.println("잘못된 형식의 입력입니다.");
		}
	}
}
