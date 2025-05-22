package com.ssafy.home.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.ZSetOperations;
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
import com.ssafy.home.user.service.SearchKeywordService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/houses")
@RequiredArgsConstructor
public class HouseController {
	
	private final HouseServiceImpl service;
	private final SearchKeywordService keywordService;

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
			return ResponseEntity.ok(ApiResponse.success(lsit));
		} catch (Exception e) {
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/search/{aptSeq}/deals")
	public ResponseEntity<ApiResponse<List>> dealsByAptSeq(@PathVariable String aptSeq){
		List<HouseDetailResponse> lsit = new ArrayList<>();
		try {
			lsit = service.selectDealsByAptSeq(aptSeq);
			return ResponseEntity.ok(ApiResponse.success(lsit));
		} catch (Exception e) {
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/search/popular")
    public ResponseEntity<ApiResponse<List<String>>> popularKeywords() {
        var redisResults = keywordService.getTopKeywords(10);
        List<String> keywords = redisResults.stream()
                .map(ZSetOperations.TypedTuple::getValue)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(keywords));
    }

}
