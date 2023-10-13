package com.lion09.board;

import lombok.Data;

@Data
public class PostLikeDTO {

	private String userId;
	private int postId;
	private String likeDate;
	private int likeState;
	
}
