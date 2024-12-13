package com.tcs.jdbc_prepared_statement_crud_operation.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.cj.jdbc.Driver;

public class LaptopConnection {
public static Connection getLaptopConnection() {
	try {
		//1. load/register driver
		//register driver
		 Driver driver = new Driver();
		 DriverManager.registerDriver(driver);
		 
		 //2. create connection
		 String url="jdbc:mysql://localhost:3306/qspider";
		 String user="root";
		 String pass="1234";
		 
		 return DriverManager.getConnection(url, user, pass);
	}catch(SQLException e) {
		e.printStackTrace();
		return null;
	}
}
}
