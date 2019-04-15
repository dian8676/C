package com.reservate;

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
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}

		stmt = conn.createStatement();
		System.out.println("Connection Success!");

		
		stmt.execute("use ss");
	
		}catch (SQLException e){
			e.printStackTrace();
		}
		
	}
	public DBConnect() {}
	public DBConnect(String stId) {
		this.stId=stId;
	}
	public DBConnect(String stId, String stName) {
		this.stId=stId;
		this.stName=stName;
	}
	public ResultSet getConSeat() throws SQLException {
		String s_id;
		rs = stmt.executeQuery("SELECT * FROM Seat");
		while(rs.next()){
			s_id=rs.getString("seatid");
			if(stId.equals(s_id)){
				return rs;
			}
		}
		return null;
	}
	public ResultSet getConRSeat() throws SQLException {
		String s_id;
		rs = stmt.executeQuery("SELECT * FROM Seat");
		while(rs.next()){
			s_id=rs.getString("reservation");
			if(stId.equals(s_id)){
				return rs;
			}
		}
		return null;
	}
	public ResultSet getConStd() throws SQLException {
		String s_id;
		String s_name;
		rs = stmt.executeQuery("SELECT * FROM Student");
		while(rs.next()){
			s_id=rs.getString("stdid");
			s_name=rs.getString("stdname");
			if((stId.equals(s_id)) && (stName.equals(s_name))){
				return rs;
			}
		}
		return null;
	}
	public ResultSet findStd() throws SQLException {
		String s_id;
		rs = stmt.executeQuery("SELECT * FROM Student");
		while(rs.next()){
			s_id=rs.getString("stdid");
			if(stId.equals(s_id)){
				return rs;
			}
		}
		return null;
	}
}
