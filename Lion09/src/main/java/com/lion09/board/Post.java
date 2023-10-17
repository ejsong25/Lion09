package com.lion09.board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.lion09.member.Member;
import com.lion09.order.Order;

import lombok.Data;

@Data
@Entity
public class Post {
	@Id
	private int postId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nick_name")
	private Member writer;
	
	@Column(length = 40)
	private String title;
	
	@Column(length = 20)
	private String status; //[recruit, done]
	
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
	
//	@Column(length = 20)
//	private String type;
	
	@Column(length = 10)
	private int categoryId;
	
	@Column(length = 100)
	private String myAddr;

	private LocalDateTime likeDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private Member member; //글 작성자 id
	
	@OneToMany(mappedBy = "post")
	private List<Order> orders = new ArrayList<>();
	
	//채팅
//	@OneToMany
}
