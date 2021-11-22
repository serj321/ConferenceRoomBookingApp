package com.mydomain.mainpackage.data;

import com.mydomain.mainpackage.ConferenceRoomDAO;

public class ConferenceRoomDAOFactory {

	public ConferenceRoomDAOFactory() {
		// TODO Auto-generated constructor stub
	}
	public static ConferenceRoomDAO getConferenceRoomDAO()
	{
		return new ConferenceRoomDAO;
	}
}
