package com.ucfbrickbreaker.brickbreak;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

class BBDatabase
{
	private Connection connection;
	private static String DBUserName = ""; 
	private static String DBPass = ""; 
	private static String URL = "C:\\Users\\Drew\\Documents\\UCFBrickBreaker.accdb";
	private static String connURL = "jdbc:odbc:DRIVER={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + URL;
	private String username = null; //if this is null, no user is logged in.
	
	protected BBDatabase()
	{
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			connection = DriverManager.getConnection(connURL, DBUserName, DBPass);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	protected void Close()
	{
		username = null;
		try
		{
			connection.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	void createUser(String username, String password) throws Exception 
	{
		username = username.toLowerCase(); //we do NOT want case sensitivity in the username 
		
		for (char c:username.toCharArray())
		{
			if (!Character.isLetterOrDigit(c))
				throw new Exception("Username contains invalid character. Use only letters and numbers.");
		}
		for (char c:password.toCharArray())
		{
			if (!Character.isLetterOrDigit(c))
				throw new Exception("Password contains invalid character. Use only letters and numbers.");
		}
		
		try
		{
			PreparedStatement stmt = connection.prepareStatement("SELECT username from Users WHERE username = ?");
			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery(); // execute query and get ResultSet
			if (rs.next())
			{
				throw new Exception("Username is already taken."); //throw exception and return
			}
			
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
		    digest.update((username + password).getBytes("UTF-8")); //salt the hash with user + pass combo
		    byte [] hash =  digest.digest(password.getBytes("UTF-8")); //get the hash to compare to DB hash
		    
		    stmt = connection.prepareStatement("INSERT INTO Users (username, PasswdHash) VALUES (? , ?)");
			stmt.setString(1, username);
			stmt.setBytes(2, hash);
			stmt.executeUpdate();
			
			stmt.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		this.username = username;
	}
	
	boolean LogIn(String username, String password) throws Exception
	{
		username = username.toLowerCase(); //we do NOT want case sensitivity in the username 
		
		for (char c:username.toCharArray())
		{
			if (!Character.isLetterOrDigit(c))
				throw new Exception("Username contains invalid character. Use only letters and numbers.");
		}
		for (char c:password.toCharArray())
		{
			if (!Character.isLetterOrDigit(c))
				throw new Exception("Password contains invalid character. Use only letters and numbers.");
		}
		
		try
		{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
		    digest.update((username + password).getBytes("UTF-8")); //salt the hash with user + pass combo
		    byte [] hash =  digest.digest(password.getBytes("UTF-8")); //get the hash to compare to DB hash
		    
		    PreparedStatement stmt = connection.prepareStatement("SELECT * from Users WHERE username = ?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery(); // execute query and get ResultSet
			stmt.close();
			if (!rs.next())
			{//TODO change this exception to match the one for incorrect password after finished debugging
				throw new Exception("Username does not exist"); //throw exception and return
			}
			
			if (Arrays.equals(rs.getBytes("PasswdHash"), hash)) //if the credentials match
			{
				this.username = username;
		    	return true;
			}
			else
				throw new Exception("Username/password does not match"); //throw exception and return
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	void logOut() throws Exception
	{
		if (username != null)
			username = null;
		else
			throw new Exception("Error! Cannot log out, not logged in!");
	}
	
	void LoadSession() throws Exception //loads saved session from DBConnection
	{
		if (username == null)
			throw new Exception("Cannot save session, user not logged in!");
		
		try
		{
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM GameState WHERE username=?");
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery(); // execute query and get ResultSet
			stmt.close();
			
			if (!rs.next())
				throw new Exception("No saved session found!");
			
			GUI.time_remaining = rs.getInt("TimeRemaining");
			GUI.money = rs.getInt("Money");
			String s = rs.getString("Resources");
			String temp[] = s.split(";");
			GUI.airGathered = Integer.parseInt(temp[0]);
			GUI.foodGathered = Integer.parseInt(temp[1]);
			GUI.fuelGathered = Integer.parseInt(temp[2]);
			GUI.waterGathered = Integer.parseInt(temp[3]);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	void SaveSession() throws Exception //saves current session to DBConnection
	{
		if (username == null)
			throw new Exception("Cannot save session, user not logged in!");
		
		try
		{
			//TODO Check if record exists in table for user, do either an update or insert based on that
			if (connection == null) //TODO placeholder condition. fix this
			{
				String query = "INSERT INTO GameState (username, TimeRemaining, Money, Resources) VALUES (?, ?, ?, ?)";
				PreparedStatement stmt = connection.prepareStatement(query);
				stmt.setString(1, username);
				stmt.setInt(2, GUI.time_remaining);
				stmt.setInt(3, GUI.money);
				stmt.setString(4, "" + GUI.airGathered + ";" + GUI.foodGathered + ";" + GUI.fuelGathered + ";" + GUI.waterGathered);
			}
			else
			{
				String query = "UPDATE GameState SET TimeRemaining=?, Money=? , Resources=? WHERE username=?";
				PreparedStatement stmt = connection.prepareStatement(query);
				stmt.setInt(1, GUI.time_remaining);
				stmt.setInt(2, GUI.money);
				stmt.setString(3, "" + GUI.airGathered + ";" + GUI.foodGathered + ";" + GUI.fuelGathered + ";" + GUI.waterGathered);
				stmt.setString(4, username);
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	String getTopNScores(int n)
	{
		try 
		{
			String ret = "";
			PreparedStatement stmt = connection.prepareStatement("SELECT TOP ? HighScores.username, HighScores.OverallScore FROM HighScores ORDER BY OverallScore DESC");
			stmt.setInt(1, n);
			
			ResultSet results = stmt.getResultSet();
			
			while (results.next())
				ret += results.getString("username") + "\t\t" + results.getString("OverallScore") + "\n";
			
			stmt.close();
			return ret;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return "";
	}
}
