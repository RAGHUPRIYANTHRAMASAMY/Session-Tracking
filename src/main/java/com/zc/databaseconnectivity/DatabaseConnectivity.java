package com.zc.databaseconnectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectivity {

	String dburl = "jdbc:mysql://localhost:3306/usercredentials";
	String dbuname = "root";
	String dbpassword = "ZcMysqL@2021";
	String dbdriver = "com.mysql.jdbc.Driver";
	
	public void loadDriver(String dBDriver) {
		try {
			Class.forName(dBDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		loadDriver(dbdriver);
		Connection connection = null;

		try {
			connection = DriverManager.getConnection(dburl, dbuname, dbpassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}
	
}
