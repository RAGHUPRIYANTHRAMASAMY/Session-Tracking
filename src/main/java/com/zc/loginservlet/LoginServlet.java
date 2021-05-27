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
//import net.sf.uadetector.UserAgent;
//import net.sf.uadetector.internal.data.domain.OperatingSystem;
//import eu.bitwalker.useragentutils.OperatingSystem;
//import eu.bitwalker.useragentutils.UserAgent;
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
//		UserAgentStringParser parser = UADetectorServiceFactory.getOnlineUpdatingParser();
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
        
//        String ip = request.getRemoteAddr();
//        Get_Location_From_IP obj_Get_Location_From_IP = new Get_Location_From_IP();
//        Location_Use_Bean obj_Location_Use_Bean = obj_Get_Location_From_IP.get_ip_Details(ip);
//        System.out.println("IP Address--" + obj_Location_Use_Bean.getIp_address());
//        System.out.println("Country Code-- " + obj_Location_Use_Bean.getIp_address());
//        System.out.println("Country--" + obj_Location_Use_Bean.getCountry());
//        System.out.println("State--" + obj_Location_Use_Bean.getState());
//        System.out.println("City--" + obj_Location_Use_Bean.getCity());
//        System.out.println("ZIP--" + obj_Location_Use_Bean.getZip());
//        System.out.println("Lat--" + obj_Location_Use_Bean.getLat());
//        System.out.println("Lon--" + obj_Location_Use_Bean.getLon());
//        System.out.println("Offset--" + obj_Location_Use_Bean.getUtc_offset());
        
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

//	String ip = request.getHeader("X-FORWARDED-FOR");
//	
//	if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
//        ip = request.getHeader("Proxy-Client-IP");  
//    }  
//    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
//        ip = request.getHeader("WL-Proxy-Client-IP");  
//    }  
//    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
//        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
//    }  
//    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
//        ip = request.getHeader("HTTP_X_FORWARDED");  
//    }  
//    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
//        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
//    }  
//    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
//        ip = request.getHeader("HTTP_CLIENT_IP");  
//    }  
//    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
//        ip = request.getHeader("HTTP_FORWARDED_FOR");  
//    }  
//    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
//        ip = request.getHeader("HTTP_FORWARDED");  
//    }  
//    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
//        ip = request.getHeader("HTTP_VIA");  
//    }  
//    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
//        ip = request.getHeader("REMOTE_ADDR");  
//    }  
//    if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {  
//        ip = request.getRemoteAddr();  
//    }
//    
//	System.out.println("Remote Address: "+ip);
	
//	System.out.println("Gateway: "+request.getHeader("VIA"));
//	System.out.println("Remote Port: "+request.getRemotePort());
//	System.out.println("Remote User: "+request.getRemoteUser());
	
//	UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
//	UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
//  OperatingSystem agent = userAgent.getOperatingSystem();
	
//    System.out.println("User Agent"+userAgent);
//    System.out.println("Browser type: "+ userAgent.getBrowser());
//    System.out.println("OS type "+agent.getName());
//    System.out.println("Device Type "+agent.getDeviceType().getName());
	
	/*
	 * private static final String[] HEADERS_TO_TRY = { "X-Forwarded-For",
	 * "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR",
	 * "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
	 * "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };
	 * 
	 * private String getClientIpAddress(HttpServletRequest request) { for (String
	 * header : HEADERS_TO_TRY) { String ip = request.getHeader(header); if (ip !=
	 * null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) { return ip; } }
	 * 
	 * return request.getRemoteAddr(); }
	 */
	
}
