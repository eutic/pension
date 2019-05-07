package board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import board.vo.BoardVo;
import common.util.DBManager;
import common.util.Util;

public class BoardDao  {
	
	
	
	public void write(BoardVo vo) {
		String sql = "INSERT INTO BOARD ( BOARDIDX, TITLE, CONT, EMAIL) " + 
				" VALUES (BOARD_SEQ.NEXTVAL, ?, ?, ?) ";
		Connection conn= DBManager.getConnection();
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			int idx = 0;
			pstmt.setString(++idx, vo.getTitle());
			pstmt.setString(++idx, vo.getCont());
			pstmt.setString(++idx, vo.getEmail());
			pstmt.executeUpdate();
			DBManager.close(conn, pstmt);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void modify(BoardVo vo) {
		String sql = "UPDATE BOARD SET TITLE = ?, CONT = ? WHERE BOARDIDX = ?";
		Connection conn= DBManager.getConnection();
		try{
			PreparedStatement pstmt = conn.prepareStatement(sql);
			int idx = 0;
			pstmt.setString(++idx, vo.getTitle());
			pstmt.setString(++idx, vo.getCont());
			pstmt.setInt(++idx, vo.getIdx());
			pstmt.executeUpdate();
			DBManager.close(conn, pstmt);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
		
	public void delete(int boardidx) {
		String sql = "DELETE BOARD WHERE BOARDIDX = ?";
		Connection conn = DBManager.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardidx);
			pstmt.executeUpdate();
			pstmt.close();
			DBManager.close(conn, pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public BoardVo get(int boardidx) {
		String sql = "SELECT ROWNUM RN, BOARDIDX, TITLE, CONT, CATEGORY, REGDATE, EMAIL, SCORE, PSIDX " + 
				"FROM BOARD " + 
				"WHERE BOARDIDX = ? ";
		BoardVo vo = null;
		Connection conn = DBManager.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardidx);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				vo = new BoardVo();
				vo.setIdx(rs.getInt("BOARDIDX"));
				vo.setTitle(rs.getString("TITLE"));
				vo.setCont(rs.getString("CONT"));
				vo.setCate(rs.getInt("CATEGORY"));
				vo.setRegdate(Util.displyTime(rs.getTimestamp("REGDATE")));
				vo.setEmail(rs.getString("EMAIL"));
				if(vo.getCate() == 3) {
					vo.setScore(rs.getInt("SCORE"));
					vo.setPsIdx(rs.getInt("PSIDX"));
				}
			}
			conn.close();
			pstmt.close();
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vo;
	
	}

	public List<BoardVo> list(String category, int from, int to) {
		String sql = "SELECT * FROM (\r\n" + 
				"    SELECT ROWNUM RN, BOARDIDX, TITLE, CONT, CATEGORY, REGDATE, EMAIL, SCORE, PSIDX \r\n" + 
				"    FROM BOARD\r\n" + 
				"    WHERE CATEGORY = ?\r\n" + 
				"    AND ROWNUM <= ?\r\n" + 
				")\r\n" + 
				"WHERE RN >= ?";
		BoardVo vo = null;
		List<BoardVo> list = new ArrayList<>();
		Connection conn = DBManager.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			pstmt.setInt(2, to);
			pstmt.setInt(3, from);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				vo = new BoardVo();
				vo.setIdx(rs.getInt("BOARDIDX"));
				vo.setTitle(rs.getString("TITLE"));
				vo.setCont(rs.getString("CONT"));
				vo.setCate(rs.getInt("CATEGORY"));
				vo.setRegdate(Util.displyTime(rs.getTimestamp("REGDATE")));
				vo.setEmail(rs.getString("EMAIL"));
				if(category.equals("3")) {
					vo.setScore(rs.getInt("SCORE"));
					vo.setPsIdx(rs.getInt("PSIDX"));
				}
				list.add(vo);
			}
			conn.close();
			pstmt.close();
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
