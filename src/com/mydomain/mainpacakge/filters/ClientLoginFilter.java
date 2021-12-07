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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.on.senecac.prg556.common.StringHelper;
import ca.senecacollege.prg556.hocorba.bean.Client;

import com.mydomain.mainpackage.data.ClientDAOFactory;

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
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		try
		{
			HttpSession session = request.getSession();

			if (null == request.getSession().getAttribute("clientSession"))
			{
				
				String username = request.getParameter("clientId");
				String password = request.getParameter("clientPassword");
				if ("POST".equals(request.getMethod()) && StringHelper.isNotNullOrEmpty(username) && StringHelper.isNotNullOrEmpty(password))
				{
					Client client = ClientDAOFactory.getClientDAO().validateClient(username, password);
					if (client != null)
					{
						session.setAttribute("clientSession", client);
						response.sendRedirect(request.getContextPath() + "/bookings.jspx"); // redirect to context root folder
						return;
					}
					else
					{
						request.setAttribute("unsuccessfulLogin", Boolean.TRUE);
						request.setAttribute("clientId", username);
						chain.doFilter(req, resp);
					}
				}
				else
				{
					chain.doFilter(req, resp); // continue on to login.jspx
				}
			}
			else
			{						
				response.sendRedirect(request.getContextPath() + "/"); // redirect to context root folder
				return;
			}	
			
			/*else
			{
				response.sendRedirect(request.getContextPath() + "/"); // already logged in -- redirect to context root folder
				return;
			}*/
		}
		catch (SQLException sqle)
		{
			throw new ServletException(sqle);
		}
	}
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
