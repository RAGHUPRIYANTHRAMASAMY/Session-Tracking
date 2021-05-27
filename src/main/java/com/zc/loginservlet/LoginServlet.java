package com.zc.loginservlet;

import java.io.IOException;

import com.zc.accessvariables.Get_Location_From_IP;
import com.zc.accessvariables.Location_Use_Bean;
import com.zc.randomsessionid.RandomSessionIdGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.uadetector.*;
import net.sf.uadetector.service.UADetectorServiceFactory;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginservlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LoginServlet() {}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String useremail = request.getParameter("email");
		String password = request.getParameter("Password");
		
		UserAgentStringParser u = UADetectorServiceFactory.getResourceModuleParser();

		ReadableUserAgent agent = u.parse(request.getHeader("User-Agent"));
		System.out.println("Browser type: " + agent.getType().getName());
        	System.out.println("Browser name: " + agent.getName());
        
        	OperatingSystem os = agent.getOperatingSystem();
        	System.out.println("\nOS Name: " + os.getName());
        	ReadableDeviceCategory device = agent.getDeviceCategory();
        	System.out.println("\nDevice: " + device.getName());
        
        	String ip = request.getRemoteAddr();
		System.out.println("\nIP Address: " + ip);
        
		boolean emailStatus = false;
		boolean passStatus = false;
		
		UserValidateClass uvc = new UserValidateClass();
		
		emailStatus = uvc.EmailValidate(useremail);
		passStatus = uvc.PasswordValidate(useremail, password);
		
		if(emailStatus && passStatus) {
			
			RandomSessionIdGenerator rsid = new RandomSessionIdGenerator();
			String sessionID = rsid.RandomSessionId();
			
			if(uvc.AddSession(sessionID, useremail))
			{
				Cookie cookie = new Cookie("_Session_ID", sessionID);
				cookie.setMaxAge( 60 * 60 );
				
				Cookie cookie1 = new Cookie("email", useremail);
				cookie1.setMaxAge( 60*60 );
				
				response.addCookie(cookie);
				response.addCookie(cookie1);
			}
			
			response.sendRedirect("profile.jsp");
		}
		else
			response.sendRedirect("login.jsp");
	}
}
