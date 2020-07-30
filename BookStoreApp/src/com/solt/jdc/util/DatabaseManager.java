package com.solt.jdc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
	
	private static final String USER = "root";
	private static final String PASS = "admin";
	private static final String URL = "jdbc:mysql://localhost:3306/bookstore";
	
	public static Connection getconConnection() throws SQLException {
		return DriverManager.getConnection(URL,USER,PASS);
	}
	
	
}
