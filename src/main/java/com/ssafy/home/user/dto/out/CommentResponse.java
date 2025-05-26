package com.ssafy.home.user.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {

	private int commentId;
	private String userId;
	private String name;
	private int parentId;
	private String commentContent;
    private String createdAt; 
    private int depth;
}
