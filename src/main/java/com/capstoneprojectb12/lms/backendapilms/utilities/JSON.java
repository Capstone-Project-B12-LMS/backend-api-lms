package com.capstoneprojectb12.lms.backendapilms.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSON {
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	public static String create(Object object) {
		return objectMapper.valueToTree(object).toPrettyString();
	}
}
