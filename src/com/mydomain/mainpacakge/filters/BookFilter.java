package com.mydomain.mainpacakge.filters;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

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
			String startingDate = request.getParameter("startingDate");
			
			if(startingDate == null || startingDate == ""){

				
			}
		}
		
		String startTime = request.getParameter("start");
		request.setAttribute("start", startTime);
		
		String duration = request.getParameter("duration");
		request.setAttribute("duration", duration);
		
		String capacity = request.getParameter("minCap");
		boolean capacityNotValid;
		
		if(capacity == null || capacity == ""){
			capacityNotValid = true;
			request.setAttribute("capacityInvalid", capacityNotValid);
		}else{
			int capacityInt;
			
			try{
				capacityInt = Integer.parseInt(capacity);
				if (capacityInt < 0){
					capacityNotValid = true;
					request.setAttribute("capacityInvalid", capacityNotValid);
				} else{
					request.setAttribute("capacityInt", capacityInt);
				}
			} catch (NumberFormatException e){
				capacityNotValid = true;
				request.setAttribute("capacityInvalid", capacityNotValid);
			}
		}
		
		String maxRate = request.getParameter("maxRate");
		boolean maxRateNotValid;
		
		if(maxRate == null || maxRate == ""){
			maxRateNotValid = true;
			request.setAttribute("maxRateNotValid", maxRateNotValid);
		}else{
			
			
			double maxRateDouble;
			
			try{
				maxRateDouble = Double.valueOf(maxRate);
				if(maxRateDouble < 0){
					maxRateNotValid = true;
					request.setAttribute("maxRateNotValid", maxRateNotValid);
				} else{
					BigDecimal maxRateBig = BigDecimal.valueOf(maxRateDouble);
					request.setAttribute("maxRateBig", maxRateBig);
				}
			} catch (NumberFormatException e){
				maxRateNotValid = true;
				request.setAttribute("maxRateNotValid", maxRateNotValid);
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
