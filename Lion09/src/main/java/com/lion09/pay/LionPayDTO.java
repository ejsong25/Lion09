package com.lion09.pay;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lion09.member.Member;

import lombok.Data;

@Table(name = "lionPay")
@Entity
@Data
public class LionPayDTO {
	@Id @GeneratedValue
	@Column(name = "lionpay_id")
	private Long id;
	
	private String accountNum;
	private String accountName;
	private String payPwd;
	private int balance;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	Member member;
//	private String userId;
	private int rechargeAmount;
}
