package com.ssafy.home.user.dto.out;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponse {

	private int postId;
	private String aptSeq;
	private String name;
	private String title;
	private String content;
	private int isSecret;//(0: 공개글, 1: 비밀글)
	private String time;
	private int commentCnt;
	
	@Builder.Default
    private List<CommentResponse> comments = new ArrayList<>();
}
