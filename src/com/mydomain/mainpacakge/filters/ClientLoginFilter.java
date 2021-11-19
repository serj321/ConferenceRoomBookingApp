package com.mydomain.mainpacakge.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClientLoginFilter {

	public ClientLoginFilter() {
		// TODO Auto-generated constructor stub
	}
	void doFilter(ServletRequest req,ServletResponse resp, FilterChain chain) throws ServletException, IOException
	{
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
	}

}
