package com.orchestration.routingService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.orchestration.response.CommonRes;

@FeignClient(name = "WeCoreMotorApi")
public interface WeCoreMotorClient {
	@PostMapping("/api/slide/nonmotorsave")
	CommonRes nonMotorSave(@RequestHeader("Authorization") String weCoreToken, @RequestBody Object body);

}
