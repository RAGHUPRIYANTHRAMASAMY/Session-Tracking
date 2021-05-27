package com.zc.passwordvalidateservlet;

import java.io.IOException;
import java.io.PrintWriter;
import com.zc.loginservlet.UserValidateClass;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PasswordValidateServlet
 */
@WebServlet("/passwordvalidate")
public class PasswordValidateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PasswordValidateServlet() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String useremail = request.getParameter("useremail");
		String password = request.getParameter("Password");
		
		PrintWriter out = response.getWriter();
		
		boolean Status = false;
		
		UserValidateClass evc = new UserValidateClass();
		
		Status = evc.PasswordValidate(useremail, password);
		
		if(Status)
			out.println("true");
		else
			out.println("false");
	}

}
