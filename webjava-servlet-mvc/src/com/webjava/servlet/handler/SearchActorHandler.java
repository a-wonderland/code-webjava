/**
 *
 */
package com.webjava.servlet.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nttdata.mvc.HttpRequestHandler;
import com.webjava.common.ViewNames;
import com.webjava.jdbc.dvd.domain.Actor;
import com.webjava.jdbc.dvd.domain.dto.ActorDetailDTO;
import com.webjava.jdbc.dvd.service.ActorService;
import com.webjava.jdbc.dvd.service.ServiceException;

/**
 * @author sumyathtarwai
 *
 */
public class SearchActorHandler implements HttpRequestHandler {

    /*
     * (non-Javadoc)
     *
     * @see com.nttdata.mvc.HttpRequestHandler#handle(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String keyword = request.getParameter("keyword");
	// String doShow = request.getParameter("doShow");
	ActorService service = new ActorService();
	RequestDispatcher reqDispatcher;

	// detail info
	String detailId = request.getParameter("detailId");
	if (null != detailId && !"".equals(detailId)) {
	    if (detailId.matches("[0-9]*")) {
		doSearchDetail(request, response, service, Integer.parseInt(String.valueOf(detailId)));
		return;
	    } else {
		// TODO handle here
		reqDispatcher = request.getRequestDispatcher(ViewNames.ERROR);
		reqDispatcher.forward(request, response);
	    }
	}

	if (null != keyword && !"".equals(keyword)) {
	    // do search by user input
	    String searchBy = request.getParameter("searchBy");
	    if (null != searchBy && !"".equals(searchBy)) {
		// support case incentive
		doSearch(request, response, keyword.toLowerCase(), service, searchBy);
	    }

	} else {
	    request.setAttribute("msg", "You can't not leave keyword empty");
	    // forward to JSP
	    reqDispatcher = request.getRequestDispatcher(ViewNames.SEARCH_ACTOR);
	    reqDispatcher.forward(request, response);
	}

    }

    /**
     * @param request
     * @param response
     * @param searchName
     * @param service
     * @param searchBy
     * @throws ServletException
     * @throws IOException
     */
    private void doSearch(HttpServletRequest request, HttpServletResponse response, String keyword,
	    ActorService service, String searchBy) throws ServletException, IOException {
	switch (searchBy) {
	case "fName":
	    findByName(request, response, service, keyword, "", false);
	    break;
	case "lName":
	    findByName(request, response, service, "", keyword, false);
	    break;
	case "fullName":
	    keyword = keyword.replaceAll("(\\r\\n|\\n\\r|\\r|\\n|\\t)", "");
	    findByName(request, response, service, keyword, "", true);
	    break;
	case "actorId":
	    findById(request, response, service, keyword);
	    break;
	}
    }

    /**
     * @param request
     * @param response
     * @param service
     * @param detailId
     * @throws IOException
     * @throws ServletException
     */
    private void doSearchDetail(HttpServletRequest request, HttpServletResponse response, ActorService service,
	    int actorID) throws ServletException, IOException {
	RequestDispatcher reqDispatcher;
	Actor actor = null;
	List<ActorDetailDTO> actorDetail = null;
	try {
	    // get selected actor basic info
	    actor = service.findActorByID(actorID);
	    if (null != actor) {
		// get selected actor detail info
		actorDetail = service.findActorDetailbyId(actorID);
		// if there is no register detail
		if (null == actorDetail || actorDetail.isEmpty()) {
		    request.setAttribute("msg", "Sorry, no detail Record Found!");
		} else {
		    request.setAttribute("actorDetail", actorDetail);
		}
		request.setAttribute("actor", actor);
	    } else {
		request.setAttribute("msg", "Something's went wrong!");
	    }

	    // forward to JSP
	    reqDispatcher = request.getRequestDispatcher(ViewNames.SEARCH_ACTOR_DETAIL);
	    reqDispatcher.forward(request, response);

	} catch (ServiceException e) {
	    System.out.println(e.getMessage());
	}

    }

    /**
     * @param request
     * @param response
     * @param service
     * @param searchName
     * @param doShow
     * @throws IOException
     * @throws ServletException
     */
    private void findByName(HttpServletRequest request, HttpServletResponse response, ActorService service,
	    String fName, String lName, boolean isFullName) throws ServletException, IOException {
	RequestDispatcher reqDispatcher;
	List<Actor> allActors = null;

	try {
	    allActors = service.findActorByName(fName, lName, isFullName);

	    if (null == allActors || allActors.isEmpty()) {
		request.setAttribute("msg", "Sorry, no Record Found!");
	    } else {
		request.setAttribute("actorList", allActors);
	    }
	    // forward to JSP
	    reqDispatcher = request.getRequestDispatcher(ViewNames.SEARCH_ACTOR);
	    reqDispatcher.forward(request, response);

	} catch (ServiceException e) {
	    System.out.println(e.getMessage());
	}

    }

    /**
     * @param request
     * @param response
     * @param service
     * @param keyword
     * @throws ServiceException
     * @throws ServletException
     * @throws IOException
     */
    private void findById(HttpServletRequest request, HttpServletResponse response, ActorService service,
	    String keyword) throws ServletException, IOException {
	RequestDispatcher reqDispatcher;
	List<Actor> actors = null;
	try {
	    if (keyword.matches("[0-9]*")) {
		Actor actor = service.findActorByID(Integer.parseInt(keyword));
		if (null == actor) {
		    request.setAttribute("msg", "Sorry, no Record Found!");
		} else {
		    actors = new ArrayList<>();
		    actors.add(actor);
		    request.setAttribute("actorList", actors);
		}
	    } else {
		request.setAttribute("msg", "Please type number");
	    }

	} catch (NumberFormatException e) {
	    e.printStackTrace();
	} catch (ServiceException e) {
	    e.printStackTrace();
	}

	// forward to JSP
	reqDispatcher = request.getRequestDispatcher(ViewNames.SEARCH_ACTOR);
	reqDispatcher.forward(request, response);
    }
}
