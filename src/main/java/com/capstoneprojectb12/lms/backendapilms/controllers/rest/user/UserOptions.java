package com.capstoneprojectb12.lms.backendapilms.controllers.rest.user;

import com.capstoneprojectb12.lms.backendapilms.services.UserService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = {"/restapi/v1/users"})
public class UserOptions {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = {"/verify/{userId}/{token}"})
	public void verify(@PathVariable(name = "userId") String userId, @PathVariable(name = "token") String token, HttpServletResponse response) throws IOException {
		var url = this.userService.verify(userId, token);
		response.sendRedirect(url);
	}
}
