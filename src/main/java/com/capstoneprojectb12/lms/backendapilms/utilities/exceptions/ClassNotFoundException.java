package com.capstoneprojectb12.lms.backendapilms.utilities.exceptions;

public class ClassNotFoundException extends RuntimeException {
	@Override
	public String getMessage() {
		return "Class not found";
	}
}
