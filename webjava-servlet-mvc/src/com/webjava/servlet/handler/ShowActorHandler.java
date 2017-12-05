/**
 * 
 */
package com.webjava.servlet.handler;

import java.io.IOException;
import java.util.List;

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
public class ShowActorHandler implements HttpRequestHandler {

    /*
     * (non-Javadoc)
     * 
     * @see com.nttdata.mvc.HttpRequestHandler#handle(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// show Actors
	String doShow = request.getParameter("doShow");
	ActorService service = new ActorService();
	// select data from DB and show
	// 1. select by ID
	if (null != doShow && !"".equals(doShow)) {
	    doShow(request, response, service, doShow);
	} else {
	    // TODO
	}
    }

    /**
     * @param request
     * @param response
     * @param doShow
     * @param service
     * @throws ServletException
     * @throws IOException
     */
    private void doShow(HttpServletRequest request, HttpServletResponse response, ActorService service, String doShow)
	    throws ServletException, IOException {
	RequestDispatcher reqDispatcher;
	try {
	    int totalActors = service.getActorCount();
	    // pagination
	    if (totalActors > 20) {
		request.setAttribute("totalActors", totalActors);
		request.setAttribute("servletName", "showActor");
		// first page always show 20
		if (doShow.matches("[0-9]*")) {
		    List<Actor> actors = service.getActors(Integer.parseInt(doShow));
		    request.setAttribute("actorList", actors);
		} else {
		    // TODO handle here
		    reqDispatcher = request.getRequestDispatcher(ViewNames.ERROR);
		    reqDispatcher.forward(request, response);
		}

	    } else if (totalActors == 0) {
		request.setAttribute("msg", "Sorry, there is no record to show.");
	    } else {
		// no calc when there is only 20 orginal record
		List<Actor> allActors = service.getActors();
		request.setAttribute("actorList", allActors);
	    }
	    // forward to JSP
	    reqDispatcher = request.getRequestDispatcher(ViewNames.SHOW_ACTOR);
	    reqDispatcher.forward(request, response);
	} catch (ServiceException e1) {
	    System.out.println(e1);
	}
    }
}
