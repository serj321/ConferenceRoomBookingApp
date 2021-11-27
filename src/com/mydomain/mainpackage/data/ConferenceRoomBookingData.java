package com.mydomain.mainpackage.data;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import ca.senecacollege.prg556.hocorba.bean.ConferenceRoom;
import ca.senecacollege.prg556.hocorba.bean.ConferenceRoomBooking;

public class ConferenceRoomBookingData {
	
	public ConferenceRoomBooking bookConferenceRoom(int clientId, String roomCode, Date start, int duration) throws ServletException{
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
		return null;
	}
	
	public void cancelConferenceRoomBooking(int bookingCode) throws ServletException{
	}
	
	public ConferenceRoomBooking getConferenceRoomBooking(int bookingCode) throws ServletException{
		return null;
	}
	
	public List<ConferenceRoomBooking> getConferenceRoomBookings (int clientId) throws ServletException, ParseException{
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
		}catch(SQLException sqle){
			throw new ServletException(sqle);
		}
	}
	
}
