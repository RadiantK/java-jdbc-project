package com.computers.service;

import java.sql.Connection;

import com.computers.dao.BoardDao;
import com.computers.dao.MemberDao;
import com.computers.dao.PaymentDao;
import com.computers.dao.ShippingInfoDao;
import com.computers.entity.Member;
import com.computers.exception.RemoveDataException;
import com.computers.exception.WrongIdPasswordException;
import com.computers.util.DataUtil;

/*
 * 회원 전용 서비스
 */
public class MemberService {

	private MemberDao memberDao;
	private ShippingInfoDao shippingInfoDao;
	private PaymentDao paymentDao;
	private BoardDao boardDao;
	
	public MemberService(MemberDao memberDao, ShippingInfoDao shippingInfoDao,
			PaymentDao paymentDao, BoardDao boardDao) {
		this.memberDao = memberDao;
		this.shippingInfoDao = shippingInfoDao;
		this.paymentDao = paymentDao;
		this.boardDao = boardDao;
	}
	
	// 회원 정보 확인
	public void memberInfo(Member currMember, String pwd) {
		Member member = memberConfirmPassword(currMember, pwd);
		
		System.out.printf("아이디:%s | 비밀번호:%s | 이름:%s | 이메일:%s | "
				+ "전화번호:%s | 가입일:%tF%n", 
				member.getId(), member.getPwd(), member.getMname(),
				member.getEmail(), member.getPhone(), member.getRegDate());
	}
	
	// 비밀번호 변경
	public void memberChangePassword(
			Member currMember, String pwd, String confirmPwd) {
		if(!isEqualsConfirmPassword(pwd, confirmPwd)) { // 비밀번호 일치확인
			System.out.println("비밀번호가 일치하지 않습니다.");
			return;
		}
		
		currMember.setPwd(pwd); // 현재비밀번호를 변경할 비밀번호로 바꿈
		Connection con = DataUtil.getConnection();
		int n = memberDao.editPassword(con, currMember); // 비밀번호 수정
		
		if(n < 1) {
			System.out.println("비밀번호 변경중 오류가 발생했습니다.");
			DataUtil.rollback(con);
			return;
		}
		
		DataUtil.commit(con);
		DataUtil.close(con);
		System.out.println("비밀번호 변경 완료");
	}
	
	//회원탈퇴
	public int withdraw(Member member) {
		String id = member.getId();
		Connection con = DataUtil.getConnection();
		
		int n = 0;
		boolean check = shippingInfoDao.findShippingInfo(con, id); // 배송정보확인
		if(check) { // 배송정보가 존재하면 제거
			n = shippingInfoDao.remove(con, id);
			if (n < 1) {
				DataUtil.rollback(con);
				throw new RemoveDataException();
			}
		}
		
		check = paymentDao.findPayment(con, id); // 결제정보 확인
		if(check) { // 결제정보가 존재하면 제거
			n = paymentDao.remove(con, id);
			if (n < 1) {
				DataUtil.rollback(con);
				throw new RemoveDataException();
			}
		}
		
		check = boardDao.findBoard(con, id); // 게시물 정보확인
		if(check) { // 게시물이 존재하면 제거
			n = boardDao.remove(con, id);
			if (n < 1) {
				DataUtil.rollback(con);
				throw new RemoveDataException();
			}
		}
		
		n = memberDao.withdraw(con, id); // 회원탈퇴
		if (n < 1) {
			DataUtil.rollback(con);
			throw new RemoveDataException();
		}
		
		DataUtil.commit(con);
		DataUtil.close(con);
		return n;
	}
	
	// 비밀번호 확인
	public Member memberConfirmPassword(Member currMember, String pwd) {
		Member member = memberDao.findByLoginId(currMember.getId());
		if(!isEqualsConfirmPassword(member.getPwd(),pwd)) {
			throw new WrongIdPasswordException();
		}
		return member;
	}
	
	// 새비밀번호와 새비밀번호 확인
	private boolean isEqualsConfirmPassword(String pwd, String confirmPwd) {
		return pwd.equals(confirmPwd);
	}
	
}
