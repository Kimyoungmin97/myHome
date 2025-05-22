package com.ssafy.home.user.service;

import java.util.Set;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchKeywordService {

	private final StringRedisTemplate redisTemplate;
	
	private static final String KEY = "search:keywords";

	// 검색어 저장 (점수 증가)
    public void saveKeyword(String keyword) {
        redisTemplate.opsForZSet().incrementScore(KEY, keyword, 1);
    }

    // 인기 검색어 TOP N 가져오기
    public Set<ZSetOperations.TypedTuple<String>> getTopKeywords(int topN) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(KEY, 0, topN - 1);
    }
	
}
