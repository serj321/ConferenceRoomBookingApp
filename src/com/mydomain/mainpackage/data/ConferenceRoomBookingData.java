// Author: Serj Sililian
package com.mydomain.mainpackage.data;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;

import ca.senecacollege.prg556.hocorba.bean.ConferenceRoom;
import ca.senecacollege.prg556.hocorba.bean.ConferenceRoomBooking;
import ca.senecacollege.prg556.hocorba.dao.ConferenceRoomBookingDAO;

public class ConferenceRoomBookingData implements ConferenceRoomBookingDAO{
	
	private DataSource ds;
	
	public ConferenceRoomBookingData(DataSource ds){
		setDs(ds);
	}
	
	private DataSource getDs(){
		return ds;
	}
	
	private void setDs(DataSource ds){
		this.ds = ds;
	}
	
	public ConferenceRoomBooking bookConferenceRoom(int clientId, String roomCode, Date start, int duration) throws SQLException{
			ConferenceRoomData crData = new ConferenceRoomData();
			ConferenceRoom cr = crData.getConferenceRoom(roomCode);
			BigDecimal durationBig = BigDecimal.valueOf(duration);
			BigDecimal dividendSixty = BigDecimal.valueOf(60);
			
			int id;
			try (Connection conn = getDs().getConnection()){
				try (Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)){
					try(ResultSet rslt = stmt.executeQuery("SELECT booking_code, client_id, room_code, start_date, duration FROM booking")){
						rslt.moveToInsertRow();
						rslt.updateInt("client_id", clientId);
						rslt.updateString("room_code", roomCode);
						rslt.updateTimestamp("start_date", new Timestamp (start.getTime()));
						rslt.updateInt("duration", duration);
						rslt.insertRow();
					}
					String sql = "SELECT @@IDENTITY";
					
					try (ResultSet rslt = stmt.executeQuery(sql)){
						rslt.next(); 
						id = rslt.getInt(1);
						return new ConferenceRoomBooking( 
								id, 
								roomCode, 
								cr.getName(), 
								cr.getCapacity(), 
								start, 
								duration, 
								cr.getRate().multiply(durationBig).divide(dividendSixty)
								);
					}
				}
			}


	}
	
	public void cancelConferenceRoomBooking(int bookingCode) throws SQLException{
		try (Connection conn = getDs().getConnection()){
			try(PreparedStatement pstmt = conn.prepareStatement("SELECT booking_code FROM booking WHERE booking_code = ?", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)){
				pstmt.setInt(1, bookingCode);
				try(ResultSet rslt = pstmt.executeQuery()){
					if (rslt.next())
						rslt.deleteRow();
				}
			}
		}
	}
	
	public ConferenceRoomBooking getConferenceRoomBooking(int bookingCode) throws SQLException{
		try (Connection conn = getDs().getConnection()){
			try (PreparedStatement pstmt = conn.prepareStatement("SELECT client_id, room_code, start_date, duration, capacity, rate FROM booking INNER JOIN conference_room ON booking.room_code = conference_room.code WHERE booking_code = ?")){
				pstmt.setInt(1,  bookingCode);
				try (ResultSet rslt = pstmt.executeQuery()){
					if(rslt.next()){
//						ConferenceRoomData crData = new ConferenceRoomData();
//						ConferenceRoom cr = crData.getConferenceRoom(rslt.getString("room_code"));
						BigDecimal durationBig = BigDecimal.valueOf(rslt.getInt("duration"));
						BigDecimal dividendSixty = BigDecimal.valueOf(60);
						
						return new ConferenceRoomBooking( 
								bookingCode, 
								rslt.getString("room_code"), 
								rslt.getString("name"), 
								rslt.getInt("capacity"),
								rslt.getDate("start_date"), 
								rslt.getInt("duration"), 
							

								rslt.getBigDecimal("rate").multiply(durationBig).divide(dividendSixty)

								);
					}
				}
			}
		}
		return null;
	}
	
	public List<ConferenceRoomBooking> getConferenceRoomBookings (int clientId) throws SQLException{
		List<ConferenceRoomBooking> conferenceRoomBookings = new ArrayList<>();
		try (Connection conn = getDs().getConnection()){
			try (Statement stmt = conn.createStatement()){
				try (PreparedStatement pstmt = conn.prepareStatement("SELECT booking_code, room_code, start_date, duration, rate FROM booking INNER JOIN conference_room ON booking.room_code = conference_room.code WHERE client_id = ? ORDER BY start_date ASC, rate DESC")){
					pstmt.setInt(1, clientId);
					try(ResultSet rslt = pstmt.executeQuery())
					{
						while(rslt.next())
						{
							String roomCode = rslt.getString("room_code");
							ConferenceRoomData crData = new ConferenceRoomData();
							ConferenceRoom cr = crData.getConferenceRoom(roomCode);
//							SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
//							Date date = formatter.parse("October 23, 2021");
							BigDecimal durationBig = BigDecimal.valueOf(rslt.getInt("duration"));
							BigDecimal dividendSixty = BigDecimal.valueOf(60);
							conferenceRoomBookings.add(new ConferenceRoomBooking(rslt.getInt("booking_code"), cr.getCode(), cr.getName(), cr.getCapacity(), rslt.getDate("start_date"), rslt.getInt("duration"), rslt.getBigDecimal("rate").multiply(durationBig).divide(dividendSixty)));
						}
					}
				}
			}
		}
		return conferenceRoomBookings;
	}

	
}
