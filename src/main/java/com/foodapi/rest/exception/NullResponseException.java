/**
 * Spring Boot Custom Exception class
 * @author Abhijeet Kotkar
 */

package com.foodapi.rest.exception;

public class NullResponseException extends Exception {

	private static final long serialVersionUID = 1L;

	public NullResponseException(String msg) {
		super(msg);
	}

}
