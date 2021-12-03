package com.mydomain.mainpacakge.filters;
import ca.senecacollege.prg556.hocorba.dao.ConferenceRoomDAO;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.mydomain.mainpackage.data.BadRequestException;
import com.mydomain.mainpackage.data.ConferenceRoomDAOFactory;

import ca.on.senecac.prg556.sima.InvalidRequestException;

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
		//check what this ca.on. shit has to be changed to somethingelse
		if (request.getAttribute("ca.on.senecac.prg556.common.exception") != null)
			throw new BadRequestException((Exception)request.getAttribute("ca.on.senecac.prg556.common.exception"));
		
		try
		{
			ConferenceRoomDAO dao = ConferenceRoomDAOFactory.getConferenceRoomDAO();
			if ("POST".equals(request.getMethod()))
				throw new BadRequestException("Not a valid request");
		}
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
