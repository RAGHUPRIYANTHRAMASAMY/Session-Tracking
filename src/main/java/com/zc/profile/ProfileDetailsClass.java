package com.zc.profile;

import java.sql.*;

import org.json.simple.JSONObject;

import com.zc.databaseconnectivity.DatabaseConnectivity;

public class ProfileDetailsClass {
	
	Connection con = null;
	
	@SuppressWarnings("unchecked")
	public JSONObject UserDetails(String email) {
		
		JSONObject udobj = new JSONObject();
		
		try {
			DatabaseConnectivity dc = new DatabaseConnectivity();
			con = dc.getConnection();
			String profileSelectQuery = "select * from userdetails where ? IN(email, secemail1, secemail2, secemail3)";
			PreparedStatement ps = con.prepareStatement(profileSelectQuery);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			rs.next();
			udobj.put("firstname", rs.getString(1));
			udobj.put("lastname", rs.getString(2));
			udobj.put("email", rs.getString(3));
			udobj.put("gender", rs.getString(5));
			udobj.put("country", rs.getString(6));
			
		} catch (Exception e) {
			System.out.println("In userdetails "+ e.getMessage());
		}
		return udobj;
	}
	
	public boolean UpdateProfile(String firstname, String lastname, String gender, String country, String email) {
		
		boolean status = true;
		try {
			DatabaseConnectivity dc = new DatabaseConnectivity();
			con = dc.getConnection();
			String profileUpdateQuery = "update userdetails set Firstname = ?,Lastname = ?,gender = ?,country = ? where ? IN(email, secemail1, secemail2, secemail3);";
			
			PreparedStatement ps = con.prepareStatement(profileUpdateQuery);
			ps.setString(1, firstname);
			ps.setString(2, lastname);
			ps.setString(3, gender);
			ps.setString(4, country);
			ps.setString(5, email);
			ps.executeUpdate();
			
		} catch (Exception e) {
			status = false;
			System.out.println("In update profile method: "+ e.getMessage());
		}
		
		return status;
	}
	
}
