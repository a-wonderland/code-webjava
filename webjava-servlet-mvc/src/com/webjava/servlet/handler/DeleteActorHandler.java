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
public class DeleteActorHandler implements HttpRequestHandler {

    /*
     * (non-Javadoc)
     * 
     * @see com.nttdata.mvc.HttpRequestHandler#handle(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	ActorService service = new ActorService();
	String doDelete = request.getParameter("doDelete");
	try {
	    if (!"".equals(doDelete) && null != doDelete) {
		doDelete(request, response, service);
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
    private void doDelete(HttpServletRequest request, HttpServletResponse response, ActorService service)
	    throws ServiceException, ServletException, IOException {
	RequestDispatcher reqDispatcher;
	// 4. delete
	String actorId = request.getParameter("actorId");
	// validate
	if (!"".equals(actorId) && null != actorId) {
	    if (actorId.matches("[0-9]*")) {
		Actor actor = service.findActorByID(Integer.parseInt(actorId));
		// if actor does not exist
		if (null == actor) {
		    // if actor does not exist
		    request.setAttribute("msg", "Actor does not exist!");
		} else {
		    int row = service.deleteActor(Integer.parseInt(actorId));
		    if (row > 0) {
			request.setAttribute("msg", "Successfully Deleted!");
			request.setAttribute("actor", actor);
		    } else {
			request.setAttribute("msg", "Fail to Delete!");
		    }
		}
	    } else {
		request.setAttribute("msg", "Please type number");
	    }

	} else {
	    request.setAttribute("msg", "Actor ID cannot be empty!");
	}
	// forward to JSP
	reqDispatcher = request.getRequestDispatcher(ViewNames.DELETE_ACTOR);
	reqDispatcher.forward(request, response);
    }

}
