package com.oasis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.oasis.db.DBConnection;

public class LoginDAO 
{
	public boolean validateUser(String username, String password)
	{
		String sql = "SELECT * from users WHERE username=? AND password=?";
		
		try(Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql))
		{
			ps.setString(1, username);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			return rs.next();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return false;
		
	}
}
