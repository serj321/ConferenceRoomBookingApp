package com.mydomain.mainpackage.data;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import com.mydomain.mainpackage.data.DataSourceFactory;

/**
 * Application Lifecycle Listener implementation class HOCORBAContextListener
 *
 */
@WebListener
public class HOCORBAContextListener implements ServletContextListener {

	@Resource(name = "HocorbaDS")
	private DataSource _ds;

    /**
     * Default constructor. 
     */
    public HOCORBAContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent context)
	{
		DataSourceFactory.setDataSource(_ds);
	}

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent context) 
    {
        // TODO Auto-generated method stub
    	DataSourceFactory.setDataSource(null);
    }
	
}
