package com.ssafy.home.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.home.user.dao.BoardDao;
import com.ssafy.home.user.dao.UserDao;
import com.ssafy.home.user.dto.User;
import com.ssafy.home.user.dto.in.BoardRequest;
import com.ssafy.home.user.dto.in.CommentRequset;
import com.ssafy.home.user.dto.in.InsertBoardRequset;
import com.ssafy.home.user.dto.out.BoardResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	private final BoardDao dao;
	private final UserDao userDao;

	@Override
	public List<BoardResponse> getBoardList(BoardRequest board) {
		return dao.getBoardList(board);
	}

	@Override
	public BoardResponse getBoardDetail(int postId) {
		return dao.getBoardDetail(postId);
	}

	@Override
	public void insertBoard(InsertBoardRequset board) {
		dao.insertBoard(board);
	}

	@Override
	public void updateBoard(InsertBoardRequset board, int postId) {
		dao.updateBoard(board, postId);
	}

	@Override
	public void deleteBoard(int postId) {
		dao.deleteBoard(postId);
	}

	@Override
	public void deleteAllCommentsByPostId(int postId) {
		dao.deleteAllCommentsByPostId(postId);
	}

	@Override
	public void insertComment(CommentRequset comment, int postId, String userId) {
		User user = userDao.select(userId);
		dao.insertComment(comment, postId, user.getUserId());
	}

	@Override
	public void updateComment(int postId, String conent, int commentId) {
		dao.updateComment(postId, conent, commentId);
	}

	@Override
	public void deleteComment(int postId, int commentId) {
		dao.deleteComment(postId, commentId);
	}	

}
