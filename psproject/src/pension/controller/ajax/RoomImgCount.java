package pension.controller.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Util;
import pension.dao.PensionDao;

@WebServlet("/imgCnt")
public class RoomImgCount extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int rmidx = Util.getParameterNumber(req.getParameter("rmidx"));
		resp.getWriter().print(new PensionDao().roomImgCount(rmidx));
	}

}
