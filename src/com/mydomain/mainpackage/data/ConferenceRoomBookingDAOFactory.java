package com.mydomain.mainpackage.data;

import ca.senecacollege.prg556.hocorba.dao.ConferenceRoomBookingDAO;

// import ConferenceROomBookingDAO;

public class ConferenceRoomBookingDAOFactory {

	public static ConferenceRoomBookingDAO getConferenceRoomBookingDAO(){
		return new ConferenceRoomBookingDAO();
	}
	
}
