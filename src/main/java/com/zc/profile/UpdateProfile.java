package com.zc.profile;

import java.io.IOException;
import java.io.PrintWriter;

import com.zc.accessvariables.AccessVariables;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class UpdateProfile
 */
@WebServlet(description = "User Profile Update( If User edit his/her Profile, Update code Provided Here )", urlPatterns = { "/updateprofile" })
public class UpdateProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public UpdateProfile() {}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String gender = request.getParameter("gender");
		String country = request.getParameter("country");
		
		PrintWriter out = response.getWriter();
		boolean status = false;
		String email = null;
		
		Cookie[] cookies = request.getCookies();
		
		for(int i =0; i<cookies.length; ++i) {
			Cookie cookie = cookies[i];
			if((cookie.getName()).compareTo("email") == 0) {
				email = cookie.getValue();
			}
		}
		
		
		ProfileDetailsClass pd = new ProfileDetailsClass();
		if(email != null) {
			status = pd.UpdateProfile(firstname, lastname, gender, country, email);
		}
		
		if(status)
			out.println("true");
		else
			out.println("false");
	}

}
