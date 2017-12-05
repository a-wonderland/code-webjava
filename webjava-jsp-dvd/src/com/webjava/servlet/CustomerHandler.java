package com.webjava.servlet;

import java.io.IOException;

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
 * Servlet implementation class AddCustomerHandler
 */
@WebServlet("/addCustomer")
public class CustomerHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CustomerHandler() {
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
		RequestDispatcher reqDispatcher;
		CustomerService service = new CustomerService();
		// 3. insert actor to DB
		// TODO valide parse
		int storeId = Integer.parseInt((String) request.getParameter("storeId"));
		String firstName = request.getParameter("fName");
		String lastName = request.getParameter("lName");
		String email = request.getParameter("email");
		int addressId = Integer.parseInt((String) request.getParameter("addressId"));
		String password = request.getParameter("password");

		// TODO validate
		if ((!"".equals(firstName) && null != firstName) && (!"".equals(lastName) && null != lastName)) {
			Customer customer = new Customer(storeId, firstName, lastName, email, addressId, password);
			long row;
			try {
				row = service.insertCustomer(customer);
				if (row > 0) {
					request.setAttribute("msg", "Successfully Added!");
					customer.setId(Integer.parseInt((String.valueOf(row))));
					request.setAttribute("customer", customer);
				} else {
					request.setAttribute("msg", "Fail to Added!");
				}
			} catch (DaoException e) {
				e.printStackTrace();
			}
		} else {
			request.setAttribute("msg", "First Name and Last Name cannot be empty!");
		}
		reqDispatcher = request.getRequestDispatcher("/view/addCustomer.jsp");
		reqDispatcher.forward(request, response);
	}

}
