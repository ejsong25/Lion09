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

import org.hibernate.annotations.ColumnDefault;

import com.lion09.board.Post;
import com.lion09.board.PostLike;
import com.lion09.order.Order;
import com.lion09.pay.LionPayDTO;
import com.lion09.pay.ListDTO;
import com.lion09.qaboard.QuestionDTO;

import lombok.Data;

@Data
@Entity
//도메인- 개인 정보
public class Member {

	@Id
	private String userId;
	private String userName;
	private String userPwd;
	private String email;
	
	//마이페이지 정보
	private String nickName;
	private int userLike;
	private int energy;
	
	private String myAddress; //동
	private String myLatitude; //위도
	private String myLongitude; //경도
	
	//프로필 사진
	private String profileImgName;
	
	//주소 반경
	private int findLocationsNearby;
	private int myRange;
	
	//주문 내역
//	@OneToMany(mappedBy = "member")
//	private List<Order> orders = new ArrayList<>();
	
	//작성 글
//	@OneToMany(mappedBy = "writer")
//	private List<Post> posts = new ArrayList<>();
	
	//라이온페이
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "member")
	private LionPayDTO lionPay;
		
	//문의 내역
	@OneToMany(mappedBy = "member")
	private List<QuestionDTO> qaboard = new ArrayList<>();
	
	@OneToMany
	private List<PostLike> postLike = new ArrayList<>();
}
