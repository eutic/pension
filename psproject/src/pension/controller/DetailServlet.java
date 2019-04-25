package pension.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.ConstPool;
import pension.dao.PensionDao;
import pension.vo.ChargeVo;
import pension.vo.PensionVo;
import pension.vo.RoomVo;
import pension.vo.RoomimgVo;

@WebServlet("/detail.do")
public class DetailServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PensionDao dao = new PensionDao();
			String psidx = req.getParameter("psidx");
			String date = req.getParameter("startdate");
			List<Map<String, String>> list = dao.detailPension(psidx, date);
			
			req.setAttribute("dao", list.get(0));
			req.setAttribute("list", list);
			req.getRequestDispatcher(ConstPool.PENSION_PATH + "/PensionDetail.jsp").forward(req, resp);
		

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}