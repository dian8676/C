package com.reservate;

import java.sql.Connection;

public class test {
	public static void main(String[] args) {
		Connection con;
		con = new DBConnect().conn;
		new MainFrame(con);
	}
}
