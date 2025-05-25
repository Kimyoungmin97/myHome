package com.ssafy.home.user.dto.out;

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
	private String username;
	private String title;
	private String content;
	private boolean isSecret;
	
}
