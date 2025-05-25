package com.ssafy.home.user.service;

import java.util.List;

import com.ssafy.home.user.dto.in.BoardRequest;
import com.ssafy.home.user.dto.out.BoardResponse;

public interface BoardService {

	public List<BoardResponse> getBoardList(BoardRequest board);
}
