package com.manage;

import java.sql.SQLException;
import java.sql.Statement;

public class InsertMember {
	DBConnect db=new DBConnect();
	Statement stmt=db.stmt;
	
	
	public boolean create(Members m) throws SQLException {
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
		db.close();
		return flag;
	}
	
	
	
	
}
