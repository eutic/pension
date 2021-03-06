package member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.dao.MemberDao;
import member.vo.Member;

@WebServlet("/resign")


public class ResignServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session  =req.getSession();
		if (session.getAttribute("member") == null) {
			resp.sendRedirect("modify");
		}
		
		Member vo = (Member)session.getAttribute("member");
		new MemberDao().resign(vo.getEmail());
		session.invalidate();
		
		resp.sendRedirect("index");
	}
	}

	



	

