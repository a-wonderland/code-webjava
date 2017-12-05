package com.webjava.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.webjava.jdbc.dvd.domain.Actor;
import com.webjava.jdbc.dvd.domain.ActorDetailDTO;
import com.webjava.jdbc.dvd.service.ActorService;
import com.webjava.jdbc.dvd.service.ServiceException;

/**
 * Servlet implementation class ActorSearchHandler
 */
@WebServlet({ "/searchActor" })
public class ActorSearchHandler extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActorSearchHandler() {
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
	String keyword = request.getParameter("keyword");
	// String doShow = request.getParameter("doShow");
	ActorService service = new ActorService();

	// detail info
	String detailId = request.getParameter("detailId");
	if (null != detailId && !"".equals(detailId)) {
	    doSearchDetail(request, response, service, Integer.parseInt(String.valueOf(detailId)));
	    return;
	}

	if (null != keyword && !"".equals(keyword)) {
	    HttpSession session = request.getSession();
	    session.setAttribute("keyword", keyword);
	    // do search by user input
	    String searchBy = request.getParameter("searchBy");
	    if (null != searchBy && !"".equals(searchBy)) {
		doSearch(request, response, keyword, service, searchBy);
	    }

	} else {
	    request.setAttribute("msg", "You can't not leave keyword empty");
	    // forward to JSP
	    RequestDispatcher reqDispatcher = request.getRequestDispatcher("/view/searchActor.jsp");
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
	    findByName(request, response, service, keyword, "", false); break;
	case "lName":
	    findByName(request, response, service, "", keyword, false); break;
	case "fullName":
	    keyword = keyword.replaceAll("(\\r\\n|\\n\\r|\\r|\\n|\\t)", "");
	    findByName(request, response, service, keyword, "", true); break;
	case "actorId":
	    findById(request, response, service, keyword); break;
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
	    reqDispatcher = request.getRequestDispatcher("/view/searchActorDetail.jsp");
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
	    reqDispatcher = request.getRequestDispatcher("/view/searchActor.jsp");
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
	    }else {
		 request.setAttribute("msg", "Please type number");
	    }

	} catch (NumberFormatException e) {
	    e.printStackTrace();
	} catch (ServiceException e) {
	    e.printStackTrace();
	}

	// forward to JSP
	reqDispatcher = request.getRequestDispatcher("/view/searchActor.jsp");
	reqDispatcher.forward(request, response);
    }
}
