package com.ssafy.home.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.exception.CustomException;
import com.ssafy.home.common.exception.ErrorCode;
import com.ssafy.home.common.response.ApiResponse;
import com.ssafy.home.user.dto.in.HouseRequest;
import com.ssafy.home.user.dto.out.HouseDetailResponse;
import com.ssafy.home.user.dto.out.HouseResponse;
import com.ssafy.home.user.service.HouseServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/houses")
@RequiredArgsConstructor
public class HouseController {
	
	private final HouseServiceImpl service;

	/**
	 * 집 검색
	 * @param house
	 * @return
	 */
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<List>> search(
			@ModelAttribute HouseRequest house){
		List<HouseResponse> lsit = new ArrayList<>();
		try {
			lsit = service.searchByKeyword(house);
		} catch (Exception e) {
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(ApiResponse.success(lsit));
	}
	
	@GetMapping("/search/{aptSeq}/deals")
	public ResponseEntity<ApiResponse<List>> dealsByAptSeq(@PathVariable String aptSeq){
		List<HouseDetailResponse> lsit = new ArrayList<>();
		try {
			lsit = service.selectDealsByAptSeq(aptSeq);
		} catch (Exception e) {
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(ApiResponse.success(lsit));
	}
}
