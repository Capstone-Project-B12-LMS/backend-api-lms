package com.capstoneprojectb12.lms.backendapilms.utilities.exceptions;

public class UserNotFoundException extends RuntimeException {
	@Override
	public String getMessage() {
		return "User not found";
	}
}
