package com.orchestration.routingService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "User-Service")
public interface UserClient {
	
	@GetMapping("/users/test")
	public ResponseEntity<String> testEndpoint() ;
	

	@PostMapping("/users/stringApi")
	public ResponseEntity<String> testEndpoint(@RequestBody String api) ;
}