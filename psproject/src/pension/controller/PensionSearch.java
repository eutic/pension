package pension.controller;

import java.io.IOException;
import java.util.List;
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
 * @author

 */

@WebServlet("/search")
public class PensionSearch extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String search = req.getParameter("search");
		
		if (search.contains("경기도")) {
			search = search.replaceFirst("경기도", "경기");
		}
		
		int page = Util.getParameterNumber(req.getParameter("page"));
		
		int rowSize = 12; // 한페이지에 보여줄 개수 
		int block = 10; // 한페이지에 보여줄 페이징범위
		PensionDao dao = new PensionDao();
		int num = dao.searchGetTotal(search); // 총게시물 수
		
		Pagination pagination = new Pagination(12, 10, num, page);
		
		List<PensionVo> items = dao.searchGetList(search,pagination.getFrom(),pagination.getTo());
		
		req.setAttribute("search", items);
		req.setAttribute("title", search);
		req.setAttribute("num", num);
		req.setAttribute("page", pagination);
		req.getRequestDispatcher(ConstPool.PENSION_PATH +"/pensionSearch.jsp").forward(req, resp);
	}
	
	public static void main(String[] args) {
//		System.out.println((int)Math.ceil((float)39/10)*10);
		System.out.println();
	}
}

