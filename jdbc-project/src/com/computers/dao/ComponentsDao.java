package com.computers.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.computers.dto.Components;
import com.computers.dto.Warehouse;
import com.computers.util.DataUtil;

// 부품관련 데이터 처리
public class ComponentsDao {
	
	public List<Components> allList() {
	
		return allList(1);
	}
	
	// 부품 리스트
	public List<Components> allList(int page) {
		String sql = "SELECT * "
				+ "FROM (SELECT ROWNUM NUM, C.* "
				+ "FROM (SELECT * FROM components ORDER BY type) C) "
				+ "WHERE NUM BETWEEN ? AND ?; ";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Components> list = new ArrayList<>(); 
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, 1 + (page - 1) * 10);
			pstmt.setInt(1, page * 10);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int cnum = rs.getInt("cnum");
				String type = rs.getString("type");
				String cname = rs.getString("cname");
				int price = rs.getInt("price");
				Date regDate = rs.getDate("regdate");
				int cnt = rs.getInt("cnt");
				
				Components comp = 
						new Components(cnum, type, cname, price, regDate, cnt);
				list.add(comp);
			}
			return list;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			DataUtil.close(con, pstmt, rs);
		}
	}
	
	// 부품 리스트
		public Components findList(int page) {
			String sql = "SELECT * FROM components WEHRE cnum = ?";
			
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Components comp = null;
			
			try {
				con = DataUtil.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, 1 + (page - 1) * 10);
				pstmt.setInt(1, page * 10);
				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int cnum = rs.getInt("cnum");
					String type = rs.getString("type");
					String cname = rs.getString("cname");
					int price = rs.getInt("price");
					Date regDate = rs.getDate("regdate");
					int cnt = rs.getInt("cnt");
					
					comp = new Components(cnum, type, cname, price, regDate, cnt);
				}
				return comp;
			}catch (SQLException e) {
				e.printStackTrace();
				return null;
			}finally {
				DataUtil.close(con, pstmt, rs);
			}
		}
	
	// 부품재고 감소
	public int minusComponents(Connection con, int cnum, int cnt) {
		String sql = "UPDATE components SET cnt = cnt - ? WHERE cnum = ?";
		PreparedStatement pstmt = null;
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cnum);
			pstmt.setInt(2, cnt);
			
			int result = pstmt.executeUpdate();
			return result;
		}catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}finally {
			DataUtil.close(pstmt);
		}
	}
	
	// 재고에서 수량 확인하기
	public int check(Connection con, int cnum) {
		String sql = "SELECT cnt FROM components WEHRE cnum = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cnum);
			
			rs = pstmt.executeQuery();
			int cnt = 0;
			if(rs.next()) {
				 cnt = rs.getInt("cnt");
			}
			return cnt;
		} catch (SQLException e) {
			System.out.println("현재 필요하신 부품은 목록에 없습니다.");
			return -1;
		}finally {
			DataUtil.close(pstmt, rs);
		}
	}
	
	// 부품정보수정
	public int update(Connection con, Components components) {
		String sql = "UPDATE Components SET price = ?, cnt = ? regdate = SYSDATE "
				+ "WHERE cnum = ?";
		
		PreparedStatement pstmt = null;
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, components.getPrice());
			pstmt.setInt(2, components.getCnt());
			pstmt.setInt(3, components.getCnum());
			
			int result = pstmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			System.out.println("부품관련 수정중 에러가 발생했습니다.");
			return -1;
		}finally {
			DataUtil.close(pstmt);
		}
	}
	
	public int insert(Warehouse warehouse) {
		String sql = "INSERT INTO components VALUES(SEQ_components,?,?,?,?,?)";
		return 1;
	}
	

}
