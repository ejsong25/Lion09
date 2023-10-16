package com.lion09.member;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.lion09.board.Post;
import com.lion09.order.Order;
import com.lion09.pay.LionPayDTO;
import com.lion09.qaboard.QuestionDTO;

import lombok.Data;

@Data
@Entity
//도메인
public class Member {

	@Id
	private String userId;
	private String userName;
	private String nickName;
	private String userPwd;
	private String email;
	
	@Embedded
	private Address address;
	
	//주문 내역
	@OneToMany(mappedBy = "member")
	private List<Order> orders = new ArrayList<>();
	
	@OneToMany(mappedBy = "member")
	private List<Post> posts = new ArrayList<>();
	
	private String energy;
	private int userLike;
	private String profileImg;
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "member")
	private LionPayDTO lionPay;
	
	@OneToMany(mappedBy = "member")
	private List<QuestionDTO> qaboard = new ArrayList<>();
}
