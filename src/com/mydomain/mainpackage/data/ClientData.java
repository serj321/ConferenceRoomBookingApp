package com.mydomain.mainpackage.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;
import ca.senecacollege.prg556.hocorba.bean.Client;
import ca.senecacollege.prg556.hocorba.dao.ClientDAO;

//import com.sun.jersey.api.client.Client;

public class ClientData implements ClientDAO
{
	private DataSource ds;
	public ClientData() 
	{
		// TODO Auto-generated constructor stub
	}
	public Client getClient(int id) throws SQLException
	{
		try (Connection conn = getDs().getConnection())
		{
			try (PreparedStatement pstmt = conn.prepareStatement("SELECT id, first_name, last_name FROM client WHERE id = ?"))
			{
				pstmt.setInt(1, id);
				try (ResultSet rslt = pstmt.executeQuery())
				{
					if (rslt.next())
						return new Client(id, rslt.getString("first_name"), rslt.getString("last_name"));
					else
						return null;
				}
			}
		}
	
	}
	public Client validateClient(String username, String password) throws SQLException
	{
		try (Connection conn = getDs().getConnection()){
			try (PreparedStatement pstmt = conn.prepareStatement("SELECT id, first_name, last_name FROM client WHERE username = ? AND password = ?"))
			{
				pstmt.setString(1,username);
				pstmt.setString(2,password);
				try (ResultSet rslt = pstmt.executeQuery())
				{
					if (rslt.next())
						return new Client(rslt.getInt("id"), rslt.getString("first_name"), rslt.getString("last_name"));
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

