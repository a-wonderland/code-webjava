package com.webjava.servlet;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpFilter;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter(dispatcherTypes = { DispatcherType.REQUEST }, urlPatterns = { "/view/*", "/showActor" })
public class LoginFilter extends HttpFilter implements Filter {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpFilter#HttpFilter()
	 */
	public LoginFilter() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpSession session = httpReq.getSession();
		RequestDispatcher rd;
		// validate already login
		if (null != session.getAttribute("userAuth")) {
			// when existing user request register page
			if (httpReq.getRequestURI().contains("/view/addCustomer.jsp")) {
				rd = request.getRequestDispatcher("/view/home.jsp");
				rd.forward(request, response);
				return;
			}
			// pass the request along the filter chain
			chain.doFilter(request, response);

		} else {
			// request.setAttribute("msg", "xxx");

			if (httpReq.getRequestURI().contains("/view/addCustomer.jsp")) {
				rd = request.getRequestDispatcher("/view/addCustomer.jsp");
			} else {
				rd = request.getRequestDispatcher("/index.jsp");
			}
			rd.forward(request, response);
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
