package com.capstoneprojectb12.lms.backendapilms.services;

import dev.ditsche.mailo.MailAddress;
import dev.ditsche.mailo.factory.MailBuilder;
import dev.ditsche.mailo.provider.MailProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {
	@Value(value = "${mail.me}")
	private String myEmail;
	@Value(value = "${mail.myname}")
	private String myName;
	
	private final MailProvider mailProvider;
	
	public boolean sendVerification(String to) {
		try {
			var mail = MailBuilder.html()
					.from(new MailAddress(myEmail, myName))
					.to(new MailAddress(to))
					.subject("Email Verification")
					.build();
			return mailProvider.send(mail);
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
}
