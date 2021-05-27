package com.zc.logout;

import java.io.IOException;

import com.zc.sessionservlet.UserSessionClass;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// import com.zc.accessvariables.AccessVariables;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LogoutServlet() {}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = "";
		String sessionId=null;
		Cookie[] cookies = request.getCookies();
		
		for(int i =0; i<cookies.length; ++i) {
			Cookie cookie = cookies[i];
			if((cookie.getName()).compareTo("email") == 0) {
				email = cookie.getValue();
			}
			if((cookie.getName()).compareTo("_Session_ID") == 0) {
				sessionId = cookie.getValue();
			}
		}
		
		UserSessionClass usc = new UserSessionClass();
		boolean status = usc.TerminateUserSession(sessionId, email);
		
		if(status) {
			for(int i=0; i<cookies.length; ++i) {
				Cookie cookie = cookies[i];
				if((cookie.getName()).compareTo("_Session_ID") == 0) {
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
				if((cookie.getName()).compareTo("email") == 0) {
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
			
			response.sendRedirect("login.jsp");
		}
	}

}
