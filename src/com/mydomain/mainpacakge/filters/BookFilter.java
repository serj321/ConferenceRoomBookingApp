package com.mydomain.mainpacakge.filters;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import ca.on.senecac.prg556.common.StringHelper;
import ca.senecacollege.prg556.hocorba.bean.Client;
import ca.senecacollege.prg556.hocorba.bean.ConferenceRoom;
import ca.senecacollege.prg556.hocorba.dao.ConferenceRoomDAO;

import com.mydomain.mainpackage.data.BadRequestException;
import com.mydomain.mainpackage.data.ClientData;
import com.mydomain.mainpackage.data.ConferenceRoomBookingData;
import com.mydomain.mainpackage.data.ConferenceRoomData;

/**
 * Servlet Filter implementation class BookFilter
 */
@WebFilter("/book.jspx")
public class BookFilter implements Filter {

    /**
     * Default constructor. 
     */
    public BookFilter() {
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
		HttpServletRequest request = (HttpServletRequest)req;
		
		if ("POST".equals(request.getMethod())) // request.getMethod requires an HttpServletRequest object
		{
			//Starting Date Check
			String startingDate = request.getParameter("startingDate");
			
			boolean startDateNotValid;
			if(startingDate == null || startingDate == ""){
				startDateNotValid = true;
				request.setAttribute("startDateNotValiod", startDateNotValid);
				throw new BadRequestException("start date not valid");
			} else{
				startDateNotValid = false;
				//Still unsure on how to validate a date
				request.setAttribute("startingDate", startingDate);
			}
			
			//Start time set
			String startTime = request.getParameter("start");
			request.setAttribute("start", startTime);
			
			
			//Duration set
			String duration = request.getParameter("duration");
			request.setAttribute("duration", duration);
			
			
			//Capacity check and set
			String capacity = request.getParameter("minCap");
			boolean capacityNotValid;
			
			if(capacity == null || capacity == ""){
				capacityNotValid = false;
				request.setAttribute("capacityNotValid", capacityNotValid);
			}else{
				int capacityInt;
				
				try{
					capacityInt = Integer.parseInt(capacity);
					if (capacityInt < 0){
						capacityNotValid = true;
						request.setAttribute("capacityNotValid", capacityNotValid);
					} else{
						capacityNotValid = false;
						request.setAttribute("capacityInt", capacityInt);
					}
				} catch (NumberFormatException e){
					capacityNotValid = true;
					request.setAttribute("capacityNotValid", capacityNotValid);
				}
			}
			
			
			//Max Rate check and set
			String maxRate = request.getParameter("maxRate");
			boolean maxRateNotValid;
			
			if(maxRate == null || maxRate == ""){
				maxRateNotValid = false;
				request.setAttribute("maxRateNotValid", maxRateNotValid);
			}else{
				
				
				double maxRateDouble;
				
				try{
					maxRateDouble = Double.valueOf(maxRate);
					if(maxRateDouble < 0){
						maxRateNotValid = true;
						request.setAttribute("maxRateNotValid", maxRateNotValid);
					} else{
						maxRateNotValid = false;
						BigDecimal maxRateBig = BigDecimal.valueOf(maxRateDouble);
						request.setAttribute("maxRateBig", maxRateBig);
					}
				} catch (NumberFormatException e){
					maxRateNotValid = true;
					request.setAttribute("maxRateNotValid", maxRateNotValid);
				}
				
				
			}
			
			//Room Code set and check
			String roomCode = request.getParameter("roomCode");
			//Do this if the submit button was pressed
			if(roomCode == null || roomCode == ""){
				throw new BadRequestException("date is invalid");
			} else{
				ConferenceRoomData crData = new ConferenceRoomData();
				
				try {
					if(crData.getConferenceRoom(roomCode) == null){
						throw new BadRequestException("Room ID is invalid");
					} else{
						ConferenceRoom cr = crData.getConferenceRoom(roomCode);
						SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
						long startTimeLong = Long.parseLong(startTime) * 60000L;
						Date dateParam = formatter.parse(startingDate);
						long startTimeCombined = (dateParam.getTime() + startTimeLong);
						dateParam.setTime(startTimeCombined);
						ClientData cd = new ClientData();
						if(cd.getClient(1) == null){
							throw new BadRequestException("client ID is invalid");
						} else{
							Client client = cd.getClient(1);
							ConferenceRoomBookingData crBookingData = new ConferenceRoomBookingData();
							crBookingData.bookConferenceRoom(client.getId(), roomCode, dateParam, Integer.parseInt(duration));
							
						}
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//Otherwise do this
			
			if( !startDateNotValid && !capacityNotValid && !maxRateNotValid ){
				ConferenceRoomData crData = new ConferenceRoomData();
				SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
				long startTimeLong = Long.parseLong(startTime) * 60000L;
				Date dateParam;
				try {
					dateParam = formatter.parse(startingDate);
					long startTimeCombined = (dateParam.getTime() + startTimeLong);
					dateParam.setTime(startTimeCombined);
					
					double maxRateDouble = Double.valueOf(maxRate);
					BigDecimal maxRateBig = BigDecimal.valueOf(maxRateDouble);
					
					try {
						List<ConferenceRoom> crs = crData.findAvailableConferenceRooms(
								dateParam, 
								Integer.parseInt(duration), 
								(StringHelper.isNullOrEmpty(capacity)) ? null : Integer.parseInt(capacity), 
								(StringHelper.isNullOrEmpty(maxRate)) ? null : maxRateBig 
								);
						request.setAttribute("conferenceRooms", crs);
					} catch (NumberFormatException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

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
