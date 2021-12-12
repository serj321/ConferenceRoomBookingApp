//Author: Vishesh Mendiratta
package com.mydomain.mainpackage.data;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Date;
import java.math.BigDecimal;
import java.sql.SQLException;



import javax.servlet.ServletException;
import javax.sql.DataSource;

import ca.senecacollege.prg556.hocorba.bean.ConferenceRoom;
import ca.senecacollege.prg556.hocorba.bean.ConferenceRoomBooking;
import ca.senecacollege.prg556.hocorba.dao.ConferenceRoomDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import ca.on.senecac.prg556.common.StringHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.sun.jmx.snmp.Timestamp;



import java.sql.Connection;


 public class ConferenceRoomData implements ConferenceRoomDAO 
{
	private DataSource ds;
	
	
	ConferenceRoomData(DataSource ds)
	{
//		setDs(ds);
	}
	
	public ConferenceRoomData() {
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("finally")
	public List<ConferenceRoom> findAvailableConferenceRooms(Date start, int duration, Integer minimumCapacity, BigDecimal maximumRate) throws SQLException
	{
		List<ConferenceRoom> rooms = new ArrayList<ConferenceRoom>();
		
		java.sql.Date sqlDate = new java.sql.Date(start.getTime());
		java.sql.Date sqlEndDate = new java.sql.Date(start.getTime() + duration*60000L);
		try{
			try (Connection conn = getDs().getConnection())
			{
				try (Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE))
				{
					if(minimumCapacity != null)
					{
						if(maximumRate != null)
						{
							try (PreparedStatement pstmt = conn.prepareStatement("SELECT code, name, capacity, rate FROM conference_room WHERE code NOT IN (SELECT room_code FROM booking WHERE ? < DATEADD(minute, duration, start_date) AND ? > start_date) AND capacity >= ? AND rate <= ? ORDER BY rate ASC, capacity DESC"))
							{
								pstmt.setDate(1, sqlDate);
								pstmt.setDate(2, sqlEndDate);
								pstmt.setInt(3,minimumCapacity);
								pstmt.setBigDecimal(4,maximumRate);
								try(ResultSet rslt = pstmt.executeQuery())
								{
									while(rslt.next())
									{
										String roomCode = rslt.getString("code");
										ConferenceRoomData crData = new ConferenceRoomData();
										//ConferenceRoom cr = crData.getConferenceRoom(roomCode);
										
										rooms.add(new ConferenceRoom(rslt.getString("code"),
												  rslt.getString("name"),
									              rslt.getInt("capacity"),
									              rslt.getBigDecimal("rate")));
									}
								}
								
								catch(SQLException sqle)
								{
									throw new ServletException(sqle);
								}
								finally
								{
									return rooms;
								}
							}		
						}
						return rooms;
					}
					return rooms;
				}
			}
		} catch(Exception e){
			return rooms;
		}
		
	}
	public ConferenceRoom getConferenceRoom(String roomCode) throws SQLException
	{
		
		try (Connection conn = getDs().getConnection())
		{
			try (PreparedStatement pstmt = conn.prepareStatement("SELECT code, name, capacity, rate FROM conference_room WHERE code = ?"))
			{
				pstmt.setString(1, roomCode);
				try (ResultSet rslt = pstmt.executeQuery())
				{
					if (rslt.next())
						return new ConferenceRoom(rslt.getString("code"),
								  rslt.getString("name"),
					              rslt.getInt("capacity"),
					              rslt.getBigDecimal("rate"));
					else
						return null;
				}
			}
		}
	}
	
	private DataSource getDs()
	{
		return DataSourceFactory.getDataSource();
	}
//	private void setDs(DataSource ds)
//	{
//		this.ds = ds;
//	}
}
