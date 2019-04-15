package com.manage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.reservate.DBConnect;

public class InsertMember {
	Connection con=null;
	static Statement stmt=null;
	
	{
		DBConnect db=new DBConnect();
		try {
			con = db.conn;
			stmt = (Statement) con.createStatement();
			stmt.execute("use ss");
		} catch (SQLException e) {
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
		}
	}
	
	public static boolean create(Members m) throws SQLException {
		boolean flag=false;
		
		int id=Members.getId();
		String name=Members.getName();
		String phone=Members.getPhone();
		
		String sql="INSERT INTO Student(stdid,stdname,stdphone) VALUES ("+id+",'"+name+"','"+phone+"')";
		
		if(stmt!=null){			
			stmt.executeUpdate(sql);
			System.out.println(sql);
			flag=true;
		} else {
			// TODO 자동 생성된 catch 블록
			flag=false;
		
		}
	    
		
		return flag;
		
		
		
		
	}
	
	
	
	
}
