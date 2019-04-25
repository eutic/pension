package member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.ConstPool;
import member.service.MemberServiceImpl;
import member.vo.Member;
@WebServlet("/signup3_2")
public class Signup3_2 extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.getRequestDispatcher(ConstPool.MEMBER_PATH+"/signup3_2.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String email = req.getParameter("email");
		String pw = req.getParameter("pw");
		String name = req.getParameter("name");
		String address= req.getParameter("address");
		String tel=req.getParameter("tel");
		int rating=Integer.parseInt(req.getParameter("rating"));
		Member vo = new Member();
		vo.setEmail(email);
		vo.setPw(pw);
		vo.setName(name);
		vo.setAddress(address);
		vo.setTel(tel);
		vo.setRating(rating);
		MemberServiceImpl.getInstance().join(vo);
		
		resp.sendRedirect("login?email=" + email);
		
	}

}
