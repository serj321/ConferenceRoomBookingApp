package com.mydomain.mainpackage.data;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.math.BigDecimal;
import java.sql.SQLException;

import com.mydomain.mainpackage.ConferenceRoom;
import com.sun.xml.rpc.processor.modeler.j2ee.xml.string;

class ConferenceRoomData implements ConferenceRoomDAO {

	public ConferenceRoomData() {
		// TODO Auto-generated constructor stub
	}
	public List<ConferenceRoom> findAvailableConferenceRooms(Date start, int duration, Integer minimumCapacity, BigDecimal maximumRate) throws SQLException
	{
		String room1 = "111";
		List<ConferenceRoom> rooms = new ArrayList<ConferenceRoom>();
		for(int i = 0; i < 2; i++)
		{
			rooms.add(getConferenceRoom(room1));
		}
	}
	public getConferenceRoom getConferenceRoom(String roomCode) throws SQLException
	{
		String room1 = "111";
		String room2 = "222";
		String room3 = "333";
		
		if(roomCode == room1)
		{
			return new ConferenceRoom(room1,"Room 1", 25, 100.00);
		}
		if(roomCode == room2)
		{
			return new ConferenceRoom(room2,"Room 2", 35, 150.00);
		}
		if(roomCode == room3)
		{
			return new ConferenceRoom(room3,"Room 3", 45, 200.00);
		}
		else
			return null;
	}
}
