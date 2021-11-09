package com.mydomain.mainpackage.data;

public class ConferenceRoomDAOFactory {

	public ConferenceRoomDAOFactory() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static ConferenceRoomDAO getConferenceRoomDAO()
	{
		return new ConferenceRoomDAO;
	}
}
