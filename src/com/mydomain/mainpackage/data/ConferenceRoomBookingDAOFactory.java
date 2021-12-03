package com.mydomain.mainpackage.data;

import ca.senecacollege.prg556.hocorba.dao.ConferenceRoomDAO;

// import ConferenceROomBookingDAO;

public class ConferenceRoomBookingDAOFactory {

	public static ConferenceRoomDAO getConferenceRoomBookingDAO(){
		return new ConferenceRoomData();
	}
	
}
