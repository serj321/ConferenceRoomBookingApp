package com.mydomain.mainpacakge.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.on.senecac.prg556.common.StringHelper;

/**
 * Servlet Filter implementation class AuthenticatedAccessFilter
 */
@WebFilter("/*")
public class AuthenticatedAccessFilter implements Filter {
	private String loginPage = "/clientlogin.jspx";
    /**
     * Default constructor. 
     */
    public AuthenticatedAccessFilter() {
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
		String uriLogin = request.getContextPath() + getLoginPage();

		// pass the request along the filter chain
		if (null == request.getSession().getAttribute("userSession")){
			((HttpServletResponse)resp).sendRedirect(uriLogin);
		}
		else{
			chain.doFilter(req, resp);
		}
	}
	
	
	

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		if (StringHelper.isNotNullOrEmpty(fConfig.getInitParameter("login")))
			setLoginPage(StringHelper.stringPrefix(fConfig.getInitParameter("login"), "/"));
	}
	public synchronized String getLoginPage() {
		return loginPage;
	}

	private synchronized void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}
}
