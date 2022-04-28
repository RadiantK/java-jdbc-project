package com.computers.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.computers.dto.Payment;
import com.computers.util.DataUtil;

// 결제관련 데이터 처리
public class PaymentDao {

	// 결제 정보(관리자용)
	public List<Payment> AllList() {
		String sql = "SELECT * FROM payment ORDER BY regdate";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Payment> list = new ArrayList<>();
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int pnum = rs.getInt("pnum");
				String id = rs.getString("id");
				String cname = rs.getString("cname");
				int cnt = rs.getInt("cnt");
				int amount = rs.getInt("amount");
				String means = rs.getString("means");
				String status = rs.getString("status");
				
				Payment payment = 
						new Payment(pnum, id, cname, cnt, amount, means, status);
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
	
	// 결제 정보(회원용)
	public List<Payment> findList(Payment pay) {
		String sql = "SELECT * FROM payment "
				+ "WHERE id = ? AND regdate > sysdate - 100 ORDER BY regdate";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Payment> list = new ArrayList<>();
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, pay.getId());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int pnum = rs.getInt("pnum");
				String id = rs.getString("id");
				String cname = rs.getString("cname");
				int cnt = rs.getInt("cnt");
				int amount = rs.getInt("amount");
				String means = rs.getString("means");
				String status = rs.getString("status");
				
				Payment payment = 
						new Payment(pnum, id, cname, cnt, amount, means, status);
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
	
	// 결제정보 삽입
	public int insert(Connection con, Payment pay,int cnt) {
		String sql = "INSERT INTO payment VALUES(SEQ_payment, ?,?,?,?,?)";
		PreparedStatement pstmt = null;
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, pay.getId());
			pstmt.setString(2, pay.getCname());
			pstmt.setInt(3, cnt);
			pstmt.setInt(4, cnt*pay.getAmount());
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
			con = DataUtil.getConnection();
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
}
