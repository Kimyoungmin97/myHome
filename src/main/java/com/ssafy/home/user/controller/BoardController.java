package com.ssafy.home.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.common.exception.CustomException;
import com.ssafy.home.common.exception.ErrorCode;
import com.ssafy.home.common.response.ApiResponse;
import com.ssafy.home.security.dto.CustomUserDetails;
import com.ssafy.home.user.dto.in.BoardRequest;
import com.ssafy.home.user.dto.in.CommentRequset;
import com.ssafy.home.user.dto.out.BoardResponse;
import com.ssafy.home.user.service.BoardServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {
	
	private final BoardServiceImpl service;
	
	@GetMapping
	public ResponseEntity<ApiResponse<List>> getBoardList(
			@ModelAttribute BoardRequest board
			){
		List<BoardResponse> lsit = new ArrayList<>();
		try {
			lsit = service.getBoardList(board);
			return ResponseEntity.ok(ApiResponse.success(lsit));
		} catch (Exception e) {
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{postId}/detail")
	public ResponseEntity<ApiResponse<BoardResponse>> getBoardDetail(
			@PathVariable int postId
			){
		BoardResponse result;
		try {
			result = service.getBoardDetail(postId);
			return ResponseEntity.ok(ApiResponse.success(result));
		} catch (Exception e) {
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/{postId}")
	public ResponseEntity<ApiResponse<Void>> insertComment(
			@ModelAttribute CommentRequset comment,
			@PathVariable int postId,
			@AuthenticationPrincipal CustomUserDetails details
			){
		try {
			service.insertComment(comment, postId, details.getUsername());
			return ResponseEntity.ok(ApiResponse.success());
		} catch (Exception e) {
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/{postId}/{commentId}")
	public ResponseEntity<ApiResponse<Void>> updateComment(
			@PathVariable int postId,
			@PathVariable int commentId,
			@RequestParam String connent
		){
	try {
		service.updateComment(postId, connent, commentId);
		return ResponseEntity.ok(ApiResponse.success());
	} catch (Exception e) {
		throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
	}
}
	
	@DeleteMapping("/{postId}/{commentId}")
	public ResponseEntity<ApiResponse<Void>> deleteComment(
				@PathVariable int postId,
				@PathVariable int commentId
			){
		try {
			service.deleteComment( postId, commentId);
			return ResponseEntity.ok(ApiResponse.success());
		} catch (Exception e) {
			throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}
}
