package com.capstoneprojectb12.lms.backendapilms.utilities.exceptions;

public class InvalidTokenException extends RuntimeException {
	@Override
	public String getMessage() {
		return "Invalid token, maybe you use expired token";
	}
}
