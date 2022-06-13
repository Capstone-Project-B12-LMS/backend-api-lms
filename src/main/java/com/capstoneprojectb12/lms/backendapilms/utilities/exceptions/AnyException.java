package com.capstoneprojectb12.lms.backendapilms.utilities.exceptions;

public class AnyException extends RuntimeException {
	@Override
	public String getMessage() {
		return "Here is any exception";
	}
}
