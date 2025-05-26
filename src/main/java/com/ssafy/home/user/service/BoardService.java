package com.ssafy.home.user.service;

import java.util.List;

import com.ssafy.home.user.dto.in.BoardRequest;
import com.ssafy.home.user.dto.in.CommentRequset;
import com.ssafy.home.user.dto.in.InsertBoardRequset;
import com.ssafy.home.user.dto.out.BoardResponse;

public interface BoardService {

	public List<BoardResponse> getBoardList(BoardRequest board);
	
	public BoardResponse getBoardDetail(int postId); 
	
	public void insertBoard(InsertBoardRequset board, String userId);
	
	public void updateBoard(InsertBoardRequset board, int postId, String userId);
	
	public void deleteBoard(int postId);
	
	public void deleteAllCommentsByPostId(int postId);
	
	public void insertComment(CommentRequset comment, int postId, String userId);
	
	public void updateComment(int postId, String conent, int commentId);
	
	public void deleteComment(int postId, int commentId);
}
