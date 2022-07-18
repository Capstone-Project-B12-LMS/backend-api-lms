package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.UserNotFoundException;
import com.capstoneprojectb12.lms.backendapilms.utilities.jwt.JwtUtils;
import dev.ditsche.mailo.MailAddress;
import dev.ditsche.mailo.factory.MailBuilder;
import dev.ditsche.mailo.provider.MailProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
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
	private final UserRepository userRepository;
	private final MailProvider mailProvider;
	private final HttpServletRequest request;
	private final JwtUtils jwtUtils;
	
	public boolean sendVerification(String to, String name) {
		try {
			var user = this.userRepository.findByEmailEqualsIgnoreCase(to).orElseThrow(UserNotFoundException :: new);
			var mail = MailBuilder.html()
					.from(new MailAddress(myEmail, myName))
					.to(new MailAddress(to, name))
					.subject("Email Verification")
					.param("name", name)
					.param("verify", ServletUriComponentsBuilder.fromContextPath(request).toUriString() + String.format("/restapi/v1/users/verify/%s/%s", user.getId(), this.jwtUtils.generateTokenString(user)))
					.loadTemplate("email/verify.html")
					.build();
			return mailProvider.send(mail);
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}
}
