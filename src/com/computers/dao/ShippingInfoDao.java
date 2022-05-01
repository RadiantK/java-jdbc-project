package com.computers.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.computers.dto.ShippingInfoCommand;
import com.computers.dto.ShippingInfoRequest;
import com.computers.entity.ShippingInfo;
import com.computers.util.DataUtil;

public class ShippingInfoDao {

	// 배송정보 (관리자용)
	public List<ShippingInfo> AllList(){
		String sql = "SELECT * FROM shippinginfo ORDER BY regdate";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ShippingInfo> list = new ArrayList<>();
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int snum = rs.getInt("snum");
				String id = rs.getString("id");
				String cname = rs.getString("cname");
				int pnum = rs.getInt("pnum");
				String sname = rs.getString("sname");
				String address = rs.getString("address");
				Date startDate = rs.getDate("startdate");
				Date endDate = rs.getDate("endDate");
				String status = rs.getString("status");
				
				ShippingInfo info = new ShippingInfo(
						snum, id, cname, pnum, sname, address, startDate, endDate, status);
				list.add(info);
			}
			return list;
		}catch (SQLException e) {
			System.out.println("목록을 읽어오는중 에러가 발생했습니다.");
			return null;
		}finally {
			DataUtil.close(con, pstmt, rs);
		}
	}
	
	// 배송정보 (회원용)
	public List<ShippingInfo> memberList(String memid, int month){
		String sql = "SELECT * FROM shippinginfo "
				+ "WHERE id = ? AND startdate > sysdate - ? ORDER BY startdate DESC";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ShippingInfo> list = new ArrayList<>();
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memid);
			pstmt.setInt(2, month*30);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int snum = rs.getInt("snum");
				String id = rs.getString("id");
				String cname = rs.getString("cname");
				int pnum = rs.getInt("pnum");
				String sname = rs.getString("sname");
				String address = rs.getString("address");
				Date startDate = rs.getDate("startdate");
				Date endDate = rs.getDate("endDate");
				String status = rs.getString("status");
				
				ShippingInfo info = new ShippingInfo(
						snum, id, cname, pnum, sname, address, startDate, endDate, status);
				list.add(info);
			}
			return list;
		}catch (SQLException e) {
			System.out.println("목록을 읽어오는중 에러가 발생했습니다.");
			return null;
		}finally {
			DataUtil.close(con, pstmt, rs);
		}
	}
	
	// 배송정보 (회원탈퇴용)
	public boolean findShippingInfo(Connection con, String memid){
		String sql = "SELECT * FROM shippinginfo WHERE id = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memid);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return true;
			}
		}catch (SQLException e) {
			System.out.println("목록을 읽어오는중 에러가 발생했습니다.");
			return false;
		}finally {
			DataUtil.close(pstmt, rs);
		}
		return false;
	}
	
	// 배송완료된 물품 확인
	public boolean checkInfo(Connection con, int no) {
		String sql = "SELECT * FROM shippinginfo "
				+ "WHERE pnum = ? AND status IN ('배송중', '배송완료')";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return true;
			}
		}catch (SQLException e) {
			System.out.println("배송정보를 확인하는 중 에러가 발생했습니다.");
			return false;
		}finally {
			DataUtil.close(pstmt, rs);
		}
		return false;
	}
	
	// 배송정보 삽입
	public int insert(Connection con, ShippingInfoRequest info) {
		String sql = "INSERT INTO shippinginfo "
				+ "VALUES(SEQ_SHIPPINGINFO.nextval, ?, ?, SEQ_PAYMENT.currval, ?, ?, sysdate, sysdate+5, '배송대기')";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, info.getId());
			pstmt.setString(2, info.getCname());
			pstmt.setString(3, info.getSname());
			pstmt.setString(4, info.getAddress());
			
			int result = pstmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			System.out.println("배송정보를 생성하는 중 오류가 발생했습니다.");
			return -1;
		}finally {
			DataUtil.close(pstmt);
		}
	}
	
	// 배송상태 변경 (관리자용)
	public int edit(ShippingInfoCommand info) {
		String sql = "UPDATE ShippingInfo "
				+ "SET status = ?, enddate = ? WHERE pnum = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, info.getStatus());
			pstmt.setString(2, info.getEndDate());
			pstmt.setInt(3, info.getPnum());
			
			int result = pstmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			System.out.println("배송정보를 수정하는중 오류가 발생했습니다.");
			return -1;
		}finally {
			DataUtil.close(con, pstmt);
		}
	}
	
	// 배송 취소 (회원용)
	public int cancel(Connection con, int pnum) {
		String sql = "UPDATE ShippingInfo "
				+ "SET status = ? WHERE pnum = ?";
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "배송취소");
			pstmt.setInt(2, pnum);
			
			int result = pstmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			System.out.println("배송정보를 수정하는중 오류가 발생했습니다.");
			return -1;
		}finally {
			DataUtil.close(pstmt);
		}
	}
	
	//계정 정보에 해당하는 배송정보 제거
	public int remove(Connection con, String id) {
		String sql = "DELETE FROM shippinginfo WHERE id = ?";
		PreparedStatement pstmt = null;
		
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			int result = pstmt.executeUpdate();
			return result;
		}catch (SQLException e) {
			System.out.println("배송정보를 제거하는 중 오류가 발생했습니다.");
			return -1;
		}finally {
			DataUtil.close(pstmt);
		}
	}
	
}
