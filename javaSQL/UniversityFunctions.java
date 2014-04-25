package university;

import java.sql.*;

public class UniversityFunctions{

	public static void main(String[] args) throws Exception{
		Class.forName("org.sqlite.JDBC");// load the sqlite-JDBC driver using the current class loader
		Connection db_conn= DriverManager.getConnection("jdbc:sqlite:UnivDB.db"); // create database connection
		Statement stmt= db_conn.createStatement();// prepare a SQL statement
		Menus.mainMenu(stmt);//enter main menu
		stmt.close();// close database statement
		db_conn.close();// close connection to university file
		return;
	}
}
