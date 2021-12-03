package com.mydomain.mainpackage.data;

import com.sun.appserv.jdbc.DataSource;

public class DataSourceFactory {

	private static DataSource dataSource;
	
	public static DataSource getDataSource(){
		return dataSource;
	}
	
	public static void setDataSource(DataSource dataSource){
		DataSourceFactory.dataSource = dataSource;
	}
	
}
