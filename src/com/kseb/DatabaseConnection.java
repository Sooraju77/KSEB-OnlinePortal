package com.kseb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/ksebproject";
	final String user = "root";
	final String pass = "mysql";
	Connection connection = null;

	public Connection getConnection() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		return connection;
	}
}
