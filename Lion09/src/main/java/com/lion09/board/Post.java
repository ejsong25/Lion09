package com.lion09.board;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.lion09.member.Member;

import lombok.Data;

@Data
@Entity
public class Post {
	@Id
	private int postId;
	
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
	
	private LocalDateTime created;
	private LocalDateTime deadLine;
	
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

	private LocalDateTime likeDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Member member;
}
