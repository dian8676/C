package com.manage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect{
	public Connection conn;
	Statement stmt;
	ResultSet rs = null;

	String Url = "jdbc:mysql://localhost:3307/ss";
	String ID = "root";
	String PassWord = "1210";
	
	String stId;
	String stName;
	
	{
		try{
			conn=DriverManager.getConnection(Url,ID,PassWord);
		
			try{
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			stmt=conn.createStatement();
			System.out.println("DB연결 성공");
		
			stmt.execute("use ss");
			rs=stmt.executeQuery("SELECT * FROM Student");
			
		
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	public void close(){
		try {
			conn.close();
			System.out.println("DB연결 해제");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
