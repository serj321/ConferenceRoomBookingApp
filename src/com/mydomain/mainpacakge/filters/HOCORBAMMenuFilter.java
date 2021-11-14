package com.mydomain.mainpacakge.filters;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import ca.senecacollege.prg556.hocorba.bean.Client;
import ca.senecacollege.prg556.hocorba.dao.ConferenceRoomBookingDAO;

import com.mydomain.mainpackage.data.ClientData;
import com.mydomain.mainpackage.data.ConferenceRoomBookingDAOFactory;

/**
 * Servlet Filter implementation class HOCORBAMMenuFilter
 */
@WebFilter("/hocorbamenu.jspx")
public class HOCORBAMMenuFilter implements Filter {

    /**
     * Default constructor. 
     */
    public HOCORBAMMenuFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		try {
			
			HttpServletRequest request = (HttpServletRequest)req;
			ClientData cd = new ClientData();
			Client client = cd.getClient(1);
			ConferenceRoomBookingDAO crBookingDAO = ConferenceRoomBookingDAOFactory.getConferenceRoomBookingDAO();
			
			request.setAttribute("client", client);
			request.setAttribute("bookingCount", crBookingDAO.getConferenceRoomBookings(client.getId()).size());
			
		} catch (SQLException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		// pass the request along the filter chain
		chain.doFilter(req, res);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
