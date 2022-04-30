package com.computers.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.computers.entity.Board;
import com.computers.util.DataUtil;

public class BoardDao {

	public List<Board> boardList(){
		
		return boardList(1);
	}
	
	// 게시물 리스트
	public List<Board> boardList(int page){
		String sql = "SELECT * FROM ( "
				+ "SELECT ROWNUM NUM, B.* FROM( "
				+ "SELECT * FROM board ORDER BY ( "
				+ "SELECT id FROM board WHERE id = 'admin'), regdate) B) "
				+ "WHERE NUM BETWEEN ? AND ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> list = new ArrayList<>();
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, 1+(page-1)*10);
			pstmt.setInt(2, page*10);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int bnum = rs.getInt("bnum");
				String id = rs.getString("id");
				String title = rs.getString("title");
				Date regDate = rs.getDate("regdate");
				
				Board board = new Board(bnum, id, title, regDate, "");
				
				list.add(board);
			}
			return list;
		}catch (SQLException e) {
			System.out.println("건의사항 목록을 불러오는 중 에러가 발생했습니다.");
			return null;
		}finally {
			DataUtil.close(con, pstmt, rs);
		}
	}
	
	// 게시물 상세정보
	public Board detailBoard(int num){
		String sql = "SELECT * FROM board WHERE bnum = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			Board board = null;
			while(rs.next()) {
				int bnum = rs.getInt("bnum");
				String id = rs.getString("id");
				String title = rs.getString("title");
				Date regDate = rs.getDate("regdate");
				String content = rs.getString("content");
				
				board = new Board(bnum, id, title, regDate, content);
			}
			return board;
		}catch (SQLException e) {
			System.out.println("건의사항 목록을 불러오는 중 에러가 발생했습니다.");
			return null;
		}finally {
			DataUtil.close(con, pstmt, rs);
		}
	}
	
	// 하나의 게시물 제거
	public int remove(Connection con, Board board) {
		String sql = "DELETE FROM board WHERE bnum = ?";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board.getBnum());
			
			int result = pstmt.executeUpdate();
			return result;
		}catch (SQLException e) {
			System.out.println("게시물을 제거하는 과정에서 오류가 발생했습니다.");
			return -1;
		}finally {
			DataUtil.close(pstmt);
		}
	}
	
	// 게시물 전부 제거
	public int removeMember(Connection con, Board board) {
		String sql = "DELETE FROM board WHERE id = ?";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board.getId());
			
			int result = pstmt.executeUpdate();
			return result;
		}catch (SQLException e) {
			System.out.println("게시물을 제거하는 과정에서 오류가 발생했습니다.");
			return -1;
		}finally {
			DataUtil.close(pstmt);
		}
	}
	
	public int edit(Connection con, Board board) {
		String sql = "UPDATE board SET title = ?, content = ? WHERE bnum = ?";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setInt(3, board.getBnum());
			
			int result = pstmt.executeUpdate();
			return result;
		}catch (SQLException e) {
			System.out.println("게시물 수정중 오류가 발생했습니다.");
			return -1;
		}finally {
			DataUtil.close(pstmt);
		}
	}
	
	// 게시물 삽입
	public int insert(Connection con, Board board) {
		String sql = "INSERT INTO board VALUES(SEQ_board, ?, ?, sysdate, ?)";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, board.getId());
			pstmt.setString(2, board.getTitle());
			pstmt.setString(3, board.getContent());
			
			int result = pstmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			System.out.println("게시물 생성 중 오류가 발생했습니다.");
			return -1;
		}finally {
			DataUtil.close(pstmt);
		}
	}
}