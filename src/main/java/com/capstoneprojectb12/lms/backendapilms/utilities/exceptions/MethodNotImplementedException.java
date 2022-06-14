package com.capstoneprojectb12.lms.backendapilms.utilities.exceptions;

public class MethodNotImplementedException extends RuntimeException {
	@Override
	public String getMessage() {
		return "Method not implemented";
	}
}
