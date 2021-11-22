package com.mydomain.mainpackage.data;

import javax.servlet.ServletException;

public class BadRequestException extends ServletException {

	public BadRequestException() {
		// TODO Auto-generated constructor stub
		//figure out how o override all super class constructors
	}

	public BadRequestException(String message)
	{
		super(message);
	}

	/**
	 * Constructs a NoCookieException with the specified root cause.
	 *
	 * @param rootCause	the root cause
	 */
	public BadRequestException(Throwable rootCause)
	{
		super(rootCause);
	}

	/**
	 * Constructs a NoCookieException with the specified detail message and root cause.
	 *
	 * @param message	the detail message
	 * @param rootCause	the root cause
	 */
	public BadRequestException(String message, Throwable rootCause)
	{
		super(message, rootCause);
	}

}
