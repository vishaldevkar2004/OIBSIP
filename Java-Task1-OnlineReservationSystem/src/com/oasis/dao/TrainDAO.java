package com.oasis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.oasis.db.DBConnection;
import com.oasis.model.Train;

public class TrainDAO 
{
	public Train getTrainByNumber(int trainNo)
	{
		String sql = "SELECT * FROM trains WHERE train_no = ?";
		
		try(Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql))
		{
			ps.setInt(1, trainNo);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
				Train train = new Train();
				
				train.setTrainNo(rs.getInt("train_no"));
				train.setTrainName(rs.getString("train_name"));
				
				return train;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;		
	}
}
