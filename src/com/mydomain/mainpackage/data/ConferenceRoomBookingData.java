package com.mydomain.mainpackage.data;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import ca.senecacollege.prg556.hocorba.bean.ConferenceRoom;
import ca.senecacollege.prg556.hocorba.bean.ConferenceRoomBooking;

public class ConferenceRoomBookingData {
	
	public ConferenceRoomBooking bookConferenceRoom(int clientId, String roomCode, Date start, int duration){
		try{
			ConferenceRoomData crData = new ConferenceRoomData();
			ConferenceRoom cr = crData.getConferenceRoom(roomCode);
			BigDecimal durationBig = BigDecimal.valueOf(duration);
			BigDecimal dividendSixty = BigDecimal.valueOf(60);
			ConferenceRoomBooking crBooking = new ConferenceRoomBooking( /*need to check where to get this code*/
					1, roomCode, cr.getName(), cr.getCapacity(), start, duration, cr.getRate().multiply(durationBig).divide(dividendSixty));
			
		}catch(SQLException sqle){
			throw new ServletException(sqle);
		}
	}
	
	public void cancelConferenceRoomBooking(int bookingCode){
		try{
			
		}catch(SQLException sqle){
			throw new ServletException(sqle);
		}
	}
	
	public ConferenceRoomBooking getConferenceRoomBooking(int bookingCode){
		try{
			//NEED TO READ FURTHER
			
			
		}catch(SQLException sqle){
			throw new ServletException(sqle);
		}
	}
	
	public List<ConferenceRoomBooking> getConferenceRoomBookings (int clientId){
		try{
			List<ConferenceRoomBooking> crBookings = {new ConferenceRoom("111", "Room 2", 35, 150.00), 
			                                          new ConferenceRoom("222","Room 1", 25, 100.00)};
			return crBookings;
		}catch(SQLException sqle){
			throw new ServletException(sqle);
		}
	}
	
}
