package com.computers.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.computers.entity.Member;
import com.computers.util.DataUtil;

public class MemberDao {

	// 회원 전체 정보
	public List<Member> memberList(int page, String type, String word) {
		String sql = "SELECT * FROM ( "
				+ "SELECT ROWNUM NUM, M.* FROM ( "
				+ "SELECT * FROM MEMBER WHERE "+type+" LIKE ? "
				+ "ORDER BY DECODE(id, 'admin', 1), ID) M) "
				+ "WHERE NUM BETWEEN ? AND ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> list = new ArrayList<>();
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+word+"%");
			pstmt.setInt(2, 1 + (page -1) * 10);
			pstmt.setInt(3, page * 10);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String mname = rs.getString("mname");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				Date regDate = rs.getDate("regdate");
				int available = rs.getInt("available");
				String authority = rs.getString("authority");
				
				Member member = new Member(
					id, pwd, mname, email, phone, regDate, available, authority);
				list.add(member);
			}
			return list;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			DataUtil.close(con, pstmt, rs);
		}
	}
	
	// 아이디가 존재하는지 확인
	public Member findById(String reqId) {
		String sql = "SELECT * FROM member WHERE id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, reqId);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String mname = rs.getString("mname");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				Date regDate = rs.getDate("regdate");	
				String authority = rs.getString("authority");
				
				Member member = new Member(
						id, pwd, mname, email, phone, regDate, 0, authority);
				return member;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			DataUtil.close(con, pstmt, rs);
		}
		return null;
	}
	
	// 내 정보확인
	public Member findByLoginId(String reqId) {
		String sql = "SELECT * FROM member WHERE id = ? AND available = 1";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, reqId);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String mname = rs.getString("mname");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				Date regDate = rs.getDate("regdate");
				int available = rs.getInt("available");
				String authority = rs.getString("authority");
				
				Member member = new Member(
						id, pwd, mname, email, phone, regDate, available, authority);
				return member;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			DataUtil.close(con, pstmt, rs);
		}
		return null;
	}
	
	// 회원정보저장
	public int insert(Connection con, Member member) {
		String sql = "INSERT INTO member(id, pwd, mname, email, phone) "
				+ "VALUES(?,?,?,?,?)";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPwd());
			pstmt.setString(3, member.getMname());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getPhone());
			
			int result = pstmt.executeUpdate();
			return result;
		}catch (SQLException e) {
			System.out.println("회원가입중 에러가 발생했습니다.");
			return -1;
		}finally {
			DataUtil.close(pstmt);
		}
	}
	
	// 비밀번호 변경
	public int editPassword(Connection con, Member member) {
		String sql = "UPDATE member SET pwd = ? WHERE id = ? AND available = 1";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getPwd());
			pstmt.setString(2, member.getId());
			
			int result = pstmt.executeUpdate();
			return result;
		}catch (SQLException e) {
			System.out.println("비밀번호 변경 중 에러가 발생했습니다.");
			return -1;
		}finally {
			DataUtil.close(pstmt);
		}
	}
	
	// 회원탈퇴
	public int withdraw(Connection con, String id) {
		String sql = "UPDATE member SET available = 0, regdate = sysdate "
				+ "WHERE id = ?";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			int result = pstmt.executeUpdate();
			return result;
		}catch (SQLException e) {
			System.out.println("회원을 탈퇴하는 과정에서 에러가 발생했습니다.");
			return -1;
		}finally {
			DataUtil.close(pstmt);
		}
	}
	
	// 내정보 확인
	public Member memberInfo(Member reqMember) {
		String sql = "SELECT * FROM member WEHRE id = ? AND pwd = ? AND available = 1";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member member = new Member();
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, reqMember.getId());
			pstmt.setString(2, reqMember.getPwd());
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member.setId(rs.getString("id"));
				member.setPwd(rs.getString("pwd"));
				member.setMname(rs.getString("mname"));
				member.setEmail(rs.getString("email"));
				member.setPhone(rs.getString("phone"));
				member.setRegDate(rs.getDate("regdate"));				
			}
			return member;
		}catch (SQLException e) {
			System.out.println("회원정보를 불러오는 과정에서 에러가 발생했습니다.");
			return null;
		}finally {
			DataUtil.close(con, pstmt, rs);
		}
	}
	
	// 계정 삭제(관리자용)
	public int remove(String id) {
		String sql = "DELETE FROM member WHERE id = ?";
		Connection con = null;
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
			DataUtil.close(con, pstmt);
		}
	}

	// 전체 회원 수
	public int getCount(String type, String word) {
		String sql = "SELECT COUNT(*) count FROM member WHERE "+type+" LIKE ?";
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
