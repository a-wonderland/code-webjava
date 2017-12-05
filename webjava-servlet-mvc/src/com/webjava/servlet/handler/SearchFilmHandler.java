/**
 *
 */
package com.webjava.servlet.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nttdata.mvc.HttpRequestHandler;
import com.webjava.jdbc.dvd.domain.Customer;
import com.webjava.jdbc.dvd.domain.Film;
import com.webjava.jdbc.dvd.service.FilmService;
import com.webjava.jdbc.dvd.service.ServiceException;

/**
 * @author sumyathtarwai
 *
 */
public class SearchFilmHandler implements HttpRequestHandler {

	/**
	 *
	 */
	public SearchFilmHandler() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.nttdata.mvc.HttpRequestHandler#handle(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FilmService service = new FilmService();
		List<Film> films = null;
		String keyword = request.getParameter("keyword");
		Map<String, Object> map = new HashMap<>();
		// prepare response
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		// create pretty json
		Gson json = new GsonBuilder().setPrettyPrinting().create();

		// get from session
		HttpSession session = request.getSession();
		if (null != session.getAttribute("userAuth") && (null != keyword && !"".equals(keyword))) {

			if (session.getAttribute("userAuth") instanceof Customer) {
				Customer customer = (Customer) session.getAttribute("userAuth");
				if (customer.getStoreId() > 0) {
					int storeId = customer.getStoreId();
					try {
						films = service.findFilmByStoreIdKeyword(storeId, keyword.toLowerCase(), false);

						if (null == films || films.isEmpty()) {

							map.put("total", films.size());
							map.put("status", "NoRecord");

						} else {
							// request.setAttribute("actorList", allActors);
							map.put("films", films);
							map.put("total", films.size());
							map.put("status", "OK");
						}
						// convert map to json and response
						out.print(json.toJson(map));
						out.flush();

					} catch (ServiceException e) {
						map.put("status", "ERROR");
						map.put("message", "Internal Exception");
						// convert map to json
						json.toJson(map);
						// response json
						out.print(json);
						out.flush();
						System.out.println(e.getMessage());
					}
				}

			} else {
				return;
			}

		}

	}

}
