package com.ssafy.home.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.user.dto.in.BoardRequest;
import com.ssafy.home.user.dto.out.BoardResponse;

@Mapper
public interface BoardDao {
	
	public List<BoardResponse> getBoardList(BoardRequest board);

}
