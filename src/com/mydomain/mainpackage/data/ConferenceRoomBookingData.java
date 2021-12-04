package com.mydomain.mainpackage.data;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.sql.DataSource;

import ca.senecacollege.prg556.hocorba.bean.ConferenceRoom;
import ca.senecacollege.prg556.hocorba.bean.ConferenceRoomBooking;

public class ConferenceRoomBookingData {
	
	private DataSource ds;
	
	ConferenceRoomBookingData(DataSource ds){
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
			try (PreparedStatement pstmt = conn.prepareStatement("SELECT client_id, room_code, start_date, duration FROM booking WHERE booking_code = ?")){
				pstmt.setInt(1,  bookingCode);
				try (ResultSet rslt = pstmt.executeQuery()){
					if(rslt.next()){
						ConferenceRoomData crData = new ConferenceRoomData();
						ConferenceRoom cr = crData.getConferenceRoom(rslt.getString("room_code"));
						BigDecimal durationBig = BigDecimal.valueOf(rslt.getInt("duration"));
						BigDecimal dividendSixty = BigDecimal.valueOf(60);
						
						return new ConferenceRoomBooking( 
								bookingCode, 
								rslt.getString("room_code"), 
								cr.getName(), 
								cr.getCapacity(), 
								rslt.getDate("start_date"), 
								rslt.getInt("duration"), 
								cr.getRate().multiply(durationBig).divide(dividendSixty)
								
								);
					}
				}
			}
		}
		return null;
	}
	
	public List<ConferenceRoomBooking> getConferenceRoomBookings (int clientId) throws SQLException, ParseException, ServletException{
		try{
			ConferenceRoomData crData = new ConferenceRoomData();
			ConferenceRoom cr1 = crData.getConferenceRoom("111");
			ConferenceRoom cr2 = crData.getConferenceRoom("222");
			SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
			Date date1 = formatter.parse("October 23, 2021");
			Date date2 = formatter.parse("October 25, 2021");
			List<ConferenceRoomBooking> crBookings = new ArrayList<ConferenceRoomBooking>();
			crBookings.add(new ConferenceRoomBooking(1, cr1.getCode(), cr1.getName(), cr1.getCapacity(), date1, 90, BigDecimal.valueOf(150.00)));
			crBookings.add(new ConferenceRoomBooking(2, cr2.getCode(), cr2.getName(), cr2.getCapacity(), date2, 120, BigDecimal.valueOf(100.00)));
			return crBookings;
		}
	}


	
}
