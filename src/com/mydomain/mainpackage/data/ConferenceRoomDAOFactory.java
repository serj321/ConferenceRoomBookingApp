package com.mydomain.mainpackage.data;

import ca.senecacollege.prg556.hocorba.dao.ConferenceRoomDAO;

public class ConferenceRoomDAOFactory {

	public ConferenceRoomDAOFactory() {
		// TODO Auto-generated constructor stub
	}
	public static ConferenceRoomDAO getConferenceRoomDAO()
	{
		return new ConferenceRoomDAO;
	}
}
