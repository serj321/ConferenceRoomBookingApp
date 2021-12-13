package com.mydomain.mainpacakge.filters;

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

import com.mydomain.mainpackage.data.ConferenceRoomBookingDAOFactory;
import com.mydomain.mainpackage.data.ConferenceRoomBookingData;
import com.mydomain.mainpackage.data.DataSourceFactory;

import ca.senecacollege.prg556.hocorba.bean.Client;
import ca.senecacollege.prg556.hocorba.bean.ConferenceRoomBooking;
import ca.senecacollege.prg556.hocorba.dao.ConferenceRoomBookingDAO;

/**
 * Servlet Filter implementation class HOCORBAMenuFilter
 */
@WebFilter(
		dispatcherTypes = {DispatcherType.INCLUDE }
					, 
		urlPatterns = { 
				"/HOCORBAMenuFilter", 
				"/hocorbamenu.jspx"
		})
public class HOCORBAMenuFilter implements Filter {

    /**
     * Default constructor. 
     */
    public HOCORBAMenuFilter() {
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
try {
			
			HttpServletRequest request = (HttpServletRequest)req;
			ConferenceRoomBookingDAO dao = ConferenceRoomBookingDAOFactory.getConferenceRoomBookingDAO();
			Client client = ((Client)request.getSession().getAttribute("clientSession"));
			dao = new ConferenceRoomBookingData(DataSourceFactory.getDataSource());
			List<ConferenceRoomBooking> conferenceRoomBookings = dao.getConferenceRoomBookings(client.getId());
			request.setAttribute("bookingCount", conferenceRoomBookings.size());
			
		} catch (SQLException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		// pass the request along the filter chain
		chain.doFilter(req, resp);
	}
	

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
