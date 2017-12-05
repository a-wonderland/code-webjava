/**
 * 
 */
package com.webjava.servlet.handler;

import java.io.IOException;
import java.util.regex.Pattern;

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
public class LoginHandler implements HttpRequestHandler {

    /*
     * (non-Javadoc)
     * 
     * @see com.nttdata.mvc.HttpRequestHandler#handle(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String usrId = request.getParameter("usrId");
	String password = request.getParameter("password");

	if ((null != usrId && !"".equals(usrId)) && (null != password && !"".equals(password))) {
	    doLogin(request, response, usrId, password);
	} else {
	    request.setAttribute("msg", "User ID and password cannot be empty");
	    RequestDispatcher rd = request.getRequestDispatcher(ViewNames.LOGIN);
	    rd.forward(request, response);
	}

    }

    /**
     * @param request
     * @param response
     * @param usrId
     * @param password
     * @throws ServletException
     * @throws IOException
     */
    private void doLogin(HttpServletRequest request, HttpServletResponse response, String usrId, String password)
	    throws ServletException, IOException {
	RequestDispatcher rd;
	int id = 0;
	// validate
	CustomerService service = new CustomerService();
	if (Pattern.matches("[0-9]*", usrId)) {
	    id = Integer.parseInt((String) usrId);

	} else {
	    request.setAttribute("msg", "User ID cannot be character!");
	    rd = request.getRequestDispatcher(ViewNames.LOGIN);
	    rd.forward(request, response);
	    return;
	}

	try {
	    Customer user = new Customer(id, password);
	    Customer dbUser = service.getUserById(id);
	    if (dbUser != null) {
		// validate password
		if (dbUser.equals(user)) {
		    // save in session
		    user.setPassword("");
		    user.setStoreId(dbUser.getStoreId());
		    request.getSession().setAttribute("userAuth", user);
		    // success
		    rd = request.getRequestDispatcher(ViewNames.HOME);
		    rd.forward(request, response);
		} else {
		    request.setAttribute("msg", "username or password error!");
		    rd = request.getRequestDispatcher(ViewNames.LOGIN);
		    rd.forward(request, response);
		}
	    } else {
		request.setAttribute("msg", "User ID does not exist!");
		rd = request.getRequestDispatcher(ViewNames.LOGIN);
		rd.forward(request, response);
	    }
	} catch (DaoException e) {
	    System.out.println(e.getMessage());
	}
    }

}
