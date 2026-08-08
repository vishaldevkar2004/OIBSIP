package com.oasis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.oasis.db.DBConnection;
import com.oasis.model.Reservation;

public class ReservationDAO 
{
	public long bookTicket(Reservation reservation)
	{
		String sql = "INSERT INTO reservations"
				+ "(passenger_name, train_no, train_name, class_type, journey_date, source_station, destination_station) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		try(Connection con = DBConnection.getConnection();
				PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS))
		{
			ps.setString(1, reservation.getPassengerName());
			ps.setInt(2, reservation.getTrainNo());
			ps.setString(3, reservation.getTrainName());
			ps.setString(4, reservation.getClassType());
			ps.setDate(5, reservation.getJourneyDate());
			ps.setString(6, reservation.getSourceStation());
			ps.setString(7, reservation.getDestinationStation());
			
			int rows = ps.executeUpdate();
			
			if(rows > 0)
			{
				ResultSet rs = ps.getGeneratedKeys();
				
				if(rs.next())
				{
					return rs.getLong(1);
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;		
	}
	
	public Reservation getReservationByPNR(long pnr) {

	    String sql = "SELECT * FROM reservations WHERE pnr = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setLong(1, pnr);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {

	            Reservation reservation = new Reservation();

	            reservation.setPnr(rs.getLong("pnr"));
	            reservation.setPassengerName(rs.getString("passenger_name"));
	            reservation.setTrainNo(rs.getInt("train_no"));
	            reservation.setTrainName(rs.getString("train_name"));
	            reservation.setClassType(rs.getString("class_type"));
	            reservation.setJourneyDate(rs.getDate("journey_date"));
	            reservation.setSourceStation(rs.getString("source_station"));
	            reservation.setDestinationStation(rs.getString("destination_station"));

	            return reservation;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	public boolean cancelReservation(long pnr) {

	    String sql = "DELETE FROM reservations WHERE pnr = ?";

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setLong(1, pnr);

	        int rows = ps.executeUpdate();

	        return rows > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}
}
