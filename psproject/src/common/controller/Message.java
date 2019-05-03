package common.controller;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/message")
public class Message extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String msg = req.getParameter("msg");
		if(msg==null) {
			
		} else if(msg.equals("notAuth")) {
			req.setAttribute("notAuth", true);
			req.setAttribute("email", req.getParameter("email"));
		}
		
		req.getRequestDispatcher("source/404.jsp").forward(req, resp);
	}
	
}
