package com.capstoneprojectb12.lms.backendapilms.utilities.exceptions;

import com.capstoneprojectb12.lms.backendapilms.utilities.FinalVariable;

public class DataNotFoundException extends RuntimeException {
	@Override
	public String getMessage() {
		return FinalVariable.DATA_NOT_FOUND;
	}
}
