/**
 *
 */
package com.webjava.servlet.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nttdata.mvc.HttpRequestHandler;
import com.webjava.common.ViewNames;
import com.webjava.jdbc.dvd.domain.dto.CartDTO;

/**
 * @author sumyathtarwai
 *
 */
public class CartHandler implements HttpRequestHandler {

    /**
     *
     */
    public CartHandler() {
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
	HttpSession session = request.getSession();
	CartDTO cartItem = null;
	// actions
	String doAdd = request.getParameter("doAdd");
	// TODO quantity clac will not do in this app
	String doUpdate = request.getParameter("doUpdate");
	String doRemove = request.getParameter("doRemove");

	synchronized (session) {
	    Object obj = session.getAttribute("cart");
	    // check session
	    if (null != obj) {
		cartItem = (CartDTO) obj;
	    } else {
		// new session
		cartItem = new CartDTO();
		session.setAttribute("cart", cartItem);
	    }
	}

	// data manipulation
	String filmIdStr = request.getParameter("filmId");
	if (null != filmIdStr && !"".equals(filmIdStr)) {
	    int filmId = 0;
	    if (filmIdStr.matches("[0-9]*")) {
		filmId = Integer.parseInt(filmIdStr);
	    } else {
		return;
	    }
	    if ((null != doAdd && !"".equals(doAdd)) || (null != doUpdate && !"".equals(doUpdate))) {
		// TODO for multiple product
		// String[] filmIds = request.getParameterValues("filmId");
		String title = request.getParameter("title");
		String categoryName = request.getParameter("categoryName");
		String description = request.getParameter("description");
		String priceStr = request.getParameter("price");
		String rating = request.getParameter("rating");
		String rentalRateStr = request.getParameter("rentalRate");
		float price = 0, rentalRate = 0;
		if ((null != priceStr && !"".equals(priceStr) && (null != priceStr && !"".equals(priceStr)))) {
		    if (priceStr.matches("\\d*\\.?\\d*") && rentalRateStr.matches("\\d*\\.?\\d*")) {
			price = Float.parseFloat(priceStr);
			rentalRate = Float.parseFloat(rentalRateStr);
		    } else {
			return;
		    }
		} else {
		    return;
		}

		if (null != doAdd && !"".equals(doAdd)) {
		    cartItem.addCart(filmId, title, categoryName, description, price, rating, rentalRate);
		} else {
		    cartItem.updateCart(filmId, title, categoryName, description, price, rating, rentalRate);
		}
		    response.sendRedirect("./searchFilm.jsp");
	    } else if (null != doRemove && !"".equals(doRemove)) {
		String indexStr = request.getParameter("index");
		if (indexStr.matches("[0-9]*")) {
		    cartItem.removeCart(Integer.parseInt(indexStr));
		} else {
		    return;
		}
		response.sendRedirect(ViewNames.SHOW_CART);
	    }
	}
    }
}
