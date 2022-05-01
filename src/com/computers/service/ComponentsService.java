package com.computers.service;

import java.sql.Connection;
import java.util.List;

import com.computers.dao.ComponentsDao;
import com.computers.dao.PaymentDao;
import com.computers.dao.ShippingInfoDao;
import com.computers.dto.CompRequest;
import com.computers.dto.PaymentRequest;
import com.computers.dto.ShippingInfoRequest;
import com.computers.entity.Components;
import com.computers.entity.Member;
import com.computers.entity.Payment;
import com.computers.entity.ShippingInfo;
import com.computers.util.DataUtil;

public class ComponentsService {

	private ComponentsDao componentsDao;
	private ShippingInfoDao shippingInfoDao;
	private PaymentDao paymentDao;
	
	public ComponentsService(ComponentsDao componentsDao, 
			ShippingInfoDao shippingInfoDao, PaymentDao paymentDao) {
		this.componentsDao = componentsDao;
		this.shippingInfoDao = shippingInfoDao;
		this.paymentDao = paymentDao;
	}
	
	// 부품 구매
	public int buyComponents(Member member, List<CompRequest> comps) {
		Connection con = DataUtil.getConnection();
		for(CompRequest c : comps) {
			// 부품번호에 해당하는 부품 찾기
			Components comp = componentsDao.findList(con, c.getCnum()); 
			if(comp == null) {
				System.out.println("부품 번호에 해당하는 부품이 존재하지 않습니다.");
				DataUtil.rollback(con);
				return -1;
			}
			
			int count = componentsDao.check(con, c.getCnum()); // 재고 파악
			if(c.getCnt() > count) {
				System.out.println("재품의 재고보다 많은 수량을 구매할 수 없습니다.");
				DataUtil.rollback(con);
				return -1;
			}
			
			// 결재정보 삽입
			PaymentRequest pay = new PaymentRequest(
					member.getId(), comp.getCname(), c.getCnt(),
					comp.getPrice(), c.getMeans());
			int n = paymentDao.insert(con, pay);
			if(n < 1) {
				System.out.println("결제정보를 등록하는 과정에서 오류가 발생했습니다.");
				DataUtil.rollback(con);
				return -1;
			}
			
			// 배송정보 삽입
			ShippingInfoRequest info = new ShippingInfoRequest(
				member.getId(), comp.getCname(), member.getMname(), c.getAddress());
			n = shippingInfoDao.insert(con, info);
			if(n < 1) {
				System.out.println("배송정보를 등록하는 과정에서 오류가 발생했습니다.");
				DataUtil.rollback(con);
				return -1;
			}
			
			// 부품 재고 감소
			n = componentsDao.minusComponents(con, c.getCnum(), c.getCnt());
			if(n < 1) {
				System.out.println("부품의 재고를 감소시키는 과정에서 오류가 발생했습니다.");
				DataUtil.rollback(con);
				return -1;
			}
		}
		
		DataUtil.commit(con);
		DataUtil.close(con);
		return 1;
	}
	
	// 결재목록
	public void PaymentList(Member member, int month){
		List<Payment> list = paymentDao.memberList(member.getId(), month);
		
		for(Payment p : list) {
			System.out.printf("결제번호:%d | 부품이름:%s | 수량:%d | 결제금액: %d | "
					+ "결제수단:%s | 결제일:%tF | 결제상태:%s%n",
					p.getPnum(), p.getCname(), p.getCnt(), p.getAmount(),
					p.getMeans(), p.getRegDate(), p.getStatus());
		}
	}
	
	// 배송정보
	public void ShippingInfoList(Member member, int month) {
		List<ShippingInfo> list = 
				shippingInfoDao.memberList(member.getId(), month);
		
		for(ShippingInfo s : list) {
			System.out.printf("배송번호:%d | 부품이름:%s | 결재번호:%d | 수신자이름:%s | "
					+ "주소:%s | 배송시작일:%tF | 배송도착일:%tF | 배송상태:%s%n",
					s.getSnum(), s.getCname(), s.getPnum(), s.getSname(),
					s.getAddress(), s.getStartDate(), s.getEndDate(), s.getStatus());
		}
	}
	
	// 결제취소 및 배송취소
	public boolean cancelComponents(int pnum) {
		Connection con = DataUtil.getConnection();
		boolean check = shippingInfoDao.checkInfo(con, pnum);
		if(check) {
			System.out.println("배송중이거나 배송완료된 상품은 결제를 취소할 수 없습니다.");
			DataUtil.rollback(con);
			return false;
		}
		
		int n = shippingInfoDao.cancel(con, pnum);
		if(n < 1) {
			System.out.println("배송취소에 실패했습니다.");
			DataUtil.rollback(con);
			return false;
		}
		
		n = paymentDao.cancel(con, pnum);
		if(n < 1) {
			System.out.println("결제취소에 실패했습니다.");
			DataUtil.rollback(con);
			return false;
		}
		
		DataUtil.commit(con);
		DataUtil.close(con);
		return true;
	}
}
