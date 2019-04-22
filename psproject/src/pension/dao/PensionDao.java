
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
import java.util.List;

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
		
		sql.append("SELECT * FROM ( ");
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
		sql.append("ORDER BY PSIDX DESC ");
		
		ArrayList<PensionVo> list = new ArrayList<>();
		Connection conn = DBManager.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, from);
			pstmt.setInt(2, to);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				PensionVo vo = new PensionVo();
				vo.setOridx(rs.getString(1));
				vo.setPsidx(rs.getInt(2));
				vo.setPreaddr(rs.getString(3));
				vo.setPstitle(rs.getString(4));
				list.add(vo);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public PensionVo detailPension(String psidx) {
		String sql = "select PSIDX,ORIDX, PSURL , PREADDR ,CURADDR,PSTITLE,CALLTEL,PICKUP,longitude,latitude from pension where psidx=? ";
		PensionVo vo = null;
		Connection conn;
		PreparedStatement pstmt;
		ResultSet rs;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, psidx);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				vo = new PensionVo();
				vo.setPsidx(rs.getInt(1));
				vo.setOridx(rs.getString(2));
				vo.setPsurl(rs.getString(3));
				vo.setPreaddr(rs.getString(4));
				vo.setCuraddr(rs.getString(5));
				vo.setPstitle(rs.getString(6));
				vo.setCalltel(rs.getString(7));
				vo.setPickup(rs.getString(8));
				vo.setLongitude(rs.getString(9));
				vo.setLatitude(rs.getString(10));
			}
			conn.close();
			pstmt.close();
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}

	public ArrayList<RoomVo> detailRoom(String psidx) {
		String sql = "SELECT PSIDX,RMIDX,RMTITLE,RMSIZE,RMPERMIN,RMPERMAX,RORDER FROM ROOM " + " WHERE PSIDX = ?  "
				+ " ORDER BY RORDER  ";
		RoomVo vo = null;
		Connection conn;
		PreparedStatement pstmt;
		ResultSet rs;
		ArrayList<RoomVo> RoomList = new ArrayList<>();
		try {

			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, psidx);
			rs = pstmt.executeQuery();
			RoomList = new ArrayList<RoomVo>();

			while (rs.next()) {
				vo = new RoomVo();
				vo.setPsidx(rs.getInt(1));
				vo.setRmidx(rs.getInt(2));
				vo.setRmtitle(rs.getString(3));
				vo.setRmsize(rs.getString(4));
				vo.setRmpermin(rs.getInt(5));
				vo.setRmpermax(rs.getInt(6));
				vo.setRorder(rs.getInt(7));
				vo.setChargeVos(getRoomCharge(rs.getInt(2)));
				vo.setRoomimgVos(roominlist(rs.getInt(2)));
				
				RoomList.add(vo);

			}
			conn.close();
			pstmt.close();
			rs.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return RoomList;
	}

	public ArrayList<ChargeVo> getRoomCharge(int rmidx) {
		String sql = "select rmidx,period,week,fri,weekend from charge where rmidx=?  ";
		ChargeVo vo = null;
		ResultSet rs;
		Connection conn;
		PreparedStatement pstmt;
		ArrayList<ChargeVo> charge = new ArrayList<>();
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rmidx);
			rs = pstmt.executeQuery();
			charge = new ArrayList<ChargeVo>();
			while (rs.next()) {
				vo = new ChargeVo();
				vo.setRmidx(rs.getInt(1));
				vo.setPeriod(rs.getInt(2));
				vo.setWeek(rs.getInt(3));
				vo.setFri(rs.getInt(4));
				vo.setWeekend(rs.getInt(5));
				charge.add(vo);
			}
			conn.close();
			pstmt.close();
			rs.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return charge;
	}

	public ArrayList<RoomimgVo> roominlist(int rmidx) {
		String sql = "SELECT RMIDX, RMIMGIDX, RMIMGINURL  FROM ROOMIMG WHERE RMIDX =? ORDER BY RMIMGIDX  ";
		RoomimgVo vo = null;
		ArrayList<RoomimgVo> room = new ArrayList<>();
		ResultSet rs;
		Connection conn;
		PreparedStatement pstmt;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rmidx);
			rs = pstmt.executeQuery();
			room = new ArrayList<RoomimgVo>();
			while (rs.next()) {
				vo = new RoomimgVo();
				vo.setRmidx(rs.getInt(1));
				vo.setRmimgidx(rs.getInt(2));
				vo.setRmimginurl(rs.getString(3));
				room.add(vo);

			}
			conn.close();
			pstmt.close();
			rs.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return room;
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
