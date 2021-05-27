package com.zc.loginservlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.simple.JSONObject;

import com.zc.databaseconnectivity.DatabaseConnectivity;
import com.zc.hashgenerator.HashGenerator;
import com.zc.sessionservlet.UserSessionClass;

public class UserValidateClass {
	
	boolean emailStatus = false;
	boolean passStatus = false;
	Connection con =null;
	
	// Validate user email present in database or not
	public boolean EmailValidate(String useremail) {
		try {
			DatabaseConnectivity dc = new DatabaseConnectivity();
			con = dc.getConnection();
			
			String emailCheckQuery = "select * from userdetails where ? IN(email, secemail1, secemail2, secemail3);";
			PreparedStatement ps;
			ps = con.prepareStatement(emailCheckQuery);
			ps.setString(1, useremail);

			ResultSet rs = ps.executeQuery();
			emailStatus = rs.next();
			
		} catch (Exception e) {
			System.out.println("EmailValidate"+e.getMessage());
		}
		return emailStatus;
	}
	
	// validate password entered by user is correct or not
	public boolean PasswordValidate(String useremail, String password) {
		
		try {

			DatabaseConnectivity dc = new DatabaseConnectivity();
			con = dc.getConnection();
			
			String getbyte = "select bytesalt from usercredentials.userdetails where ? IN(email, secemail1, secemail2, secemail3);";
			PreparedStatement ps = con.prepareStatement(getbyte);
			ps.setString(1, useremail);
			ResultSet rs = ps.executeQuery();
			rs.next();
			byte[] salt = rs.getBytes(1);

			HashGenerator hg = new HashGenerator();
			String hashpass = hg.generateHash(password, salt);
			
			String validate = "select * from userdetails where ? IN(email, secemail1, secemail2, secemail3) and password = ? ;";
			ps = con.prepareStatement(validate);
			ps.setString(1, useremail);
			ps.setString(2, hashpass);

			rs = ps.executeQuery();
			passStatus = rs.next();

		} catch (Exception e) {
			System.out.println("PasswordValidate"+e.getMessage());
		}
		return passStatus;
	}
	
	// Fetching user sessions and update newly created session into database
	@SuppressWarnings("unchecked")
	public boolean AddSession(String sessionId, String email) {
		boolean sessionStatus = true;
		
		UserSessionClass usc = new UserSessionClass();
		JSONObject sobj = new JSONObject(); 
		
		sobj = usc.UserActiveSessions(email);
		int sessionCount = 0; 
		
		if(sobj != null) {
			sessionCount = (int) sobj.get("sCount");
			sobj.remove("sCount");
			sobj.put(sessionId, email);
		}
		
		try {
			DatabaseConnectivity dc = new DatabaseConnectivity();
			con = dc.getConnection();
			
			String sessionUpdate = "update userdetails set sessions = ?, sessioncount = ? where ? IN(email, secemail1, secemail2, secemail3);";
			PreparedStatement ps = con.prepareStatement(sessionUpdate);
			
			ps.setObject(1, sobj.toJSONString());
			ps.setInt(2, (sessionCount+1));
			ps.setString(3, email);
			ps.executeUpdate();
			
		} catch (Exception e) {
			sessionStatus = false;
			System.out.println("In Add Session Method "+ e.getMessage());
		}
		return sessionStatus;
	}
	
}
