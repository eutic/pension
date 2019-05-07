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
@WebServlet("/boardWrite")
public class BoardWrite extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int boardIdx = Util.getParameterNumber(req.getParameter("idx"));
		req.getRequestDispatcher(ConstPool.BOARD_PATH+"/write.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(req.getSession() != null && req.getSession().getAttribute("member") != null
				&& ((Member)req.getSession().getAttribute("member")).getRating() ==2) {
			
		String title = req.getParameter("title");
		String cont = req.getParameter("cont");
		String email = ((Member)req.getSession().getAttribute("member")).getEmail();
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setCont(cont);
		vo.setEmail(email);
		
		new BoardDao().write(vo);
		} 
		resp.sendRedirect("boardList");
	}
	
}
