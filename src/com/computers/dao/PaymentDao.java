package com.computers.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.computers.dto.PayInfoCommand;
import com.computers.dto.PaymentRequest;
import com.computers.entity.Payment;
import com.computers.util.DataUtil;

// 결제관련 데이터 처리
public class PaymentDao {

	// 결제정보와 배송정보 통합(관리자용)
	public List<PayInfoCommand> allList(int page, String type, String word) {
		String sql = "SELECT * FROM ( "
				+ "SELECT ROWNUM NUM, C.* FROM ( "
				+ "SELECT p.pnum, p.id, p.cname, p.cnt, p.amount, p.means, p.status pstatus, "
				+ "s.snum, s.sname, s.address, s.startdate, s.enddate, s.status sstatus "
				+ "FROM payment p JOIN shippinginfo s ON p.pnum = s.pnum) C "
				+ "WHERE "+type+" LIKE ?) "
				+ "WHERE NUM BETWEEN ? AND ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PayInfoCommand> list = new ArrayList<>();
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+word+"%");
			pstmt.setInt(2, 1+(page-1)*10);
			pstmt.setInt(3, page*10);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int pnum = rs.getInt("pnum");
				String id = rs.getString("id");
				String cname = rs.getString("cname");
				int cnt = rs.getInt("cnt");
				int amount = rs.getInt("amount");
				String means = rs.getString("means");
				String pstatus = rs.getString("pstatus");
				int snum = rs.getInt("snum");
				String sname = rs.getString("sname");
				String address = rs.getString("address");
				Date startDate = rs.getDate("startdate");
				Date endDate = rs.getDate("enddate");
				String sstatus = rs.getString("sstatus");
				
				PayInfoCommand pay = new PayInfoCommand(
						pnum, id, cname, cnt, amount, means, pstatus, snum,
						sname, address, startDate, endDate, sstatus);
				
				list.add(pay);
			}
			return list;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			DataUtil.close(con, pstmt, rs);
		}
	}
	
	// 결제 정보(회원용)
	public List<Payment> memberList(String memid, int month) {
		String sql = "SELECT * FROM payment "
				+ "WHERE id = ? AND regdate > sysdate - ? ORDER BY regdate DESC";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Payment> list = new ArrayList<>();
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memid);
			pstmt.setInt(2, month * 30);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int pnum = rs.getInt("pnum");
				String id = rs.getString("id");
				String cname = rs.getString("cname");
				int cnt = rs.getInt("cnt");
				int amount = rs.getInt("amount");
				String means = rs.getString("means");
				Date regDate = rs.getDate("regDate");
				String status = rs.getString("status");
				
				Payment payment = 
						new Payment(pnum, id, cname, cnt, amount, means, regDate, status);
				list.add(payment);
			}
			return list;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			DataUtil.close(con, pstmt, rs);
		}
	}
	
	// 결제 정보(회원탈퇴용)
	public boolean findPayment(Connection con, String memId) {
		String sql = "SELECT * FROM payment WHERE id = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			DataUtil.close(pstmt, rs);
		}
		return false;
	}
	
	// 결제정보 삽입
	public int insert(Connection con, PaymentRequest pay) {
		String sql = "INSERT INTO payment VALUES("
				+ "SEQ_payment.nextval, ?, ?, ?, ?, ?, sysdate, '결재 완료')";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, pay.getId());
			pstmt.setString(2, pay.getCname());
			pstmt.setInt(3, pay.getCnt());
			pstmt.setInt(4, pay.getCnt()*pay.getPrice());
			pstmt.setString(5, pay.getMeans());
			
			int result = pstmt.executeUpdate();
			return result;
		}catch (SQLException e) {
			System.out.println("결재정보 추가중 오류가 발생했습니다.");
			return -1;
		}finally {
			DataUtil.close(pstmt);
		}
	}
	
	// 결제정보 제거
	public int remove(Connection con, String id) {
		String sql = "DELETE FROM payment WHERE id = ?";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			int result = pstmt.executeUpdate();
			return result;
		}catch (SQLException e) {
			System.out.println("회원정보 제거중 오류가 발생했습니다.");
			return -1;
		}finally {
			DataUtil.close(pstmt);
		}
	}
	
	// 결제 취소
	public int cancel(Connection con, int pnum) {
		String sql = "UPDATE payment SET status = ? WHERE pnum = ?";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "결제취소");
			pstmt.setInt(2, pnum);
			
			int result = pstmt.executeUpdate();
			return result;
		}catch (SQLException e) {
			System.out.println("회원정보 제거중 오류가 발생했습니다.");
			return -1;
		}finally {
			DataUtil.close(pstmt);
		}
	}
	
	// 전체 결제정보 갯수(결제정보, 배송정보)
	public int getCount(String type, String word) {
		String sql = "SELECT COUNT(*) count FROM ( "
				+ "SELECT p.pnum, p.id, p.cname, p.cnt, p.amount, p.means, p.status pstatus, "
				+ "s.snum, s.sname, s.address, s.startdate, s.enddate, s.status sstatus "
				+ "FROM payment p JOIN shippinginfo s ON p.pnum = s.pnum) C "
				+ "WHERE "+type+" LIKE ?";
		int count = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+word+"%");
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt("count");
			}
		}catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}finally {
			DataUtil.close(con, pstmt, rs);
		}
		return count;
	}
}
