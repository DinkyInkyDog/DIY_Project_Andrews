package project.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import project.exception.DbException;

public class Dbconnection {

	private static final String SCHEMA = "diy_projects";
	private static final String USER = "diy_projects";
	private static final String PASSWORD = "diy_projects";
	private static final String HOST = "localhost";
	private static final int PORT = 3306;
	
	public static Connection getConnections() {
		String url = String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s&useSSL=false", HOST, PORT, SCHEMA, USER, PASSWORD);
		System.out.println(url);
		try {
			Connection conn = DriverManager.getConnection(url);
			System.out.println("Connection Successful!");
			return conn;
		} catch (SQLException e) {
			System.out.println("Connection Failed.");
			throw new DbException(e); 
		}
	}
}
