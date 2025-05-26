package com.ssafy.home.user.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertBoardRequset {
	
	private String aptSeq;
	private String userId;
	private String title;
	private String content;
	private int isSecret;//(0: 공개글, 1: 비밀글)
}
