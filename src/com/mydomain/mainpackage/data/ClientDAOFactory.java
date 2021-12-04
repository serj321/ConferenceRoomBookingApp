package com.mydomain.mainpackage.data;

import ca.senecacollege.prg556.hocorba.dao.ClientDAO;

public class ClientDAOFactory {

	public ClientDAOFactory() {
		// TODO Auto-generated constructor stub
	}
	public static ClientDAO getClientDAO()
	{
		return new ClientData();
	}

}
