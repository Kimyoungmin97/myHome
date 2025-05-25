package com.ssafy.home.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.home.user.dao.BoardDao;
import com.ssafy.home.user.dto.in.BoardRequest;
import com.ssafy.home.user.dto.out.BoardResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	private final BoardDao dao;

	@Override
	public List<BoardResponse> getBoardList(BoardRequest board) {
		return dao.getBoardList(board);
	}

}
