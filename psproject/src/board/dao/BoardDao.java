package board.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.xml.internal.fastinfoset.sax.Properties;

import board.vo.BoardVo;
import common.util.DBManager;
import common.util.Util;

public class BoardDao {

	public void write(BoardVo vo) {
		String sql = "INSERT INTO BOARD(BOARDIDX,TITLE,CONT,EMAIL)VALUES(BOARD_SEQ.NEXTVAL,\r\n" + 
				"?,?, ?)";
		Connection conn = DBManager.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			int idx = 0;
			pstmt.setString(++idx, vo.getTitle());
			pstmt.setString(++idx, vo.getCont());
			pstmt.setString(++idx, vo.getEmail());
			pstmt.executeUpdate();
			DBManager.close(conn, pstmt);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void modify(BoardVo vo) {
		String sql = "UPDATE BOARD SET TITLE = ?, CONT = ? WHERE BOARDIDX = ? ";
		Connection conn = DBManager.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			int idx = 0;
			pstmt.setString(++idx, vo.getTitle());
			pstmt.setString(++idx, vo.getCont());
			pstmt.setInt(++idx, vo.getIdx());
			pstmt.executeUpdate();
			DBManager.close(conn, pstmt);

		} catch (SQLException e) {
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
	public BoardVo get(int boardIdx) {
		String sql ="SELECT ROWNUM RN, BOARDIDX,TITLE,CONT,CATEGORY,\r\n" + 
				"REGDATE,EMAIL,SCORE,PSIDX \r\n" + 
				"FROM BOARD\r\n" + 
				"WHERE BOARDIDX=?";
		BoardVo vo = null;
		List<BoardVo> list = new ArrayList<>();
		Connection conn = DBManager.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardIdx);
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				vo = new BoardVo();
				vo.setIdx(rs.getInt("BOARDIDX"));
				vo.setTitle(rs.getString("TITLE"));
				vo.setCont(rs.getString("CONT"));
				vo.setCate(rs.getInt("CATEGORY"));
				vo.setRegdate(Util.displayTime(rs.getTimestamp("REGDATE")));
				vo.setEmail(rs.getString("EMAIL"));
				if(vo.getCate() == 3) {
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
		return vo;
	}

	public List<BoardVo> list(String category,int from,int to) {
		String sql = "SELECT * FROM(\r\n" + 
				"SELECT ROWNUM RN, BOARDIDX,TITLE,CONT,CATEGORY,\r\n" + 
				"REGDATE,EMAIL,SCORE,PSIDX \r\n" + 
				"FROM BOARD\r\n" + 
				"WHERE CATEGORY = ?\r\n" + 
				"AND ROWNUM <= ?\r\n" + 
				")\r\n" + 
				"WHERE RN >= ? ";
		BoardVo vo = null;
		
		List<BoardVo> list = new ArrayList<>();
		Connection conn = DBManager.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			pstmt.setInt(2,to);
			pstmt.setInt(3, from);
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				vo = new BoardVo();
				vo.setIdx(rs.getInt("BOARDIDX"));
				vo.setTitle(rs.getString("TITLE"));
				vo.setCont(rs.getString("CONT"));
				vo.setCate(rs.getInt("CATEGORY"));
				vo.setRegdate(Util.displayTime(rs.getTimestamp("REGDATE")));
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
	
	public List<BoardVo> listReview(int psidx,int from,int to) {
		String sql = "SELECT * FROM(\r\n" + 
				"    SELECT BOARDIDX,TITLE,CONT,SCORE,\r\n" + 
				"    CASE\r\n" + 
				"        WHEN SYSDATE - REGDATE < 1 THEN TO_CHAR(REGDATE, 'HH24:MI:SS')\r\n" + 
				"        ELSE TO_CHAR(REGDATE, 'YY/MM/DD')\r\n" + 
				"    END REGDATE,\r\n" + 
				"    PSIDX,ROWNUM RN\r\n" + 
				"    ,CONCAT(SUBSTR(EMAIL,1,3),'*****')EMAIL\r\n" + 
				"    FROM BOARD WHERE CATEGORY = 2 AND PSIDX = ? AND ROWNUM <= ?\r\n" + 
				" )\r\n" + 
				"WHERE RN >= ?";
		
		BoardVo vo = null;
		List<BoardVo> list = new ArrayList<>();
		Connection conn = DBManager.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,psidx);
			pstmt.setInt(2,to);
			pstmt.setInt(3,from);
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				vo = new BoardVo();
				vo.setIdx(rs.getInt("BOARDIDX"));
				vo.setTitle(rs.getString("TITLE"));
				vo.setCont(rs.getString("CONT"));
				vo.setScore(rs.getInt("SCORE"));
				vo.setRegdate(rs.getString("REGDATE"));
				vo.setPsIdx(rs.getInt("PSIDX"));	
				vo.setEmail(rs.getString("EMAIL"));
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
	
	public Map<String, Object> reviewOverall(int psidx) {
		String sql = "WITH TMP AS( " + 
				"    SELECT SCORE FROM BOARD WHERE CATEGORY = 2 AND PSIDX = ? " + 
				") " + 
				"SELECT A.*,(SELECT ROUND(AVG(SCORE*2))/2  FROM TMP)AS AVG, " + 
				"    (SELECT COUNT(SCORE) FROM TMP) AS CNT " + 
				"FROM( " + 
				"    SELECT * " + 
				"    FROM TMP " + 
				"    PIVOT (COUNT(SCORE) FOR SCORE IN(1 ,2 ,3 ,4 ,5 )) " + 
				")A";
		Map<String, Object> map = null;
		Connection conn = DBManager.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,psidx);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				map = new HashMap<>();
				List<Integer> list = new ArrayList<>();
				list.add(rs.getInt("5"));
				list.add(rs.getInt("4"));
				list.add(rs.getInt("3"));
				list.add(rs.getInt("2"));
				list.add(rs.getInt("1"));
				map.put("avg", rs.getString("AVG"));
				map.put("cnt", rs.getString("CNT"));
				map.put("score", list);
			}
			conn.close();
			pstmt.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
		
	}


}
