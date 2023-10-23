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
import javax.persistence.OneToOne;

import com.lion09.chat.ChatDTO;
import com.lion09.chat.ChatRoomDTO;
import com.lion09.member.Member;
import com.lion09.order.Order;
import com.lion09.pay.LionPayDTO;

import lombok.Data;

@Data
@Entity
public class Post {
	@Id
	private int postId;
	
//	//작성자
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "nickName")
//	private Member writer;

	private String nickName;
	
	private String userId;
	
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
	
	@Column(length = 5)
	private int participant;
	
//	@Column(length = 20)
//	private String type;
	
	@Column(length = 10)
	private int categoryId;
	
	@Column(length = 100)
	private String myAddr;

	private LocalDateTime likeDate;
	
	//주문 리스트
	@OneToMany(mappedBy = "post")
	private List<Order> orders = new ArrayList<>();
	
	//관심
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "post")
	private PostLike postLike;
	
	//채팅
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "post")
	private ChatRoomDTO chatRoom;
}
