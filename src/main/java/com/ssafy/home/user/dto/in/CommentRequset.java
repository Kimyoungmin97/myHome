package com.ssafy.home.user.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class CommentRequset {
	
	private int parentId;
	private String content;
}
