package common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import common.util.ConstPool;

@WebFilter("/*")
public class SetDefaultValueFilter implements Filter{
	FilterConfig fc;
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		fc=arg0;
	}
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		arg0.getServletContext().setAttribute("web_path",ConstPool.WEP_PATH);
		arg0.getServletContext().setAttribute("member_path",ConstPool.WEP_PATH);
		arg0.getServletContext().setAttribute("board_path",ConstPool.WEP_PATH);
		arg0.getServletContext().setAttribute("reserve_path",ConstPool.WEP_PATH);
		arg0.getServletContext().setAttribute("pension_path",ConstPool.WEP_PATH);
		arg0.getServletContext().setAttribute("favi_path",ConstPool.WEP_PATH);
		arg2.doFilter(arg0, arg1);
	}

	@Override
	public void destroy() {}
}

