package com.webjava.servlet;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webjava.jdbc.dvd.dao.DaoException;
import com.webjava.jdbc.dvd.domain.Customer;
import com.webjava.jdbc.dvd.service.CustomerService;

/**
 * Servlet implementation class LoginHandler
 */
@WebServlet("/login")
public class LoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String usrId = request.getParameter("usrId");
		String password = request.getParameter("password");

		if ((null != usrId && !"".equals(usrId)) && (null != password && !"".equals(password))) {
			RequestDispatcher rd;
			int id = 0;
			// validate
			CustomerService service = new CustomerService();
			if (Pattern.matches("[0-9]*", usrId)) {
				id = Integer.parseInt((String) usrId);

			} else {
				request.setAttribute("msg", "User ID cannot be character!");
				rd = request.getRequestDispatcher("/view/login.jsp");
				rd.forward(request, response);
			}

			try {
				Customer user = new Customer(id, password);
				Customer dbUser = service.getUserById(id);
				if (dbUser != null) {
					// validate password
					if (dbUser.equals(user)) {
						// save in session
						request.getSession().setAttribute("userAuth", dbUser);
						// success
						rd = request.getRequestDispatcher("/view/home.jsp");
						rd.forward(request, response);
					} else {
						request.setAttribute("msg", "username or password error!");
						rd = request.getRequestDispatcher("/view/login.jsp");
						rd.forward(request, response);
					}
				} else {
					request.setAttribute("msg", "User ID does not exist!");
					rd = request.getRequestDispatcher("/view/login.jsp");
					rd.forward(request, response);
				}
			} catch (DaoException e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
