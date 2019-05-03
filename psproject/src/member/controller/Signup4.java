package member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.util.ConstPool;

@WebServlet("/signup4")
public class Signup4 extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.getRequestDispatcher(ConstPool.MEMBER_PATH+"/signup4.jsp").forward(req, resp);
	}
	// 
	// 메일 서버 구축이 어려워 내 계정으로 메일 발송
	// mail 발송 위해 구글 메일 로그인 후 환경설정 -> 전달 및 POP/IMAP 설정 변경
}
