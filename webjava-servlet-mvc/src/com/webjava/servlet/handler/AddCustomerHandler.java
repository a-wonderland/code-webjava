/**
 * 
 */
package com.webjava.servlet.handler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nttdata.mvc.HttpRequestHandler;
import com.webjava.common.ViewNames;
import com.webjava.jdbc.dvd.dao.DaoException;
import com.webjava.jdbc.dvd.domain.Customer;
import com.webjava.jdbc.dvd.service.CustomerService;

/**
 * @author sumyathtarwai
 *
 */
public class AddCustomerHandler implements HttpRequestHandler {

    /*
     * (non-Javadoc)
     * 
     * @see com.nttdata.mvc.HttpRequestHandler#handle(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	RequestDispatcher reqDispatcher;
	CustomerService service = new CustomerService();
	// 3. insert actor to DB
	// TODO valide parse
	String storeIdParam = request.getParameter("storeId");
	String addressIdParam = request.getParameter("addressId");
	String firstName = request.getParameter("fName");
	String lastName = request.getParameter("lName");
	String email = request.getParameter("email");
	String password = request.getParameter("password");
	// TODO validate
	if ((!"".equals(firstName) && null != firstName) && (!"".equals(lastName) && null != lastName)
		&& (!"".equals(email) && null != email) && (!"".equals(password) && null != password)
		&& (!"".equals(storeIdParam) && null != storeIdParam)
		&& (!"".equals(addressIdParam) && null != addressIdParam)) {
	    doAdd(request, response, service, storeIdParam, addressIdParam, firstName, lastName, email, password);
	} else {
	    request.setAttribute("msg", "Please fill for required field.");
	    reqDispatcher = request.getRequestDispatcher(ViewNames.ADD_CUSTOMER);
	    reqDispatcher.forward(request, response);
	}

    }

    /**
     * @param request
     * @param response
     * @param service
     * @param storeIdParam
     * @param addressIdParam
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @throws ServletException
     * @throws IOException
     */
    private void doAdd(HttpServletRequest request, HttpServletResponse response, CustomerService service,
	    String storeIdParam, String addressIdParam, String firstName, String lastName, String email,
	    String password) throws ServletException, IOException {
	RequestDispatcher reqDispatcher;
	if (storeIdParam.matches("[0-9]*") && addressIdParam.matches("[0-9]*")) {
	    int storeId = Integer.parseInt(storeIdParam);
	    int addressId = Integer.parseInt(addressIdParam);

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
	    request.setAttribute("msg", "Please enter number for Store ID and Address ID");
	}
	reqDispatcher = request.getRequestDispatcher(ViewNames.ADD_CUSTOMER);
	reqDispatcher.forward(request, response);
    }
}
