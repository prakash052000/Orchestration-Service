package com.orchestration.userController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orchestration.routingService.UserClient;
@RestController
@RequestMapping("/test")
public class Test {
	@Autowired
	private UserClient userclient;

	@GetMapping("/user")
	public ResponseEntity<String> getUser() {
		return userclient.testEndpoint();
	}
}
