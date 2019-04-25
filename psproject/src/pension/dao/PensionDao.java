
/**
 * 
 *  @author 장우영
 *	
 *
 *
 *
 *  - 염윤호 
 *  19-03-29
 *  싱글턴 패턴 만들었음
 *  index에서 쓰일 indexList 메서드 만듬
 *  
 *  19-04-01
 *  getList 메서드 쿼리문변경
 *  
 */

package pension.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.DBManager;
import pension.vo.CartVo;
import pension.vo.ChargeVo;
import pension.vo.PensionVo;
import pension.vo.RoomVo;
import pension.vo.RoomimgVo;

public class PensionDao {
public static PensionDao dao = new PensionDao();
	
	public static PensionDao getInstance() {
		return dao;
	}

	

	public List<PensionVo> readList() {
		String sql = "select  PSURL,PSTITLE,preaddr,psidx,oridx from  PENSION " + " WHERE ROWNUM < 9 "
				+ " order by ORIDX  ";
		Connection conn;
		PreparedStatement pstmt;

		ResultSet rs;
		PensionVo vo = null;
		ArrayList<PensionVo> list = new ArrayList<>();
		conn = DBManager.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo = new PensionVo();
				vo.setPsurl(rs.getString(1));
				vo.setPstitle(rs.getString(2));
				vo.setPreaddr(rs.getString(3));
				vo.setPsidx(rs.getInt(4));
				vo.setOridx(rs.getString(5));
				list.add(vo);

			}
			conn.close();
			pstmt.close();
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public int selectPensionCount(String search, int type) {
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT count(*) FROM ( ");
		sql.append("SELECT oridx,psidx,preaddr,pstitle, ");
		sql.append("(SELECT MIN(RMSIZE) FROM ROOM R ");
		sql.append("WHERE R.PSIDX = P.PSIDX GROUP BY PSIDX) AS RMSIZE ");
		sql.append("FROM pension P ");
		sql.append("WHERE preaddr like '%"+ search +"%' ");
		if(type == 2 )
			sql.append("AND PSTITLE LIKE '%스파%' "); //
		if(type == 3 )
			sql.append("AND PSTITLE LIKE '%풀빌라%' "); //
		
		sql.append(") ");
		if(type == 4)
			sql.append(" WHERE RMSIZE >= 30 "); //
		Connection conn = DBManager.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public List<PensionVo> selectPension(String search, int type, int from, int to) {
		StringBuilder sql = new StringBuilder();
		
		sql.append(" SELECT * FROM ( ");
		sql.append(" SELECT ROWNUM RN, A.* FROM ( ");
		sql.append(" SELECT P.PSIDX, ORIDX, PREADDR, PSTITLE, MIN(RMSIZE) RMSIZE, MIN(WEEK) LOWPRICE ");
		sql.append(" FROM PENSION P ");
		sql.append(" JOIN ROOM R ON R.PSIDX = P.PSIDX ");
		sql.append(" JOIN CHARGE C ON R.RMIDX = C.RMIDX AND WEEK != 0 ");
		
		sql.append(" WHERE PREADDR LIKE '%" + search +"%' ");
		if(type == 2 )
		sql.append(" AND PSTITLE LIKE '%스파%' ");
		if(type == 3 )
		sql.append(" AND PSTITLE LIKE '%풀빌라%' ");
		sql.append(" GROUP BY P.PSIDX, ORIDX, PREADDR, PSTITLE ");
		sql.append("	    ) A ");
		if(type == 4 )
		sql.append(" WHERE RMSIZE > 30 ");
		sql.append(" ORDER BY 2 ");
		sql.append(" ) WHERE RN BETWEEN ? AND ? ");
		
		
/*		sql.append("SELECT * FROM ( ");
		sql.append("SELECT oridx,psidx,preaddr,pstitle, RMSIZE, ROWNUM RN FROM ( ");
		sql.append("SELECT oridx,psidx,preaddr,pstitle, ");
		sql.append("(SELECT MIN(RMSIZE) FROM ROOM R ");
		sql.append("WHERE R.PSIDX = P.PSIDX GROUP BY PSIDX) AS RMSIZE ");
		sql.append("FROM pension P ");
		sql.append("WHERE preaddr like '%"+ search +"%' ");
		if(type == 2 )
			sql.append("AND PSTITLE LIKE '%스파%' "); //
		if(type == 3 )
			sql.append("AND PSTITLE LIKE '%풀빌라%' "); //
		
		sql.append(") ");
		if(type == 4)
			sql.append(" WHERE RMSIZE >= 30 "); //
		
		sql.append(") ");
		sql.append("WHERE RN BETWEEN ? AND ? ");
		sql.append("ORDER BY PSIDX DESC ");*/
		
		ArrayList<PensionVo> list = new ArrayList<>();
		Connection conn = DBManager.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, from);
			pstmt.setInt(2, to);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				PensionVo vo = new PensionVo();
				vo.setOridx(rs.getString("oridx"));
				vo.setPsidx(rs.getInt("psidx"));
				vo.setPreaddr(rs.getString("preaddr"));
				vo.setPstitle(rs.getString("pstitle"));
				vo.setLowPrice(rs.getInt("lowprice"));
				list.add(vo);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<Map<String, String>> detailPension(String psidx, String date) {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT P.PSIDX, ORIDX , PREADDR ,CURADDR, PSTITLE, CALLTEL, PICKUP, LONGITUDE, LATITUDE, " );
		sb.append("         R.RMIDX, RMTITLE, RMSIZE, RMPERMIN, RMPERMAX, RORDER, ");
		sb.append("    (SELECT  \r\n" ); 
		sb.append("        CASE WHEN TO_CHAR(TO_DATE(?),'D') < 6 THEN WEEK\r\n" ); 
		sb.append("             WHEN TO_CHAR(TO_DATE(?),'D') = 6 THEN FRI\r\n" ); 
		sb.append("        ELSE WEEKEND\r\n" ); 
		sb.append("        END RESULT\r\n" ); 
		sb.append(" FROM CHARGE WHERE RMIDX = 310\r\n" ); 
		sb.append(" AND PERIOD = (\r\n" ); 
		sb.append("    CASE WHEN \r\n" ); 
		sb.append("        TO_CHAR(TO_DATE(?), 'MM') IN ('07', '08', '12', '01')\r\n" ); 
		sb.append("        THEN 1\r\n" ); 
		sb.append("    ELSE 0\r\n" ); 
		sb.append("    END\r\n" ); 
		sb.append("    )) AS PRICE\r\n" ); 
		sb.append(" FROM PENSION P\r\n" ); 
		sb.append(" JOIN ROOM R ON P.PSIDX = R.PSIDX\r\n" ); 
		sb.append(" WHERE P.PSIDX = ?") ;
		
		PensionVo vo = null;
		Connection conn;
		PreparedStatement pstmt;
		ResultSet rs;
		List<Map<String, String>> list = new ArrayList<>();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, date);
			pstmt.setString(2, date);
			pstmt.setString(3, date);
			pstmt.setString(4, psidx);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Map<String, String> map = new HashMap<>();
				map.put("psidx", rs.getString("psidx"));
				map.put("oridx", rs.getString("oridx"));
				map.put("preaddr", rs.getString("preaddr"));
				map.put("curaddr", rs.getString("curaddr"));
				map.put("pstitle", rs.getString("pstitle"));
				map.put("calltel", rs.getString("calltel"));
				map.put("pickup", rs.getString("pickup"));
				map.put("longitude", rs.getString("longitude"));
				map.put("latitude", rs.getString("latitude"));
				
				map.put("rmidx", rs.getString("rmidx"));
				map.put("rmtitle", rs.getString("rmtitle"));
				map.put("rmsize", rs.getString("rmsize"));
				map.put("rmpermin", rs.getString("rmpermin"));
				map.put("rmpermax", rs.getString("rmpermax"));
				map.put("rorder", rs.getString("rorder"));
				
				map.put("price", rs.getString("price"));
				list.add(map);
				
			}
			conn.close();
			pstmt.close();
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}


