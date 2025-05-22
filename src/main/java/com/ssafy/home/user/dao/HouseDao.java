package com.ssafy.home.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.common.page.PageRequestDto;
import com.ssafy.home.user.dto.in.HouseRequest;
import com.ssafy.home.user.dto.out.HouseDetailResponse;
import com.ssafy.home.user.dto.out.HouseResponse;

@Mapper
public interface HouseDao {

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
