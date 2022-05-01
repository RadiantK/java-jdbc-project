package com.computers.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.computers.dto.BoardRequest;
import com.computers.entity.Board;
import com.computers.util.DataUtil;

public class BoardDao {

	public List<Board> boardList(){
		
		return boardList(1, "title", "");
	}
	
	// 게시물 리스트
	public List<Board> boardList(int page, String type, String word){
		String sql = "SELECT * FROM ( "
				+ "SELECT ROWNUM NUM, B.* FROM( "
				+ "SELECT * FROM board WHERE "+type+" LIKE ? "
				+ "ORDER BY DECODE(id, 'admin', 1), regdate DESC) B) "
				+ "WHERE NUM BETWEEN ? AND ?";
				
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> list = new ArrayList<>();
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+word+"%");
			pstmt.setInt(2, 1+(page-1)*10);
			pstmt.setInt(3, page*10);
			
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
	public int removeNum(int num) {
		String sql = "DELETE FROM board WHERE bnum = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			int result = pstmt.executeUpdate();
			return result;
		}catch (SQLException e) {
			System.out.println("게시물을 제거하는 과정에서 오류가 발생했습니다.");
			return -1;
		}finally {
			DataUtil.close(con, pstmt);
		}
	}
	
	// 게시물 전부 제거
	public int remove(Connection con, String id) {
		String sql = "DELETE FROM board WHERE id = ?";
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			int result = pstmt.executeUpdate();
			return result;
		}catch (SQLException e) {
			System.out.println("게시물을 제거하는 과정에서 오류가 발생했습니다.");
			return -1;
		}finally {
			DataUtil.close(pstmt);
		}
	}
	
	// 게시물 수정
	public int editBoard(BoardRequest board) {
		String sql = "UPDATE board SET title = ?, content = ? WHERE bnum = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = DataUtil.getConnection();
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
			DataUtil.close(con, pstmt);
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
	
	// 전체 게시물 개수
	public int getCount(String type, String word) {
		String sql = "SELECT COUNT(*) count FROM board WHERE "+type+" LIKE ?";
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
	
	public boolean findBoard(Connection con, String memId) {
		String sql = "SELECT * FROM board WHERE id = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DataUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memId);
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
}