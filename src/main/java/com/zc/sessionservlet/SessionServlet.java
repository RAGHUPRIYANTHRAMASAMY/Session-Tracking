package com.zc.sessionservlet;

import java.io.IOException;
import java.io.PrintWriter;

import org.json.simple.JSONObject;

// import com.zc.accessvariables.AccessVariables;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class SessionServlet
 */
@WebServlet("/usersession")
public class SessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public SessionServlet() {}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
		
		String email = null;
		
		Cookie[] cookies = request.getCookies();
		
		for(int i =0; i<cookies.length; ++i) {
			Cookie cookie = cookies[i];
			if((cookie.getName()).compareTo("email") == 0) {
				email = cookie.getValue();
			}
		}
		
		PrintWriter out = response.getWriter();
		
		UserSessionClass usc = new UserSessionClass();
		JSONObject sobj = new JSONObject();
		sobj = usc.UserActiveSessions(email);
		
		if(sobj != null) {
			sobj.remove("useremail");
			sobj.remove("sCount");
			out.println(sobj);
		}
		else
			out.println("false");
	}

}
