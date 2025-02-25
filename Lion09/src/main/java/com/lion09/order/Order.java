package com.lion09.order;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "orderId")
	private Long id;
	private LocalDate orderDate; //주문 시간
	
	private String userId;
	
	private int postId;
	
	private int orderPrice;
	private int count;
	
	private String title;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status; //주문 상태[Complete, Canceled, Reserved]
	
	private String type; // 페이결제는 L, 직거래는 M
	
}