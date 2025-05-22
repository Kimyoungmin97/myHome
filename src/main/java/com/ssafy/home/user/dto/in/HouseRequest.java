package com.ssafy.home.user.dto.in;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HouseRequest {
	
	private String keyword;         // 검색어
    private String lastAptSeq;      // 마지막 조회한 apt_seq (ex: "11110-2109")
    private int size = 10;          // 한 번에 불러올 개수

}
