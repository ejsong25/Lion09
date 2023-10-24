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
	
	private String userId;
	
	private int postId;
	
	private int orderPrice;
	private int count;
	
	private String title;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status; //주문 상태[Complete, Canceled, Reserved]	
	
	
}