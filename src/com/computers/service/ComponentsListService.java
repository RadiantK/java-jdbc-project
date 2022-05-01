package com.computers.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.computers.dao.ComponentsDao;
import com.computers.entity.Components;

public class ComponentsListService {

	private ComponentsDao componentsDao;
	private int page;
	private String type;
	private String word;
	
	public ComponentsListService(ComponentsDao componentsDao) {
		this.componentsDao = componentsDao;
		page = 1;
		type = "type";
		word = "";
	}
	
	public void getComponentsList() {
		List<Components> list = componentsDao.allList(page, type, word);
		int count = componentsDao.getCount(type, word);
		int lastPage = count / 10;
		lastPage = count%10==0 ? lastPage : lastPage + 1;
		
		System.out.println("====================================================================================================================");
		System.out.printf("<< 전체 부품 목록 (총 게시글 수 : %d) >>%n", count);
		System.out.println("====================================================================================================================");
		for(Components c : list) {
			System.out.printf("부품번호:%d | 부품종류:%s | 부품이름:%s | "
					+ "부품가격:%d | 부품입고일:%tF | 재고:%d%n",
					c.getCnum(), c.getType(), c.getCname(), c.getPrice(),
					c.getRegDate(), c.getCnt());
		}
		System.out.println("====================================================================================================================");
		System.out.printf("                                                  %d / %d%n", page, lastPage);
		System.out.println("                        1.이전페이지   2.다음페이지   3. 검색하기   4.부품추가   5.부품수정   6.돌아가기");
	}

	public void prevPage() {
		if(page == 1) {
			System.out.println("[ 이전 페이지가 없습니다. ]");
			return;
		}
		page--;
	}

	public void nextPage() {
		int count = componentsDao.getCount(type, word);
		int lastPage = count / 10;
		lastPage = count%10==0 ? lastPage : lastPage + 1;
		
		if(page == lastPage) {
			System.out.println("[ 다음페이지가 없습니다. ]");
			return;
		}
		
		page++;
	}
	
	public void search(BufferedReader br) {
		try {
			System.out.println("검색할 범주를 선택하세요.(type(종류)/cname(부품))");
			String temp = br.readLine();
			if(!temp.equals("type") && !temp.equals("cname")) {
				System.out.println("잘못된 입력입니다.");
				type = "type";
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
	
	// 검색값 초기화
	public void init() {
		page = 1;
		type = "type";
		word = "";
	}

	
	/*
	 * 관리자용
	 */
	public void insertComponents(BufferedReader br) {
		try {
			System.out.println("부품종류를 입력하세요.");
			String compType = br.readLine();
			System.out.println("부품명을 입력하세요");
			String compName = br.readLine();
			System.out.println("부품 가격을 입력하세요.");
			int compPrice = Integer.parseInt(br.readLine());
			System.out.println("부품 입고일을 입력하세요[ex) 2020/01/01 ]");
			String regDate = br.readLine();
			System.out.println("부품 재고량을 입력하세요.");
			int compCnt = Integer.parseInt(br.readLine());
			
			Components comp = new Components(0,
					compType, compName, compPrice, null, compCnt);
			
			int n = componentsDao.insert(comp, regDate);
			if(n < 1) {
				System.out.println("등록에 실패했습니다. 중복되는 상품이 있는지 확인하세요.");
				return;
			}
			System.out.println("제품이 등록되었습니다.");
		} catch (NumberFormatException | IOException e) {
			System.out.println("잘못된 양식으로 입력했습니다.");
		}
	}

	public void updateComponents(BufferedReader br) {
		try {
			System.out.println("수정할 부품의 번호를 입력하세요");
			int num = Integer.parseInt(br.readLine());
			System.out.println("부품의 가격을 입력하세요");
			int compPrice = Integer.parseInt(br.readLine());
			System.out.println("부품의 재고량을 입력하세요");
			int compCnt = Integer.parseInt(br.readLine());
			
			Components comp = new Components(
					num, "", "", compPrice, null, compCnt);
			
			int n = componentsDao.editComp(comp);
			if(n < 1) {
				System.out.println("제품 수정에 실패했습니다. 제품번호를 다시 확인하세요.");
				return;
			}
			System.out.println("제품이 수정되었습니다.");
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}

}
