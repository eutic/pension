package member.controller.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.util.Util;

@WebServlet("/sendMail")
public class SendMail extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = req.getParameter("email");
		if(email != null) {
			Util.sendMail(req.getParameter("email"));
			resp.getWriter().print("1");
		} else {
			resp.getWriter().print("0");
		}
	}

}
