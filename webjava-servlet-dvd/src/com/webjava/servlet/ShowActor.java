package com.webjava.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
@WebServlet({ "/showActor", "/findActor", "/addActor", "/updateActor", "/deleteActor" })
public class ShowActor extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowActor() {
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
	// is it select?
	String doFind = request.getParameter("doFind");

	ActorService service = new ActorService();
	List<Actor> allActors = null;
	// is it insert?
	String doAdd = request.getParameter("doAdd");
	String doUpdate = request.getParameter("doUpdate");
	String doDelete = request.getParameter("doDelete");
	try {
	    // HTML
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    // select data from DB and show
	    // 1. select by ID
	    if (!"".equals(doFind) && null != doFind) {
		// validate
		String actorId = request.getParameter("actorId");
		if (!"".equals(actorId) && null != actorId) {
		    Actor actor = service.findActorByID(Integer.parseInt(actorId));
		    out.write(writeActor(actor));
		} else {
		    out.write("Actor ID cannot be empty!");
		    RequestDispatcher dispatcher = request.getRequestDispatcher("error.html");
		    dispatcher.include(request, response);
		}
	    } else if (!"".equals(doAdd) && null != doAdd) {
		// 3. insert actor to DB
		String firstName = request.getParameter("fName");
		String lastName = request.getParameter("lName");
		// validate
		if ((!"".equals(firstName) && null != firstName) && (!"".equals(lastName) && null != lastName)) {
		    Actor newActor = new Actor(firstName, lastName);
		    long row = service.insertActor(newActor);
		    if (row > 0) {
			out.println("<h2>Successfully Added!</h2>");
			newActor.setActorId(Integer.parseInt((String.valueOf(row))));
			out.write(writeActor(newActor));
		    }
		} else {
		    out.write("First Name and Last Name cannot be empty!");
		    RequestDispatcher dispatcher = request.getRequestDispatcher("error.html");
		    dispatcher.include(request, response);
		}
	    } else if (!"".equals(doUpdate) && null != doUpdate) {
		// 3. insert actor to DB
		String actorId = request.getParameter("actorId");
		String lastName = request.getParameter("lName");
		// validate
		if ((!"".equals(actorId) && null != actorId) && (!"".equals(lastName) && null != lastName)) {
		    Actor newActor = new Actor(actorId, lastName);
		    int row = service.updateLastName(Integer.parseInt(actorId), lastName);
		    if (row > 0) {
			String firstName = service.findActorByID(Integer.parseInt(actorId)).getFirstName();
			out.println("<h2>Successfully Updated!</h2>");
			newActor.setFirstName(firstName);
			newActor.setActorId(Integer.parseInt(actorId));
			out.write(writeActor(newActor));
		    } else {
			// if actor does not exist
			out.write("Actor does not exist!");
			RequestDispatcher dispatcher = request.getRequestDispatcher("error.html");
			dispatcher.include(request, response);
		    }
		} else {
		    out.write("Actor ID and Last Name cannot be empty!");
		    RequestDispatcher dispatcher = request.getRequestDispatcher("error.html");
		    dispatcher.include(request, response);
		}

	    } else if (!"".equals(doDelete) && null != doDelete) {
		// 4. delete
		String actorId = request.getParameter("actorId");
		// validate
		if (!"".equals(actorId) && null != actorId) {
		    Actor actor = service.findActorByID(Integer.parseInt(actorId));
		    // if actor does not exist
		    if (null == actor) {
			// if actor does not exist
			out.write("Actor does not exist!");
			RequestDispatcher dispatcher = request.getRequestDispatcher("error.html");
			dispatcher.include(request, response);
		    } else {
			int row = service.deleteActor(Integer.parseInt(actorId));
			if (row > 0) {
			    out.println("<h2>Successfully Deleted!</h2>");
			    out.write(writeActor(actor));
			}
		    }

		} else {
		    out.write("Actor ID cannot be empty!");
		    RequestDispatcher dispatcher = request.getRequestDispatcher("error.html");
		    dispatcher.include(request, response);
		}
	    } else {
		// 2. show all actor
		String doShow = request.getParameter("doShow");
		// page
		int total = service.getActorCount();
		if (total > 20) {
		    int isPerPage = 0;
		    int pageNo = 0;
		    for (int i = 1; i <= total; i++) {
			isPerPage = i % 20;
			pageNo = i / 20;

			if (isPerPage == 0) {
			    //  offset = pageNo == 1 ? 0 : offset + 20; -> way 1 calc offset
			    // link display
			    String linkText = "";
			    if (i < total) {
				linkText = pageNo + " - ";
			    } else {
				linkText = pageNo + "";
			    }
			    
			    out.write("<a href='showActor?doShow=" + (pageNo-1) * 20 + "'>" + linkText + "</a>");
			}
		    }
		    if (null != doShow && !"".equals(doShow)) {
			List<Actor> actors = service.getActors(Integer.parseInt(doShow));
			out.write(writeActor(actors));
		    }
		} else {
		    allActors = new ArrayList<>();
		    allActors = service.getActors();
		    out.write(writeActor(allActors));
		}

	    }
	    out.write("</table>");
	    out.print("</tbody></html>");
	    out.close();
	} catch (ServiceException e) {
	    System.out.println(e.getMessage());
	}
    }

    /**
     * @param actors
     * @return
     */
    private String writeActor(List<Actor> actors) {
	StringBuilder sb = new StringBuilder();
	sb.append("<html><head>");
	sb.append("<style>table,th,tr,td {border-collapse: collapse;border: 1px solid;}</style>");
	sb.append("</head><tbody>");
	sb.append("<h2>Actor Record</h2>");
	sb.append("<a href='index.html'>Go Back</a></br>");
	sb.append("</body></html>");
	sb.append("<table>");
	sb.append("<thead><th>actor_id</th>");
	sb.append("<th>first_name</th>");
	sb.append("<th>last_name</th></thead>");
	for (Actor actor : actors) {
	    sb.append("<tr>");
	    sb.append("<td>" + actor.getActorId() + "</td>");
	    sb.append("<td>" + actor.getFirstName() + "</td>");
	    sb.append("<td>" + actor.getLastName() + "</td>");
	    sb.append("</tr>");
	}
	return sb == null ? "" : new String(sb);
    }

    /**
     * @param actors
     * @param out
     * @return
     */
    private String writeActor(Actor actor) {
	StringBuilder sb = new StringBuilder();
	sb.append("<html><head>");
	sb.append("<style>table,th,tr,td {border-collapse: collapse;border: 1px solid;}</style>");
	sb.append("</head><tbody>");
	sb.append("<h2>Actor Record</h2>");
	sb.append("<a href='index.html'>Go Back</a></br>");
	sb.append("</body></html>");
	sb.append("<table>");
	sb.append("<thead><th>actor_id</th>");
	sb.append("<th>first_name</th>");
	sb.append("<th>last_name</th></thead>");
	sb.append("<tr>");
	sb.append("<td>" + actor.getActorId() + "</td>");
	sb.append("<td>" + actor.getFirstName() + "</td>");
	sb.append("<td>" + actor.getLastName() + "</td>");
	sb.append("</tr>");
	return sb == null ? "" : new String(sb);
    }

}
