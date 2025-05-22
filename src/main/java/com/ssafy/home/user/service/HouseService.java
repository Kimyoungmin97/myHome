package com.ssafy.home.user.service;

import java.util.List;

import com.ssafy.home.common.page.PageRequestDto;
import com.ssafy.home.user.dto.in.HouseRequest;
import com.ssafy.home.user.dto.out.HouseDetailResponse;
import com.ssafy.home.user.dto.out.HouseResponse;

public interface HouseService {
	
	/**
	 * 키워드 검색
	 * @param HouseRequest
	 * @return List
	 */
	public List<HouseResponse> searchByKeyword(HouseRequest house);
	
	/**
	 * 실거래 조회
	 * @param aptSeq
	 * @return List
	 */
	public List<HouseDetailResponse> selectDealsByAptSeq(String aptSeq);
	
}
