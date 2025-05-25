package com.ssafy.home.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.exception.CustomException;
import com.ssafy.home.common.exception.ErrorCode;
import com.ssafy.home.common.response.ApiResponse;
import com.ssafy.home.user.dto.in.BoardRequest;
import com.ssafy.home.user.dto.out.HouseResponse;
import com.ssafy.home.user.service.BoardServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {
	
	private final BoardServiceImpl service;
	
	@GetMapping
	public ResponseEntity<ApiResponse<List>> getBoardList(
			@ModelAttribute BoardRequest board){
		List<HouseResponse> lsit = new ArrayList<>();
		try {
//			lsit = service.getBoardList(board);
			return ResponseEntity.ok(ApiResponse.success(lsit));
		} catch (Exception e) {
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}

}
