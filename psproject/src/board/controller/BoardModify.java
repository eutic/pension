package board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.dao.BoardDao;
import board.vo.BoardVo;
import common.util.ConstPool;
import common.util.Util;
import member.vo.Member;
@WebServlet("/boardModify")
public class BoardModify extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int boardIdx = Util.getParameterNumber(req.getParameter("idx"));
		
		BoardVo board = new BoardDao().get(boardIdx);
		
		req.setAttribute("vo", board);
		req.getRequestDispatcher(ConstPool.BOARD_PATH+"/write.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int boardIdx = Util.getParameterNumber(req.getParameter("idx"));
		if(req.getSession() != null && req.getSession().getAttribute("member") != null
				&& ((Member)req.getSession().getAttribute("member")).getRating() ==2) {
			
		String title = req.getParameter("title");
		String cont = req.getParameter("cont");
		String email = ((Member)req.getSession().getAttribute("member")).getEmail();
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setCont(cont);
		vo.setIdx(boardIdx);
		
		new BoardDao().modify(vo);
		} 
		resp.sendRedirect("boardView?idx=" + boardIdx);
	}
	
}