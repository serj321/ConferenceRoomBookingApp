package com.mydomain.mainpackage.data;

import ca.senecacollege.prg556.hocorba.dao.ConferenceRoomDAO;

public class ConferenceRoomDAOFactory 
{
	public static ConferenceRoomDAO getConferenceRoomDAO()
	{
		return new ConferenceRoomData();
	}
}
