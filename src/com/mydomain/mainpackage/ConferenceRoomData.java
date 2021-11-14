package com.mydomain.mainpackage;
import java.sql.SQLException;

import javax.servlet.ServletException;

class ConferenceRoomData implements ConferenceRoomDAO{

	public ConferenceRoomData() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public List<ConferenceRoom> findAvailableConferenceRooms(Date start, int duration, Integer minimumCapacity, BigDecimal maximumRate) throws SQLException, ServletException
	{
		try
		{
			ConferenceRoomDAOFactory roomDAO = new ConferenceRoomDAOFactory();
			List<ConferenceRoom> rooms = new ArrayList<ConferenceRoom>();
			for(int i = 0; i < 2; i++)
			{
				rooms.add(getConferenceRoomDAO());
			}
			return rooms;
		}
		catch(SQLException sqle)
		{
			throw new ServletException(sqle);
		}
	}
	
	public ConferenceRoom getConferenceRoom(String roomCode) throws SQLException
	{
		
	}
	
}
