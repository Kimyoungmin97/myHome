package com.ssafy.home.user.controller;

import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.home.common.exception.CustomException;
import com.ssafy.home.common.exception.ErrorCode;
import com.ssafy.home.common.response.ApiResponse;
import com.ssafy.home.user.dto.User;
import com.ssafy.home.user.service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserServiceImpl userService;
	
	@PostMapping
    private ResponseEntity<ApiResponse> registMember(@RequestBody User user) {
        try {
        	userService.insert(user);
            return ResponseEntity.ok(ApiResponse.success(Map.of("user", user)));
        } catch (DataAccessException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
