package com.zc.sessionservlet;

import java.sql.*;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import jakarta.servlet.http.Cookie;

import com.zc.databaseconnectivity.DatabaseConnectivity;

public class UserSessionClass {
	
	Connection con = null;
	
	@SuppressWarnings("unchecked")
	public JSONObject UserActiveSessions(String email) {
		
		JSONObject obj = new JSONObject();
		JSONParser parser = new JSONParser();
		
		try {
			DatabaseConnectivity dc = new DatabaseConnectivity();
			con = dc.getConnection();
			
			String sessionQuery = "select sessions, sessioncount from usercredentials.userdetails where ? IN(email, secemail1, secemail2, secemail3);";
			
			PreparedStatement ps = con.prepareStatement(sessionQuery);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			rs.next();
			obj  = (JSONObject) parser.parse(rs.getString(1));
			int sessionCount = rs.getInt(2);
			obj.put("sCount", sessionCount);
		} catch (Exception e) {
			System.out.println("In user active session method: "+ e.getMessage());
		}
		
		return obj;
	}
	
	public boolean TerminateUserSession(String sessionId, String email) {
		
		boolean teminationStatus = true;
		JSONObject obj = new JSONObject();
		
		UserSessionClass usc = new UserSessionClass();
		
		obj = usc.UserActiveSessions(email);
		
		int sCount = (int) obj.get("sCount");
	
		obj.remove("sCount");
		obj.remove(sessionId);
		
		try {
			DatabaseConnectivity dc = new DatabaseConnectivity();
			con = dc.getConnection();
			
			String sessionUpdate = "update userdetails set sessions = ?, sessioncount = ? where ? IN(email, secemail1, secemail2, secemail3);";
			PreparedStatement ps = con.prepareStatement(sessionUpdate);
			
			ps.setObject(1, obj.toJSONString());
			ps.setInt(2, (sCount-1));
			ps.setString(3, email);
			ps.execute();
			ps.executeUpdate();
		} catch (Exception e) {
			teminationStatus = false;
			System.out.println("In Terminate User Session Method "+ e.getMessage());
		}
		return teminationStatus;
	}
	
	public boolean UserSesionStatus(Cookie[] cookies, String email) {
		
		boolean sessionStatus = false;
		try {
			
			JSONObject obj = new JSONObject();
			
			UserSessionClass usc = new UserSessionClass();
			
			obj = usc.UserActiveSessions(email);
			obj.remove("sCount");
			obj.remove("useremail");
			
			Set<?> s =  obj.keySet();
		    Iterator<?> i = s.iterator();
		    do{
		        String k = i.next().toString();
		        for(int j=0; j<cookies.length; ++j)
		        { 
		        	Cookie cookie = cookies[j];
		        	if ((cookie.getName()).compareTo("_Session_ID") == 0) {
						if (((cookie.getValue()).toString()).compareTo(k) == 0) {
							sessionStatus = true;
						}
					}
		        }
		        
		    }while(i.hasNext());
			
		} catch (Exception e) {
			System.out.println("In User Session Status " + e.getMessage());
		}
		return sessionStatus;
	}
	
}
