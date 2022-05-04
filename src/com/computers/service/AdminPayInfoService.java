package com.computers.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.computers.dao.PaymentDao;
import com.computers.dao.ShippingInfoDao;
import com.computers.dto.PayInfoCommand;
import com.computers.dto.ShippingInfoCommand;

public class AdminPayInfoService {

	private ShippingInfoDao shippingInfoDao;
	private PaymentDao paymentDao;
	int page;
	String type;
	String word;
	
	public AdminPayInfoService(
			ShippingInfoDao shippingInfoDao, PaymentDao paymentDao) {
		this.shippingInfoDao = shippingInfoDao;
		this.paymentDao = paymentDao;
		page = 1;
		type = "id";
		word = "";
	}
	
	//모든 회원 정보(권한, 회원탈퇴 여부 포함)
	public void getMemberService() {
		List<PayInfoCommand> list = paymentDao.allList(page, type, word);
		int count =  paymentDao.getCount(type, word);
		int lastPage = count / 10;
		lastPage = count%10==0 ? lastPage : lastPage + 1;
		
		System.out.println("===================================================================================================================================================================================================================================");
		System.out.printf("<< 전체 결제 목록(총원 : %d) >>%n", count);
		System.out.println("===================================================================================================================================================================================================================================");
		for(PayInfoCommand p : list) {
			System.out.printf("결제번호:%d | 회원아이디:%s | 부품이름:%s | 구매수량:%d | "
					+ "결제액:%d | 결제수단:%s | 결제상태:%s | %n배송번호:%d | 구매자명:%s | "
					+ "주소:%s | 배송시작일:%tF | 배송도착일:%tF | 배송상태:%s%n%n",
					p.getPnum(), p.getId(), p.getCname(), p.getCnt(), p.getAmount(),
					p.getMeans(), p.getPstatus(), p.getSnum(), p.getSname(),
					p.getAddress(), p.getStartDate(), p.getEndDate(), p.getSstatus()
					);
		}
		System.out.println("===================================================================================================================================================================================================================================");
		System.out.printf("                                                  %d / %d%n", page, lastPage);
		System.out.println("                      1.이전페이지   2.다음페이지   3.검색하기   4.배송정보 수정  5.돌아가기");
	}
	
	public void prevPage() {
		if(page == 1) {
			System.out.println("[ 이전 페이지가 없습니다. ]");
			return;
		}
		
		page--;
	}

	public void nextPage() {
		int count = paymentDao.getCount(type, word);
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
			System.out.println("검색할 범주를 선택하세요.(id/sstatus)");
			String temp = br.readLine();
			if(!temp.equals("sstatus") && !temp.equals("id")) {
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

	public void editShippingInfo(BufferedReader br) {
		try {
			System.out.println("변경하실 결제 번호를 입력하세요.");
			int num = Integer.parseInt(br.readLine());
			System.out.println("변경하실 배송 상태를 입력하세요.");
			System.out.println("배송상태는 [배송대기/배송중/배송완료/배송취소/배송지연] 사용가능");
			String status = br.readLine();
			System.out.println("변경하실 배송도착일을 입력하세요. [ 2001/01/01 ]형식으로 입력");
			String endDate = br.readLine();
			
			ShippingInfoCommand info = 
					new ShippingInfoCommand(num, endDate, status);
			int n = shippingInfoDao.edit(info);
			if(n < 1) {
				System.out.println("배송정보 수정에 실패했습니다.");
				return;
			}
			
			System.out.println("배송정보가 수정되었습니다.");
		} catch (NumberFormatException | IOException e) {
			System.out.println("잘못된 입력입니다.");
		}
	}
	
	// 검색값 초기화
	public void init() {
		page = 1;
		type = "id";
		word = "";
	}
	
}
