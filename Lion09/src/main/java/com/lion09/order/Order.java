package com.lion09.order;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lion09.board.Post;
import com.lion09.member.Member;

import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderId")
	private Long id;
	private LocalDateTime orderDate; //주문 시간
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private Member member;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postId")
	private Post post;
	
	private int orderPrice;
	private int count;
	
	private String title;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status; //주문 상태[Complete, Canceled, Reserved]	
	
	//주문 생성 로직
	public static Order createOrder(Member member, Post post) {
		Order order = new Order();
		order.setMember(member);
		order.setPost(post);
		order.setStatus(OrderStatus.Reserved);
		order.setOrderDate(LocalDateTime.now());
		
		return order;
	}
	
	//거래 취소 로직
//	public void cancel() {
//		if(getStatus() == OrderStatus.Complete) {
//			throw new IllegalStateException("이미 거래 완료된 상품입니다.");
//		}
//		this.setStatus(OrderStatus.Canceled);
//		재고 업데이트
//	}
	
	//가격 조회 로직
	public int getTotalPrice() {
		int totalPrice = 0;
		
		return totalPrice;
	}
	
	
	public String getUserId() {
	    return member.getUserId();
	}
	
	public int getPostId() {
	    return post.getPostId();
	}
	
	public String getStatus() {
	    if (status != null) {
	        return status.name(); // 혹은 status.toString()
	    } else {
	        return "Status is null"; // 또는 다른 적절한 처리
	    }
	}

	public Long getOrderId() {
	    return this.id;
	}


	
}
