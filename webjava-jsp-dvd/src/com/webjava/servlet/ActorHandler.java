package com.webjava.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webjava.jdbc.dvd.domain.Actor;
import com.webjava.jdbc.dvd.service.ActorService;
import com.webjava.jdbc.dvd.service.ServiceException;

/**
 * Servlet implementation class ShowActor
 */
@WebServlet({ "/showActor", "/addActor", "/updateActor", "/deleteActor" })
public class ActorHandler extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActorHandler() {
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
	// response.sendRedirect("error.html");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	// show Actors
	String doShow = request.getParameter("doShow");
	ActorService service = new ActorService();
	// is it insert?
	String doAdd = request.getParameter("doAdd");
	String doUpdate = request.getParameter("doUpdate");
	String doDelete = request.getParameter("doDelete");
	try {
	    // select data from DB and show
	    // 1. select by ID
	    if (null != doShow && !"".equals(doShow)) {

		doShow(request, response, service);

	    } else if (!"".equals(doAdd) && null != doAdd) {

		doAdd(request, response, service);

	    } else if (!"".equals(doUpdate) && null != doUpdate) {

		doUpdate(request, response, service);

	    } else if (!"".equals(doDelete) && null != doDelete) {

		doDelete(request, response, service);
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
	reqDispatcher = request.getRequestDispatcher("/view/deleteActor.jsp");
	reqDispatcher.forward(request, response);
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
	    }else{
		 request.setAttribute("msg", "Please type number");
	    }

	} else {
	    request.setAttribute("msg", "Actor ID and Last Name cannot be empty!");
	}
	// forward to JSP
	reqDispatcher = request.getRequestDispatcher("/view/updateActor.jsp");
	reqDispatcher.forward(request, response);
    }

    /**
     * @param request
     * @param response
     * @param doShow
     * @param service
     * @throws ServletException
     * @throws IOException
     */
    private void doShow(HttpServletRequest request, HttpServletResponse response, ActorService service)
	    throws ServletException, IOException {
	RequestDispatcher reqDispatcher;
	String doShow = request.getParameter("doShow");
	try {
	    int totalActors = service.getActorCount();
	    // pagination
	    if (totalActors > 20) {
		request.setAttribute("totalActors", totalActors);
		request.setAttribute("servletName", "showActor");
		// first page always show 20
		List<Actor> actors = service.getActors(Integer.parseInt(doShow));
		request.setAttribute("actorList", actors);
	    } else if (totalActors == 0) {
		request.setAttribute("msg", "Sorry, there is no record to show.");
	    } else {
		// no calc when there is only 20 orginal record
		List<Actor> allActors = service.getActors();
		request.setAttribute("actorList", allActors);
	    }
	    // forward to JSP
	    reqDispatcher = request.getRequestDispatcher("/view/showActor.jsp");
	    reqDispatcher.forward(request, response);
	} catch (ServiceException e1) {
	    System.out.println(e1);
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

	reqDispatcher = request.getRequestDispatcher("/view/addActor.jsp");
	reqDispatcher.forward(request, response);
    }

}