	// index에 쓰이는 메서드
	public List<PensionVo> indexList() {
		String sql = "select * from ( " + 
				"    select psidx, pstitle, curaddr, oridx, rownum rn, ( " + 
				"        select min(week) from room r " + 
				"        join charge c on r.rmidx = c.rmidx " + 
				"        where  p2.psidx = r.psidx " + 
				"        and week <> 0 and r.psidx = p2.psidx " + 
				"        group by r.psidx) as min_week " + 
				"    from pension p2 " + 
				"    order by 1 desc " + 
				") " + 
				"order by rn desc";
		
		PensionVo vo = null;
		ArrayList<PensionVo> pensions = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn= DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				vo = new PensionVo();
				vo.setPsidx(rs.getInt(1));
				vo.setPstitle(rs.getString(2));
				vo.setCuraddr(rs.getString(3));
				vo.setOridx(rs.getString(4));
				vo.setLowPrice(rs.getInt(6));
				pensions.add(vo);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {conn.close();}
				if (pstmt != null) {pstmt.close();}
				if (rs != null) {rs.close();}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return pensions;
	}
	
	public int roomImgCount(int rmidx) {
		String sql = "SELECT COUNT(*) FROM ROOMIMG WHERE RMIDX = ?";
		int i = 0;
		ResultSet rs;
		Connection conn;
		PreparedStatement pstmt;
		
		try {
			conn=DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rmidx);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				i = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
	// 장바구니
	public ArrayList<CartVo> getcakt(String email) {
		String sql = "select p.pstitle,p.preaddr,p.oridx ,p.psidx " + 
				"from pension p, cart c, member m " + 
				"where c.psidx=p.psidx and  m.email=c.email  " + 
				"and m.email=? " ;
		Connection conn;
		PreparedStatement pstmt;

		ResultSet rs;
		CartVo vo = null;
		ArrayList<CartVo> cartlist = new ArrayList<>();
		conn = DBManager.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo = new CartVo();
				vo.setPstitle(rs.getString(1));
				vo.setPreaddr(rs.getString(2));
				vo.setOridx(rs.getString(3));
				vo.setPsidx(rs.getInt(4));
				cartlist.add(vo);
			}
			conn.close();
			pstmt.close();
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cartlist;
	}
	
	public int caktinsert(String email,int psidx) {
		boolean b = false; 
		try {
			b = !cartselect(psidx, email);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		String sql = "INSERT INTO CART VALUES (CART_SEQ.nextval,?,?) ";
		int ret = 0;		
		Connection conn;
		PreparedStatement pstmt;
		conn = DBManager.getConnection();
		try {
			if(b) { // 없을때
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, email);
				pstmt.setInt(2, psidx);
				ret = pstmt.executeUpdate();
				pstmt.close();
			}
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	public void cartdelete(int psidx,String email) {
		String sql = " DELETE CART WHERE psidx = ? ";
		Connection conn;
		PreparedStatement pstmt;
		
		conn = DBManager.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,psidx);
				
			pstmt.executeUpdate();
			
			conn.close();
			pstmt.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
		public boolean cartselect(int psidx,String email) throws SQLException {
			String sql = " SELECT * FROM CART WHERE psidx = ? and email = ? " ;
			Connection conn;
			PreparedStatement pstmt;
			ResultSet rs;
		
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, psidx);
			pstmt.setString(2, email);
			rs = pstmt.executeQuery();
			boolean ret = rs.next();
			conn.close();
			pstmt.close();
			rs.close();
				
			return ret;
	}
}
