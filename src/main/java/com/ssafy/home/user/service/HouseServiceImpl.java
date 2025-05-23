package com.ssafy.home.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.home.user.dao.HouseDao;
import com.ssafy.home.user.dto.in.HouseRequest;
import com.ssafy.home.user.dto.out.HouseDetailResponse;
import com.ssafy.home.user.dto.out.HouseResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService{
	
	private final HouseDao dao;
	private final RedisService redisService;
	
	/**
	 * 키워드 검색
	 * @param HouseRequest
	 * @return List
	 */
	@Override
	public List<HouseResponse> searchByKeyword(HouseRequest house) {
		List<HouseResponse> list = dao.searchByKeyword(house);
		// Redis에 검색 키워드 저장
        if (house.getKeyword() != null && !house.getKeyword().isBlank() && list.size() > 0) {
        	redisService.saveKeyword(house.getKeyword());
        }
		return list;
	}
	
	/**
	 * 실거래 조회
	 * @param aptSeq
	 * @return List
	 */
	@Override
	public List<HouseDetailResponse> selectDealsByAptSeq(String aptSeq){
		return dao.selectDealsByAptSeq(aptSeq);
	}
	
}
