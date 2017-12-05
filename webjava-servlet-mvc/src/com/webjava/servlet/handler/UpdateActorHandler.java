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
public class UpdateActorHandler implements HttpRequestHandler {

    /*
     * (non-Javadoc)
     * 
     * @see com.nttdata.mvc.HttpRequestHandler#handle(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	ActorService service = new ActorService();
	String doUpdate = request.getParameter("doUpdate");
	try {
	    if (!"".equals(doUpdate) && null != doUpdate) {

		doUpdate(request, response, service);

	    } else {
		// TODO
	    }
	} catch (ServiceException e) {
	    System.out.println(e.getMessage());
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
    private void doUpdate(HttpServletRequest request, HttpServletResponse response, ActorService service)
	    throws ServiceException, ServletException, IOException {
	RequestDispatcher reqDispatcher;
	// 3. insert actor to DB
	String actorId = request.getParameter("actorId");
	String lastName = request.getParameter("lName");
	// validate
	if ((!"".equals(actorId) && null != actorId) && (!"".equals(lastName) && null != lastName)) {
	    Actor newActor = new Actor(actorId, lastName);
	    if (actorId.matches("[0-9]*")) {
		int id = Integer.parseInt(actorId);
		int row = service.updateLastName(id, lastName);
		if (row > 0) {
		    String firstName = service.findActorByID(id).getFirstName();
		    newActor.setFirstName(firstName);
		    newActor.setActorId(Integer.parseInt(actorId));
		    // pass data
		    request.setAttribute("msg", "Successfully Updated!");
		    request.setAttribute("actor", newActor);
		} else {
		    // if actor does not exist
		    request.setAttribute("msg", "Fail to Update!");
		}
	    } else {
		request.setAttribute("msg", "Please type number");
	    }

	} else {
	    request.setAttribute("msg", "Actor ID and Last Name cannot be empty!");
	}
	// forward to JSP
	reqDispatcher = request.getRequestDispatcher(ViewNames.UPDATE_ACTOR);
	reqDispatcher.forward(request, response);
    }
}
