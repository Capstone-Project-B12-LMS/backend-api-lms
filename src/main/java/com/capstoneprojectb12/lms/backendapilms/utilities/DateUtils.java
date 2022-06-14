package com.capstoneprojectb12.lms.backendapilms.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtils {
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FinalVariable.DATE_FORMAT);
	
	public static Date parse(String date) {
		try {
			return simpleDateFormat.parse(date);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
}
