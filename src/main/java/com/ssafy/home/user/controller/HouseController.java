package com.ssafy.home.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.response.ApiResponse;

@RestController
@RequestMapping("/house")
public class HouseController {

	@GetMapping
	public ResponseEntity<ApiResponse<Void>> list(){
		return ResponseEntity.ok(ApiResponse.success());
	}
}
