package cuc.digital.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Jdbc {
	private static Connection conn;
	
	private Jdbc() throws Exception{
		
		
	}

	public static Connection openConn() throws ClassNotFoundException, SQLException {
		
		if(conn==null){
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lucene","root","root");
		}
		return conn;
		
	}
	
	public  static void closeConn() throws SQLException{
		conn.close();
	}
}
