package com.lion09.board;

import javax.persistence.Column;
import javax.persistence.Id;

import lombok.Data;

@Data
public class Post {

	@Id
	private int postId;
	
	@Column(length = 16)
	private String nickName;
	
	@Column(length = 40)
	private String title;
	
	@Column(length = 20)
	private String status;
	
	@Column(length = 7)
	private int productsPrice;
	
	@Column(length = 7)
	private int hitCount;
	
	@Column(columnDefinition = "TEXT")
	private String contents;
	
	private String created;
	private String deadLine;
	
	@Column(length = 999)
	private String chooseFile;
	
	@Column(length = 10)
	private int postLikeCount;
	
	@Column(length = 5)
	private int recruitment;
	
	@Column(length = 20)
	private String type;
	
	@Column(length = 10)
	private int categoryId;
	
	@Column(length = 100)
	private String myAddr;
	
	@Column(length = 20)
	private String userId;

	private String likeDate;


	
	
}
