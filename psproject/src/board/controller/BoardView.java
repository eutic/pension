package board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.dao.BoardDao;
import board.vo.BoardVo;
import common.util.ConstPool;
import common.util.Pagination;
import common.util.Util;

@WebServlet("/boardView")
public class BoardView extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int boardIdx =Util.getParameterNumber(req.getParameter("idx"));
		
		BoardVo board = new BoardDao().get(boardIdx);

		req.setAttribute("vo", board);
		req.getRequestDispatcher(ConstPool.BOARD_PATH +"/view.jsp").forward(req, resp);
	}

}
