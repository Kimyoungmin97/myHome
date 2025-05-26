package com.ssafy.home.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.user.dto.in.BoardRequest;
import com.ssafy.home.user.dto.in.CommentRequset;
import com.ssafy.home.user.dto.in.InsertBoardRequset;
import com.ssafy.home.user.dto.out.BoardResponse;

@Mapper
public interface BoardDao {
	
	public List<BoardResponse> getBoardList(BoardRequest board);
	
	public BoardResponse getBoardDetail(int postId);
	
	public void insertBoard(InsertBoardRequset board);
	
	public void updateBoard(InsertBoardRequset board, int postId);
	
	public void deleteBoard(int postId);
	
	public void deleteAllCommentsByPostId(int postId);
	
	public void insertComment(CommentRequset comment, int postId, int userId);
	
	public void updateComment(int postId, String conent, int commentId);
	
	public void deleteComment(int postId, int commentId);

}
