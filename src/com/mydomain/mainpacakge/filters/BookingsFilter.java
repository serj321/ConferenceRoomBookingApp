//Author: Vishesh Mendiratta
package com.mydomain.mainpacakge.filters;
import ca.on.senecac.prg556.common.StringHelper;
import ca.senecacollege.prg556.hocorba.bean.Client;
import ca.senecacollege.prg556.hocorba.bean.ConferenceRoomBooking;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mydomain.mainpackage.data.BadRequestException;
import com.mydomain.mainpackage.data.ConferenceRoomBookingData;
import com.mydomain.mainpackage.data.DataSourceFactory;


/**
 * Servlet Filter implementation class BookingsFilter
 */
@WebFilter(
		dispatcherTypes = {
				DispatcherType.REQUEST, 
				DispatcherType.FORWARD
		}
					, 
		urlPatterns = { 
				"/BookingsFilter", 
				"/bookings.jspx"
		})
public class BookingsFilter implements Filter {

    /**
     * Default constructor. 
     */
    public BookingsFilter() {
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
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		HttpServletRequest request = (HttpServletRequest)req;
		
		HttpSession session = request.getSession();

		try
		{
			
			// ConferenceRoomDAO dao = ConferenceRoomDAOFactory.getConferenceRoomDAO();
			ConferenceRoomBookingData roomBooking = new ConferenceRoomBookingData(DataSourceFactory.getDataSource());
			if ("POST".equals(request.getMethod()) && StringHelper.isNotNullOrEmpty(request.getParameter("cancel-booking-clicked") ))
			{
				int getBookingCode = Integer.parseInt(request.getParameter("bookingCode"));
				if(getBookingCode < 0 )
				{
					throw new BadRequestException("Not a valid Booking Code	");
				}
				else
					roomBooking.cancelConferenceRoomBooking(getBookingCode);//  .cancelConferenceRoomBooking(getBookingCode);
					//roomBooking.getConferenceRoomBooking(getBookingCode);
			}
			Client client = ((Client)session.getAttribute("clientSession"));
			try{
				List<ConferenceRoomBooking> crbList = roomBooking.getConferenceRoomBookings(client.getId());
				request.setAttribute("crbList",crbList );
				chain.doFilter(req, resp);
			} catch(SQLException e)
			{
					
			}
		}
		catch (IllegalArgumentException | NullPointerException e) // NumberFormatException is a child of IllegalArgumentException
		{
			throw new BadRequestException(e);
		}
		catch (SQLException sqle)
		{
			throw new ServletException(sqle);
		} 
		// pass the request along the filter chain
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
