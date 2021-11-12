package com.mydomain.mainpackage;

class ConferenceRoomData implements ConferenceRoomDAO{

	public ConferenceRoomData() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public List<ConferenceRoom> findAvailableConferenceRooms(Date start, int duration, Integer minimumCapacity, BigDecimal maximumRate) throws SQLException
	{
		ConferenceRoomDAOFactory roomDAO = new ConferenceRoomDAOFactory();
		List<ConferenceRoom> rooms = new ArrayList<ConferenceRoom>();
		for(int i = 0; i < 2; i++)
		{
			rooms.add(getConferenceRoomDAO());
		}
	}
	
	public ConferenceRoom getConferenceRoom(String roomCode) throws SQLException
	{
		
	}
	
}
