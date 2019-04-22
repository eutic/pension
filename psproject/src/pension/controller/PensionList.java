package pension.controller;

import java.io.IOException;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.ConstPool;
import common.Pagination;
import common.Util;
import pension.dao.PensionDao;
import pension.vo.PensionVo;

/**
 * 
 * @author 장우영
 * 
 * 
 * -
 * 
 * @param type 1 : 전체목록 2: 스파, 3: 풀빌라, 4 : 워크샵, 5 : 전체검색
 */

@WebServlet("/list.do")
public class PensionList extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int type = Util.getParameterNumber(req.getParameter("type"));
		
		
		PensionDao dao = new PensionDao();
		List<PensionVo> list = dao.readList();
		req.setAttribute("list", list);
		
		// 첫 페이지 로드시 기본적으로 보여줄 리스트
		
		// 1. 일반 목록
		// 검색어
		
		// 2. 검색 결과
		// 검색어
		String search = req.getParameter("search");
		if (search==null) {
			search = "가평";
		}
		
		int page = Util.getParameterNumber(req.getParameter("page"));
		
		int num = dao.selectPensionCount(search, type); // 총게시물 수
		
		Pagination pagination = new Pagination(12, 10, num, page);
		List<PensionVo> items = dao.selectPension(search, type, pagination.getFrom(), pagination.getTo());
		
		req.setAttribute("type", type);
		req.setAttribute("search", search);
		req.setAttribute("items", items);
		req.setAttribute("num", num);
		req.setAttribute("page", pagination);
		req.getRequestDispatcher(ConstPool.PENSION_PATH +"/PensionList.jsp").forward(req, resp);
	}
}
