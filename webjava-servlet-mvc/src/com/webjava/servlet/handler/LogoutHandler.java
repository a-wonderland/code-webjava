/**
 * 
 */
package com.webjava.servlet.handler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nttdata.mvc.HttpRequestHandler;
import com.webjava.common.ViewNames;

/**
 * @author sumyathtarwai
 *
 */
public class LogoutHandler implements HttpRequestHandler {

    /*
     * (non-Javadoc)
     * 
     * @see com.nttdata.mvc.HttpRequestHandler#handle(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	HttpSession session = request.getSession();
	if (null != session.getAttribute("userAuth")) {
	    session.invalidate();
	    request.setAttribute("msg", "Successfull Logout!");
	    RequestDispatcher rd = request.getRequestDispatcher(ViewNames.INDEX);
	    rd.forward(request, response);
	}
    }
}
