package com.zc.profile;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import org.json.simple.JSONObject;

import com.zc.accessvariables.AccessVariables;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/userprofile")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ProfileServlet() {}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = null;
		
		Cookie[] cookies = request.getCookies();
		
		for(int i =0; i<cookies.length; ++i) {
			Cookie cookie = cookies[i];
			if((cookie.getName()).compareTo("email") == 0) {
				email = cookie.getValue();
			}
		}
		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		ProfileDetailsClass pd = new ProfileDetailsClass();
		
		if(email != null) {
			JSONObject obj= pd.UserDetails(email);
			if(obj != null)
				out.println(obj);
		}
	}
}
