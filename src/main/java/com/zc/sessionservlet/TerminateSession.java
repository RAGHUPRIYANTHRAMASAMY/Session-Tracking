package com.zc.sessionservlet;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class TerminateSession
 */
@WebServlet("/terminatesession")
public class TerminateSession extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public TerminateSession() {}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sessionID = request.getParameter("sessionname");
		
		PrintWriter out = response.getWriter();
		
		String email = "";
		
		Cookie[] cookies = request.getCookies();
		
		for(int i =0; i<cookies.length; ++i) {
			Cookie cookie = cookies[i];
			if((cookie.getName()).compareTo("email") == 0) {
				email = cookie.getValue();
			}
		}
		
		UserSessionClass usc = new UserSessionClass();
		boolean teminationStatus = usc.TerminateUserSession(sessionID, email);
		
		if(teminationStatus) {
			out.println("true");
		}
		else
			out.println("false");
	}

}
