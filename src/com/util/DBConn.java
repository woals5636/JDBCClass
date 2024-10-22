package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// 싱글톤으로 db연결 객체를 만들어보자.
public class DBConn {
	
	private static Connection conn = null;
	
	private DBConn() {
		
	}
	
	public static Connection getConnection() {
		
		if (conn == null) {
			String className = "oracle.jdbc.driver.OracleDriver"; // oracleDriver 컨트롤엔터하면 나오는것 복붙
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "scott";
			String password = "tiger";
			
			try {
				Class.forName(className);
				conn = DriverManager.getConnection(url, user, password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		} // if
		
		return conn; // 만들어져있으니, 그대로 반환.
		
	}
	
	//
	

	
	public static Connection getConnection(String url, String user, String password)   {
		
		if (conn == null) {
			// conn 생성해서 반환.
			String className = "oracle.jdbc.driver.OracleDriver"; // oracleDriver 컨트롤엔터하면 나오는것 복붙
			try {
				Class.forName(className);
				conn = DriverManager.getConnection(url, user, password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		} // if
		
		return conn; // 만들어져있으니, 그대로 반환.
		
	} //getConnection
	
	public static void close() {
		
			 try {
				 if (conn != null && !conn.isClosed()) {
					 conn.close();
				}
				 
			} catch (SQLException e) {
				e.printStackTrace();
			}
			 conn = null;
		
	}
	

} // class


