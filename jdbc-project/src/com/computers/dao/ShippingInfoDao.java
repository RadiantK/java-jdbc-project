package com.computers.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.computers.dto.ShippingInfo;
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
				String sname = rs.getString("sname");
				String address = rs.getString("address");
				Date startDate = rs.getDate("startdate");
				Date endDate = rs.getDate("endDate");
				String status = rs.getString("status");
				
				ShippingInfo info = new ShippingInfo(
						snum, id, cname, sname, address, startDate, endDate, status);
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
	public List<ShippingInfo> findList(){
		String sql = "SELECT * FROM shippinginfo "
				+ "WHERE id = ? AND regdate > sysdate - 100 ORDER BY regdate";
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
				String sname = rs.getString("sname");
				String address = rs.getString("address");
				Date startDate = rs.getDate("startdate");
				Date endDate = rs.getDate("endDate");
				String status = rs.getString("status");
				
				ShippingInfo info = new ShippingInfo(
						snum, id, cname, sname, address, startDate, endDate, status);
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
	
	// 배송완료된 물품 확인
	public int check(Connection con, int no) {
		String sql = "SELECT * FROM shippinginfo where snum = ? AND status = '배송완료'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return 1;
			}
			return -1;
		}catch (SQLException e) {
			System.out.println("배송완료된 물품입니다.");
			return -1;
		}finally {
			DataUtil.close(pstmt, rs);
		}
	}
	
	public int insert(Connection con, ShippingInfo info) {
		String sql = "INSERT INTO shippinginfo "
				+ "VALUES(SEQ_SHIPPINGINFO, ?, ?, ?, ?, sysdate, sysdate+5, '배송대기'";
		PreparedStatement pstmt = null;
		
		try {
			con = DataUtil.getConnection();
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
	public int edit(Connection con, ShippingInfo info) {
		String sql = "UPDATE ShippingInfo "
				+ "SET status = ?, enddate = sysDate WHERE snum = ?";
		
		PreparedStatement pstmt = null;
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, info.getStatus());
			pstmt.setInt(2, info.getSnum());
			
			int result = pstmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			System.out.println("배송정보를 수정하는중 오류가 발생했습니다.");
			return -1;
		}finally {
			DataUtil.close(pstmt);
		}
	}
	
	public int remove(Connection con, ShippingInfo info) {
		String sql = "DELETE FROM shippinginfo WEHRE id = ?";
		PreparedStatement pstmt = null;
		
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, info.getId());
			
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
