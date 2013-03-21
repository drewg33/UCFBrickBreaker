package com.ucfbrickbreaker.brickbreak;

import java.sql.*;

public class BBDatabase
{
	private Connection connection;
	private static String DBUserName = ""; 
	private static String DBPass = ""; 
	private static String URL = "C:\\Users\\Drew\\Documents\\UCFBrickBreaker.accdb";
	private static String connURL = "jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + URL;
	
	protected BBDatabase()
	{
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			connection = DriverManager.getConnection(connURL, DBUserName, DBPass);
			/*Statement stmt = connection.createStatement();
			stmt.execute("select * from Users"); // execute query in table student
			ResultSet rs = stmt.getResultSet(); // get any Result that came from our query

			if (rs != null)
				while (rs.next())
				{
					System.out.println("UserName: " + rs.getString("UserName")
							+ " ID: " + rs.getString("ID"));
				}

			stmt.close();*/
		} 
		catch (Exception err)
		{
			System.out.println("ERROR: " + err);
		}
	}
	
	protected void Close()
	{
		try
		{
			connection.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	boolean LogIn(String username, String password)
	{
		
		return false;
	}
	
	void LoadSession(String username)
	{
		
	}

	String getTopNScores(int n)
	{
		try 
		{
			Statement s = connection.createStatement();
			s.execute("SELECT TOP " + n + " HighScores.UserName, HighScores.OverallScore FROM HighScores ORDER BY HighScores.OverallScore DESC");
			ResultSet results = s.getResultSet();
			return results.toString();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return "";
	}
}
