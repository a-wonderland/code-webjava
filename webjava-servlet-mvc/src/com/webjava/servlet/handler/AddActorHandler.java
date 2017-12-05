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
import com.webjava.jdbc.dvd.domain.Actor;
import com.webjava.jdbc.dvd.service.ActorService;
import com.webjava.jdbc.dvd.service.ServiceException;

/**
 * @author sumyathtarwai
 *
 */
public class AddActorHandler implements HttpRequestHandler {

    /*
     * (non-Javadoc)
     * 
     * @see com.nttdata.mvc.HttpRequestHandler#handle(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	ActorService service = new ActorService();
	String doAdd = request.getParameter("doAdd");

	if (!"".equals(doAdd) && null != doAdd) {
	    try {
		doAdd(request, response, service);
	    } catch (ServiceException e) {
		System.out.println(e.getMessage());
	    }
	} else {
	    // TODO
	}

    }

    /**
     * @param request
     * @param response
     * @param service
     * @throws ServiceException
     * @throws ServletException
     * @throws IOException
     */
    private void doAdd(HttpServletRequest request, HttpServletResponse response, ActorService service)
	    throws ServiceException, ServletException, IOException {
	RequestDispatcher reqDispatcher;
	// 3. insert actor to DB
	String firstName = request.getParameter("fName");
	String lastName = request.getParameter("lName");
	// validate
	if ((!"".equals(firstName) && null != firstName) && (!"".equals(lastName) && null != lastName)) {
	    Actor newActor = new Actor(firstName, lastName);
	    long row = service.insertActor(newActor);
	    if (row > 0) {
		request.setAttribute("msg", "Successfully Added!");
		newActor.setActorId(Integer.parseInt((String.valueOf(row))));
		request.setAttribute("actor", newActor);
	    } else {
		request.setAttribute("msg", "Fail to Added!");
	    }
	} else {
	    request.setAttribute("msg", "First Name and Last Name cannot be empty!");
	}

	reqDispatcher = request.getRequestDispatcher(ViewNames.ADD_ACTOR);
	reqDispatcher.forward(request, response);
    }

}
