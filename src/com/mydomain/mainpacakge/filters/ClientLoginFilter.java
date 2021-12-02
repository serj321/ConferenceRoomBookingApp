package com.mydomain.mainpacakge.filters;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import ca.senecacollege.prg556.hocorba.bean.Client;

import com.mydomain.mainpackage.data.ClientData;

/**
 * Servlet Filter implementation class ClientLoginFilter
 */
@WebFilter(
		dispatcherTypes = {
				DispatcherType.REQUEST, 
				DispatcherType.FORWARD
		}
					, 
		urlPatterns = { 
				"/ClientLoginFilter", 
				"/clientlogin.jspx"
		})
public class ClientLoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public ClientLoginFilter() {
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
	public void doFilter(ServletRequest request,ServletResponse response, FilterChain chain) throws ServletException, IOException		// TODO Auto-generated method stub
		
	{
		// place your code here
	
		HttpServletRequest req = (HttpServletRequest)request;
		
		if ("POST".equals(((HttpServletRequest) request).getMethod())) // request.getMethod requires an HttpServletRequest object
		{	
			ClientData clientData = new ClientData();
			//Client client = new Client();
			String clientID = req.getParameter("clientId");
			String clientPassword = req.getParameter("clientPassword");
			
			if(clientID != null && !clientID.trim().isEmpty() && clientPassword != null && !clientPassword.trim().isEmpty())
			{
				try 
				{
					if(clientData.validateClient(clientID, clientPassword) != null)
					{
						//redirect to context root
					}
				}
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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
