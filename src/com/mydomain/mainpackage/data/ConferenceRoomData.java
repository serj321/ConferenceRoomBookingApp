package com.mydomain.mainpackage.data;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Date;
import java.math.BigDecimal;
import java.sql.SQLException;




import ca.senecacollege.prg556.hocorba.dao.ConferenceRoomDAO;


//import com.mydomain.mainpackage.ConferenceRoom;
//import com.sun.xml.rpc.processor.modeler.j2ee.xml.string;
//import java.math.BigDecimal;
//import javax.servlet.ServletException;

public class ConferenceRoomData implements ConferenceRoomDAO {

	List<ca.senecacollege.prg556.hocorba.bean.ConferenceRoom> rooms = new ArrayList<ca.senecacollege.prg556.hocorba.bean.ConferenceRoom>();
	public ConferenceRoomData() {
		// TODO Auto-generated constructor stub
	}
	@SuppressWarnings("finally")
	public List<ca.senecacollege.prg556.hocorba.bean.ConferenceRoom> findAvailableConferenceRooms(Date start, int duration, Integer minimumCapacity, BigDecimal maximumRate) throws SQLException
	{
		try
		{
			String room1 = "111";
			for(int i = 0; i < 2; i++)
			{
				rooms.add(getConferenceRoom(room1));
			}
			
			}
			catch(SQLException sqle)
			{
				//throw new ServletException(sqle);
			}
		finally
		{
			return rooms;
		}
	}
	public ca.senecacollege.prg556.hocorba.bean.ConferenceRoom getConferenceRoom(String roomCode) throws SQLException
	{
		String room1 = "111";
		String room2 = "222";
		String room3 = "333";
		
		if(roomCode == room1)
		{	
			BigDecimal rate = new BigDecimal("100");
			return new ca.senecacollege.prg556.hocorba.bean.ConferenceRoom(room1,"Room 1", 25, rate);
		}
		if(roomCode == room2)
		{
			BigDecimal rate = new BigDecimal("170");
			return new ca.senecacollege.prg556.hocorba.bean.ConferenceRoom(room1,"Room 2", 35, rate);		
		}
		if(roomCode == room3)
		{
			BigDecimal rate = new BigDecimal("230");
			return new ca.senecacollege.prg556.hocorba.bean.ConferenceRoom(room1,"Room 3", 45, rate);		
		}
		else
			return null;
	}
}
